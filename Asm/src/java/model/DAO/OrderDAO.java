package model.DAO;

import model.DTO.OrderDTO;
import model.DTO.OrderItemDTO;
import model.DTO.PaymentDTO;
import utils.DbUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAO {

   
    public int createNewOrder(String userId) throws Exception {
        String sql = "INSERT INTO [Order](userId,status) VALUES (?, 'pending')";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, userId);
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }

    
    public Optional<OrderDTO> findById(int orderId) throws Exception {
        String sql = "SELECT orderId, userId, orderDate, expectedDeliveryDate, status "
                   + "FROM [Order] WHERE orderId = ?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OrderDTO o = new OrderDTO(
                        rs.getInt("orderId"),
                        rs.getString("userId"),
                        rs.getDate("orderDate"),
                        rs.getDate("expectedDeliveryDate"),
                        rs.getString("status")
                    );
                    // load items
                    o.setItems(new OrderItemDAO().findByOrder(o.getOrderId()));
                    // load payment (if any)
                    o.setPayment(new PaymentDAO().findByOrder(o.getOrderId()));
                    return Optional.of(o);
                }
            }
        }
        return Optional.empty();
    }

    public List<OrderDTO> findByUser(String userId) throws Exception {
        String sql = "SELECT orderId, userId, orderDate, expectedDeliveryDate, status "
                   + "FROM [Order] WHERE userId = ?";
        List<OrderDTO> list = new ArrayList<>();
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                OrderItemDAO itemDao = new OrderItemDAO();
                PaymentDAO payDao = new PaymentDAO();
                while (rs.next()) {
                    OrderDTO o = new OrderDTO(
                        rs.getInt("orderId"),
                        rs.getString("userId"),
                        rs.getDate("orderDate"),
                        rs.getDate("expectedDeliveryDate"),
                        rs.getString("status")
                    );
                    // load items and payment
                    o.setItems(itemDao.findByOrder(o.getOrderId()));
                    o.setPayment(payDao.findByOrder(o.getOrderId()));
                    list.add(o);
                }
            }
        }
        return list;
    }

    
    public List<OrderDTO> findAll() throws Exception {
        String sql = "SELECT orderId, userId, orderDate, expectedDeliveryDate, status FROM [Order]";
        List<OrderDTO> list = new ArrayList<>();
        try (Connection con = DbUtils.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            OrderItemDAO itemDao = new OrderItemDAO();
            PaymentDAO payDao = new PaymentDAO();
            while (rs.next()) {
                OrderDTO o = new OrderDTO(
                    rs.getInt("orderId"),
                    rs.getString("userId"),
                    rs.getDate("orderDate"),
                    rs.getDate("expectedDeliveryDate"),
                    rs.getString("status")
                );
                o.setItems(itemDao.findByOrder(o.getOrderId()));
                o.setPayment(payDao.findByOrder(o.getOrderId()));
                list.add(o);
            }
        }
        return list;
    }

    public boolean updateStatus(int orderId, String newStatus) throws Exception {
        String sql = "UPDATE [Order] SET status = ? WHERE orderId = ?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        }
    }

   
    public boolean delete(int orderId) throws Exception {
        
        new PaymentDAO().findByOrder(orderId)
                       .ifPresent(p -> {
                           try { new PaymentDAO().delete(p.getPaymentId()); }
                           catch(Exception ignore) {}
                       });
        for (OrderItemDTO item : new OrderItemDAO().findByOrder(orderId)) {
            new OrderItemDAO().delete(item.getItemId());
        }
        String sql = "DELETE FROM [Order] WHERE orderId = ?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        }
    }
}
