package view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.*;

import control.DateLabelFormatter;
import control.Control;
import control.CreatePdf;


public class CreateInvoice {
	
	JFrame frame;
	private JPanel header;
	private JPanel headerUp;
	private JPanel headerLow;
	private JPanel headerLowLeft;
	private JPanel headerLowRight;
	private JPanel details;
	private JPanel detailsNorth;
	private JPanel detailsCenter;
	private JPanel detailsSouth;
	private JPanel footer;
	private JButton selectInvoice;
	private JButton addCustomer;
	private JButton addIssuer;
	private JButton addProduct;
	private JButton removeProduct;
	private JButton editProduct;
	private JButton save;
	private JButton savePrint;
	private JButton delete;
	private JButton print;
	private JButton edit;
	private JTextField totalField;
	private JTextArea customerId;
	private JTextArea customerData;
	private JTextArea issuerData;
	private JLabel invoiceDateLabel;
	private JLabel orderDateLabel;
	private JLabel totalLabel;
	private JTable items;
	private JScrollPane scr;
	private UtilDateModel invDateModel;
	private JDatePanelImpl invDatePanel;
	private JDatePickerImpl invDatePicker;
	private UtilDateModel ordDateModel;
	private JDatePanelImpl ordDatePanel;
	private JDatePickerImpl ordDatePicker;
	private Map<Integer, Integer> mapId;
	private DefaultTableModel model;
	
	private double totalGross;
	
	public CreateInvoice() {
		frame = new JFrame("Invoice Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(5, 10));
		
		//-------JPanels-----------
		header = new JPanel(new BorderLayout());
		header.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));	
		headerUp = new JPanel(new FlowLayout(10,30,10));
		headerUp.setBorder(BorderFactory.createEtchedBorder());
		headerLow = new JPanel(new GridLayout());
		headerLow.setBorder(BorderFactory.createEtchedBorder());
		headerLow.setPreferredSize(new Dimension(100,100));
		headerLowLeft = new JPanel();
		headerLowLeft.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		headerLowRight = new JPanel(new FlowLayout());
		details = new  JPanel(new BorderLayout());
		details.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		detailsNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		detailsNorth.setBorder(BorderFactory.createEtchedBorder());
		detailsCenter = new JPanel(new GridLayout());
		detailsCenter.setBorder(BorderFactory.createEtchedBorder());
		detailsSouth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		detailsSouth.setBorder(BorderFactory.createEtchedBorder());
		footer = new JPanel();
		footer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		
		
		//header JPanel(NORTH)
		selectInvoice = new JButton("Select invoice");
		addCustomer = new JButton("Add customer");
		addCustomer.setPreferredSize(new Dimension(120,15));
		customerData = new JTextArea();
		customerData.setEditable(false);
		customerData.setBackground(new Color(242,242,242));
		customerData.setPreferredSize(new Dimension(350,50));
		customerData.setBorder(BorderFactory.createLoweredBevelBorder());
		customerId = new JTextArea();
		addIssuer = new JButton("Add issuer");
		addIssuer.setPreferredSize(new Dimension(120,15));
		issuerData = new JTextArea(3,30);
		issuerData.setEditable(false);
		issuerData.setBackground(new Color(242,242,242));
		issuerData.setBorder(BorderFactory.createLoweredBevelBorder());	
		issuerData.setText((Control.getIssuer()).toString());
		invoiceDateLabel = new JLabel("Invoice date:");
		invoiceDateLabel.setBorder(BorderFactory.createEmptyBorder(0, 280, 0, 0));
		//Date picker for invoice date
		invDateModel = new UtilDateModel();
		invDateModel.setSelected(true);
		Properties invDateProp = new Properties();
		invDateProp.put("text.today", "Today");
		invDateProp.put("text.month", "Month");
		invDateProp.put("text.year", "Year");
		invDatePanel = new JDatePanelImpl(invDateModel, invDateProp);
		invDatePicker = new JDatePickerImpl(invDatePanel, new DateLabelFormatter());
		//details JPanel(CENTER)
		addProduct = new JButton("Add product");
		editProduct = new JButton("Edit product");
		removeProduct = new JButton("Remove product");
		orderDateLabel = new JLabel("Order date:");
		//Date picker for invoice date
		ordDateModel = new UtilDateModel();
		Properties ordDateProp = new Properties();
		ordDateProp.put("text.today", "Today");
		ordDateProp.put("text.month", "Month");
		ordDateProp.put("text.year", "Year");
		ordDatePanel = new JDatePanelImpl(ordDateModel, ordDateProp);
		ordDatePicker = new JDatePickerImpl(ordDatePanel, new DateLabelFormatter());
		//Summary field		
		totalLabel = new JLabel("Total:");
		totalLabel.setBorder(BorderFactory.createEmptyBorder(0, 260, 0, 0));
		totalField = new JTextField(15);
		totalField.setEditable(false);
		totalField.setBackground(Color.white);

		
		//Footer JPanel (SOUTH)
		save = new JButton("Save");
		savePrint = new JButton("Save print");
		delete = new JButton("Delete");
		print = new JButton("Print");
		edit = new JButton("Edit");
		
