#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/**
 * Enum representing QbitCardProvider.
 * Please describe the business purpose of this class.
 */
@Getter
public enum QbitCardProviderEnum {
    PennyCard("PennyCard"),
    NiumCard("NiumCard"),
    NiumCardRecharge("NiumCardRecharge"),
    BlueBancCard("BlueBancCard"),
    BlueBancCardRecharge("BlueBancCardRecharge"),
    BlueBancCard428852("BlueBancCard428852"),
    BlueBancCardRecharge428852("BlueBancCardRecharge428852"),
    BlueBancCard517746("BlueBancCard517746"),
    BlueBancCardRecharge517746("BlueBancCardRecharge517746"),
    MarqetaCard("MarqetaCard"),
    MarqetaCardRecharge("MarqetaCardRecharge"),
    ComdataCard("ComdataCard"),
    ComdataCardRecharge("ComdataCardRecharge"),
    TripLinkCardRecharge222929("TripLinkCardRecharge222929"),
    TripLinkCardRecharge428837("TripLinkCardRecharge428837"),
    TripLinkCardRecharge540524("TripLinkCardRecharge540524"),
    HighnoteCardRecharge462042002("HighnoteCardRecharge462042002"),
    HighnoteCardRecharge428831001("HighnoteCardRecharge428831001"),
    HighnoteCardRecharge428831000("HighnoteCardRecharge428831000"),
    HighnoteCard462042002("HighnoteCard462042002"),
    HighnoteCard428831001("HighnoteCard428831001"),
    HighnoteCard428831000("HighnoteCard428831000"),
    TripLinkCard222929("TripLinkCard222929"),
    ConnexCardRecharge531993("ConnexCardRecharge531993"),
    ConnexCardRecharge489683("ConnexCardRecharge489683"),
    ConnexCardRecharge553437("ConnexCardRecharge553437"),
    ConnexCardRecharge441112("ConnexCardRecharge441112"),
    ConnexCardRechargeIPR("ConnexCardRechargeIPR"),
    ThepennyincCard("ThepennyincCard"),
    ThepennyincCardRecharge("ThepennyincCardRecharge"),
    ReapCard414631("ReapCard414631"),
    ReapCardRecharge414631("ReapCardRecharge414631"),
    ReapCard493728("ReapCard493728"),
    ReapCardRecharge493728("ReapCardRecharge493728"),
    IPRCard("IPRCard"),
    QbitIssuingCard49387520("QbitIssuingCard49387520"),
    QbitIssuingCardRecharge49387520("QbitIssuingCardRecharge49387520"),
    QbitIssuingCard49387519("QbitIssuingCard49387519"),
    QbitIssuingCardRecharge49387519("QbitIssuingCardRecharge49387519"),
    RainCard455988("RainCard455988"),
    RainCardRecharge455988("RainCardRecharge455988"),
    IPRCardRecharge("IPRCardRecharge"),

    ;

    @JsonValue
    private final String value;

    QbitCardProviderEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}