#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.service;

import ${package}.api.system.modal.request.OcrRequest;

/**
 * @author martinjiang
 */
public interface OcrService {

    /**
     * 调用 OCR（光学字符识别）接口，根据传入的请求参数识别图片内容。
     * <p>
     * 支持多种 OCR 类型（身份证、护照、营业执照、银行卡、发票等），
     * 具体识别逻辑由 {@link OcrRequest} 中的 {@code type} 参数决定。
     * </p>
     *
     * @param request OCR 请求参数对象，包含图片数据、识别类型、正反面等信息
     * @return 返回 OCR 解析结果，通常为结构化数据的 Map 或实体对象
     */
    Object getOcr(OcrRequest request);
}
