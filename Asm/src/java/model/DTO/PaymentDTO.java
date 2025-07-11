package model.DTO;

import java.sql.Date;

public class PaymentDTO {
    private int paymentId;
    private int orderId;
    private double amount;
    private String method;  
    private Date   paidAt;

    public PaymentDTO() {}
    public PaymentDTO(int paymentId, int orderId,
                      double amount, String method, Date paidAt) {
        this.paymentId = paymentId;
        this.orderId   = orderId;
        this.amount    = amount;
        this.method    = method;
        this.paidAt    = paidAt;
    }
    /* getter + setter */

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Date getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }
}
