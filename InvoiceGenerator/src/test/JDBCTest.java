package test;


import invoice.*;
import view.*;
import control.*;

public class JDBCTest {
	public static void main(String[] args) {
		new CreatePdf();
		//new CreateInvoice();
		
		//Adding some sample data to database
		Invoice i = new Invoice();
		i.insertCustomer("Stokrotka Sp.z o.o.", "Marsza³kowska 123/12",
						"Warszawa","01-112", "1234567890");
		i.insertCustomer("Adidas Polska", "Wolska 3/2", 
						"Warszawa","02-123", "4352342534");
		i.insertCustomer("Jeronimo Martins Polska", "Dolna 3",
						"Warszawa","02-343","2435234523");
	
		i.insertProduct("Adidas shoe size 43",199.99, 12);
		i.insertProduct("Lamp 32cm",19.59, 12);
		i.insertProduct("Rounded table, color white",259.99, 22);
		i.insertProduct("Milk 3,2%",1.99, 15);

	}
}
