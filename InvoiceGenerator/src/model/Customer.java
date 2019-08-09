package model;

public class Customer {
	private int customerId;
	private String customerName;
	private String customerStreet;
	private String customerCity;
	private String customerPostCode;
	private String customerNip;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerStreet() {
		return customerStreet;
	}
	public void setCustomerStreet(String customerStreet) {
		this.customerStreet = customerStreet;
	}
	public String getCustomerCity() {
		return customerCity;
	}
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
	public String getCustomerPostCode() {
		return customerPostCode;
	}
	public void setCustomerPostCode(String customerPostCode) {
		this.customerPostCode = customerPostCode;
	}
	public String getCustomerNip() {
		return customerNip;
	}
	public void setCustomerNip(String customerNip) {
		this.customerNip = customerNip;
	}
	
	public Customer() {};
	public Customer(int customerId, String customerName, String customerStreet,
					String customerCity, String customerPostCode, String customerNip) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerStreet = customerStreet;
		this.customerCity = customerCity;
		this.customerPostCode = customerPostCode;
		this.customerNip = customerNip;
	}
	@Override
	public String toString() {
		return "["+customerId + "] - " + customerName + ", Address: " + customerStreet +
				", " + customerCity + ", " + customerPostCode + ", NIP: " + customerNip;
	}
}

