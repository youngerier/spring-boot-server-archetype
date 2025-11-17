#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.repository;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import ${package}.api.tenant.model.request.TenantCustomerConfigQuery;
import ${package}.biz.system.dal.entity.TenantCustomerConfig;
import ${package}.biz.system.dal.mapper.TenantCustomerConfigMapper;
import ${package}.biz.system.dal.entity.table.TenantCustomerConfigTableRefs;
import ${package}.core.enums.QueryWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 10:43
 * 租户配置表
 * 数据访问层实现类
 */
@Repository
public class TenantCustomerConfigRepository extends ServiceImpl<TenantCustomerConfigMapper, TenantCustomerConfig> {
    private QueryWrapper buildQueryWrapper(TenantCustomerConfigQuery query) {
        TenantCustomerConfigTableRefs tenantCustomerConfigTableRefs = TenantCustomerConfigTableRefs.tenantCustomerConfig;
        return QueryWrapperHelper.withOrder(query)
                .from(tenantCustomerConfigTableRefs)
                .where(tenantCustomerConfigTableRefs.id.eq(query.getId()))
                .where(tenantCustomerConfigTableRefs.tenantId.eq(query.getTenantId()))
                .where(tenantCustomerConfigTableRefs.outAccountId.eq(query.getOutAccountId()))
                .where(tenantCustomerConfigTableRefs.appDomain.eq(query.getAppDomain()))
                .where(tenantCustomerConfigTableRefs.adminDomain.eq(query.getAdminDomain()))
                .where(tenantCustomerConfigTableRefs.clientId.eq(query.getClientId()))
                .where(tenantCustomerConfigTableRefs.clientSecret.eq(query.getClientSecret()))
                .where(tenantCustomerConfigTableRefs.mainEmail.eq(query.getMainEmail()))
                .where(tenantCustomerConfigTableRefs.version.eq(query.getVersion()))
                .where(tenantCustomerConfigTableRefs.remarks.eq(query.getRemarks()))
                .where(tenantCustomerConfigTableRefs.createTime.eq(query.getCreateTime()))
                .where(tenantCustomerConfigTableRefs.updateTime.eq(query.getUpdateTime()))
                .where(tenantCustomerConfigTableRefs.deleteTime.eq(query.getDeleteTime()))
                .and(tenantCustomerConfigTableRefs.createTime.ge(query.getMinCreateTime()))
                .and(tenantCustomerConfigTableRefs.createTime.le(query.getMaxCreateTime()))
                .and(tenantCustomerConfigTableRefs.updateTime.ge(query.getMinUpdateTime()))
                .and(tenantCustomerConfigTableRefs.updateTime.le(query.getMaxUpdateTime()));
    }

    public List<TenantCustomerConfig> selectListByQuery(TenantCustomerConfigQuery query) {
        return getMapper().selectListByQuery(buildQueryWrapper(query));
    }

    public Page<TenantCustomerConfig> page(TenantCustomerConfigQuery query) {
        Page<TenantCustomerConfig> page = new Page<>(query.getQueryPage(), query.getQuerySize());
        return getMapper().paginate(page, buildQueryWrapper(query));
    }

    public TenantCustomerConfig getByTenantId(Long tenantId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .from(TenantCustomerConfig.class)
                .where(TenantCustomerConfig::getTenantId).eq(tenantId);
        return this.getOne(queryWrapper);
    }
}
