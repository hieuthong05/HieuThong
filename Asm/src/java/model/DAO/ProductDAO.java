
package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DTO.ProductDTO;
import utils.DbUtils;

public class ProductDAO {

    //SQL
    private static final String GET_ALL_PRODUCTS = "SELECT productId,name,brandId,categoryId,price,stock,description,imageUrl FROM Product";
    private static final String GET_PRODUCT_BY_ID = "SELECT name,brandId,categoryId,price,stock,description,imageUrl FROM Product WHERE productId = ?";
    private static final String GET_PRODUCT_BY_CATEGORY = "SELECT productId,name,brandId,price,stock,description,imageUrl FROM Product WHERE categoryId = ?";
    private static final String GET_PRODUCT_BY_BRAND = "SELECT productId,name,categoryId,price,stock,description,imageUrl FROM Product WHERE brandId = ?";
    
    private static final String CREATE_PRODUCT = "INSERT INTO Product(name,brandId,categoryId,price,stock,description,imageUrl) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "UPDATE Product SET name=?,brandId=?,categoryId=?,price=?,stock=?,description=?,imageUrl=? WHERE productId=?";
    private static final String DELETE_PRODUCT = "DELETE FROM Product WHERE productId=?";

    public List<ProductDTO>getAll(){
        List<ProductDTO> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_PRODUCTS);
            rs = ps.executeQuery();
            
            while(rs.next()){
                ProductDTO product = new ProductDTO();
                product.setProductId(rs.getInt("productId"));
                product.setName(rs.getString("name"));
                product.setBrandId(rs.getInt("brandId"));
                product.setCategoryId(rs.getInt("categoryId"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setDescription(rs.getString("description"));
                product.setImageUrl(rs.getString("imageUrl"));
                
                products.add(product);
            }
            
        }catch (Exception e){
            
        }finally{
            closeResources(conn,ps,rs);
        }
        return products;
    }
    
    public ProductDTO getProductByCategory(String id) {
        ProductDTO product = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PRODUCT_BY_CATEGORY);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                product = new ProductDTO();
                product.setProductId(rs.getInt("productId"));
                product.setName(rs.getString("name"));
                product.setBrandId(rs.getInt("brandId"));
                product.setCategoryId(rs.getInt("categoryId"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setDescription(rs.getString("description"));
                product.setImageUrl(rs.getString("imageUrl"));
                
            }
        } catch (Exception e) {
            System.err.println("Error in getProductByName(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return product;
    }


    //
    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try{
            if(rs!=null){
                rs.close();
            }
            if(ps!=null){
                ps.close();
            }
            if(conn!=null){
                conn.close();
            }
            
        }catch(Exception e){
            System.err.println("Error closing resources: "+ e.getMessage());
            e.printStackTrace();
        }
    }

}   
