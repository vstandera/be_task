package cz.vasek.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.Set;

/**
 * Payment
 */
public class Payment {

    private BigDecimal amount;
    private String currency;

    public Payment(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = verifyCurrency(currency);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    /**
     * Verify if it is valid currency
     * @param currency
     * @return String currency code
     * @throws RuntimeException
     */
    public String verifyCurrency(String currency) throws RuntimeException {
        final Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
        final Optional<Currency> anyCurr = availableCurrencies.stream().filter(curr -> curr.getCurrencyCode().equalsIgnoreCase(currency)).findAny();
        if (anyCurr.isPresent()) {
            return anyCurr.get().getCurrencyCode();
        } else {
            throw new RuntimeException("The Currency is not valid. : " + currency);
        }
    }

}
