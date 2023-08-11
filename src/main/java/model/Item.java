package model;

import java.util.Comparator;

public class Item {
	
	private String itemID;
	private String name;
	private String description;
	private String category;
	private String brand;
	private int price;
	private int quantity;
	
	public Item(String itemID, String name, String description, String category, String brand, int price, int quantity) {
		super();
		this.itemID = itemID;
		this.name = name;
		this.description = description;
		this.category = category;
		this.brand = brand;
		this.price = price;
		this.quantity = quantity;
	}
	
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
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "Item [itemID=" + itemID + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", brand=" + brand + ", price=" + price + ", quantity=" + quantity + "]";
	}
}
