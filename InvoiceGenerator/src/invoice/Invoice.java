package invoice;
  
import java.sql.*;
import java.util.*;

import javax.swing.JOptionPane;

import model.*;

public class Invoice {
	public static final String DRIVER="org.sqlite.JDBC";
	public static final String DB_URL= "jdbc:sqlite:invoice";
	
	private Connection con;
	private Statement stat;
	
	public Invoice() {
		try {
			Class.forName(Invoice.DRIVER);
		}catch(ClassNotFoundException e) {
			//System.err.println("No driver JDBC");
			JOptionPane.showMessageDialog(null,  "No driver JDBC");
			e.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection(Invoice.DB_URL);
			stat = con.createStatement();
		}catch(SQLException e) {
			//System.err.println("Problem with opening connection");
			JOptionPane.showMessageDialog(null,  "Problem with opening connection");
			e.printStackTrace();
		}
		createTables();
	}
	public boolean createTables() {
		String createCustomer= "CREATE TABLE IF NOT EXISTS Customer (" + 
				"  CustomerId INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " + 
				"  CustomerName VARCHAR(45) NOT NULL, " + 
				"  CustomerStreet VARCHAR(45) NOT NULL, " + 
				"  CustomerCity VARCHAR(45) NOT NULL, " + 
				"  CustomerPostCode VARCHAR(45) NOT NULL, " + 
				"  CustomerNIP VARCHAR(45) NOT NULL " + 
				");";
		String createProduct = "CREATE TABLE IF NOT EXISTS Product (" + 
				"  ProductId INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " + 
				"  ProductName VARCHAR(45) NOT NULL, " + 
				"  ProductPrice DOUBLE NOT NULL, " + 
				"  ProductVAT INT NOT NULL " + 
				");" ;
		String createOrders = "CREATE TABLE IF NOT EXISTS Orders (" + 
				"  OrderId INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " + 
				"  CustomerId INT NOT NULL, " + 
				"  OrderDate DATE NOT NULL, " + 
				"  InvoiceDate DATE NOT NULL, " + 
				"  OrderTotal VARCHAR(45) NOT NULL, " + 
				"  CONSTRAINT fk_Orders_Customer " + 
				"    FOREIGN KEY (CustomerId) REFERENCES Customer (CustomerId) " + 
				");"; 
		String createOrders_Details = "CREATE TABLE IF NOT EXISTS Orders_Details (" + 
				"  OrderId INT NOT NULL PRIMARY KEY, " + 
				"  ItemNumber INT NOT NULL PRIMARY KEY, " + 
				"  ProductId INT, " + 
				"  PurchasePrice DOUBLE NOT NULL, " + 
				"  ItemQuantity INT NOT NULL, " + 
				"  ItemTotal DOUBLE NOT NULL, " + 
				"  CONSTRAINT FK_Orders_Details_Product FOREIGN KEY(ProductId) " +
				" 	REFERENCES Product(ProductId), " + 
				"  CONSTRAINT FK_Orders_Details_Orders FOREIGN KEY(OrderId) " +
				"	REFERENCES Orders(OrderId) " + 
				"  );"; 
		try {
			stat.execute(createCustomer);
			stat.execute(createProduct);
			stat.execute(createOrders);
			stat.execute(createOrders_Details);
		}catch(SQLException e) {
			//System.err.println("Error creating tables");
			JOptionPane.showMessageDialog(null,  "Error with creating tables");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean insertCustomer(String customerName, String customerStreet,
					String customerCity, String customerPostCode, String customerNip) {
		try {
			PreparedStatement prepStmt = con.prepareStatement(
					"INSERT INTO Customer VALUES(NULL,?,?,?,?,?);");
			prepStmt.setString(1, customerName);
			prepStmt.setString(2, customerStreet);
			prepStmt.setString(3, customerCity);
			prepStmt.setString(4, customerPostCode);
			prepStmt.setString(5, customerNip);
		}catch(SQLException e) {
			//System.err.println("Error with inserting Customer");
			JOptionPane.showMessageDialog(null, "Error with inserting Customer");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean insertProduct(String productName, double productPrice, int productTax) {
		try {
			PreparedStatement prepStmt = con.prepareStatement(
					"INSERT INTO Product VALUES(NULL,?,?,?);");
			prepStmt.setString(1, productName);
			prepStmt.setDouble(2, productPrice);
			prepStmt.setInt(3, productTax);
		}catch(SQLException e) {
			//System.err.println("Error with inserting Product");
			JOptionPane.showMessageDialog(null, "Error with inserting Product");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean insertOrders(int customerId, String orderDate, String invoiceDate,
						String orderTotal) {
		try {PreparedStatement prepStmt = con.prepareStatement(
				"INSERT INTO Orders VALUES(NULL,? ,? ,?, ?);");
			prepStmt.setInt(1, customerId);
			prepStmt.setString(2, orderDate);
			prepStmt.setString(3, invoiceDate);
			prepStmt.setString(4, orderTotal);
		}catch(SQLException e) {
			//System.err.println("Error with inserting Orders");
			JOptionPane.showMessageDialog(null, "Error with inserting Orders");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean insertOrders_Details(int orderId, int itemNumber, int productId,
						double purchasePrice, int itemQuantity, double itemTotal) {
		try {
			PreparedStatement prepStmt = con.prepareStatement(
					"INSERT INTO Orders_Details VALUES(?, ?, ?, ?, ?, ?");
			prepStmt.setInt(1, orderId);
			prepStmt.setInt(2, itemNumber);
			prepStmt.setInt(3, productId);
			prepStmt.setDouble(4, purchasePrice);   
			prepStmt.setInt(5, itemQuantity);
			prepStmt.setDouble(6, itemTotal);
		}catch(SQLException e){
			//System.err.println("Error with inserting Orders_Details");
			JOptionPane.showMessageDialog(null, "Error with inserting Orders_Details");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
