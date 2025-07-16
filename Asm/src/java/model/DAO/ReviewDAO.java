package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DTO.ReviewDTO;
import utils.DbUtils;

/**
 *
 * @author ADMIN
 */
public class ReviewDAO {
    
    private static final String GET_REVIEW_BY_PRODUCTID = "SELECT reviewId, userId, productId, rating, comment, createdAt, status FROM Review WHERE productId = ? AND status = 1";
            
    public ReviewDAO() {
    }
    
    public List<ReviewDTO> getReviewByProductId(String productId)
    {
        List<ReviewDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try
        {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_REVIEW_BY_PRODUCTID);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            
            while(rs.next())
            {
                ReviewDTO review = new ReviewDTO();
                review.setReviewId(rs.getString("reviewId"));
                review.setUserId(rs.getString("userId"));
                review.setProductId(rs.getString("productId"));
                review.setRating(rs.getString("rating"));
                review.setComment(rs.getString("comment"));
                review.setCreatedAt(rs.getDate("createdAt").toLocalDate());
                review.setStatus(rs.getBoolean("status"));
                
                list.add(review);
            }
            
        } catch (Exception e) {
            System.err.println("Error in getReviewByProductId(productId): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return list;
    }
    
    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("Error closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
