#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.validation.validators;

import ${package}.infra.toolkits.validation.constraints.Currency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * CurrencyValidator
 *
 * @author <a href="mailto:litao@qbitnetwork.com">Kratos</a>
 */
public class CurrencyValidator implements ConstraintValidator<Currency, String> {
    private List<String> acceptedCurrencies;

    @Override
    public void initialize(Currency currency) {
        this.acceptedCurrencies = List.of(currency.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return acceptedCurrencies.contains(value);
    }
}
