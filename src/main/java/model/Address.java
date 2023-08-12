package model;

public class Address {
	
	private int id;
	private String streetAddress;
	private String province;
	private String country;
	private String postalCode;
	private String phone;
	
	public Address(int id, String streetAddress, String province, String country, String postalCode, String phone) {
		super();
		this.id = id;
		this.streetAddress = streetAddress;
		this.province = province;
		this.country = country;
		this.postalCode = postalCode;
		this.phone = phone;
	}
	
	public Address() {}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStreetAddress() {
		return streetAddress;
	}
	
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return "Address [id=" + id + ", streetAddress=" + streetAddress + ", province=" + province + ", counry="
				+ country + ", postalCode=" + postalCode + ", phone=" + phone + "]";
	}
}
