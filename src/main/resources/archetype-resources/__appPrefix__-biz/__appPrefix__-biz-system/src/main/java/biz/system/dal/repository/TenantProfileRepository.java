#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.repository;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import ${package}.api.tenant.model.request.TenantProfileQuery;
import ${package}.biz.system.dal.entity.TenantProfile;
import ${package}.biz.system.dal.mapper.TenantProfileMapper;
import ${package}.biz.system.dal.entity.table.TenantProfileTableRefs;
import ${package}.core.enums.QueryWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 10:58
 * 数据访问层实现类
 */
@Repository
public class TenantProfileRepository extends ServiceImpl<TenantProfileMapper, TenantProfile> {
    private QueryWrapper buildQueryWrapper(TenantProfileQuery query) {
        TenantProfileTableRefs tenantProfileTableRefs = TenantProfileTableRefs.tenantProfile;
        return QueryWrapperHelper.withOrder(query)
                .from(tenantProfileTableRefs)
                .where(tenantProfileTableRefs.id.eq(query.getId()))
                .where(tenantProfileTableRefs.tenantId.eq(query.getTenantId()))
                .where(tenantProfileTableRefs.settingKey.eq(query.getSettingKey()))
                .where(tenantProfileTableRefs.settingValue.eq(query.getSettingValue()))
                .where(tenantProfileTableRefs.version.eq(query.getVersion()))
                .where(tenantProfileTableRefs.remarks.eq(query.getRemarks()))
                .where(tenantProfileTableRefs.createTime.eq(query.getCreateTime()))
                .where(tenantProfileTableRefs.updateTime.eq(query.getUpdateTime()))
                .where(tenantProfileTableRefs.deleteTime.eq(query.getDeleteTime()))
                .and(tenantProfileTableRefs.createTime.ge(query.getMinCreateTime()))
                .and(tenantProfileTableRefs.createTime.le(query.getMaxCreateTime()))
                .and(tenantProfileTableRefs.updateTime.ge(query.getMinUpdateTime()))
                .and(tenantProfileTableRefs.updateTime.le(query.getMaxUpdateTime()));
    }

    public List<TenantProfile> selectListByQuery(TenantProfileQuery query) {
        return getMapper().selectListByQuery(buildQueryWrapper(query));
    }

    public Page<TenantProfile> page(TenantProfileQuery query) {
        Page<TenantProfile> page = new Page<>(query.getQueryPage(), query.getQuerySize());
        return getMapper().paginate(page, buildQueryWrapper(query));
    }

    public Boolean updateByTenantIdAndKey(Long tenantId, String key, String value) {
        TenantProfile tenantProfile = new TenantProfile();
        tenantProfile.setSettingValue(value);
        return this.update(tenantProfile, QueryWrapper.create().eq(TenantProfile::getTenantId, tenantId)
                .eq(TenantProfile::getSettingKey, key));
    }

    public TenantProfile getByTenantIdAndKey(Long tenantId, String settingKey) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .from(TenantProfile.class)
                .where(TenantProfile::getTenantId).eq(tenantId)
                .where(TenantProfile::getSettingKey).eq(settingKey);
        return this.getOne(queryWrapper);
    }
}
