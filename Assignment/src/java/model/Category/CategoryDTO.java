
package model.Category;

public class CategoryDTO {
    private int categoryId;
    private String name;
    private String description;

    public CategoryDTO() {
    }

    public CategoryDTO(int categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }
    
    
}
