package control;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import invoice.*;
import model.Customer;

public class Control {
	public static void addCustomer(String customerName, String customerStreet,
			String customerCity, String customerPostCode, String customerNip)
	{
		Invoice i = new Invoice();
		i.insertCustomer(customerName, customerStreet, customerCity,
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
}
