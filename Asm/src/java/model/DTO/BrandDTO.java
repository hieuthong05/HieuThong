
package model.DTO;

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
