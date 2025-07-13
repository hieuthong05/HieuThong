package model.DTO;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class OrderDTO {

    private int orderId;
    private String userId;
    private Date orderDate;
    private Date expectedDeliveryDate;
    private String status;
    private List<OrderItemDTO> items;
    private Optional<PaymentDTO> payment;

    public OrderDTO() {
    }

    public OrderDTO(int orderId, String userId, Date orderDate,
            Date expectedDeliveryDate, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public Optional<PaymentDTO> getPayment() {
        return payment;
    }

    public void setPayment(Optional<PaymentDTO> payment) {
        this.payment = payment;
    }
}
