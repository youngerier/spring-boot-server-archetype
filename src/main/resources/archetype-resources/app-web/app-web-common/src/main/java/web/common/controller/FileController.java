#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.controller;

import ${package}.api.system.modal.dto.FileBaseDTO;
import ${package}.api.system.modal.request.FilePreviewRequest;
import ${package}.api.system.modal.request.UploadFileRequest;
import ${package}.api.system.service.FileService;
import ${package}.infra.business.Result;
import ${package}.web.common.constants.WebConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 19:03
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.COMMON_API_V1_PREFIX + "/file")
@Tag(name = "文件服务接口", description = "文件服务接口")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件", description = "上传文件")
    public Result<FileBaseDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                          UploadFileRequest request) {
        return Result.ok(fileService.uploadFile(file, request));
    }

    @PostMapping("/upload/hash")
    @Operation(summary = "上传文件并返回文件hash值", description = "上传文件并返回文件hash值")
    public Result<FileBaseDTO> uploadFileHash(@RequestParam("file") MultipartFile file,
                                              UploadFileRequest request) {
        return Result.ok(fileService.uploadFileHash(file, request));
    }

    @PostMapping("/upload/image")
    @Operation(summary = "上传图片", description = "上传图片")
    public Result<FileBaseDTO> uploadImage(@RequestParam("file") MultipartFile file,
                                           UploadFileRequest request) {
        return Result.ok(fileService.uploadImage(file, request));
    }

    @PostMapping("/presigned-url")
    @Operation(summary = "获取文件上传预签名URL", description = "获取文件上传预签名URL")
    public Result<String> getPresignedUrl(@RequestBody FilePreviewRequest request) {
        return Result.ok(fileService.getPresignedUrl(request));
    }
}
