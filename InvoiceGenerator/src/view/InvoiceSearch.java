package view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import org.jdatepicker.impl.*;

import control.Control;
import control.DateLabelFormatter;
import model.*;

import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InvoiceSearch {
	private JDialog dialInv;
	private JPanel NorthPan;
	private JPanel NorthPanIn;
	private JPanel CenterPan;
	private JPanel CenterPanIn;
	private JPanel SouthPan;
	private JPanel SouthPanIn;
	private JButton accept;
	private JButton cancel;
	private JButton search;
	private JTextField searchField;
	private UtilDateModel startDateModel;
	private JDatePanelImpl startDatePanel;
	private JDatePickerImpl startDatePicker;
	private Properties dateProp;
	private UtilDateModel endDateModel;
	private JDatePanelImpl endDatePanel;
	private JDatePickerImpl endDatePicker;
	private JTable table;
	private JTable tab;
	private JScrollPane scr;
	
	public InvoiceSearch(JFrame frame,JTextArea customerData, JTextField invoiceId,
			UtilDateModel ordDateModel, UtilDateModel invDateModel, JTable items,
			JTextField totalField){

		dialInv = new JDialog(
				frame, "Invoice search:", JDialog.DEFAULT_MODALITY_TYPE);
		dialInv.setLayout(new BorderLayout(5,0));
		dialInv.setPreferredSize(new Dimension(600,400));
		dialInv.setResizable(false);
		dialInv.setLocation(100,100);;
		dialInv.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		NorthPan = new JPanel(new BorderLayout(5,5));
		NorthPan.setPreferredSize(new Dimension(100,65));
		NorthPan.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		NorthPanIn = new JPanel();
		NorthPanIn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		CenterPan = new JPanel();
		CenterPan.setBorder(BorderFactory.createEmptyBorder(0,5,5,5));
		CenterPanIn = new JPanel();
		CenterPanIn.setPreferredSize(new Dimension(580,240));
		CenterPanIn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		SouthPan = new JPanel(new BorderLayout(5,5));
		SouthPan.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		SouthPanIn = new JPanel();
		SouthPanIn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		accept = new JButton("Accept");
		cancel = new JButton("Cancel");
		
		startDateModel = new UtilDateModel();
		startDateModel.setDate(1900, 01, 01);
		startDateModel.setSelected(true);
		dateProp = new Properties();
		dateProp.put("text.today", "Today");
		dateProp.put("text.month", "Month");
		dateProp.put("text.year", "Year");
		startDatePanel = new JDatePanelImpl(startDateModel, dateProp);
		startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
		startDatePicker.setPreferredSize(new Dimension(130,50));
		startDatePicker.setBorder(BorderFactory.createTitledBorder("Start date:"));
		
		endDateModel = new UtilDateModel();
		endDateModel.setSelected(true);
		endDatePanel = new JDatePanelImpl(endDateModel, dateProp);
		endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
		endDatePicker.setPreferredSize(new Dimension(130,50));
		endDatePicker.setBorder(BorderFactory.createTitledBorder("End date:"));
		
		
		//searching for customer name like %x% and date between two selected dates
		searchField = new JTextField(20);
		search = new JButton("Search:");
		
		Action searchAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				String searchValue = searchField.getText();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String startDate = sdf.format(startDateModel.getValue());
				String endDate = sdf.format(endDateModel.getValue());
				table.setModel(Control.populateOrdersLike(searchValue,startDate,endDate));
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.getColumnModel().getColumn(0).setPreferredWidth(30);
				table.getColumnModel().getColumn(1).setPreferredWidth(228);
				table.getColumnModel().getColumn(2).setPreferredWidth(100);
				table.getColumnModel().getColumn(3).setPreferredWidth(100);
				table.getColumnModel().getColumn(4).setPreferredWidth(100);
				}catch(NullPointerException n) {
					JOptionPane.showMessageDialog(null, "Choose date");
				
				}
			}
		};
		
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//selecting invoice number
					int invNumber = Integer.parseInt(
							table.getValueAt(table.getSelectedRow(),0).toString());
					invoiceId.setText(Integer.toString(invNumber));
					
					tab = new JTable(Control.populateOrdersClean(invNumber));
					int custNumber = Integer.parseInt(
							tab.getValueAt(0,1).toString());
					
					//selecting customer from invoice
					String custData = Control.selectCustomer(custNumber);
					customerData.setText(custData);
					
					//selecting order date
					String oDate = table.getValueAt(table.getSelectedRow(), 2).toString();
					Date ordDate = null;
					try {
						ordDate = new SimpleDateFormat("yyyy-MM-dd").parse(oDate);
					} catch (ParseException e1) {

						e1.printStackTrace();
					}
					ordDateModel.setValue(ordDate);
					
					//selecting invoice date
					String iDate = table.getValueAt(table.getSelectedRow(), 3).toString();
					Date invDate = null;
					try {
						invDate = new SimpleDateFormat("yyyy-MM-dd").parse(iDate);
					} catch (ParseException e1) {

						e1.printStackTrace();
					}
					invDateModel.setValue(invDate);
					
					//selecting total from invoice
					
					String tot = table.getValueAt(table.getSelectedRow(), 4).toString();
					totalField.setText(tot);
					
					//selecting invoice details into table
					Control.selectOrders_DetailsLikeId(items, invNumber);
				
					
					
					dialInv.dispose();
				}catch(ArrayIndexOutOfBoundsException a) {
					JOptionPane.showMessageDialog(null, "Select invoice to accept!");
				}catch(NullPointerException n) {
					JOptionPane.showMessageDialog(null, "Null pointer...");
				}
				
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialInv.dispose();	
			}
		});
		
		searchField.addActionListener(searchAction);
		search.addActionListener(searchAction);
		
		
		//Table with invoices on the panel
		table = new JTable(Control.populateOrders());
		table.setDefaultEditor(Object.class, null);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(228);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);

		
		scr = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setPreferredSize(new Dimension(575,230));
		
		
		
		
		//Adding to panels
		
		NorthPanIn.add(startDatePicker);
		NorthPanIn.add(endDatePicker);
		NorthPanIn.add(search);
		NorthPanIn.add(searchField);
		CenterPanIn.add(scr);
		SouthPanIn.add(accept);
		SouthPanIn.add(cancel);
		
		NorthPan.add(NorthPanIn);
		CenterPan.add(CenterPanIn);
		SouthPan.add(SouthPanIn);
		
		dialInv.add(NorthPan,BorderLayout.NORTH);
		dialInv.add(CenterPan, BorderLayout.CENTER);
		dialInv.add(SouthPan, BorderLayout.SOUTH);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dialInv.pack();
				dialInv.setVisible(true);
			}
		});
		
	}
}
