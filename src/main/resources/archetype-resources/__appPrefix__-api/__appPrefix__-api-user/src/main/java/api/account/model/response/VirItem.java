#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.response;

import ${package}.core.enums.QbitCardProviderEnum;

import lombok.Data;

@Data
public class VirItem {
    private QbitCardProviderEnum provider;
    private String bin;
}
