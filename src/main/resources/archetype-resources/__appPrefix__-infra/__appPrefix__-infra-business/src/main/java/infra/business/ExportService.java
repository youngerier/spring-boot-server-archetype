#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business;


import com.alibaba.excel.support.ExcelTypeEnum;
import ${package}.infra.business.excel.ExcelCommonWriter;
import ${package}.infra.business.excel.ExcelCommonWriterBuilder;
import ${package}.infra.business.excel.ExcelExportBO;
import ${package}.api.system.modal.dto.ExportNoUploadDTO;


import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author litao
 */
public interface ExportService {

    /**
     * 生成标准的 Excel 导出文件路径。
     * <p>
     * 文件名格式：{@code export/3.0/{name}.xlsx}
     * <br>示例：{@code export/3.0/account-list.xlsx}
     * </p>
     *
     * @param name 导出模块名称或文件前缀
     * @return 生成的文件路径（包含文件名和后缀）
     */
    default String generatorFilepath(String name) {
        return "export/3.0/" + name + "-" + DateUtil.YYYY_MM_DD_HH_MM_SS_SSS_PATTERN.format(new Date()) + ExcelTypeEnum.XLSX.getValue();
    }

    /**
     * 生成标准的 PDF 导出文件路径。
     * <p>
     * 文件名格式：{@code export/3.0/{name}.pdf}
     * <br>示例：{@code export/3.0/report-detail-20251013185930123.pdf}
     * </p>
     *
     * @param name 导出模块名称或文件前缀
     * @return 生成的 PDF 文件路径（包含文件名和后缀）
     */
    default String generatorPdfFilepath(String name) {
        return "export/3.0/" + name + "-" + DateUtil.YYYY_MM_DD_HH_MM_SS_SSS_PATTERN.format(new Date()) + ".pdf";
    }

    /**
     * 生成对账单类 PDF 文件路径。
     * <p>
     * 文件名格式：{@code export/3.0/{name}-yyyyMMdd.pdf}
     * <br>示例：{@code export/3.0/statement-20251013.pdf}
     * </p>
     *
     * @param name 导出模块名称或文件前缀（通常为对账单类型）
     * @return 生成的 PDF 文件路径（包含文件名和后缀）
     */
    default String generatorStatementPdfFilepath(String name) {
        return "export/3.0/" + name + "-" + DateUtil.dateFormat(new Date(), "yyyyMMdd") + ".pdf";
    }

    /**
     * 将指定文件流上传到文件存储系统。
     * <p>
     * 该方法接收一个生成好的文件流（通常由导出方法产生的 {@link ByteArrayOutputStream}），
     * 并将其上传至指定的路径 {@code filepath}。
     * 上传完成后返回可访问的文件 URL，通常用于前端下载或系统引用。
     * </p>
     *
     * @param filepath 文件存储路径（含文件名和扩展名），例如 {@code "export/3.0/report-20251013.xlsx"}
     * @param stream   文件输出流，包含待上传的文件内容
     * @return 上传后的文件访问 URL
     */
    String upload(String tenantId, String filepath, ByteArrayOutputStream stream);

    /**
     * 导出处理
     *
     * @param name     模块名
     * @param consumer 数据处理
     * @return file url
     */
    default String exportHandler(String tenantId, String name, BiConsumer<ExcelCommonWriterBuilder, ExcelCommonWriter> consumer) {
        return this.handler(tenantId, generatorFilepath(name), consumer);
    }

    /**
     * 导出处理
     * @param tenantId 租户id
     * @param filepath filepath
     * @param consumer 消费者回调
     * @return file url
     */
    String handler(String tenantId, String filepath, BiConsumer<ExcelCommonWriterBuilder, ExcelCommonWriter> consumer);

    /**
     * 单sheet导出
     *
     * @param name    filename
     * @param data    数据集
     * @param headers headers
     * @return file url
     */
    <T> String export(String tenantId, String name, Collection<T> data, Class<T> headers);

