package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import invoice.*;
import model.*;

public class CustomerSearch {

	
	
	public CustomerSearch(){
		JFrame f = new JFrame("Customer search");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout(5,5));
		
		JPanel EastPan = new JPanel();
		EastPan.setPreferredSize(new Dimension(30,10));
		
		JPanel WestPan = new JPanel();
		WestPan.setPreferredSize(new Dimension(30,10));
		
		JPanel SouthPan = new JPanel();
		SouthPan.setPreferredSize(new Dimension(10,30));
		
		JPanel ButPan = new JPanel(new FlowLayout(20,20,20));
		//ButPan.setBackground(Color.RED);
		
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddingCustomer();
			}
		});
		
		JButton delete = new JButton("Delete");
		JButton edit = new JButton("Edit");
		JButton accept = new JButton("Accept");
		JButton search = new JButton("Search");
		
		
		JPanel TabPan = new JPanel();
		TabPan.setLayout(new BorderLayout());
		TabPan.setSize(1000,100);
		TabPan.setBackground(Color.green);
		
		
		Invoice inv = new Invoice();
		List<Customer> customers = inv.selectCustomer() ;
		
		DefaultTableModel model = new DefaultTableModel(
				new String[]{"Id", "Name", "Street", "City", "Post code", "Nip"},0);
				
		for(Customer c : customers) {
			model.addRow(new Object[] {c.getCustomerId(),c.getCustomerName(), c.getCustomerStreet(),
					c.getCustomerCity(), c.getCustomerPostCode(), c.getCustomerNip()});
		}
		
		String[][] rows = new String[100][100];
		for(Customer c : customers) {
			for(int i = 0;i < 100; i++) {
				for(int j = 0; j < 100; j++) {
					//String x = "" + c.getC;
					rows[i][0] = Integer.valueOf(c.getCustomerId()).toString();
				}
			}
		}
		
		
		JTable table = new JTable(model);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(119);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);

		
		JScrollPane scr = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
												JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		TabPan.add(scr);
		ButPan.add(add);
		ButPan.add(delete);
		ButPan.add(edit);
		ButPan.add(accept);
		ButPan.add(search);
		
		f.add(ButPan, BorderLayout.NORTH);
		f.add(TabPan, BorderLayout.CENTER);
		f.add(WestPan, BorderLayout.WEST);
		f.add(SouthPan, BorderLayout.SOUTH);
		f.add(EastPan, BorderLayout.EAST);
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
			f.pack();
			f.setVisible(true);
			f.setSize(800,600);
			f.setResizable(false);
			}
		});
	}
}
