#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OcrType {

    /**
     * 身份证（已弃用，请使用 {@link ${symbol_pound}ID_CARD_NEW}）
     */
    ID_CARD("idcard"),

    /**
     * 身份证（新版）
     */
    ID_CARD_NEW("idcard_new"),

    /**
     * 营业执照（已弃用，请使用 {@link ${symbol_pound}BUSINESS_LICENSE_NEW}）
     */
    BUSINESS_LICENSE("business_license"),

    /**
     * 营业执照（新版）
     */
    BUSINESS_LICENSE_NEW("business_license_new"),

    /**
     * 护照（已弃用，请使用 {@link ${symbol_pound}CHINESE_PASSPORT} 或 {@link ${symbol_pound}GLOBAL_PASSPORT}）
     */
    PASSPORT("passport"),

    /**
     * 中国护照
     */
    CHINESE_PASSPORT("chinese_passport"),

    /**
     * 国际护照
     */
    GLOBAL_PASSPORT("global_passport"),

    /**
     * 户口页识别
     */
    HOUSEHOLD_REGISTER("household_register"),

    /**
     * 中国香港身份证
     */
    HK_IDCARD("HKIdcard"),

    /**
     * 驾驶证识别
     */
    DRIVER_LICENSE("driver_license"),

    /**
     * 行驶证
     */
    VEHICLE("vehicle"),

    /**
     * 银行卡
     */
    BANK_CARD("bank_card"),

    /**
     * 二维码
     */
    QR_CODE("QrCode"),

    /**
     * 电子发票（增值税专用发票）
     */
    ELECTRONIC_INVOICE("ElectronicInvoice"),

    /**
     * 火车票
     */
    TRAIN_TICKETS("TrainTickets"),

    /**
     * 飞机票（航空运输电子客票行程单）
     */
    AIR_TICKETS("AirTickets");

    @EnumValue
    private final String value;

    OcrType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static OcrType fromValue(String value) {
        for (OcrType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown OcrTypeEnum value: " + value);
    }
}
