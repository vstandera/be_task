package cz.vasek.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 * Test for the Payments
 */
public class PaymentsTest {

    @Before
    public void setPayments(){
        Payments.getInstance().getPayments().clear();
    }

    @Test
    public void added() {
        Payments payments = Payments.getInstance();
        payments.added( new Payment(new BigDecimal(100), "CZK"));
        payments.added(new Payment(new BigDecimal(150), "CZK"));

        final Map<String, List<Payment>> payments1 = payments.getPayments();
        assertThat(payments1.get("CZK").get(0).getAmount(), is(new BigDecimal(100)));
        assertThat(payments1.get("CZK").get(1).getAmount(), is(new BigDecimal(150)));
        assertThat(payments1.get("CZK").get(0).getCurrency(), is("CZK"));
        assertThat(payments1.get("CZK").get(1).getCurrency(), is("CZK"));
    }
}