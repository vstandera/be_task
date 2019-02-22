package cz.vasek.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 * Tests for the Payment
 */
public class PaymentTest {

    @Before
    public void setPayments(){
        Payments.getInstance().getPayments().clear();
    }

    @Test
    public void verifyCurrency() {
        Payment payment = new Payment(new BigDecimal(100), "CZK");
        assertThat(payment.getAmount(), is((new BigDecimal(100))));
        assertThat(payment.getCurrency(), is("CZK"));
    }

    @Test(expected =RuntimeException.class)
    public void verifyCurrencyThrowException() {
        Payment payment = new Payment(new BigDecimal(100), "MMN");
    }
}