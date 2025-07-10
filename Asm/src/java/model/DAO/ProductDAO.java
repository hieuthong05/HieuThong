
package model.DAO;

public class ProductDAO {

    //SQL
    private static final String GET_ALL_PRODUCTS = "SELECT productId,name,brandId,categoryId,price,stock,description,imageUrl FROM Product";
    private static final String GET_PRODUCT_BY_ID = "SELECT name,brandId,categoryId,price,stock,description,imageUrl FROM Product WHERE productId = ?";
    private static final String GET_PRODUCT_BY_CATEGORY = "SELECT productId,name,brandId,price,stock,description,imageUrl FROM Product WHERE categoryId = ?";
    private static final String GET_PRODUCT_BY_BRAND = "SELECT productId,name,categoryId,price,stock,description,imageUrl FROM Product WHERE brandId = ?";
    
    private static final String CREATE_PRODUCT = "INSERT INTO Product(name,brandId,categoryId,price,stock,description,imageUrl) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "UPDATE Product SET name=?,brandId=?,categoryId=?,price=?,stock=?,description=?,imageUrl=? WHERE productId=?";
    private static final String DELETE_PRODUCT = "DELETE FROM Product WHERE productId=?";
}   
