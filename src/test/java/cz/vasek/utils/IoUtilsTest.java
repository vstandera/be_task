package cz.vasek.utils;

import cz.vasek.domain.ExchangeRates;
import cz.vasek.domain.Payment;
import cz.vasek.domain.Payments;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests IoUtils
 */
public class IoUtilsTest {

    @Before
    public void setPayments(){
        Payments.getInstance().getPayments().clear();
    }

    @Test
    public void readPaymentsFile() throws IOException {

        IoUtils ioUtils = new IoUtils();
        ioUtils.readFile(".\\src\\test\\resources\\InputFile\\input.txt", true);
        Payments payments = Payments.getInstance();
        Map<String, List<Payment>> payments1 = payments.getPayments();
        assertTrue(payments1.get("CZK").size()==3);
        assertThat(payments1.get("CZK").get(0).getAmount(), is(new BigDecimal(100)));
        assertThat(payments1.get("CZK").get(1).getAmount(), is(new BigDecimal(50)));
        assertThat(payments1.get("CZK").get(2).getAmount(), is(new BigDecimal(10)));
        assertThat(payments1.get("CZK").get(0).getCurrency(), is("CZK"));
        assertThat(payments1.get("CZK").get(1).getCurrency(), is("CZK"));
        assertThat(payments1.get("CZK").get(2).getCurrency(), is("CZK"));

        assertTrue(payments1.get("EUR").size()==2);
        assertThat(payments1.get("EUR").get(0).getAmount(), is(new BigDecimal(20)));
        assertThat(payments1.get("EUR").get(1).getAmount(), is(new BigDecimal(6)));
        assertThat(payments1.get("EUR").get(0).getCurrency(), is("EUR"));
        assertThat(payments1.get("EUR").get(1).getCurrency(), is("EUR"));

        assertTrue(payments1.get("USD").size()==2);
        assertThat(payments1.get("USD").get(0).getAmount(), is(new BigDecimal(10)));
        assertThat(payments1.get("USD").get(1).getAmount(), is(new BigDecimal(7)));
        assertThat(payments1.get("USD").get(0).getCurrency(), is("USD"));
        assertThat(payments1.get("USD").get(1).getCurrency(), is("USD"));

        assertTrue(payments1.get("FJD").size()==2);
        assertThat(payments1.get("FJD").get(0).getAmount(), is(new BigDecimal(2)));
        assertThat(payments1.get("FJD").get(1).getAmount(), is(new BigDecimal(80)));
        assertThat(payments1.get("FJD").get(0).getCurrency(), is("FJD"));
        assertThat(payments1.get("FJD").get(1).getCurrency(), is("FJD"));

        assertTrue(payments1.get("JPY").size()==3);
        assertThat(payments1.get("JPY").get(0).getAmount(), is(new BigDecimal(40)));
        assertThat(payments1.get("JPY").get(1).getAmount(), is(new BigDecimal(800)));
        assertThat(payments1.get("JPY").get(2).getAmount(), is(new BigDecimal(-1000)));
        assertThat(payments1.get("JPY").get(0).getCurrency(), is("JPY"));
        assertThat(payments1.get("JPY").get(1).getCurrency(), is("JPY"));
        assertThat(payments1.get("JPY").get(2).getCurrency(), is("JPY"));
    }

    @Test
    public void readExchangeRate() throws IOException {
        IoUtils ioUtils = new IoUtils();
        ioUtils.readFile(".\\src\\test\\resources\\InputFile\\exchangeRate.txt", false);
        final ExchangeRates instance = ExchangeRates.getInstance();
        assertTrue(instance.getRate("EUR").compareTo(new BigDecimal(1.1329).setScale(4, BigDecimal.ROUND_HALF_UP))==0);
        assertTrue(instance.getRate("CZK").compareTo(new BigDecimal(0.0442).setScale(4, BigDecimal.ROUND_HALF_UP))==0);
        assertTrue(instance.getRate("FJD").compareTo(new BigDecimal(60.2365).setScale(4, BigDecimal.ROUND_HALF_UP))==0);
        assertTrue(instance.getRate("JPY").compareTo(new BigDecimal(110.72).setScale(2, BigDecimal.ROUND_HALF_UP))==0);

    }

    @Test
    public void parseFileName() {
        IoUtils ioUtils = new IoUtils();
        final String fileName = ioUtils.parseFileName("FILE .\\src\\test\\resources\\InputFile\\input.txt");
        assertThat(fileName, is(".\\src\\test\\resources\\InputFile\\input.txt"));
    }

    @Test
    public void parseFileNameFail() {
        IoUtils ioUtils = new IoUtils();
        final String fileName = ioUtils.parseFileName("FI .\\src\\test\\resources\\InputFile\\input.txt");
        assertNull(fileName);
    }
}