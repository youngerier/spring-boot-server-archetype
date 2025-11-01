#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteTypeEnum;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;

import ${package}.api.system.service.FileService;
import ${package}.infra.business.excel.ExcelCommonWriter;
import ${package}.infra.business.excel.ExcelCommonWriterBuilder;
import ${package}.infra.business.excel.ExcelExportBO;
import ${package}.infra.business.excel.ExcelI18nHeaderCellWriteHandler;
import ${package}.api.system.modal.dto.ExportNoUploadDTO;
import ${package}.infra.business.ExportService;
import ${package}.infra.business.PdfUtil;
import ${package}.infra.toolkits.exception.CustomerException;
import jakarta.annotation.Resource;
import jodd.util.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;


/**
 * @author litao
 */
@Slf4j
@Service
public class ExportServiceImpl implements ExportService {
    @Resource
    protected FileService fileService;

    @Resource
    protected ExcelI18nHeaderCellWriteHandler i18nHeaderHandler;

    /**
     * 上传流到oss
     *
     * @param filepath 文件路径
     * @param stream   流
     * @return file url
     */
    @Override
    public String upload(String tenantId, String filepath, ByteArrayOutputStream stream) {
        // ByteArrayOutputStream是个特殊的流不需要执行close()
        try {
            return fileService.putObjectRequest(tenantId, filepath, new ByteArrayInputStream(stream.toByteArray()));
        } catch (CustomerException e) {
            log.error(e.getMessage());
            throw new CustomerException("导出异常请联系管理员");
        }
    }

