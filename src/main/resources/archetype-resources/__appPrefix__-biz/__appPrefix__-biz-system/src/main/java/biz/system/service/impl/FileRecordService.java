#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.query.QueryWrapper;
import ${package}.biz.system.dal.entity.FileDetail;
import ${package}.biz.system.dal.repository.FileDetailRepository;
import ${package}.biz.system.dal.repository.FilePartDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.hash.HashInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 19:29
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class FileRecordService implements FileRecorder {

    private final FileDetailRepository fileDetailRepository;

    private final FilePartDetailRepository filePartDetailRepository;

    @SneakyThrows
    @Override
    public boolean save(FileInfo info) {
        FileDetail detail = toFileDetail(info);
        boolean b = fileDetailRepository.save(detail);
        if (b) {
            info.setId(String.valueOf(detail.getId()));
        }
        return b;
    }

    @SneakyThrows
    @Override
    public void update(FileInfo info) {
        FileDetail detail = toFileDetail(info);
        fileDetailRepository.update(detail, QueryWrapper.create()
                .eq(FileDetail.COL_URL, detail.getUrl(), detail.getUrl() != null)
                .eq(FileDetail.COL_ID, detail.getId(), detail.getId() != null));
    }

    @SneakyThrows
    @Override
    public FileInfo getByUrl(String url) {
        return toFileInfo(fileDetailRepository.getOne(QueryWrapper.create().eq(FileDetail.COL_URL, url)));
    }

    @Override
    public boolean delete(String url) {
        fileDetailRepository.remove(QueryWrapper.create().eq(FileDetail.COL_URL, url));
        return true;
    }

    @Override
    public void saveFilePart(FilePartInfo filePartInfo) {
        filePartDetailRepository.saveFilePart(filePartInfo);
    }

    @Override
    public void deleteFilePartByUploadId(String uploadId) {
        filePartDetailRepository.deleteFilePartByUploadId(uploadId);
    }

    /**
     * 将 FileInfo 转为 FileDetail
     */
    public FileDetail toFileDetail(FileInfo info) throws JsonProcessingException {
        FileDetail detail = BeanUtil.copyProperties(
                info, FileDetail.class, "metadata", "userMetadata", "thMetadata", "thUserMetadata", "attr", "hashInfo");

        // 这里手动获 元数据 并转成 json 字符串，方便存储在数据库中
        detail.setMetadata(JSON.toJSONString(info.getMetadata()));
        detail.setUserMetadata(JSON.toJSONString(info.getUserMetadata()));
        detail.setThMetadata(JSON.toJSONString(info.getThMetadata()));
        detail.setThUserMetadata(JSON.toJSONString(info.getThUserMetadata()));
        // 这里手动获 取附加属性字典 并转成 json 字符串，方便存储在数据库中
        detail.setAttr(JSON.toJSONString(info.getAttr()));
        // 这里手动获 哈希信息 并转成 json 字符串，方便存储在数据库中
        detail.setHashInfo(JSON.toJSONString(info.getHashInfo()));
        return detail;
    }

    /**
     * 将 FileDetail 转为 FileInfo
     */
    public FileInfo toFileInfo(FileDetail detail) throws JsonProcessingException {
        FileInfo info = BeanUtil.copyProperties(
                detail, FileInfo.class, "metadata", "userMetadata", "thMetadata", "thUserMetadata", "attr", "hashInfo");

        // 这里手动获取数据库中的 json 字符串 并转成 元数据，方便使用
        info.setMetadata(jsonToMetadata(detail.getMetadata()));
        info.setUserMetadata(jsonToMetadata(detail.getUserMetadata()));
        info.setThMetadata(jsonToMetadata(detail.getThMetadata()));
        info.setThUserMetadata(jsonToMetadata(detail.getThUserMetadata()));
        // 这里手动获取数据库中的 json 字符串 并转成 附加属性字典，方便使用
        info.setAttr(jsonToDict(detail.getAttr()));
        // 这里手动获取数据库中的 json 字符串 并转成 哈希信息，方便使用
        info.setHashInfo(jsonToHashInfo(detail.getHashInfo()));
        return info;
    }

    /**
     * 将JSON字符串转换为元数据Map
     */

    public Map<String, String> jsonToMetadata(String json) throws JsonProcessingException {
        if (StringUtils.isBlank(json)) {
            return Collections.emptyMap();
        }
        return JSON.parseObject(json, new TypeReference<>() {
        });
    }

    /**
     * 将 json 字符串转换成字典对象
     */
    public Dict jsonToDict(String json) throws JsonProcessingException {
        if (StrUtil.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, Dict.class);
    }

    /**
     * 将 json 字符串转换成哈希信息对象
     */
    public HashInfo jsonToHashInfo(String json) throws JsonProcessingException {
        if (StrUtil.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, HashInfo.class);
    }
}
