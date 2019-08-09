package test;

import java.util.List;
import java.sql.*;

import model.*;
import invoice.*;

public class JDBCTest {
	public static void main(String[] args) {
		Invoice i = new Invoice();
		
		i.insertCustomer("Stokrotka Sp.z o.o.", "Marsza³kowska 123/12",
						"Warszawa","01-112", "1234567890");
		i.insertCustomer("Adidas Polska", "Wolska 3/2", 
						"Warszawa","02-123", "43523425345");
		i.insertCustomer("Jeronimo Martins Polska", "Dolna 3",
						"Warszawa","02-343","24352345234");
		List<Customer> customersi = i.selectCustomer();
		System.out.println("List of customers");
		for(Customer c : customersi)
			System.out.println(c);
		
		i.insertProduct("Adidas shoe size 43qwerqwer",199.99, 12);
		i.insertProduct("Lamp",19.59, 12);
		i.insertProduct("Rounded table, color white",259.99, 22);
		
		List<Product> products = i.selectProduct();
		System.out.println("List of products");
		for(Product p : products)
			System.out.println(p);
		
		i.insertOrders(2, "12-02-2019", "14-03-2019kj", 1243.12);
		i.insertOrders(2, "11-01-2016", "21-02-2016", 924.52);
		i.insertOrders(2, "01-12-2019", "27-12-2019", 34.67);
		
		List<Orders> orders_list = i.selectOrders();
		System.out.println("List of orders");
		for(Orders o : orders_list)
			System.out.println(o);

		i.insertOrders_Details(1,7,3, 12.44,4, 12.44);
		i.insertOrders_Details(1,5,1, 56.3 ,5, 5*56.3);
		i.insertOrders_Details(1,8,2, 22.99,3, 3*22.99);
		i.insertOrders_Details(2,3,2, 92.44,2, 2*92.44);

		List<Orders_Details> orders_details_list = i.selectOrders_Details();
		System.out.println("List of Orders Details");
		for(Orders_Details od : orders_details_list)
			System.out.println(od);
		
	}
}
