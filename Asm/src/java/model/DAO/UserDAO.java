package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.DTO.UserDTO;
import utils.DbUtils;

/**
 *
 * @author ADMIN
 */
public class UserDAO {
    
     private static final String GET_USER_BY_USERNAME = "SELECT userId, userName, name, email, password, role, createdAt FROM Users WHERE userName = ?";
     
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
}