    @Override
    public String handler(String tenantId, String filepath, BiConsumer<ExcelCommonWriterBuilder, ExcelCommonWriter> consumer) {
        ExcelCommonWriterBuilder builder = new ExcelCommonWriterBuilder();
        builder.autoCloseStream(true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        builder.file(stream);
        ExcelCommonWriter writer = builder.build2();
        consumer.accept(builder, writer);
        writer.finish();
        return upload(tenantId, filepath, stream);
    }

    private void setCellValue(ExcelWriter excelWriter, ExcelWriterSheetBuilder sheetBuilder, int rowIndex, String value) {
        excelWriter.writeContext().currentSheet(sheetBuilder.build(), WriteTypeEnum.ADD);
        Sheet sheet = excelWriter.writeContext().writeSheetHolder().getSheet();
        Row row = WorkBookUtil.createRow(sheet, rowIndex);
        // 设置单元格内容
        WorkBookUtil.createCell(row, 0, value);
        // 合并单元格
        CellRangeAddress range = new CellRangeAddress(rowIndex, rowIndex, 0, 3);
        sheet.addMergedRegionUnsafe(range);
    }

    /**
     * 写文件
     *
     * @param excelWriter ExcelWriter
     * @param params      params
     */
    private <T> void doWrite(ExcelWriter excelWriter, ExcelExportBO<T> params) {
        ExcelWriterSheetBuilder sheetBuilder = new ExcelWriterSheetBuilder(excelWriter);
        // 设置sheet name
        String name = params.getSheetName();
        if (StringUtils.isEmpty(name)) {
            throw new CustomerException("sheetName不能为空");
        }
        sheetBuilder.sheetName(name);
        // 设置表头
        if (params.getClazz() != null) {
            sheetBuilder.head(params.getClazz());
        } else if (params.getHeaders() != null) {
            sheetBuilder.head(params.getHeaders());
        } else {
            throw new CustomerException("表头不能为空");
        }
        // 自定义头部信息
        Boolean useDefault = params.getUseDefaultHeaderInfo();
        if (useDefault == null) {
            useDefault = true;
        }
        String[][] info = params.getHeaderInfo();
        /*User user = UserContext.getUser();
        if (Boolean.TRUE.equals(useDefault) && user != null) {
            info = new String[][]{new String[]{MessageUtils.get("EXCEL_EXPORT_TIME"), DateUtil.dateFormat(new Date())}, new String[]{MessageUtils.get("EXCEL_USER_ID"), user.getAccount().getDisplayId()}, new String[]{MessageUtils.get("EXCEL_USERNAME"), user.getNickname()}};
        }*/
        if (info != null) {
            sheetBuilder.relativeHeadRowIndex(info.length + 1);
            for (int i = 0; i < info.length; i++) {
                String[] head = info[i];
                setCellValue(excelWriter, sheetBuilder, i, head[0] + StringPool.COLON + StringPool.SPACE + head[1]);
            }
        }
        Collection<T> data = params.getData();
        if (data == null) {
            throw new CustomerException("导出的数据集不能为null");
        }
        excelWriter.write(data, sheetBuilder.build());
    }

    @Override
    public <T> String export(String tenantId, String name, Collection<T> data, Class<T> headers) {
        ExcelExportBO<T> params = new ExcelExportBO<>();
        params.setSheetName(name);
        params.setClazz(headers);
        params.setData(data);
        return export(tenantId, name, params);
    }

    @Override
    public <T> String export(String tenantId, String name, Collection<T> data, String[] headers) {
        ExcelExportBO<T> params = new ExcelExportBO<>();
        params.setSheetName(name);
        params.setHeaders(headers);
        params.setData(data);
        return export(tenantId, name, params);
    }

    @Override
    public <T> ExportNoUploadDTO export(String tenantId, String uploadUrl, String name, Collection<T> data, Class<T> headers) {
        ExcelExportBO<T> params = new ExcelExportBO<>();
        params.setSheetName(name);
        params.setClazz(headers);
        params.setData(data);
        return export(tenantId, uploadUrl, name, List.of(params));
    }

    @Override
    public <T> String export(String tenantId, String name, ExcelExportBO<T> bo) {
        return export(tenantId, name, List.of(bo));
    }


    @Override
    public String export(String tenantId, String name, Collection<ExcelExportBO<?>> collection) {
        String filepath = generatorFilepath(name);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ExcelWriterBuilder builder = EasyExcelFactory.write(stream);
        builder.registerWriteHandler(i18nHeaderHandler);
        // 取消默认表头
        builder.useDefaultStyle(false);
        ExcelWriter excelWriter = builder.build();

        for (ExcelExportBO<?> params : collection) {
            doWrite(excelWriter, params);
        }
        excelWriter.finish();
        return upload(tenantId, filepath, stream);
    }

    @Override
    public ExportNoUploadDTO export(String tenantId, String uploadUrl, String name, Collection<ExcelExportBO<?>> collection) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ExcelWriter excelWriter;

        ExcelWriterBuilder builder = EasyExcelFactory.write(stream);
        // 取消默认表头
        builder.useDefaultStyle(false);
        excelWriter = builder.build();

        for (ExcelExportBO<?> params : collection) {
            doWrite(excelWriter, params);
        }
        excelWriter.finish();
        // 对于追加操作，无需返回上传链接
        return new ExportNoUploadDTO(stream, uploadUrl);
    }

    @Override
    public String exportPdf(String tenantId, String name, Map<String, Object> data, String template) {
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        String path = null;
        String pdfName = generatorPdfFilepath(name);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfUtil.pdfFile(context, template, outputStream);
            path = upload(tenantId, pdfName, outputStream);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return path;
    }

    @Override
    public ByteArrayOutputStream exportNoUploadPdf(String name, Map<String, Object> data, String template) {
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfUtil.pdfFile(context, template, outputStream);
        } catch (Exception e) {
            log.error("PDF export failed: {}", e.getMessage(), e);
        }
        return outputStream;
    }

    @Override
    public String exportPdf(String tenantId, String name, Map<String, Object> data, List<String> templates) {
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        String path = null;
        String pdfName = generatorStatementPdfFilepath(name);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfUtil.pdfFile(context, templates, outputStream);
            path = upload(tenantId, pdfName, outputStream);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return path;
    }
}
