#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.service;

import ${package}.api.system.modal.dto.ExportNoUploadDTO;
import ${package}.api.system.modal.dto.FileBaseDTO;
import ${package}.api.system.modal.request.FilePreviewRequest;
import ${package}.api.system.modal.request.UploadFileRequest;
import ${package}.infra.toolkits.exception.CustomerException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.function.Consumer;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 19:08
 * @Description: 文件服务接口
 **/
public interface FileService {
    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件基础信息
     */
    FileBaseDTO uploadFile(MultipartFile file, UploadFileRequest request);

    /**
     * 上传文件并返回文件hash值
     *
     * @param file 文件
     * @return 文件基础信息
     */
    FileBaseDTO uploadFileHash(MultipartFile file, UploadFileRequest request);

    /**
     * 上传图片
     *
     * @param file 文件
     * @return 文件基础信息
     */
    FileBaseDTO uploadImage(MultipartFile file, UploadFileRequest request);

    /**
     * 获取文件上传预签名URL
     *
     * @return 预签名URL
     */
    String getPresignedUrl(FilePreviewRequest request);

    /**
     * 下载文件
     * @param url 文件地址
     */
    byte[] downloadFile(String url);

    /**
     * 下载文件
     * @param url 文件地址
     * @param consumer 输入流消费者
     */
    void downloadFile(String url, Consumer<InputStream> consumer);

    /**
     * 流式上传
     * @param tenantId 租户id
     * @param fileUrl     文件链接
     * @param inputStream 文件流
     * @return 文件链接
     * @throws CustomerException 异常
     */
    String putObjectRequest(String tenantId, String fileUrl, InputStream inputStream) throws CustomerException;

    /**
     * 指定文件下面所有的文件打成zip的包
     *
     * @param exportNoUploadList 当前文件夹路径
     * @param zipFileName        输出的 ZIP 文件名
     * @param tenantId 租户id
     * @return zip 下载路径
     * @throws CustomerException ClientException
     */
    String buildZip(List<ExportNoUploadDTO> exportNoUploadList, String zipFileName, String tenantId) throws CustomerException;

    /**
     * 指定文件下面所有的文件打成zip的包
     *
     * @param exportNoUploadList 当前文件夹路径
     * @param zipFileName        输出的 ZIP 文件名
     * @param tenantId 租户id
     * @return zip 下载路径
     * @throws CustomerException ClientException
     */
    String buildZipFileName(List<ExportNoUploadDTO> exportNoUploadList, String zipFileName, String tenantId) throws CustomerException;

    /**
     * @param fileUrl 下载文件
     * @return 文件流
     */
    ByteArrayOutputStream dealUrlToStream(String fileUrl);
}
