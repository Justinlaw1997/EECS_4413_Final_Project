package model;

import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private User customer;
	private String dateOfPurchase;
	private int total;
	private HashMap<Item, Integer> items;
	
	public Order() {}

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
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public HashMap<Item, Integer> getItems() {
		return items;
	}

	public void setItems(HashMap<Item, Integer> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", dateOfPurchase=" + dateOfPurchase + ", total=" + total
				+ ", items=" + items + "]";
	}

}
