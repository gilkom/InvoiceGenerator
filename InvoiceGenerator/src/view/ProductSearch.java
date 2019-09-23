package view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;

import control.Control;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;

public class ProductSearch {
	JTable table;

	
	public ProductSearch(JTable items, JFrame frame, JTextField totalField, Map<Integer,Integer> mapId){
		
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
			private static final long serialVersionUID = 1L;
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
		
		//Accept button. selects product to invoice
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent prode) {
			try {
				
				int rowIndex = Integer.parseInt(	
						table.getValueAt(table.getSelectedRow(), 0).toString());
				
				Control.populateOrders_Details(items, rowIndex, mapId);
				
				DefaultTableCellRenderer rend = new DefaultTableCellRenderer();
				rend.setBackground(new Color(242,242,242));
				items.getColumnModel().getColumn(0).setCellRenderer(rend);
				items.getColumnModel().getColumn(1).setCellRenderer(rend);
				items.getColumnModel().getColumn(2).setCellRenderer(rend);
				items.getColumnModel().getColumn(3).setCellRenderer(rend);
				items.getColumnModel().getColumn(4).setCellRenderer(rend);
				items.getColumnModel().getColumn(5).setCellRenderer(rend);
				items.getColumnModel().getColumn(6).setCellRenderer(rend);
				items.getColumnModel().getColumn(7).setCellRenderer(rend);
				
				
				//calculating and setting totalGross value
				int rowCounter = items.getRowCount();
				double totalGross = 0;
				String tot = "";
				for(int i =0; i < rowCounter; i++) {
					tot = items.getValueAt(i, 7).toString();
					totalGross = totalGross + Double.parseDouble(tot);
				}

				totalField.setText(Control.formatValue(totalGross));
				
			}catch(ArrayIndexOutOfBoundsException a) {
				JOptionPane.showMessageDialog(null,  "Select product to accept!");
			}
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialProd.dispose();	
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
		SouthPan.add(cancel);
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
			}
		});
	}
}
