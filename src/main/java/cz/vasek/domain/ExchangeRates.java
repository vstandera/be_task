package cz.vasek.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Save ExchangeRates Singleton
 */
public class ExchangeRates {
    /**
     * Map to save all exchangeRates
     */
    private Map<String, BigDecimal> exchangeRates;

    private static class SingletonHelper {
        private static final ExchangeRates INSTANCE = new ExchangeRates();
    }
    private ExchangeRates() {
        exchangeRates= new HashMap<>();
    }


    public BigDecimal getRate (String currency){
        return exchangeRates.get(currency);
    }

    public void setExchangeRate(String currencyCode, BigDecimal rate){
        exchangeRates.put(currencyCode,rate);
    }

    /**
     * Get Instance
     * @return ExchangeRates instance
     */
    public static synchronized ExchangeRates getInstance() {
        return ExchangeRates.SingletonHelper.INSTANCE;
    }

}
