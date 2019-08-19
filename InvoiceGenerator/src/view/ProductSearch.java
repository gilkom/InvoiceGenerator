package view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import control.Control;

import java.awt.*;
import java.awt.event.*;

public class ProductSearch {
	JTable table;
	
	
	public ProductSearch(JTable items, JFrame frame){
		//JFrame f = new JFrame("Product search");
		//f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//f.setLayout(new BorderLayout(5,5));
		
		JDialog dialProd = new JDialog(
				frame, "Product search:", JDialog.DEFAULT_MODALITY_TYPE);
		dialProd.setLayout(new BorderLayout(5,5));
		dialProd.setPreferredSize(new Dimension(800,600));
		dialProd.setResizable(false);
		dialProd.setLocation(100,100);
		dialProd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
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

		
//Button "Add" opens AddingProduct panel in which we can add new product
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new AddingProduct(table,f);
				new AddingProduct(table,dialProd);
				
				table.setModel(Control.populateProduct());
				
				//setting table size
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.getColumnModel().getColumn(0).setPreferredWidth(40);
				table.getColumnModel().getColumn(1).setPreferredWidth(500);
				table.getColumnModel().getColumn(2).setPreferredWidth(110);
				table.getColumnModel().getColumn(3).setPreferredWidth(50);
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
					new EditingProduct(table, dialProd, rowIndex);
					
					//table.setModel(Control.populateProduct());
					
					//setting table size
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					table.getColumnModel().getColumn(0).setPreferredWidth(40);
					table.getColumnModel().getColumn(1).setPreferredWidth(500);
					table.getColumnModel().getColumn(2).setPreferredWidth(110);
					table.getColumnModel().getColumn(3).setPreferredWidth(50);
					
				}catch(ArrayIndexOutOfBoundsException a) {
					JOptionPane.showMessageDialog(null,  "Select row to edit!");
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
				table.setModel(Control.populateProductLike(searchValue));
				
				//setting table size
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.getColumnModel().getColumn(0).setPreferredWidth(40);
				table.getColumnModel().getColumn(1).setPreferredWidth(500);
				table.getColumnModel().getColumn(2).setPreferredWidth(110);
				table.getColumnModel().getColumn(3).setPreferredWidth(50);
			}
		};
		
		searchField.addActionListener(searchAction);
		search.addActionListener(searchAction);
		
	
// Button deletes selected row
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
				//getting id and name of selected row					
				int deletingProdId = Integer.parseInt(	
					table.getValueAt(table.getSelectedRow(), 0).toString());
				String deletingProdName = table.getValueAt(table.getSelectedRow(), 1).toString();
				
				int conf = JOptionPane.showConfirmDialog(
						null,"Are you sure you want to delete product \n" + deletingProdName + "?",
						"Confirm deleting", JOptionPane.YES_NO_OPTION);
				if(conf == JOptionPane.YES_OPTION) {
					//deleting selected row	and setting table without that row					
					Control.removeProduct(deletingProdId);
					String searchValue = searchField.getText();
					table.setModel(Control.populateProductLike(searchValue));
					
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					table.getColumnModel().getColumn(0).setPreferredWidth(40);
					table.getColumnModel().getColumn(1).setPreferredWidth(500);
					table.getColumnModel().getColumn(2).setPreferredWidth(110);
					table.getColumnModel().getColumn(3).setPreferredWidth(50);
				}
				}catch(ArrayIndexOutOfBoundsException a) {
					JOptionPane.showMessageDialog(null,  "Select row to delete!");
				}
			}
		});
		
		//Accept button not ready yet. select product to invoice
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			try {
				int rowIndex = table.getSelectedRow();
				
				//trzeba dokoñczyc. wyswietla siê pozycja ale nie tak jak trzeba
				
				Control.populateOrders_Details(items, rowIndex);
			}catch(ArrayIndexOutOfBoundsException a) {
				JOptionPane.showMessageDialog(null,  "Select product!");
			}
			}
		});
		
		
		
		JPanel TabPan = new JPanel();
		TabPan.setLayout(new BorderLayout());
		
		
		table = new JTable(Control.populateProduct());
		table.setDefaultEditor(Object.class, null);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		table.getColumnModel().getColumn(2).setPreferredWidth(110);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);

		
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
		dialProd.add(ButPan, BorderLayout.NORTH);
		dialProd.add(TabPan, BorderLayout.CENTER);
		dialProd.add(WestPan, BorderLayout.WEST);
		dialProd.add(SouthPan, BorderLayout.SOUTH);
		dialProd.add(EastPan, BorderLayout.EAST);
		

		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
			dialProd.pack();
			dialProd.setVisible(true);
			//f.setSize(800,600);
			//f.setResizable(false);
			}
		});
	}
}