		//Select invoice button
		selectInvoice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new InvoiceSearch(frame);
			}
		});
		
		
		//Add issuer button
		addIssuer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddingIssuer(frame, issuerData);
				
			}
		});
		
		
		//Add customer button
		addCustomer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new CustomerSearch(frame, customerData, customerId);
				
			}
		});
		
		//Add item button
		addProduct.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ProductSearch(items, frame, totalField, mapId);
				
			}
		});
		
		//Edit item button
		editProduct.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//getting id and name of selected row					
					int rowIndex = items.getSelectedRow();
					int columnIndex = items.getSelectedColumn();
					new EditingItem(items,frame, rowIndex, columnIndex,totalField);
					
				}catch(ArrayIndexOutOfBoundsException a) {
					JOptionPane.showMessageDialog(null,  "Select row to edit!");
			}
			}
		});
		
		//Deleting item button
		removeProduct.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int deletingProdId = Integer.parseInt(	
							items.getValueAt(items.getSelectedRow(), 0).toString());
					
					int conf = JOptionPane.showConfirmDialog(
							null,"Are you sure you want to delete product? \n",
							"Confirm deleting", JOptionPane.YES_NO_OPTION);
					if(conf == JOptionPane.YES_OPTION) {
						Control.removeItem(items, deletingProdId, mapId);
						
						//calculating and setting totalGross value
						int rowCounter = items.getRowCount();
						totalGross = 0;
						for(int i =0; i < rowCounter; i++)
							totalGross = totalGross + (Double)items.getValueAt(i, 7);
						
						totalField.setText(Control.formatValue(totalGross));
						
						
					}
				}catch(ArrayIndexOutOfBoundsException a) {
					JOptionPane.showMessageDialog(null, "Select row to delete!");
				}
				
			}
		});
		
		//Save invoice button
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			try {
				//converting date to string
				int custId = Integer.parseInt(customerId.getText());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String ordDate =  sdf.format(ordDateModel.getValue());
				String invDate =  sdf.format(invDateModel.getValue());
				Double totalF = Double.parseDouble(totalField.getText());
				
				Control.addOrders(custId, ordDate, invDate, totalF);
				int lastId = Control.lastOrderId;
				Control.addOrders_Details(items, lastId, mapId);

				new CreatePdf(lastId);
				JOptionPane.showMessageDialog(null, "Invoice saved");
				model.setRowCount(0);
				totalGross = 0;
				totalField.setText("");
				customerId.setText("");
				customerData.setText("");
				ordDateModel.setSelected(false);
				mapId.clear();
			}catch(NumberFormatException a) {
				JOptionPane.showMessageDialog(null,  "All fields have to be filled!");
			}catch(NullPointerException an) {
				JOptionPane.showMessageDialog(null,  "All fields have to be filled!");
			}
			}
		});
		
		
		//create empty table model
		model = new DefaultTableModel(new String[] {
				"Item", "Product", "Qty",
				"Net price", "Total net", "Tax rate(%)",
				"Tax amount","Total gross"},0) {

			private static final long serialVersionUID = 1L;
			boolean[] canEdit = new boolean[] {
					false,false,false,false,false, false,false,false};
			public boolean isCellEditable(int rowIndex, int columnIndex) {
			return canEdit[columnIndex];
			}
		};
		
		
		
		// Creating table for items
		items = new JTable(model);
		mapId = new HashMap<Integer, Integer>();

		items.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		items.getColumnModel().getColumn(0).setPreferredWidth(30);
		items.getColumnModel().getColumn(1).setPreferredWidth(250);
		items.getColumnModel().getColumn(2).setPreferredWidth(80);
		items.getColumnModel().getColumn(3).setPreferredWidth(80);
		items.getColumnModel().getColumn(4).setPreferredWidth(80);
		items.getColumnModel().getColumn(5).setPreferredWidth(80);
		items.getColumnModel().getColumn(6).setPreferredWidth(80);
		items.getColumnModel().getColumn(7).setPreferredWidth(80);
		
		scr = new JScrollPane(items, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
									JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		//--Adding elements to JPanels
		header.add(headerUp, BorderLayout.NORTH);
		header.add(headerLow, BorderLayout.SOUTH);		
		headerUp.add(selectInvoice);
		headerUp.add(invoiceDateLabel);
		headerUp.add(invDatePicker);
		headerLow.add(headerLowLeft);
		headerLow.add(headerLowRight);
		headerLowLeft.add(addIssuer);
		headerLowLeft.add(issuerData);
		headerLowRight.add(addCustomer);
		headerLowRight.add(customerData);
		details.add(detailsNorth, BorderLayout.NORTH);
		details.add(detailsCenter, BorderLayout.CENTER);
		details.add(detailsSouth, BorderLayout.SOUTH);
		detailsNorth.add(addProduct);
		detailsNorth.add(editProduct);
		detailsNorth.add(removeProduct);
		detailsCenter.add(scr);
		detailsSouth.add(orderDateLabel, BorderLayout.SOUTH);
		detailsSouth.add(ordDatePicker, BorderLayout.SOUTH);
		detailsSouth.add(totalLabel, BorderLayout.SOUTH);
		detailsSouth.add(totalField, BorderLayout.SOUTH);
		footer.add(save);
		footer.add(savePrint);
		footer.add(delete);
		footer.add(print);
		footer.add(edit);
		

		//adding jpanels to Jframe
		frame.add(header,BorderLayout.NORTH);
		frame.add(details, BorderLayout.CENTER);
		frame.add(footer, BorderLayout.SOUTH);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				frame.pack();
				frame.setVisible(true);
				frame.setSize(800,600);
				frame.setResizable(false);
				
			}
		}); 
		}

}
