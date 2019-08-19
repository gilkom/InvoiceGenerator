package view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

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
	private JButton save;
	private JButton savePrint;
	private JButton delete;
	private JButton print;
	private JButton edit;
	private JTextField invoiceDateField;
	private JTextField orderDateField;
	private JTextField totalField;
	private JTextArea customerData;
	private JTextArea issuerData;
	private JLabel invoiceDateLabel;
	private JLabel orderDateLabel;
	private JLabel totalLabel;
	private JTable items;
	private JScrollPane scr;
	
	public CreateInvoice() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(5, 10));
		
		
		header = new JPanel(new BorderLayout());
		//header.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
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
		detailsSouth = new JPanel();
		detailsSouth.setBorder(BorderFactory.createEtchedBorder());
		footer = new JPanel();
		footer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		
		selectInvoice = new JButton("Select invoice");
		addCustomer = new JButton("Add customer");
		addCustomer.setPreferredSize(new Dimension(120,15));
		invoiceDateField = new JTextField(10);
		customerData = new JTextArea();
		customerData.setPreferredSize(new Dimension(350,50));
		issuerData = new JTextArea(3,30);
		issuerData.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		invoiceDateLabel = new JLabel("Invoice date:");
		invoiceDateLabel.setBorder(BorderFactory.createEmptyBorder(0, 350, 0, 0));
		addProduct = new JButton("Add product");
		orderDateLabel = new JLabel("Order date:");
		orderDateField = new JTextField(10);
		totalLabel = new JLabel("Total:");
		totalField = new JTextField(15);
		save = new JButton("Save");
		savePrint = new JButton("Save print");
		delete = new JButton("Delete");
		print = new JButton("Print");
		edit = new JButton("Edit");
		
		addProduct.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ProductSearch prodPane = new ProductSearch(items, frame);
				
			}
		});
		
		
		
		
		//create empty table model
		DefaultTableModel model = new DefaultTableModel(new String[] {
				"Item", "Product", "Qty",
				"Net price", "Total net", "Tax rate(%)",
				"Tax amount","Total gross"},0);
		
		//items = new JTable(Control.populateOrders_Details());
		items = new JTable(model);
		items.setDefaultEditor(Object.class, null);
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
		headerUp.add(invoiceDateField);
		headerLow.add(headerLowLeft);
		headerLow.add(headerLowRight);
		headerLowLeft.add(issuerData);
		headerLowRight.add(addCustomer);
		headerLowRight.add(customerData);
		details.add(detailsNorth, BorderLayout.NORTH);
		details.add(detailsCenter, BorderLayout.CENTER);
		details.add(detailsSouth, BorderLayout.SOUTH);
		detailsNorth.add(addProduct, BorderLayout.NORTH);
		detailsCenter.add(scr);
		detailsSouth.add(orderDateLabel, BorderLayout.SOUTH);
		detailsSouth.add(orderDateField, BorderLayout.SOUTH);
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
