
package model.DTO;

public class BrandDTO {
    private int brandId;
    private String name;
    private String origin;
    private int foundedYear;
    
    private String description;

    public BrandDTO() {
    }

    public BrandDTO(int brandId, String name, String origin, int foundedYear, String description) {
        this.brandId = brandId;
        this.name = name;
        this.origin = origin;
        this.foundedYear = foundedYear;
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(int foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
