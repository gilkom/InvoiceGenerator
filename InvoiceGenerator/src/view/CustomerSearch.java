package view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import control.Control;
import view.*;
import java.awt.*;
import java.awt.event.*;


public class CustomerSearch {
	JTable table;
	
	
	public CustomerSearch(JFrame frame, JTextArea customerData, JTextArea customerId){

		//JFrame f = new JFrame("Customer search");
		//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.setLayout(new BorderLayout(5,5));
		
		JDialog dialCust = new JDialog(
				frame, "Customer search:",JDialog.DEFAULT_MODALITY_TYPE);
		dialCust.setLayout(new BorderLayout(5,5));
		dialCust.setPreferredSize(new Dimension(800,600));
		dialCust.setResizable(false);
		dialCust.setLocation(100,100);
		dialCust.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JPanel EastPan = new JPanel();
		EastPan.setPreferredSize(new Dimension(30,10));
		
		JPanel WestPan = new JPanel();
		WestPan.setPreferredSize(new Dimension(30,10));
		
		JPanel SouthPan = new JPanel(new FlowLayout(FlowLayout.RIGHT,40,10));
		
		JPanel ButPan = new JPanel(new FlowLayout(10,10,10));
		ButPan.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		JPanel LeftOuterButPan = new JPanel();
		LeftOuterButPan.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
		JPanel LeftButPan = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
		LeftButPan.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		JPanel RightOuterButPan = new JPanel();
		RightOuterButPan.setBorder(BorderFactory.createEmptyBorder(0,180,0,0));
		JPanel RightButPan = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));	
		RightButPan.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		
//Button "Add" opens AddingCustomer panel in which we can add new customer
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddingCustomer(table,dialCust);
				
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
				//getting id and name of selected row					
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
		
		// Button which edits selected row
		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//getting id and name of selected row					
					int rowIndex = table.getSelectedRow();
					new EditingCustomer(table, dialCust, rowIndex);
					
					table.setModel(Control.populateCustomer());
					
					//setting table size
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					table.getColumnModel().getColumn(0).setPreferredWidth(40);
					table.getColumnModel().getColumn(1).setPreferredWidth(300);
					table.getColumnModel().getColumn(2).setPreferredWidth(119);
					table.getColumnModel().getColumn(3).setPreferredWidth(100);
					table.getColumnModel().getColumn(4).setPreferredWidth(60);
					table.getColumnModel().getColumn(5).setPreferredWidth(80);
					
				}catch(ArrayIndexOutOfBoundsException a) {
					JOptionPane.showMessageDialog(null,  "Select row to edit!");
				}
			}
		});
		
		//Accept button  select customer to invoice
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					int rowIndex = Integer.parseInt(
							table.getValueAt(table.getSelectedRow(),0).toString());
					// getting customer string and removing signs [ and ] from string
					String res = Control.selectCustomer(rowIndex);
					res = res.substring(1);
					res = res.substring(0,res.length() - 1);
					//customerId.setText("3");
					customerId.setText(Integer.toString(rowIndex));
					customerData.setText(res);

					dialCust.dispose();	
				}catch(ArrayIndexOutOfBoundsException a) {
					JOptionPane.showMessageDialog(null, "Select customer to accept!");
				}
				catch(NullPointerException n) {
					JOptionPane.showMessageDialog(null,  "NullPOinter..");
				}

			}
		});
		
		
		//searching for name like %x%
		JTextField searchField = new JTextField(15);		
		JButton search = new JButton("Search");
		Action searchAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchValue = searchField.getText();
				table.setModel(Control.populateCustomerLike(searchValue));
				
				//setting table size
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.getColumnModel().getColumn(0).setPreferredWidth(40);
				table.getColumnModel().getColumn(1).setPreferredWidth(300);
				table.getColumnModel().getColumn(2).setPreferredWidth(119);
				table.getColumnModel().getColumn(3).setPreferredWidth(100);
				table.getColumnModel().getColumn(4).setPreferredWidth(60);
				table.getColumnModel().getColumn(5).setPreferredWidth(80);	
			}
		};
		
		searchField.addActionListener(searchAction);
		search.addActionListener(searchAction);
		

		
		
		JPanel TabPan = new JPanel();
		TabPan.setLayout(new BorderLayout());
		
		
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
		LeftOuterButPan.add(LeftButPan);
		LeftButPan.add(add);
		LeftButPan.add(delete);
		LeftButPan.add(edit);
		SouthPan.add(accept);
		RightOuterButPan.add(RightButPan);
		RightButPan.add(search);
		RightButPan.add(searchField);
		
		ButPan.add(LeftOuterButPan);
		ButPan.add(RightOuterButPan);
		dialCust.add(ButPan, BorderLayout.NORTH);
		dialCust.add(TabPan, BorderLayout.CENTER);
		dialCust.add(WestPan, BorderLayout.WEST);
		dialCust.add(SouthPan, BorderLayout.SOUTH);
		dialCust.add(EastPan, BorderLayout.EAST);
		
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
			dialCust.pack();
			dialCust.setVisible(true);
			//f.setSize(800,600);
			//f.setResizable(false);
			}
		});
	}
}
