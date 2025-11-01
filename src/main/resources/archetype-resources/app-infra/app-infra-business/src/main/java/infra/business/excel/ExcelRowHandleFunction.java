#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business.excel;

@FunctionalInterface
public interface ExcelRowHandleFunction<T> {
    void handle(ExcelRowProperties row, T data);
}
