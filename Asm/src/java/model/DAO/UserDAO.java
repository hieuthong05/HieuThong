package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DTO.UserDTO;
import utils.DbUtils;

/**
 *
 * @author ADMIN
 */
public class UserDAO {
    
     private static final String GET_ALL_USER = "SELECT userId, userName, name, email, password, role, createdAt, isActive FROM Users WHERE role = 'customer'";
     private static final String GET_USER_BY_ID = "SELECT userId, userName, name, email, password, role, createdAt, isActive FROM Users WHERE userId = ?";
     private static final String GET_USER_BY_USERNAME = "SELECT userId, userName, name, email, password, role, createdAt, isActive FROM Users WHERE (userName COLLATE Latin1_General_CS_AS = ? OR email COLLATE Latin1_General_CS_AS = ?) AND isActive = 1";
     private static final String CREATE_USER = "INSERT INTO Users (userName, name, email, password, role) VALUES (?, ?, ?, ?, ?)";
     private static final String UPDATE_USER = "UPDATE Users SET userName = ?, name = ?, email = ?, password = ? WHERE userId = ?";
     private static final String DELETE_USER = "DELETE FROM Users WHERE userId = ?";
     
     public UserDTO getUserById(String userId)
     {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try
        {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_USER_BY_ID);
            ps.setString(1, userId);
            
            rs = ps.executeQuery();
            
            if (rs.next())
            {
                user = new UserDTO();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getDate("createdAt").toLocalDate());
                user.setIsActive(rs.getBoolean("isActive"));
            }    
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getUserById" + e.getMessage());
        }
           
        return user;
     }
     
     public UserDTO getUserByUsername(String username)
    {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try
        {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_USER_BY_USERNAME);
            ps.setString(1, username);
            ps.setString(2, username);
            rs = ps.executeQuery();
            
            if (rs.next())
            {
                user = new UserDTO();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getDate("createdAt").toLocalDate());
                user.setIsActive(rs.getBoolean("isActive"));
            }    
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getUserByUsername" + e.getMessage());
        }
           
        return user;
    }
     
     public boolean login(String username, String password)
    {
        UserDTO user = getUserByUsername(username);
        if (user != null)
        {
            if (user.getPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }
     
     public boolean isUserExists(String userName)
     {
         return getUserByUsername(userName) != null;
     }
     
     public boolean create(UserDTO user)
     {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        
         try
         {
             conn = DbUtils.getConnection();
             
             ps = conn.prepareStatement(CREATE_USER);
             ps.setString(1, user.getUserName());
             ps.setString(2, user.getName());
             ps.setString(3, user.getEmail());
             ps.setString(4, user.getPassword());
             ps.setString(5, user.getRole());
             
             int rowsAffected = ps.executeUpdate();
             success = (rowsAffected > 0);
         }
         catch (Exception e) {
             e.printStackTrace();
             System.err.println("Error in create(): " + e.getMessage());
         } finally {
             closeResources(conn, ps, null);
         }
         return success;
     }
     
     public List<UserDTO> getAllUser()
     {
         List<UserDTO> list = new ArrayList<>();
         Connection conn = null;
         PreparedStatement ps = null;
         ResultSet rs = null;
         try
         {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_USER);
            rs = ps.executeQuery();
             
            while (rs.next())
            {
                UserDTO user = new UserDTO();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getDate("createdAt").toLocalDate());
                user.setIsActive(rs.getBoolean("isActive"));
                
                list.add(user);
            }
         }
         catch (Exception e) {
             System.err.println("Error in getAllUser(): " + e.getMessage());
             e.printStackTrace();
         } finally {
             closeResources(conn, ps, rs);
         }
         return list;
     }
     
     public boolean update(UserDTO user)
     {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        
         try
         {
             conn = DbUtils.getConnection();
             ps = conn.prepareStatement(UPDATE_USER);
             ps.setString(1, user.getUserName());
             ps.setString(2, user.getName());
             ps.setString(3, user.getEmail());
             ps.setString(4, user.getPassword());
             ps.setString(5, user.getUserId());
             
             int rowsAffected = ps.executeUpdate();
             success = (rowsAffected > 0);
         }
         catch (Exception e) {
             System.err.println("Error in update(user): " + e.getMessage());
             e.printStackTrace();
         } finally {
             closeResources(conn, ps, null);
         }
         return success;
     }
     
     public boolean delete(String userId)
     {
         boolean success = false;
         Connection conn = null;
         PreparedStatement ps = null;
         
         try
         {
             conn = DbUtils.getConnection();
             ps = conn.prepareStatement(DELETE_USER);
             ps.setString(1, userId);
             
             int rowsAffected = ps.executeUpdate();
             success = (rowsAffected > 0);
         }
         catch (Exception e) {
             System.err.println("Error in  delete(userId): " + e.getMessage());
             e.printStackTrace();
         } finally {
             closeResources(conn, ps, null);
         }
         return success;
     }
     
     private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs)
    {
        try
        {
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
