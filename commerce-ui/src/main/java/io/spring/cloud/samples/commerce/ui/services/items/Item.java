package io.spring.cloud.samples.commerce.ui.services.items;



/** Item class with all getter and setter methods for the item related  
 * Details are same as the item details in item service**/


public class Item {
	
	private Long id;
    private String name;
    private String description;
    private String category;
    private String price;
    

    public Item() {
    }

    public Item(Long id, String name, String description, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //using toString and String builder to append and convert details to a string
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append('}');
        return sb.toString();
    }
    

}
