package view;


import java.awt.*;
import javax.swing.*;


public class CreateInvoice {
	
	JFrame frame;
	private JPanel header;
	private JPanel headerUp;
	private JPanel headerLow;
	private JPanel details;
	private JPanel footer;
	private JButton selectInvoice;
	private JButton addCustomer;
	private JTextField invoiceDateField;
	private JTextArea customerData;
	private JTextArea issuerData;
	private JLabel invoiceDateLabel;

	
	public CreateInvoice() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(10, 10));
		
		header = new JPanel(new BorderLayout());
		headerUp = new JPanel();
		headerLow = new JPanel();
		details = new  JPanel();
		footer = new JPanel();
		
		selectInvoice = new JButton("Select invoice");
		addCustomer = new JButton("Add customer");
		invoiceDateField = new JTextField(10);
		customerData = new JTextArea(3,30);
		issuerData = new JTextArea(3,30);
		invoiceDateLabel = new JLabel("Invoice date:");
		
		
		headerUp.add(selectInvoice);
		headerUp.add(invoiceDateLabel);
		headerUp.add(invoiceDateField);
		headerLow.add(issuerData);
		headerLow.add(addCustomer);
		headerLow.add(customerData);
		
		header.add(headerUp, BorderLayout.NORTH);
		header.add(headerLow, BorderLayout.SOUTH);
		
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
