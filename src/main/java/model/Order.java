package model;

import java.util.List;

public class Order {
	
	private int id;
	private User customer;
	private String dateOfPurchase;
	private List<Item> items;
	
	public Order(int id, User customer, String dateOfPurchase, List<Item> items) {
		super();
		this.id = id;
		this.customer = customer;
		this.items = items;
		this.dateOfPurchase = dateOfPurchase;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public User getCustomer() {
		return customer;
	}
	
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	
	public String getDateOfPurchase() {
		return dateOfPurchase;
	}
	
	public void setDateOfPurchase(String dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}
	
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", dateOfPurchase=" + dateOfPurchase + ", items=" + items
				+ "]";
	}	
}
