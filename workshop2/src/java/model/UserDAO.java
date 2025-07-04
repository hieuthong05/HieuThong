
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class UserDAO {
    public boolean login(String username, String password){
        try{
            UserDTO user = getUserName(username);
            if(user!=null){
                if(user.getPassword().equals(password)){
                    return true;
                }
            }
        }catch(Exception e){
            
        }
        return false;
    }
    
    public UserDTO getUserName(String username) {
        try{
            String sql = "SELECT * FROM tblUsers"+" WHERE username=?";
            
            //Connect
            Connection conn = DbUtils.getConnection();
            
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, username);
            ResultSet rs = pr.executeQuery();
            
            while(rs.next()){
                String userName = rs.getString("username");
                String name = rs.getString("name");
                String Password = rs.getString("password");
                String role = rs.getString("role");
                
                UserDTO userDTO = new UserDTO(userName, name, Password, role);
                return userDTO;
            }
            
            return null;
        } catch(Exception e){
            return null;
        }
    }
    
    public List<UserDTO> getAllUser() {
        List<UserDTO> userList = new ArrayList<>();
        String sql = "SELECT username,name,password,role FROM tblUsers ORDER BY username";
        try{
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                UserDTO user = new UserDTO();
                user.setUserName(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                userList.add(user);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
