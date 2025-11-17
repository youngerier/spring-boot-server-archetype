#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business.base;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * BaseEntity
 *
 * @author <a href="mailto:litao@qbitnetwork.com">Kratos</a>
 */
@Data
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 8974343870931818228L;
    /**
     * 主键
     */
    @Id(keyType = KeyType.Generator, value = "snowFlakeId")
    private Long id;

    @Column(
            onInsertValue = "((date_part('epoch'::text, clock_timestamp()) * (1000)::double precision))::bigint"
    )
    private Long createTime;

    @Column(
            onInsertValue = "((date_part('epoch'::text, clock_timestamp()) * (1000)::double precision))::bigint",
            onUpdateValue = "((date_part('epoch'::text, clock_timestamp()) * (1000)::double precision))::bigint"
    )
    private Long updateTime;
}
