package model;

import java.io.Serializable;

public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String itemID;
	private String name;
	private String description;
	private Category category;
	private Brand brand;
	private int price;
	private int quantityStocked;
	private int quantityPurchased;
	private String image;
	
	public Item() {}
	
	public String getItemID() {
		return itemID;
	}
	
	public void setItemID(String itemID) {
		this.itemID = itemID;
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
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Brand getBrand() {
		return brand;
	}
	
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getQuantityStocked() {
		return quantityStocked;
	}
	
	public void setQuantityStocked(int quantityStocked) {
		this.quantityStocked = quantityStocked;
	}
	
	public int getQuantityPurchased() {
		return quantityPurchased;
	}
	
	public void setQuantityPurchased(int quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Item [itemID=" + itemID + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", brand=" + brand + ", price=" + price + ", quantityStocked=" + quantityStocked
				+ ", quantityPurchased=" + quantityPurchased + ", image=" + image + "]";
	}
	
}
