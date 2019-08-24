package view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import org.jdatepicker.impl.*;

import control.DateLabelFormatter;
import control.Control;


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
	private JButton addProduct;
	private JButton removeProduct;
	private JButton editProduct;
	private JButton save;
	private JButton savePrint;
	private JButton delete;
	private JButton print;
	private JButton edit;
	private JTextField totalField;
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
	
	public CreateInvoice() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(5, 10));
		
		
		header = new JPanel(new BorderLayout());
		header.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));	
		headerUp = new JPanel(new FlowLayout(10,30,10));
		headerUp.setBorder(BorderFactory.createEtchedBorder());
		headerLow = new JPanel(new GridLayout());
		headerLow.setBorder(BorderFactory.createEtchedBorder());
		headerLowLeft = new JPanel();
		headerLowLeft.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
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
		
		selectInvoice = new JButton("Select invoice");
		addCustomer = new JButton("Add customer");
		addCustomer.setPreferredSize(new Dimension(120,15));
		customerData = new JTextArea();
		customerData.setPreferredSize(new Dimension(350,50));
		issuerData = new JTextArea(3,30);
		issuerData.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
		
		addProduct = new JButton("Add product");
		editProduct = new JButton("Edit product");
		removeProduct = new JButton("Remove product");
		orderDateLabel = new JLabel("Order date:");
		//Date picker for invoice date
				ordDateModel = new UtilDateModel();
				//ordDateModel.setSelected(true);
				Properties ordDateProp = new Properties();
				ordDateProp.put("text.today", "Today");
				ordDateProp.put("text.month", "Month");
				ordDateProp.put("text.year", "Year");
				ordDatePanel = new JDatePanelImpl(ordDateModel, ordDateProp);
				ordDatePicker = new JDatePickerImpl(ordDatePanel, new DateLabelFormatter());
				
		totalLabel = new JLabel("Total:");
		totalLabel.setBorder(BorderFactory.createEmptyBorder(0, 260, 0, 0));
		totalField = new JTextField(15);
		save = new JButton("Save");
		savePrint = new JButton("Save print");
		delete = new JButton("Delete");
		print = new JButton("Print");
		edit = new JButton("Edit");
		
		addCustomer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new CustomerSearch(frame, customerData);
				
			}
		});
		
		addProduct.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ProductSearch(items, frame);
				
			}
		});
		editProduct.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//getting id and name of selected row					
					int rowIndex = items.getSelectedRow();
					int columnIndex = items.getSelectedColumn();
					new EditingItem(items,frame, rowIndex, columnIndex);
				}catch(ArrayIndexOutOfBoundsException a) {
					JOptionPane.showMessageDialog(null,  "Select row to edit!");
			}
			}
		});
		
		
		
		//create empty table model
		DefaultTableModel model = new DefaultTableModel(new String[] {
				"Item", "Product", "Qty",
				"Net price", "Total net", "Tax rate(%)",
				"Tax amount","Total gross"},0) {
			boolean[] canEdit = new boolean[] {
					false,false,false,false,false, false,false,false};
			public boolean isCellEditable(int rowIndex, int columnIndex) {
			return canEdit[columnIndex];
			}
		};

		//items = new JTable(Control.populateOrders_Details());
		items = new JTable(model);

		//items.setDefaultEditor(Object.class, null);
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
		
		
		header.add(headerUp, BorderLayout.NORTH);
		header.add(headerLow, BorderLayout.SOUTH);		
		headerUp.add(selectInvoice);
		headerUp.add(invoiceDateLabel);
		headerUp.add(invDatePicker);
		headerLow.add(headerLowLeft);
		headerLow.add(headerLowRight);
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