    /**
     * 导出单个 Sheet 的 Excel 文件。
     * <p>
     * 该方法根据提供的表头（{@code headers}）与数据集合（{@code data}），生成一个单 Sheet 的 Excel 文件，
     * 并自动上传到文件存储系统，返回可访问的文件 URL。
     * 适用于无固定实体类结构、动态列定义的导出场景。
     * </p>
     *
     * @param name    文件名（不包含后缀），例如 {@code "account-report"}
     * @param data    导出的数据集，集合中每个元素代表一行数据
     * @param headers Excel 表头数组，对应列标题，顺序与数据字段顺序一致
     * @param <T>     数据类型（可为任意对象或 Map）
     * @return 上传后的文件访问 URL
     */
    <T> String export(String tenantId, String name, Collection<T> data, String[] headers);

    /**
     * 导出单个 Sheet 的 Excel 文件（追加模式，无需重新上传）。
     * <p>
     * 该方法用于在已存在的上传任务（由 {@code uploadUrl} 指定）中追加导出数据。
     * 当导出数据量较大或需要分批生成时，可通过此方法分段导出后再统一上传，提高导出性能与内存利用率。
     * </p>
     *
     * @param uploadUrl 已存在的上传任务 URL 或唯一标识，用于标记追加导出的目标文件
     * @param name      文件名（不包含后缀），例如 {@code "transaction-summary"}
     * @param data      导出的数据集，集合中每个元素代表一行数据
     * @param headers   导出实体类类型，用于自动生成表头
     * @param <T>       导出数据类型，对应表头定义的实体类
     * @return {@link ExportNoUploadDTO} 对象，包含导出结果的中间状态（未上传文件流信息）
     */
    <T> ExportNoUploadDTO export(String tenantId, String uploadUrl, String name, Collection<T> data, Class<T> headers);

    /**
     * 单sheet导出
     *
     * @param name   filename
     * @param params export params
     * @return file url
     */
    <T> String export(String tenantId, String name, ExcelExportBO<T> params);

    /**
     * 多sheet导出
     *
     * @param name       filename
     * @param collection params collection
     * @return file url
     */
    String export(String tenantId, String name, Collection<ExcelExportBO<?>> collection);

    /**
     * 多个 Sheet 的 Excel 导出（追加模式，无上传）。
     * <p>
     * 该方法支持将多个 {@link ExcelExportBO} 实例分别导出为同一 Excel 文件中的多个 Sheet，
     * 并以追加的方式写入已有的导出任务（由 {@code uploadUrl} 指定）。
     * 适用于大数据量或分批次导出场景，可有效降低内存占用并提高导出性能。
     * </p>
     *
     * @param uploadUrl  已存在的上传任务 URL 或唯一标识，用于标记追加导出的目标文件
     * @param name       文件名（不包含扩展名），例如 {@code "multi-report-export"}
     * @param collection {@link ExcelExportBO} 集合，每个元素代表一个 Sheet 的导出配置（含数据与表头）
     * @return {@link ExportNoUploadDTO} 对象，包含导出结果的中间状态（未上传文件流信息）
     */
    ExportNoUploadDTO export(String tenantId, String uploadUrl, String name, Collection<ExcelExportBO<?>> collection);


    /**
     * pdf 导出
     *
     * @param name     filename
     * @param data     collection
     * @param template pdf模版
     * @return file url
     */
    String exportPdf(String tenantId, String name, Map<String, Object> data, String template);

    /**
     * 生成 PDF 文件（不执行上传操作）。
     * <p>
     * 该方法根据指定的模板文件 {@code template} 和数据模型 {@code data}，
     * 生成对应的 PDF 内容，并以 {@link ByteArrayOutputStream} 的形式返回，
     * 适用于需要自定义上传或直接返回文件流的场景。
     * </p>
     *
     * @param name     文件名（不包含扩展名），例如 {@code "monthly-statement"}
     * @param data     PDF 渲染数据模型，键值对形式传入模板变量
     * @param template PDF 模板路径或名称，用于指定渲染样式与内容结构
     * @return 生成的 PDF 文件内容流（未上传）
     */
    ByteArrayOutputStream exportNoUploadPdf(String name, Map<String, Object> data, String template);

    /**
     * pdf 多页导出
     *
     * @param name     filename
     * @param data     collection
     * @param templates pdf模版
     * @return file url
     */
    String exportPdf(String tenantId, String name, Map<String, Object> data, List<String> templates);
}
