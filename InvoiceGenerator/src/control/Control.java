package control;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
//import java.util.Date;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import invoice.*;
import model.*;

public class Control {

	public static int lastOrderId;
	public static DecimalFormat df = new DecimalFormat("0.00");
	
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
	public static List<Customer> selectCustomerLikeId(int rowIndex) {
		Invoice i = new Invoice();
		List<Customer> customers = i.selectCustomerLikeId(rowIndex);
		return customers;
	}
	public static List<Customer> selectCustomers() {
		Invoice i = new Invoice();
		List<Customer> customers = i.selectCustomer();
		return customers;
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
	public static List<Product> selectProductLikeId(int prodId) {
		Invoice i = new Invoice();
		List<Product> products = i.selectProductLikeId(prodId);
		return products;
	}
	public static String selectProductNameLikeId(int prodId) {
		Invoice i = new Invoice();
		List<Product> products = i.selectProductLikeId(prodId);
		String result = products.get(0).getProductName();
		return result;
	}	
	public static List<Product> selectProduct() {
		Invoice i = new Invoice();
		List<Product> products = i.selectProduct();
		return products;
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
		lastOrderId = i.lastOrderId;
	}
	
	public static List<Orders> selectOrdersLikeId(int orderId) {
		Invoice i = new Invoice();
		List<Orders> orders_list = i.selectOrdersLikeId(orderId);
		return orders_list;
	}
	
	public static DefaultTableModel populateOrders() {
		Invoice i = new Invoice();
		List<Orders> orders_list = i.selectOrders();
		List<Customer> customers = selectCustomers();
		int c;
		
		
		DefaultTableModel model = new DefaultTableModel(new String[] {
				"Inv.", "Customer name", "Order date", "Invoice date", "Total"},0);
		
		for(Orders p : orders_list) {
			c = p.getCustomerId();
			model.addRow(new Object[] {p.getOrderId(),customers.get(c-1).getCustomerName(),
					p.getOrderDate(),p.getInvoiceDate(), p.getOrderTotal()});
		}
		
		return model;
	}
	public static DefaultTableModel populateOrdersLike(
			String name, String startDate,String endDate) {
		Invoice i = new Invoice();
		List<Orders> orders_list = i.selectOrdersLike(name, startDate, endDate);
		List<Customer> customers = i.selectCustomer();
		int c;
		
		DefaultTableModel model = new DefaultTableModel(new String[] {
				"Inv. ", "Customer name", "Order date", "Invoice date", "Total"},0);
		for(Orders p : orders_list) {
			c = p.getCustomerId();
			model.addRow(new Object[] {p.getOrderId(),customers.get(c-1).getCustomerName(),
					p.getOrderDate(),p.getInvoiceDate(), p.getOrderTotal()});
		}
		return model;
	}
	public static DefaultTableModel populateOrdersClean(int orderId) {
		Invoice i = new Invoice();
		List<Orders> orders_list = i.selectOrdersLikeId(orderId);
		
		
		DefaultTableModel model = new DefaultTableModel(new String[] {
				"Inv.", "Customer name", "Order date", "Invoice date", "Total"},0);
		
		for(Orders p : orders_list) {
			model.addRow(new Object[] {p.getOrderId(),p.getCustomerId(),
					p.getOrderDate(),p.getInvoiceDate(), p.getOrderTotal()});
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
	public static void addOrders_Details(JTable items, int lastId, Map<Integer,Integer> mapId) {
		Invoice i = new Invoice();
		int leng = items.getRowCount();
		
		for(int j = 0; j < leng; j++) {
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
	
	public static List<Orders_Details> selectOrders_DetailsLikeId(int ordId) {
		Invoice i = new Invoice();
		List<Orders_Details> orders_details_list = i.selectOrdersDetailsLikeId(ordId);
		return orders_details_list;
	}
	public static void selectOrders_DetailsLikeId(JTable items, int ordId) {
		Invoice i = new Invoice();
		List<Orders_Details> orders_details_list = i.selectOrdersDetailsLikeId(ordId);
		List<Product> products = i.selectProduct();
		int p;
		DefaultTableModel model = (DefaultTableModel) items.getModel();
		model.setRowCount(0);
		
		int quantity = 0;
		double totalNet = 0;
		double taxAmount = 0;
		double totalGross = 0;
		
		for(Orders_Details o : orders_details_list) {
			p = o.getProductId();
			quantity = o.getItemQuantity();
			totalNet = Math.floor((quantity * o.getPurchasePrice())*100d)/100d;
			taxAmount = Math.floor(((
					o.getItemTax()*o.getPurchasePrice()*quantity)/100)*100d)/100d;
			totalGross = Math.floor((totalNet + taxAmount)*100d)/100d;
			model.addRow(new Object[] {o.getItemNumber(),products.get(p-1).getProductName(),
					o.getItemQuantity(),o.getPurchasePrice(), totalNet,
					o.getItemTax(),taxAmount, totalGross});

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
			taxAmount =(((
					p.getProductTax()*p.getProductPrice()* quantity)/100));
			totalGross = (totalNet + taxAmount);
			mapId.put(itemNo, p.getProductId());
			model.addRow(new Object[] {itemNo, p.getProductName(), quantity,
					p.getProductPrice(),totalNet ,p.getProductTax(),
					Control.formatValue(taxAmount), Control.formatValue(totalGross)});
		}

	}
	public static void updItem(JTable items,  
			int columnIndex, int rowIndex, int productQuantity,
			double productPrice,int productTax)
	{

		double totalNet = Math.floor((productQuantity * productPrice)*100d)/100d;
		double taxAmount = Math.floor((
				productTax * (productPrice* productQuantity)/100d)*100d)/100d;
		double totalGross = Math.floor((totalNet + taxAmount)*100d)/100d;
		
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
			mapId.replace(y, mapId.get(y+1));
		}
		mapId.remove(mapId.size());

		
		int rowCount = items.getRowCount();		
		
		for(int i = 0; i < rowCount; i++) {
			model.setValueAt(i+1, i, 0);}
	}
	
	public static Customer createIssuer(String customerName, String customerStreet,
			String customerCity, String customerPostCode, String customerNip){
		
		Customer c = new Customer(0, customerName, customerStreet,
				customerCity, customerPostCode, customerNip);
		
		try {
			FileOutputStream fo = new FileOutputStream(new File("issuer.txt"));
			ObjectOutputStream oo  = new ObjectOutputStream(fo);
			
			oo.writeObject(c);
			
			oo.close();
			fo.close();
			
			FileInputStream fi = new FileInputStream(new File("issuer.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);
			
			c = (Customer)oi.readObject();
			oi.close();
			fi.close();
			
		}catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Error initializing stream");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return c;
	}
	public static Customer getIssuer(){
		
		Customer c = new Customer();
		
		try {
			
			FileInputStream fi = new FileInputStream(new File("issuer.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);
			
			c = (Customer)oi.readObject();
			oi.close();
			fi.close();
			
		}catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Error initializing stream");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return c;
	}
	public static String formatValue(double comVal) {
		String formattedResult = df.format(new BigDecimal(comVal));
		String replacedResult = formattedResult.replace(",",".");
		return replacedResult;
	}
	
}



