#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.service.impl;

import com.aliyuncs.exceptions.ClientException;

import ${package}.api.system.modal.dto.FileBaseDTO;
import ${package}.api.system.modal.request.FilePreviewRequest;
import ${package}.api.system.modal.request.UploadFileRequest;
import ${package}.api.system.service.FileService;
import ${package}.biz.system.dal.entity.ClientFile;
import ${package}.biz.system.dal.repository.ClientFileRepository;
import ${package}.infra.business.context.CurrentUserContext;
import ${package}.api.system.modal.dto.ExportNoUploadDTO;
import ${package}.infra.toolkits.exception.CustomerException;
import ${package}.infra.toolkits.encrypt.Md5Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.x.file.storage.core.Downloader;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.hash.HashInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.function.Consumer;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 19:10
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileStorageService fileStorageService;

    private final ClientFileRepository clientFileRepository;

    public static final String name = "temp/zip/压缩包.zip";
    public static final String LOCAL_PATH = "/temp/zip";

    @Override
    public FileBaseDTO uploadFile(MultipartFile file, UploadFileRequest request) {
        Long userId = CurrentUserContext.getUserId();
        FileInfo fileInfo = fileStorageService
                .of(file)
                .setPath(getTenantPath(request.getTenantId()))
                .upload();

        if (fileInfo == null) {
            throw new CustomerException("文件上传失败");
        }

        clientFileRepository.save(buildClientFile(fileInfo, userId));
        return buildFileBaseDTO(fileInfo, userId);
    }


    @Override
    public FileBaseDTO uploadFileHash(MultipartFile file, UploadFileRequest request) {
        Long userId = CurrentUserContext.getUserId();
        FileInfo fileInfo = fileStorageService
                .of(file)
                .setPath(getTenantPath(request.getTenantId()))
                .setHashCalculatorMd5().upload();

        validateFileHash(file, fileInfo);

        clientFileRepository.save(buildClientFile(fileInfo, userId));
        return buildFileBaseDTO(fileInfo, userId);
    }

    private void validateFileHash(MultipartFile file, FileInfo fileInfo) {
        try {
            HashInfo hashInfo = fileInfo.getHashInfo();
            String calculatedMd5 = Md5Util.md5Hex(file.getBytes());
            if (!calculatedMd5.equals(hashInfo.getMd5())) {
                throw new CustomerException("文件 MD5 对比不一致！");
            }
        } catch (IOException e) {
            throw new CustomerException("文件 MD5 对比不一致");
        }
    }


    @Override
    public FileBaseDTO uploadImage(MultipartFile file, UploadFileRequest request) {
        Long userId = CurrentUserContext.getUserId();
        FileInfo fileInfo = fileStorageService
                .of(file)
                .setPath(getTenantPath(request.getTenantId()))
                .image(img -> img.size(
                        Objects.requireNonNullElse(request.getImageWidthSize(), 1000),
                        Objects.requireNonNullElse(request.getImageHeightSize(), 1000)))
                .thumbnail(th -> th.size(
                        Objects.requireNonNullElse(request.getThumbnailWidthSize(), 200),
                        Objects.requireNonNullElse(request.getThumbnailHighSize(), 200)))
                .upload();

        if (fileInfo == null) {
            throw new CustomerException("图片上传失败");
        }

        clientFileRepository.save(buildClientFile(fileInfo, userId));
        return buildFileBaseDTO(fileInfo, userId);
    }

    private FileBaseDTO buildFileBaseDTO(FileInfo fileInfo, Long userId) {
        return FileBaseDTO.builder()
                .fileName(fileInfo.getFilename())
                .originalFileName(fileInfo.getOriginalFilename())
                .fileType(fileInfo.getContentType())
                .fileSize(fileInfo.getSize())
                .fileUrl(fileInfo.getUrl())
                .userId(userId)
                .build();
    }

    private ClientFile buildClientFile(FileInfo fileInfo, Long userId) {
        return ClientFile.builder()
                .url(fileInfo.getUrl())
                .userId(userId)
                .size(fileInfo.getSize())
                .filename(fileInfo.getFilename())
                .originalFilename(fileInfo.getOriginalFilename())
                .ext(fileInfo.getExt())
                .uploadId(fileInfo.getUploadId())
                .build();
    }


    @Override
    public String getPresignedUrl(FilePreviewRequest request) {
        if (StringUtils.isBlank(request.getUrl())) {
            throw new CustomerException("文件URL不能为空");
        }
        // 获取文件信息
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(request.getUrl());
        if (fileInfo == null) {
            throw new CustomerException("文件不存在");
        }
        // 生成预签名URL
        if (request.getExpire() == null || request.getExpire() <= 0) {
            request.setExpire(60 * 12 * 60L);
        }
        return fileStorageService.generatePresignedUrl(fileInfo, new Date(System.currentTimeMillis() + request.getExpire() * 1000));
    }

    @Override
    public byte[] downloadFile(String url) {
        if (StringUtils.isBlank(url)) {
            throw new CustomerException("文件URL不能为空");
        }
        Downloader download = fileStorageService.download(url);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        download.outputStream(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public void downloadFile(String url, Consumer<InputStream> consumer) {
        if (StringUtils.isBlank(url)) {
            throw new CustomerException("文件URL不能为空");
        }
        Downloader download = fileStorageService.download(url);
        download.inputStream(consumer);
    }


    /**
     * 流式上传
     *
     * @return Boolean
     */
    @Override
    public String putObjectRequest(String tenantId, String fileUrl, InputStream inputStream) throws CustomerException {
        try {
            // 构建文件存储上传对象
            FileInfo fileInfo = fileStorageService
                    .of(inputStream)
                    .setPath(getTenantPath(tenantId))
                    .setSaveFilename(fileUrl)
                    .upload();
            if (fileInfo == null || StringUtils.isBlank(fileInfo.getUrl())) {
                throw new CustomerException("文件上传失败");
            }
            log.info("文件上传成功: {}", fileInfo.getUrl());
            return fileInfo.getUrl();
        } catch (Exception e) {
            log.error("文件上传异常: {}", e.getMessage(), e);
            throw new CustomerException("文件上传失败: " + e.getMessage());
        }
    }


    @Override
    public String buildZip(List<ExportNoUploadDTO> exportNoUploadList, String zipFileName, String tenantId) {
        String url = null;
        try {
            downloadFilesAndCreateZip(exportNoUploadList);
            url = uploadZipToOSS(zipFileName, tenantId);
            log.info("压缩包上传成功！");
        } catch (ClientException | IOException e) {
            log.error("上传压缩包失败:{}", e.getMessage());
        } finally {
            // 删除本地生成的压缩包
            Path path = Paths.get(name);
            try {
                Files.delete(path);
            } catch (IOException e) {
                log.error("删除文件异常", e);
            }
        }
        return url;
    }

    @Override
    public String buildZipFileName(List<ExportNoUploadDTO> exportNoUploadList, String zipFileName, String tenantId) {
        String url = null;
        try {
            downloadFilesAndCreateZipFileName(exportNoUploadList);
            url = uploadZipToOSS(zipFileName, tenantId);
            log.info("压缩包上传成功！");
        } catch (ClientException | IOException e) {
            log.error("上传压缩包失败:{}", e.getMessage());
        } finally {
            // 删除本地生成的压缩包
            Path path = Paths.get(name);
            try {
                Files.delete(path);
            } catch (IOException e) {
                log.error("删除文件异常", e);
            }
        }
        return url;
    }

    public String uploadZipToOSS(String zipFilePath, String tenantId) throws ClientException {
        try (InputStream inputStream = new FileInputStream(name)) {
            return putObjectRequest(tenantId, zipFilePath, inputStream);
        } catch (IOException e) {
            log.error("上传文件异常:{}", e.getMessage());
        }
        return null;
    }

    public void downloadFilesAndCreateZip(List<ExportNoUploadDTO> exportNoUploadList) throws IOException {
        String currentWorkingDir = System.getProperty("user.dir");
        // 创建临时文件夹
        File tempDir = new File(currentWorkingDir + LOCAL_PATH);
        if (!tempDir.exists()) {
            boolean mkdir = tempDir.mkdirs();
            log.info("创建临时文件夹：{},{}", LOCAL_PATH, mkdir);
        }
        try (FileOutputStream fos = new FileOutputStream(name);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (ExportNoUploadDTO exportNoUploadDTO : exportNoUploadList) {
                String fileName = exportNoUploadDTO.getUploadUrl().substring(exportNoUploadDTO.getUploadUrl().lastIndexOf("/") + 1);
                File tempFile = new File(tempDir, fileName);

                try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                    ByteArrayOutputStream stream = exportNoUploadDTO.getStream();
                    // 将 ByteArrayOutputStream 中的数据写入临时文件
                    stream.writeTo(fileOutputStream);
                } catch (IOException e) {
                    log.error("转换流为临时文件时出错：{}", e.getMessage());
                }

                // 将文件写入压缩包
                addFileToZip(tempFile, fileName, zos);

                // 删除临时文件
                Path path = Paths.get(tempFile.getPath());
                Files.delete(path);
                log.info("删除临时文件：{}", tempFile.getPath());
            }
        }
    }

    public void downloadFilesAndCreateZipFileName(List<ExportNoUploadDTO> exportNoUploadList) throws IOException {
        String currentWorkingDir = System.getProperty("user.dir");
        // 创建临时文件夹
        File tempDir = new File(currentWorkingDir + LOCAL_PATH);
        if (!tempDir.exists()) {
            boolean mkdir = tempDir.mkdirs();
            log.info("创建临时文件夹：{},{}", LOCAL_PATH, mkdir);
        }
        try (FileOutputStream fos = new FileOutputStream(name);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (ExportNoUploadDTO exportNoUploadDTO : exportNoUploadList) {
                String fileName = exportNoUploadDTO.getFileName();
                File tempFile = new File(tempDir, fileName);

                try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                    ByteArrayOutputStream stream = exportNoUploadDTO.getStream();
                    // 将 ByteArrayOutputStream 中的数据写入临时文件
                    stream.writeTo(fileOutputStream);
                } catch (IOException e) {
                    log.error("转换流为临时文件时出错：{}", e.getMessage());
                }

                // 将文件写入压缩包
                addFileToZip(tempFile, fileName, zos);

                // 删除临时文件
                Path path = Paths.get(tempFile.getPath());
                Files.delete(path);
                log.info("删除临时文件：{}", tempFile.getPath());
            }
        }
    }

    public void addFileToZip(File file, String fileName, ZipOutputStream zos) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            zos.putNextEntry(new ZipEntry(fileName));
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, bytesRead);
            }
            zos.closeEntry();
        }
    }


    @Override
    public ByteArrayOutputStream dealUrlToStream(String fileUrl) {
        URL url;
        try {
            url = new URL(fileUrl);
        } catch (MalformedURLException e) {
            throw new CustomerException("下载文件异常 + " + e.getMessage());
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (InputStream in = url.openStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new CustomerException("下载文件异常 + " + e.getMessage());
        }
        return outputStream;
    }

    public String getTenantPath(String tenantId) {
        String path = StringUtils.isNotBlank(tenantId) ? tenantId : CurrentUserContext.getTenantId().toString();
        return path + "/";
    }
}
