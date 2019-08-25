package model;

public class Orders_Details {
	private int orderId;
	private int itemNumber;
	private int productId;
	private double purchasePrice;
	private int itemQuantity;
	private int itemTax;
	private double itemTotal;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public int getItemTax() {
		return itemTax;
	}
	public void setItemTax(int itemTax) {
		this.itemTax = itemTax;
	}
	public double getItemTotal() {
		return itemTotal;
	}
	public void setItemTotal(double itemTotal) {
		this.itemTotal = itemTotal;
	}
	
	public Orders_Details() {};
	public Orders_Details(int orderId, int itemNumber, int productId, double purchasePrice,
							int itemQuantity, int itemTax, double itemTotal) {
		this.orderId = orderId;
		this.itemNumber = itemNumber;
		this.productId = productId;
		this.purchasePrice = purchasePrice;
		this.itemQuantity = itemQuantity;
		this.itemTax = itemTax;
		this.itemTotal = itemTotal;
	}
	
	@Override
	public String toString() {
		return "Order[" + orderId + "], Item[" + itemNumber + "], product[" +
				productId + "], Price: " + purchasePrice + ", quantity: " +
				itemQuantity + ", tax: " + itemTax + ", Item total: " + itemTotal;
	}
	
}
