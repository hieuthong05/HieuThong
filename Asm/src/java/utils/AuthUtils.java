
package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.DTO.UserDTO;

/**
 *
 * @author ADMIN
 */
public class AuthUtils {
    
    public static UserDTO getCurrentUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        if (session != null)
        {
            return (UserDTO) session.getAttribute("user");
        }
        return null;
    }
    
    public static boolean isLoggedIn(HttpServletRequest request)
    {
       return getCurrentUser(request) != null;
    }
    
    public static boolean hasRole(HttpServletRequest request, String role)
    {
        UserDTO user = getCurrentUser(request);
        if (user != null)
        {
            return user.getRole().equals(role);
        }
        return false;
    }
    
    public static boolean isAdmin(HttpServletRequest request)
    {
        return hasRole(request, "admin");
    }
    
    public static boolean isCustomer(HttpServletRequest request)
    {
        return hasRole(request, "customer");
    }
    
    public static String getAccessDeniedMessage(String action)
    {
        return "You don't have permission access to " + action + ". Please contact administrator.";
    }
}
