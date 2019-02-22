package cz.vasek.utils;

import cz.vasek.domain.Payment;
import cz.vasek.domain.Payments;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests Utils
 */
public class UtilsTest {

    private PrintStream originalSystemOut;
    private ByteArrayOutputStream systemOutContent;

    @Before
    public void redirectSystemOutStream() {
        originalSystemOut = System.out;
        systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));
        Payments.getInstance().getPayments().clear();

    }

    @After
    public void restoreSystemOutStream() {
        System.setOut(originalSystemOut);
    }

    @Test
    public void parseToPayment() {
        final Payment payment = Utils.parseToPayment("USD 100");
        assertThat(payment.getCurrency(), is("USD"));
        assertThat(payment.getAmount(), is(new BigDecimal(100)));
    }

    @Test
    public void writeAllPayments() throws IOException {
        IoUtils ioUtils = new IoUtils();
        ioUtils.readFile(".\\src\\test\\resources\\InputFile\\exchangeRate.txt", false);
        ioUtils.readFile(".\\src\\test\\resources\\InputFile\\input.txt", true);
        Utils.writeAllPayments(false);
        assertTrue(systemOutContent.toString().contains("Sum payments : FJD 82 4939.40 USD"));
        assertTrue(systemOutContent.toString().contains("Sum payments : JPY -160 -17715.20 USD"));
        assertTrue(systemOutContent.toString().contains("Sum payments : EUR 26 29.46 USD"));
        assertTrue(systemOutContent.toString().contains("Sum payments : CZK 160 7.08 USD"));
        assertTrue(systemOutContent.toString().contains("Sum payments : USD 17"));
    }
}