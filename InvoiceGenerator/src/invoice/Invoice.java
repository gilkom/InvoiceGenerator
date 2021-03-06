package invoice;
  
import java.sql.*;
import java.util.*;

import javax.swing.JOptionPane;

import model.*;

public class Invoice {
	public static final String DRIVER="org.sqlite.JDBC";
	public static final String DB_URL= "jdbc:sqlite:invoice_database";
	
	private Connection con;
	private Statement stat;
	public int lastOrderId;
	
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//-------Creating connection------------------------------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	
	public Invoice() {  
		try {
			Class.forName(Invoice.DRIVER);
		}catch(ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,  "No driver JDBC");
			e.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection(Invoice.DB_URL);
			stat = con.createStatement();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null,  "Problem with opening connection");
			e.printStackTrace();
		}
		createTables();
	}
	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//-------Creating Tables------------------------------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	public boolean createTables() {
		String createCustomer= "CREATE TABLE IF NOT EXISTS Customer (" + 
				"  CustomerId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + 
				"  CustomerName VARCHAR(45) NOT NULL, " + 
				"  CustomerStreet VARCHAR(45) NOT NULL, " + 
				"  CustomerCity VARCHAR(45) NOT NULL, " + 
				"  CustomerPostCode VARCHAR(6) NOT NULL, " + 
				"  CustomerNIP VARCHAR(10) NOT NULL" + 
				");";
		String createProduct = "CREATE TABLE IF NOT EXISTS Product (" + 
				"  ProductId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + 
				"  ProductName VARCHAR(45) NOT NULL, " + 
				"  ProductPrice DOUBLE NOT NULL, " + 
				"  ProductTax INT NOT NULL " + 
				");" ;
		String createOrders = "CREATE TABLE IF NOT EXISTS Orders (" + 
				"  OrderId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + 
				"  CustomerId INTEGER NOT NULL, " + 
				"  OrderDate DATE NOT NULL, " + 
				"  InvoiceDate DATE NOT NULL, " + 
				"  OrderTotal DOUBLE NOT NULL, " + 
				"  CONSTRAINT fk_Orders_Customer " + 
				"    FOREIGN KEY (CustomerId) REFERENCES Customer (CustomerId) " + 
				");"; 
		String createOrders_Details = "CREATE TABLE IF NOT EXISTS Orders_Details (" + 
				"  OrderId INTEGER NOT NULL, " + 
				"  ItemNumber INTEGER NOT NULL, " + 
				"  ProductId INTEGER, " + 
				"  PurchasePrice DOUBLE NOT NULL, " + 
				"  ItemQuantity INTEGER NOT NULL, " +
				"  ItemTax INTEGER NOT NULL, " +
				"  ItemTotal DOUBLE NOT NULL, " + 
				"  PRIMARY KEY(OrderId, ItemNumber)," +
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
			JOptionPane.showMessageDialog(null,  "Error with creating tables");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//---Customer insert update delete select-------------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
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
			prepStmt.execute();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error with inserting Customer");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean updateCustomer(int customerId,String customerName, String customerStreet,
					String customerCity, String customerPostCode, String customerNip) {
		try {
			PreparedStatement prepStmt = con.prepareStatement(
					"UPDATE Customer SET CustomerName = ?,"
									+ " CustomerStreet = ?,"
									+ " CustomerCity = ?,"
									+ " CustomerPostCode = ?,"
									+ " CustomerNip = ?"
					+ " WHERE CustomerId = ?;");
			prepStmt.setString(1, customerName);
			prepStmt.setString(2, customerStreet);
			prepStmt.setString(3, customerCity);
			prepStmt.setString(4, customerPostCode);
			prepStmt.setString(5, customerNip);
			prepStmt.setInt(6, customerId);
			prepStmt.execute();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null,"Error with updating customer");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public boolean deleteCustomer(int customerId) {
		try {
			PreparedStatement prepStmt = con.prepareStatement(
					"DELETE FROM Customer WHERE customerId = ?;");
			prepStmt.setInt(1,customerId);
			prepStmt.execute();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error with deleting Customer");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	

	public List<Customer> selectCustomer(){
		List<Customer> customers = new LinkedList<Customer>();
		try {
			ResultSet result = stat.executeQuery("SELECT * FROM Customer;");
			int customerId;
			String customerName, customerStreet, customerCity,
					customerPostCode, customerNip;
			while(result.next()) {
				customerId = result.getInt("customerId");
				customerName = result.getString("customerName");
				customerStreet = result.getString("customerStreet");
				customerCity = result.getString("customerCity");
				customerPostCode = result.getString("customerPostCode");
				customerNip = result.getString("customerNip");
				customers.add(new Customer(customerId, customerName,
										customerStreet, customerCity, 
										customerPostCode,customerNip));
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error while selecting from customers");
			e.printStackTrace();
			return null;
		}
		return customers;
	}
	
	public List<Customer> selectCustomerLike(String likeName){
		List<Customer> customers = new LinkedList<Customer>();
		try {
			ResultSet result = stat.executeQuery(
					"SELECT * FROM Customer WHERE CustomerName LIKE '%" + likeName +"%';");
			int customerId;
			String customerName, customerStreet, customerCity,
					customerPostCode, customerNip;
			while(result.next()) {
				customerId = result.getInt("customerId");
				customerName = result.getString("customerName");
				customerStreet = result.getString("customerStreet");
				customerCity = result.getString("customerCity");
				customerPostCode = result.getString("customerPostCode");
				customerNip = result.getString("customerNip");
				customers.add(new Customer(customerId, customerName,
										customerStreet, customerCity, 
										customerPostCode,customerNip));
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error while selecting from customers");
			e.printStackTrace();
			return null;
		}
		return customers;
	}
	
	public List<Customer> selectCustomerLikeId(int rowIndex){
		List<Customer> customers = new LinkedList<Customer>();
		try {
			ResultSet result = stat.executeQuery(
					"SELECT * FROM Customer WHERE CustomerId =" + rowIndex +";");
			int customerId;
			String customerName, customerStreet, customerCity,
					customerPostCode, customerNip;
			while(result.next()) {
				customerId = result.getInt("customerId");
				customerName = result.getString("customerName");
				customerStreet = result.getString("customerStreet");
				customerCity = result.getString("customerCity");
				customerPostCode = result.getString("customerPostCode");
				customerNip = result.getString("customerNip");
				customers.add(new Customer(customerId, customerName,
										customerStreet, customerCity, 
										customerPostCode,customerNip));
			}

		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error while selecting from customers");
			e.printStackTrace();
			return null;
		}
		return customers;
	}
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//---Product insert update delete select-------------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	public boolean insertProduct(String productName, double productPrice, int productTax) {
		try {
			PreparedStatement prepStmt = con.prepareStatement(
					"INSERT INTO Product VALUES(NULL,?,?,?);");
			prepStmt.setString(1, productName);
			prepStmt.setDouble(2, productPrice);
			prepStmt.setInt(3, productTax);
			prepStmt.execute();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error with inserting Product");
			e.printStackTrace();
			return false;
		}
		return true;
	}
		
	public boolean updateProduct(int productId,String productName, 
			double productPrice, int productTax) {
		try {
			PreparedStatement prepStmt = con.prepareStatement(
					"UPDATE Product SET ProductName = ?,"
									+ " ProductPrice = ?,"
									+ " ProductTax = ?"
					+ " WHERE ProductId = ?;");
			prepStmt.setString(1, productName);
			prepStmt.setDouble(2, productPrice);
			prepStmt.setInt(3, productTax);
			prepStmt.setInt(4, productId);
			prepStmt.execute();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null,"Error with updating product");
			e.printStackTrace();
			return false;
		}
		return true;
		}
		
		
	public boolean deleteProduct(int productId) {
		try {
			PreparedStatement prepStmt = con.prepareStatement(
					"DELETE FROM Product WHERE productId = ?;");
			prepStmt.setInt(1,productId);
			prepStmt.execute();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error with deleting Product");
			e.printStackTrace();
			return false;
		}
		
		return true;
		}
		

	
	public List<Product> selectProduct(){
		List<Product> products = new LinkedList<Product>();
		try {
			ResultSet result = stat.executeQuery("SELECT * FROM Product;");
			int productId, productTax;
			String productName;
			double productPrice;
			while(result.next()) {
				productId = result.getInt("productId");
				productTax = result.getInt("productTax");
				productName = result.getString("productName");
				productPrice = result.getDouble("productPrice");
				products.add(new Product(productId, productName, productPrice, productTax));
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error while selecting from products");
			e.printStackTrace();
			return null;
		}
		return products;
	}

	public List<Product> selectProductLike(String likeName){
		List<Product> products = new LinkedList<Product>();
		try {
			ResultSet result = stat.executeQuery(
					"SELECT * FROM Product WHERE ProductName LIKE '%" + likeName +"%';");
			int productId, productTax;
			String productName;
			double productPrice;
			while(result.next()) {
				productId = result.getInt("productId");
				productName = result.getString("productName");
				productPrice = result.getDouble("productPrice");
				productTax = result.getInt("productTax");
				products.add(new Product(productId, productName,
										productPrice, productTax)); 
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error while selecting from products");
			e.printStackTrace();
			return null;
		}
		return products;
	}
	public List<Product> selectProductLikeId(int rowIndex){
		List<Product> products = new LinkedList<Product>();
		try {
			ResultSet result = stat.executeQuery(
					"SELECT * FROM Product WHERE ProductId =" + rowIndex +";");
			int productId, productTax;
			String productName;
			double productPrice;
			while(result.next()) {
				productId = result.getInt("productId");
				productName = result.getString("productName");
				productPrice = result.getDouble("productPrice");
				productTax = result.getInt("productTax");
				products.add(new Product(productId, productName,
										productPrice, productTax)); 
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error while selecting from products");
			e.printStackTrace();
			return null;
		}
		return products;
	}
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//---Orders insert update delete select---------------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	public boolean insertOrders(int customerId, String orderDate, String invoiceDate,
						double orderTotal) {
		try {

			PreparedStatement prepStmt = con.prepareStatement(
				"INSERT INTO Orders VALUES(NULL,? ,? ,?, ?);",Statement.RETURN_GENERATED_KEYS);
			
			prepStmt.setInt(1, customerId);
			prepStmt.setString(2, orderDate);
			prepStmt.setString(3, invoiceDate);
			prepStmt.setDouble(4, orderTotal);
			prepStmt.execute();
			
			//getting autogenerated id
			ResultSet rs = prepStmt.getGeneratedKeys();
			lastOrderId = rs.getInt(1);
			
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error with inserting Orders");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	


	public List<Orders> selectOrders(){
		List<Orders> orders_list = new LinkedList<Orders>();
		try {
			ResultSet result = stat.executeQuery("SELECT * FROM Orders");
			int orderId, customerId;
			String orderDate, invoiceDate;
			double orderTotal;
			while(result.next()) {
				orderId = result.getInt("orderId");
				customerId = result.getInt("customerId");
				orderDate = result.getString("orderDate");
				invoiceDate =result.getString("invoiceDate");
				orderTotal = result.getDouble("orderTotal");
				orders_list.add(new Orders(orderId, customerId, orderDate,
												invoiceDate, orderTotal));
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error while selecting from orders");
			e.printStackTrace();
			return null;
		}
		return orders_list;
	}
	
	public List<Orders> selectOrdersLikeId(int ordId){
		List<Orders> orders_list = new LinkedList<Orders>();
		try {
			ResultSet result = stat.executeQuery(
					"SELECT * FROM Orders WHERE OrderId =" + ordId +";");
			int orderId, customerId;
			String orderDate, invoiceDate;
			double orderTotal;
			while(result.next()) {
				orderId = result.getInt("orderId");
				customerId = result.getInt("customerId");
				orderDate = result.getString("orderDate");
				invoiceDate =result.getString("invoiceDate");
				orderTotal = result.getDouble("orderTotal");
				orders_list.add(new Orders(orderId, customerId, orderDate,
						invoiceDate, orderTotal));
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error while selecting from orders");
			e.printStackTrace();
			return null;
		}
		return orders_list;
	}
	
	public List<Orders> selectOrdersLike(String name, String startDate, String endDate){
		List<Orders> orders_list = new LinkedList<Orders>();
		List<Customer> customers = selectCustomerLike(name);
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < customers.size();i++) {
			builder.append(customers.get(i).getCustomerId()+",");
		}
		if(builder.length()>0) {
			builder.deleteCharAt(builder.lastIndexOf(","));
		}
		try {
			ResultSet result = stat.executeQuery(
					"SELECT * FROM Orders WHERE CustomerId IN (" + builder +")"
					+ " AND OrderDate BETWEEN '" +startDate +"' AND '"+endDate+"';");
			int orderId, customerId;
			String orderDate, invoiceDate;
			double orderTotal;
			while(result.next()) {
				orderId = result.getInt("orderId");
				customerId = result.getInt("customerId");
				orderDate = result.getString("orderDate");
				invoiceDate =result.getString("invoiceDate");
				orderTotal = result.getDouble("orderTotal");
				orders_list.add(new Orders(orderId, customerId, orderDate,
						invoiceDate, orderTotal));
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error while selecting from orders");
			e.printStackTrace();
			return null;
		}
		return orders_list;
	}
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//---Orders_Details insert update delete select-------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	
	public boolean insertOrders_Details(int orderId, int itemNumber, int productId,
						double purchasePrice, int itemQuantity, int itemTax, double itemTotal) {
		try {
			PreparedStatement prepStmt = con.prepareStatement(
					"INSERT INTO Orders_Details VALUES(?, ?, ?, ?, ?, ?, ?);");
			prepStmt.setInt(1, orderId);
			prepStmt.setInt(2, itemNumber);
			prepStmt.setInt(3, productId);
			prepStmt.setDouble(4, purchasePrice);   
			prepStmt.setInt(5, itemQuantity);
			prepStmt.setInt(6, itemTax);
			prepStmt.setDouble(7, itemTotal);
			prepStmt.execute();
		}catch(SQLException e){
			//System.err.println("Error with inserting Orders_Details");
			JOptionPane.showMessageDialog(null, "Error with inserting Orders_Details");
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public List<Orders_Details> selectOrders_Details(){
		List<Orders_Details> orders_details_list = new LinkedList<Orders_Details>();
		try {
			ResultSet result = stat.executeQuery("SELECT * FROM Orders_Details");
			int orderId, itemNumber, productId,itemQuantity, itemTax;
			double purchasePrice, itemTotal;
			while(result.next()) {
				orderId = result.getInt("orderId");
				itemNumber = result.getInt("itemNumber");
				productId = result.getInt("productId");
				itemQuantity = result.getInt("itemQuantity");
				purchasePrice = result.getDouble("purchasePrice");
				itemTax =result.getInt("itemTax");
				itemTotal = result.getDouble("itemTotal");
				orders_details_list.add(new Orders_Details(orderId, itemNumber, productId,
											purchasePrice, itemQuantity, itemTax, itemTotal));
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error while selecting from Orders_Details");
			e.printStackTrace();
			return null;
		}
		return orders_details_list;
	}
	public List<Orders_Details> selectOrdersDetailsLikeId(int ordId){
		List<Orders_Details> orders_details_list = new LinkedList<Orders_Details>();
		try {
			ResultSet result = stat.executeQuery(
					"SELECT * FROM Orders_Details WHERE OrderId =" + ordId +";");
			int orderId, itemNumber, productId,itemQuantity, itemTax;
			double purchasePrice, itemTotal;
			while(result.next()) {
				orderId = result.getInt("orderId");
				itemNumber = result.getInt("ItemNumber");
				productId = result.getInt("productId");
				itemQuantity = result.getInt("itemQuantity");
				purchasePrice = result.getDouble("purchasePrice");
				itemTax =result.getInt("itemTax");
				itemTotal = result.getDouble("itemTotal");
				orders_details_list.add(new Orders_Details(orderId, itemNumber, productId,
											purchasePrice, itemQuantity, itemTax, itemTotal));
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error while selecting from orders details");
			e.printStackTrace();
			return null;
		}
		return orders_details_list;
	}
	
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//-------Closing connection---------------------------------------
	//----------------------------------------------------------------	
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	
	public void closeConnection() {
		try {
			con.close();
		}catch(SQLException e) {
			//System.err.println("Problem with closing connection");
			JOptionPane.showMessageDialog(null, "Problem with closing connection");
			e.printStackTrace();
		}
	}
	
}
