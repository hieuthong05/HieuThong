package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DbUtils;

/**
 *
 * @author ADMIN
 */
public class UserDAO {
    
    private static final String GET_USER_BY_USERNAME = "SELECT Username, Name, Password, Role FROM tblUsers WHERE Username = ?";

    public UserDAO() {
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
            rs = ps.executeQuery();
            
            if (rs.next())
            {
                user = new UserDTO();
                user.setUsername(rs.getString("Username"));
                user.setName(rs.getString("Name"));
                user.setPassword(rs.getString("Password"));
                user.setRole(rs.getString("Role"));              
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
