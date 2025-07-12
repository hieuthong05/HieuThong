
package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DTO.BrandDTO;
import utils.DbUtils;

public class BrandDAO {
    private static final String GET_ALL_BRAND = "SELECT brandId,name,origin,foundedYear,description FROM Brand";
    
    
    public List<BrandDTO> getAll() {
        List<BrandDTO> brands = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_BRAND);
            rs = ps.executeQuery();

            while (rs.next()) {
                BrandDTO brand = new BrandDTO();
                
                brand.setBrandId(rs.getInt("brandId"));
                brand.setName(rs.getString("name"));
                brand.setOrigin(rs.getString("origin"));
                brand.setFoundedYear(rs.getInt("foundedYear"));
                brand.setDescription(rs.getString("description"));

                brands.add(brand);
            }
        } catch (Exception e) {
            System.err.println("Error in getAll(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return brands;
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
