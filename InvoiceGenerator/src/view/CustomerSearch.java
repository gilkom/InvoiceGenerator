package view;

import javax.swing.*;


import control.Control;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import invoice.*;
import model.*;

public class CustomerSearch {
	JTable table;
	
	
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
		
//Button "Add" opens AddingCustomer panel in which we can add new customer
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddingCustomer(table,f);
				
				table.setModel(Control.populateCustomer());
				
				//setting table size
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.getColumnModel().getColumn(0).setPreferredWidth(40);
				table.getColumnModel().getColumn(1).setPreferredWidth(300);
				table.getColumnModel().getColumn(2).setPreferredWidth(119);
				table.getColumnModel().getColumn(3).setPreferredWidth(100);
				table.getColumnModel().getColumn(4).setPreferredWidth(60);
				table.getColumnModel().getColumn(5).setPreferredWidth(80);
			}
		});
// Button deletes selected row
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
				//getting id and nameof selected row					
				int deletingCustId = Integer.parseInt(	
					table.getValueAt(table.getSelectedRow(), 0).toString());
				String deletingCustName = table.getValueAt(table.getSelectedRow(), 1).toString();
				
				int conf = JOptionPane.showConfirmDialog(
						null,"Are you sure you want to delete customer \n" + deletingCustName + "?",
						"Confirm deleting", JOptionPane.YES_NO_OPTION);
				if(conf == JOptionPane.YES_OPTION) {
					//deleting selected row	and setting table without that row					
					Control.removeCustomer(deletingCustId);
					table.setModel(Control.populateCustomer());
				
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					table.getColumnModel().getColumn(0).setPreferredWidth(40);
					table.getColumnModel().getColumn(1).setPreferredWidth(300);
					table.getColumnModel().getColumn(2).setPreferredWidth(119);
					table.getColumnModel().getColumn(3).setPreferredWidth(100);
					table.getColumnModel().getColumn(4).setPreferredWidth(60);
					table.getColumnModel().getColumn(5).setPreferredWidth(80);
				}
				}catch(ArrayIndexOutOfBoundsException a) {
					JOptionPane.showMessageDialog(null,  "Select row to delete!");
				}
			}
		});
		JButton edit = new JButton("Edit");
		JButton accept = new JButton("Accept");
		JButton search = new JButton("Search");
		
		
		JPanel TabPan = new JPanel();
		TabPan.setLayout(new BorderLayout());
		TabPan.setSize(1000,100);
		TabPan.setBackground(Color.green);
		
		
		table = new JTable(Control.populateCustomer());
		table.setDefaultEditor(Object.class, null);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
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
