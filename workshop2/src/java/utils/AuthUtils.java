
package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.UserDTO;

public class AuthUtils {
    
    public static UserDTO getCurrentUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session!=null){
            return (UserDTO) session.getAttribute("user");
        }
        return null;
    }
    
    public static boolean isLoggedIn(HttpServletRequest request){
        return AuthUtils.getCurrentUser(request) !=null;        
    }
    
    public static boolean hasRole(HttpServletRequest request, String role){
        UserDTO user = getCurrentUser(request);
        if(user !=null){
            String userRole = user.getRole();
            return userRole.equals(role);
        }
        
        return false;
    }
    
    public static boolean isStudent(HttpServletRequest request){
        return hasRole(request,"Student");
    }
    
    public static boolean isInstructor(HttpServletRequest request){
        return hasRole(request,"Instructor");
    }
    
    public static String getLoginURL(){
        return "MainController?action=login";
    }
    
    public static String getAccessDeniedMessage (String action){
        return "You don't have permission to do this.Please contact instructor.";
    }
}
