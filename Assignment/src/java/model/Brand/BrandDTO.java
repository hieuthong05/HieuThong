
package model.Brand;

public class BrandDTO {
    private int brandId;
    private String name;
    private int foundedYear;
    private String description;

    public BrandDTO() {
    }

    public BrandDTO(int brandId, String name, int foundedYear, String description) {
        this.brandId = brandId;
        this.name = name;
        this.foundedYear = foundedYear;
        this.description = description;
    }
    
    
}
