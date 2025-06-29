package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author ADMIN
 */
public class ProjectDAO {
    
    private static final String GET_ALL_PROJECTS = "SELECT project_id, project_name, Description, Status, estimated_launch FROM tblStartupProjects";
    private static final String CREATE_PROJECT = "INSERT INTO tblStartupProjects (project_id, project_name, Description, Status, estimated_launch) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_PROJECT_STATUS = "UPDATE tblStartupProjects SET Status = ? WHERE project_id = ?";

    public ProjectDAO() {
    }
    
    public List<ProjectDTO> getAllProjects()
    {
        List<ProjectDTO> projects = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try
        {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_PROJECTS);
            rs = ps.executeQuery();
            
            while (rs.next())
            {
                ProjectDTO proj = new ProjectDTO();
                proj.setProjId(rs.getInt("project_id"));
                proj.setProjName(rs.getString("project_name"));
                proj.setDescription(rs.getString("Description"));
                proj.setStatus(rs.getString("Status"));
                proj.setEst(rs.getDate("estimated_launch").toLocalDate());
                
                projects.add(proj);
            }            
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getAllProjects(): " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        
        return projects;
    }
    
    public List<ProjectDTO> getProjectsByName(String name)
    {
        List<ProjectDTO> projects = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = GET_ALL_PROJECTS + "  WHERE project_name like ?";
        
        try
        {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
            
            while (rs.next())
            {
                ProjectDTO proj = new ProjectDTO();
                proj.setProjId(rs.getInt("project_id"));
                proj.setProjName(rs.getString("project_name"));
                proj.setDescription(rs.getString("Description"));
                proj.setStatus(rs.getString("Status"));
                proj.setEst(rs.getDate("estimated_launch").toLocalDate());
                
                projects.add(proj);
            }
            
            return projects;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getProjectsByName(): " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return projects;
    }
    
    public boolean create(ProjectDTO proj)
    {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        
        try
        {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_PROJECT);
            
            ps.setInt(1, proj.getProjId());
            ps.setString(2, proj.getProjName());
            ps.setString(3, proj.getDescription());
            ps.setString(4, proj.getStatus());
            ps.setDate(5, java.sql.Date.valueOf(proj.getEst()));
            
            int rowsAffected = ps.executeUpdate();
            return (rowsAffected > 0);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in create(): " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
        return success;
    }
    
    public ProjectDTO getProjectById(int id)
    {
        ProjectDTO proj = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = GET_ALL_PROJECTS + " WHERE project_id = ?";
        
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next())
            {
                proj = new ProjectDTO();
                proj.setProjId(rs.getInt("project_id"));
                proj.setProjName(rs.getString("project_name"));
                proj.setDescription(rs.getString("Description"));
                proj.setStatus(rs.getString("Status"));
                proj.setEst(rs.getDate("estimated_launch").toLocalDate());
            }
            return proj;
        } catch (Exception e) {
            System.err.println("Error in getProjectById(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return proj;
    }
    
    public boolean isProjectExists(int id)
    {
        return getProjectById(id) != null;
    }
    
    public boolean updateProjectStatus(ProjectDTO proj)
    {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        
        try
        {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_PROJECT_STATUS);
            
            ps.setString(1, proj.getStatus());
            ps.setInt(2, proj.getProjId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;     
        }
        catch (Exception e) {
            System.err.println("Error in updateProjectStatus(): " + e.getMessage());
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
