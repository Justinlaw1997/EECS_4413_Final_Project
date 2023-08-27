package model;

import java.io.Serializable;

public class Brand implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	
	// For creating a new database entry (id values are auto-incrementing)
	public Brand(String name) {
		super();
		this.name = name;
	}
	
	// For querying an existing brand from the database
	public Brand(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + "]";
	}		
}
