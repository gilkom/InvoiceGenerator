package test;

import java.util.List;

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
		
		
	}
}
