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
    
    private static final String GET_REVIEW_BY_ID = "SELECT reviewId, userId, productId, rating, comment, createdAt, status FROM Review WHERE reviewId = ? AND status = 1";
    private static final String GET_REVIEW_BY_PRODUCTID = "SELECT reviewId, userId, productId, rating, comment, createdAt, status FROM Review WHERE productId = ? AND status = 1";
    private static final String CREATE_REVIEW = "INSERT INTO Review (userId, productId, rating, comment) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_REVIEW = "UPDATE Review SET rating = ?, comment = ?, status = ? WHERE reviewId = ?";
    
    public ReviewDAO() {
    }
    
    public ReviewDTO getReviewById(String reviewId)
    {
        ReviewDTO review = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try
        {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_REVIEW_BY_ID);
            ps.setString(1, reviewId);
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                review = new ReviewDTO();
                review.setReviewId(rs.getString("reviewId"));
                review.setUserId(rs.getString("userId"));
                review.setProductId(rs.getString("productId"));
                review.setRating(rs.getString("rating"));
                review.setComment(rs.getString("comment"));
                review.setCreatedAt(rs.getDate("createdAt").toLocalDate());
                review.setStatus(rs.getBoolean("status"));
            }
            
        } catch (Exception e) {
            System.err.println("Error in getReviewById(reviewId): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return review;
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
    
    public boolean create(ReviewDTO review)
    {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        
        try
        {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_REVIEW);
            ps.setString(1, review.getUserId());
            ps.setString(2, review.getProductId());
            ps.setString(3, review.getRating());
            ps.setString(4, review.getComment());
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in create(review): " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
        return success;
    }
    
    public boolean update(ReviewDTO review)
    {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        
        try
         {
             conn = DbUtils.getConnection();
             ps = conn.prepareStatement(UPDATE_REVIEW);
             ps.setString(1, review.getRating());
             ps.setString(2, review.getComment());
             ps.setBoolean(3, review.isStatus());
             ps.setString(4, review.getReviewId());
             
             
             int rowsAffected = ps.executeUpdate();
             success = (rowsAffected > 0);
         }
         catch (Exception e) {
             System.err.println("Error in update(review): " + e.getMessage());
             e.printStackTrace();
         } finally {
             closeResources(conn, ps, null);
         }
        return success;
    }
    
    public boolean updateReviewStatus(String reviewId, boolean status)
    {
        ReviewDTO review = getReviewById(reviewId);
        if(review != null)
        {
            review.setStatus(status);
            return update(review);
        }
        else
        {
            return false;
        }
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
