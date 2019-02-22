package cz.vasek.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Payments
 */
public class Payments {
    /**
     * Here is saved all payments.
     */
    private Map<String, List<Payment>> payments;

    private static class SingletonHelper {
        private static final Payments INSTANCE = new Payments();
    }

    private Payments() {
        this.payments = new HashMap<>();
    }

    /**
     * Added payment to payments
     * @param payment
     */
    public synchronized void added(Payment payment) {
        final List<Payment> paymentCur = this.payments.get(payment.getCurrency());
        if (paymentCur == null) {
            final List<Payment> pay = new ArrayList<>();
            pay.add(payment);
            this.payments.put(payment.getCurrency(), pay);
        } else {
            paymentCur.add(payment);
        }
    }

    /**
     * Get all payments
     * @return
     */
    public synchronized Map<String, List<Payment>> getPayments() {
        return payments;
    }

    /**
     * Get Instance
     * @return Payments instance
     */
    public static synchronized Payments getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
