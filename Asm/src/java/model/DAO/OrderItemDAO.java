package model.DAO;

import model.DTO.OrderItemDTO;
import utils.DbUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderItemDAO {

    /* -----------------  C R U D  ----------------- */

    /** Create */
    public boolean insert(OrderItemDTO oi) throws Exception {
        String sql = "INSERT INTO OrderItem(orderId, productId, quantity, unitPrice) "
                   + "VALUES (?,?,?,?)";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setInt   (1, oi.getOrderId());
            ps.setInt   (2, oi.getProductId());
            ps.setInt   (3, oi.getQuantity());
            ps.setDouble(4, oi.getUnitPrice());

            int row = ps.executeUpdate();
            if (row > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) oi.setItemId(rs.getInt(1));
                return true;
            }
            return false;
        }
    }

    /** Read – by PK */
    public Optional<OrderItemDTO> findById(int id) throws Exception {
        String sql = "SELECT * FROM OrderItem WHERE itemId=?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(map(rs));
            return Optional.empty();
        }
    }

    /** Read – all */
    public List<OrderItemDTO> findAll() throws Exception {
        String sql = "SELECT * FROM OrderItem";
        List<OrderItemDTO> list = new ArrayList<>();
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    /** Update quantity & unitPrice (nếu muốn) */
    public boolean update(OrderItemDTO oi) throws Exception {
        String sql = "UPDATE OrderItem SET quantity=?, unitPrice=? WHERE itemId=?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt   (1, oi.getQuantity());
            ps.setDouble(2, oi.getUnitPrice());
            ps.setInt   (3, oi.getItemId());
            return ps.executeUpdate() > 0;
        }
    }

    /** Delete */
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM OrderItem WHERE itemId=?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /* --------------  Helper & extra --------------- */

    /** Lấy toàn bộ item của 1 đơn  */
    public List<OrderItemDTO> findByOrder(int orderId) throws Exception {
        String sql = "SELECT * FROM OrderItem WHERE orderId=?";
        List<OrderItemDTO> list = new ArrayList<>();
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    private OrderItemDTO map(ResultSet rs) throws SQLException {
        return new OrderItemDTO(
                rs.getInt("itemId"),
                rs.getInt("orderId"),
                rs.getInt("productId"),
                rs.getInt("quantity"),
                rs.getDouble("unitPrice"));
    }
}
