#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business.excel;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;

/**
 * @author litao
 */
public class ExcelCommonWriterBuilder extends ExcelWriterBuilder {

    public ExcelCommonWriterBuilder() {
        super();
        this.excelType(ExcelTypeEnum.XLSX);
    }

    public ExcelCommonWriter build2() {
        return new ExcelCommonWriter(parameter());
    }
}
