#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.repository;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import ${package}.biz.system.dal.entity.FilePartDetail;
import ${package}.biz.system.dal.mapper.FilePartDetailMapper;
import lombok.SneakyThrows;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 19:01
 **/
@Repository
public class FilePartDetailRepository extends ServiceImpl<FilePartDetailMapper, FilePartDetail> {


    /**
     * 保存文件分片信息
     *
     * @param info 文件分片信息
     */
    @SneakyThrows
    public void saveFilePart(FilePartInfo info) {
        FilePartDetail detail = toFilePartDetail(info);
        if (save(detail)) {
            info.setId(detail.getId());
        }
    }

    /**
     * 删除文件分片信息
     */
    public void deleteFilePartByUploadId(String uploadId) {
        QueryWrapper queryWrapper = QueryWrapper.create().eq(FilePartDetail.COL_UPLOAD_ID, uploadId);
        remove(queryWrapper);
    }


    /**
     * 将 FilePartInfo 转成 FilePartDetail
     *
     * @param info 文件分片信息
     */
    public FilePartDetail toFilePartDetail(FilePartInfo info) throws JsonProcessingException {
        FilePartDetail detail = new FilePartDetail();
        detail.setPlatform(info.getPlatform());
        detail.setUploadId(info.getUploadId());
        detail.setETag(info.getETag());
        detail.setPartNumber(info.getPartNumber());
        detail.setPartSize(info.getPartSize());
        detail.setHashInfo(JSON.toJSONString(info.getHashInfo()));
        detail.setCreateTime(info.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return detail;
    }
}
