/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.DTO;

/**
 *
 * @author ASUS
 */
public class ProductDTO {
    private int productId;
    private String name;
    private int brandId;
    private double price;
    private int stock;
    private String description;
    private String imageUrl;
    private int CategoryId;

    public ProductDTO() {
    }

    public ProductDTO(int productId, String name, int brandId, double price, int stock, String description, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.brandId = brandId;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    
    
    public ProductDTO(int productId, String name, int brandId, double price, int stock, String description, String imageUrl, int CategoryId) {
        this.productId = productId;
        this.name = name;
        this.brandId = brandId;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.imageUrl = imageUrl;
        this.CategoryId = CategoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int CategoryId) {
        this.CategoryId = CategoryId;
    }
    
    
}
