package control;

//import java.util.Date;
import java.util.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import invoice.*;
import model.*;

public class Control {

	public static int lastId;
	
	
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
	
	public static String selectCustomer(int rowIndex) {
		String result = "";
		Invoice i = new Invoice();
		List<Customer> customers = i.selectCustomerLikeId(rowIndex);
		result = customers.toString();
		result = result.substring(1);
		result = result.substring(0,result.length() - 1);
		return result;
	}
	// getting customer string and removing signs [ and ] from string

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
	//-------Orders database-JTable---------------------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	public static void addOrders(int customerId, String orderDate, String invoiceDate,
					double orderTotal)
	{
		Invoice i = new Invoice();
		i.insertOrders(customerId, orderDate, invoiceDate, orderTotal);
		lastId = i.lastId;
	}
	
	
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//-------Orders_Details database-JTable---------------------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	public static void addOrders_Details(JTable items, int lastId, Map<Integer,Integer> mapId) {
		Invoice i = new Invoice();
		int leng = items.getRowCount();
		
		for(int j = 0; j < leng; j++) {
			System.out.println("j: " + j + " item[0]: " + items.getValueAt(j, 0)
			+ ", item[1]: " + items.getValueAt(j, 1));
		i.insertOrders_Details(
				lastId , 
				Integer.parseInt(items.getValueAt(j, 0).toString()),
				mapId.get(j+1),
				Double.parseDouble(items.getValueAt(j, 3).toString()), 
				Integer.parseInt(items.getValueAt(j, 2).toString()),
				Integer.parseInt(items.getValueAt(j, 5).toString()), 
				Double.parseDouble(items.getValueAt(j, 7).toString()));
		}
	}
	public static void populateOrders_Details(JTable items, int rowIndex, Map<Integer,Integer> mapId) {
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
			taxAmount = Math.round(((
					p.getProductTax()*p.getProductPrice()* quantity)/100)*100.0)/100.0;
			totalGross = Math.round((totalNet + taxAmount)*100.0)/100.0;
			//Math.round(totalGross *100.0)/100.0
			mapId.put(itemNo, p.getProductId());
			System.out.println("map0,1,2,3,4,5: "+mapId.get(0)+ ","+mapId.get(1)+ ","+
					mapId.get(2)+ ","+mapId.get(3)+ ","+mapId.get(4)+","+mapId.get(5));
			model.addRow(new Object[] {itemNo, p.getProductName(), quantity,
					p.getProductPrice(),totalNet ,p.getProductTax(),
					taxAmount, totalGross});
		}

	}
	public static void updItem(JTable items,  
			int columnIndex, int rowIndex, int productQuantity,
			double productPrice,int productTax)
	{

		double totalNet = Math.round((productQuantity * productPrice)*100.0)/100.0;
		double taxAmount = Math.round((
				productTax * (productPrice* productQuantity)/100)*100.0)/100.0;
		double totalGross = Math.round((totalNet + taxAmount)*100.0)/100.0;
		
		items.setValueAt(productQuantity,  rowIndex, 2);
		items.setValueAt(productPrice,  rowIndex, 3);
		items.setValueAt(totalNet,  rowIndex, 4);
		items.setValueAt(productTax,  rowIndex, 5);
		items.setValueAt(taxAmount,  rowIndex, 6);
		items.setValueAt(totalGross,  rowIndex, 7);
	}
	
	public static void removeItem(JTable items, int rowIndex, Map<Integer,Integer> mapId) {

		DefaultTableModel model = (DefaultTableModel)items.getModel();
		model.removeRow(rowIndex-1);
		
		//setting the right numbers after removing key from map
		int mapSize = mapId.size();
		for(int y = rowIndex;y < mapSize+2;y++) {

			System.out.println(",rowIndget:"+mapId.get(y+1)+"y:"+y+",size:"+mapSize);
			mapId.replace(y, mapId.get(y+1));
		}
		mapId.remove(mapId.size());

		
		int rowCount = items.getRowCount();		
		
		for(int i = 0; i < rowCount; i++) {
			model.setValueAt(i+1, i, 0);}
	}
	
}
