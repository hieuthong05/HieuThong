
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
    private static final String GET_PRODUCT_BY_ID = "SELECT productId,name,brandId,categoryId,price,stock,description,imageUrl FROM Product WHERE productId = ?";
    private static final String GET_PRODUCT_BY_CATEGORY = "SELECT productId,name,brandId,price,stock,description,imageUrl FROM Product WHERE categoryId = ?";
    private static final String GET_PRODUCT_BY_BRAND = "SELECT productId,name,categoryId,price,stock,description,imageUrl FROM Product WHERE brandId = ?";
    private static final String GET_PRODUCT_BY_NAME = "SELECT productId,name,brandId,categoryId,price,stock,description,imageUrl FROM Product WHERE name LIKE ?";

    
    private static final String CREATE_PRODUCT = "INSERT INTO Product(name,brandId,categoryId,price,stock,description,imageUrl) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "UPDATE Product SET name=?,brandId=?,categoryId=?,price=?,stock=?,description=?,imageUrl=? WHERE productId=?";
    private static final String DELETE_PRODUCT = "DELETE FROM Product WHERE productId = ?";

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
    
    public ProductDTO getProductById(int productId) {
        ProductDTO product = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PRODUCT_BY_ID);
            ps.setInt(1, productId);
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
            System.err.println("Error in getProductById(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return product;
    }
    
    public List<ProductDTO> getProductByName(String name) {
        List<ProductDTO> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PRODUCT_BY_NAME);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
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

        } catch (Exception e) {
            System.err.println("Error in getProductByName(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return products;
    }

    
    
    public List<ProductDTO> getProductsByCategoryId(int categoryId) {
        List<ProductDTO> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PRODUCT_BY_CATEGORY);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO product = new ProductDTO();
                product.setProductId(rs.getInt("productId"));
                product.setName(rs.getString("name"));
                product.setBrandId(rs.getInt("brandId"));
                product.setCategoryId(categoryId);
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setDescription(rs.getString("description"));
                product.setImageUrl(rs.getString("imageUrl"));

                products.add(product);
            }

        } catch (Exception e) {
            System.err.println("Error in getProductsByCategoryId(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return products;
    }
    public List<ProductDTO> getProductsByBrandId(int brandId) {
        List<ProductDTO> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PRODUCT_BY_BRAND);
            ps.setInt(1, brandId);
            rs = ps.executeQuery();
            
            while(rs.next()){
                ProductDTO product = new ProductDTO();
                product.setProductId(rs.getInt("productId"));
                product.setName(rs.getString("name"));
                product.setBrandId(brandId);
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
    public void create(ProductDTO product) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_PRODUCT);
            ps.setString(1, product.getName());
            ps.setInt(2, product.getBrandId());
            ps.setInt(3, product.getCategoryId());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getStock());
            ps.setString(6, product.getDescription());
            ps.setString(7, product.getImageUrl());

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
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

    public boolean update(ProductDTO product) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_PRODUCT);

            ps.setString(1, product.getName());
            ps.setInt(2, product.getBrandId());
            ps.setInt(3, product.getCategoryId());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getStock());
            ps.setString(6, product.getDescription());
            ps.setString(7, product.getImageUrl());
            ps.setInt(8, product.getProductId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in update(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return false;
    }
    
    public boolean delete(String productId)
    {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        
        try
         {
             conn = DbUtils.getConnection();
             ps = conn.prepareStatement(DELETE_PRODUCT);
             ps.setString(1, productId);
             
             int rowsAffected = ps.executeUpdate();
             success = (rowsAffected > 0);
         }
         catch (Exception e) {
             System.err.println("Error in  delete(productId): " + e.getMessage());
             e.printStackTrace();
         } finally {
             closeResources(conn, ps, null);
         }
        return success;
    }


}   
