package control;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import invoice.*;
import model.*;

public class Control {
	
	
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//-------Customer database-JTable---------------------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	public static void addCustomer(String customerName, String customerStreet,
			String customerCity, String customerPostCode, String customerNip)
	{
		Invoice i = new Invoice();
		i.insertCustomer(customerName, customerStreet, customerCity,
						customerPostCode, customerNip);
	}
	
	public static void removeCustomer(int customerId) {
		Invoice i = new Invoice();
		i.deleteCustomer(customerId);
	}
	
	public static void updCustomer(int customerId, String customerName, String customerStreet,
							String customerCity, String customerPostCode, String customerNip)
	{
		Invoice i = new Invoice();
		i.updateCustomer(customerId, customerName, customerStreet, customerCity, 
							customerPostCode, customerNip);
	}
	public static DefaultTableModel populateCustomer() {
		Invoice i = new Invoice();
		List<Customer> customers = i.selectCustomer();
		
		DefaultTableModel model = new DefaultTableModel(new String[] {
				"Id", "Name", "Street", "City", "Post code", "Nip"},0);
		
		for(Customer c : customers) {
			model.addRow(new Object[] {c.getCustomerId(),c.getCustomerName(), c.getCustomerStreet(),
			c.getCustomerCity(), c.getCustomerPostCode(), c.getCustomerNip()});
		}
		
		return model;
	}
	
		

	public static DefaultTableModel populateCustomerLike(String likeName) {
		Invoice i = new Invoice();
		List<Customer> customers = i.selectCustomerLike(likeName);
		
		DefaultTableModel model = new DefaultTableModel(new String[] {
				"Id", "Name", "Street", "City", "Post code", "Nip"},0);
		
		for(Customer c : customers) {
			model.addRow(new Object[] {c.getCustomerId(),c.getCustomerName(), c.getCustomerStreet(),
			c.getCustomerCity(), c.getCustomerPostCode(), c.getCustomerNip()});
		}
		
		return model;
	}
	
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//-------Product database-JTable---------------------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------

	public static void addProduct(String productName, double productPrice,int productTax)
	{
		Invoice i = new Invoice();
		i.insertProduct(productName, productPrice, productTax);
	}
	
	public static void removeProduct(int productId) {
		Invoice i = new Invoice();
		i.deleteProduct(productId);
	}
	
	public static void updProduct(int productId, String productName,
									double productPrice,int productTax)
	{
		Invoice i = new Invoice();
		i.updateProduct(productId, productName, productPrice, productTax);
	}
	
	public static DefaultTableModel populateProduct() {
		Invoice i = new Invoice();
		List<Product> products = i.selectProduct();
		
		DefaultTableModel model = new DefaultTableModel(new String[] {
				"Id", "Name", "Price", "Tax"},0);
		
		for(Product p : products) {
			model.addRow(new Object[] {p.getProductId(),p.getProductName(),
					p.getProductPrice(),p.getProductTax()});
		}
		
		return model;
	}
	public static DefaultTableModel populateProductLike(String likeName) {
		Invoice i = new Invoice();
		List<Product> products = i.selectProductLike(likeName);
		
		DefaultTableModel model = new DefaultTableModel(new String[] {
				"Id", "Name", "Price", "Tax"},0);
		
		for(Product p : products) {
			model.addRow(new Object[] {p.getProductId(),p.getProductName(),
					p.getProductPrice(),p.getProductTax()});
		}
		
		return model;
	}
	
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//-------Orders_Details database-JTable---------------------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	public static void populateOrders_Details(JTable items, int rowIndex) {
		Invoice i = new Invoice();
		List<Product> products = i.selectProductLikeId(rowIndex);
		DefaultTableModel model = (DefaultTableModel) items.getModel();

		int itemNo = items.getRowCount(); 
		int quantity = 1;
		double totalNet = 0;
		double taxAmount = 0;
		double totalGross = 0;
		
		for(Product p : products) {
			itemNo++;
			totalNet = quantity * p.getProductPrice();
			taxAmount = (p.getProductTax()*p.getProductPrice()* quantity)/100;
			totalGross = totalNet + taxAmount;
			
			model.addRow(new Object[] {itemNo, p.getProductName(), quantity,
					p.getProductPrice(),totalNet ,p.getProductTax(),
					taxAmount, totalGross});
		}

	}
	public static void updItem(JTable items,  
			int columnIndex, int rowIndex, int productQuantity,
			double productPrice,int productTax)
	{

		double totalNet = productQuantity * productPrice;
		double taxAmount = productTax * (productPrice* productQuantity)/100;
		double totalGross = totalNet + taxAmount;
		
		items.setValueAt(productQuantity,  rowIndex, 2);
		items.setValueAt(productPrice,  rowIndex, 3);
		items.setValueAt(totalNet,  rowIndex, 4);
		items.setValueAt(productTax,  rowIndex, 5);
		items.setValueAt(taxAmount,  rowIndex, 6);
		items.setValueAt(totalGross,  rowIndex, 7);
	}
	
	
	public static void updateOrders_Details(JTable items, DefaultTableModel model, int columnIndex, int rowIndex) {
		//Invoice i = new Invoice();
		//List<Product> products = i.selectProductLikeId(rowIndex);

		JOptionPane.showMessageDialog(null, "updateOrders_Details1" + 
				"columnIndex: " + columnIndex+ "rowIndex: "+ rowIndex);
		
		DefaultTableModel model1 = (DefaultTableModel)items.getModel();
		
		JOptionPane.showMessageDialog(null, "updateOrders_Details2" + 
				"columnIndex: " + columnIndex+ "rowIndex: "+ rowIndex);
		
		int value = 15;
		
		JOptionPane.showMessageDialog(null, "updateOrders_Details3" + 
				"columnIndex: " + columnIndex+ "rowIndex: "+ rowIndex);
		DefaultTableModel model2 = new DefaultTableModel();
		model2 = model1;
		model.setValueAt(value, 0, 2);
		
		JOptionPane.showMessageDialog(null, "updateOrders_Details4" + 
				"columnIndex: " + columnIndex+ "rowIndex: "+ rowIndex);
		int itemNo = items.getRowCount(); 
		int quantity = 1;
		double totalNet = 0;
		double taxAmount = 0;
		double totalGross = 0;
		

		/*for(Product p : products) {
			itemNo++;
			totalNet = quantity * p.getProductPrice();
			taxAmount = (p.getProductTax()*p.getProductPrice()* quantity)/100;
			totalGross = totalNet + taxAmount;
			
			model.addRow(new Object[] {itemNo, p.getProductName(), quantity,
					p.getProductPrice(),totalNet ,p.getProductTax(),
					taxAmount, totalGross});
		}*/

	}
	
}
