package model.DAO;

import model.DTO.PaymentDTO;
import utils.DbUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentDAO {

    /* Create */
    public boolean insert(PaymentDTO p) throws Exception {
        String sql = "INSERT INTO Payment(orderId, amount, method, paidAt) "
                   + "VALUES (?,?,?,?)";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setInt   (1, p.getOrderId());
            ps.setDouble(2, p.getAmount());
            ps.setString(3, p.getMethod());
            ps.setDate  (4, p.getPaidAt());

            int row = ps.executeUpdate();
            if (row > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) p.setPaymentId(rs.getInt(1));
                return true;
            }
            return false;
        }
    }

    /* Read – by PK */
    public Optional<PaymentDTO> findById(int id) throws Exception {
        String sql = "SELECT * FROM Payment WHERE paymentId=?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(map(rs));
            return Optional.empty();
        }
    }

    /* Read – all */
    public List<PaymentDTO> findAll() throws Exception {
        List<PaymentDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Payment";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    /* Update phương thức + amount (ít dùng) */
    public boolean update(PaymentDTO p) throws Exception {
        String sql = "UPDATE Payment SET amount=?, method=? WHERE paymentId=?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setDouble(1, p.getAmount());
            ps.setString(2, p.getMethod());
            ps.setInt   (3, p.getPaymentId());
            return ps.executeUpdate() > 0;
        }
    }

    /* Delete (nên hạn chế) */
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM Payment WHERE paymentId=?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /* Lấy payment theo order */
    public Optional<PaymentDTO> findByOrder(int orderId) throws Exception {
        String sql = "SELECT * FROM Payment WHERE orderId=?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(map(rs));
            return Optional.empty();
        }
    }

    private PaymentDTO map(ResultSet rs) throws SQLException {
        return new PaymentDTO(
                rs.getInt("paymentId"),
                rs.getInt("orderId"),
                rs.getDouble("amount"),
                rs.getString("method"),
                rs.getDate("paidAt"));
    }
}
