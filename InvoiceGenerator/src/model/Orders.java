package model;

public class Orders {
	private int orderId;
	private int customerId;
	private String orderDate;
	private String invoiceDate;
	private double orderTotal;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate= orderDate;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	
	public Orders() {};
	public Orders(int orderId, int customerId, String orderDate,
					String invoiceDate, double orderTotal) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.invoiceDate = invoiceDate;
		this.orderTotal = orderTotal;
	}
	
	@Override
	public String toString() {
		return "[" + orderId + "] customer id: " + customerId + ", Order date: " + 
				orderDate + ", Invoice date: " + invoiceDate + ", Total: " + orderTotal;
	}
}
