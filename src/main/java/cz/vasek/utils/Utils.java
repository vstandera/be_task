package cz.vasek.utils;

import cz.vasek.domain.ExchangeRates;
import cz.vasek.domain.Payment;
import cz.vasek.domain.Payments;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Utils all about Payments and ExchangeRates
 */
public class Utils {
    /**
     * Parse the payment from file or console
     * @param lineString
     * @return Payment ready to save
     */
    public static Payment parseToPayment(String lineString) {
        StringTokenizer strTkn = new StringTokenizer(lineString, " ");
        BigDecimal amount = BigDecimal.ZERO;
        String currency = "";
        if (strTkn.countTokens() != 2) {
            throw new RuntimeException("String is not valid. :" + lineString);
        }

        if (strTkn.hasMoreTokens()) {
            currency = strTkn.nextToken();
        }
        if (strTkn.hasMoreTokens()) {
            final String amountStr = strTkn.nextToken();
            try {
                amount = new BigDecimal(amountStr);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Amount is not valid. :" + amountStr);
            }
        }
        return new Payment(amount, currency);
    }

    /**
     * Parse exchangeRates form file
     * @param line
     */
    public static void parseExchangeRate(String line) {
        StringTokenizer strTkn = new StringTokenizer(line, " ");
        BigDecimal amount = BigDecimal.ZERO;
        String currency = "";
        if (strTkn.countTokens() != 2) {
            throw new RuntimeException("File exchange is not valid. :" + line);
        }

        if (strTkn.hasMoreTokens()) {
            currency = strTkn.nextToken();
        }
        if (strTkn.hasMoreTokens()) {
            final String amountStr = strTkn.nextToken();
            try {
                amount = new BigDecimal(amountStr);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Amount exchange is not valid. :" + amountStr);
            }
        }
        ExchangeRates.getInstance().setExchangeRate(currency, amount);
    }

    /**
     * Write sum all Payments and exchange to console
     * @param writeComment true - write also comment to console
     */
    public static void writeAllPayments(boolean writeComment) {
        Payments.getInstance().getPayments().values().forEach(pay -> writeToSystemOut(pay));
        if (writeComment) {
            System.out.print("Enter Amount and Currency format( \"CZK 100\" ) \n:");
        }
    }

    /**
     * Sum all payments
     * @param pay List<Payzmen>
     * @return
     */
    private static BigDecimal sumPayments(List<Payment> pay) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Payment p : pay) {
            sum = sum.add(p.getAmount());
        }
        return sum;
    }

    /**
     * Write sum all Payments and exchange to console
     * @param pay
     */
    private static void writeToSystemOut(List<Payment> pay) {
        final BigDecimal sum = sumPayments(pay);
        if (!(sum.compareTo(BigDecimal.ZERO) == 0)) {
            System.out.println("Sum payments : " + pay.get(0).getCurrency() + " " + sum + calculateExchange(pay.get(0).getCurrency(), sum));
        }

    }

    /**
     * Calculate exchange to USD.
     * @param curr currency
     * @param amount amount
     * @return String of exchange + USD
     */
    private static String calculateExchange(String curr, BigDecimal amount) {
        final BigDecimal rate = ExchangeRates.getInstance().getRate(curr);
        if (!"USD".equalsIgnoreCase(curr) && rate != null) {
            BigDecimal multiply = rate.multiply(amount);
            multiply = multiply.setScale(2, RoundingMode.CEILING);
            return " " +multiply.toString()+ " USD";
        }
        return "";
    }


}
