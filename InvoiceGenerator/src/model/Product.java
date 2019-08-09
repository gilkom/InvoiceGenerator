package model;

public class Product {
	private int productId;
	private String productName;
	private double productPrice;
	private int productTax;
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public int getProductTax() {
		return productTax;
	}
	public void setProductTax(int productTax) {
		this.productTax = productTax;
	}
	
	public Product() {};
	public Product(int productId, String productName, double productPrice, int productTax) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productTax = productTax;
	}
	@Override
	public String toString() {
		return "[" + productId + "] - " + productName + ", price: " + productPrice +
				", tax: " + productTax + "%";
	}
}
