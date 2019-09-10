package view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import org.jdatepicker.impl.*;

import control.Control;
import control.DateLabelFormatter;
import model.*;

import java.awt.*;
import java.awt.List;
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
	
	public InvoiceSearch(JFrame frame){
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

		
		startDateModel = new UtilDateModel();
		dateProp = new Properties();
		dateProp.put("text.today", "Today");
		dateProp.put("text.month", "Month");
		dateProp.put("text.year", "Year");
		startDatePanel = new JDatePanelImpl(startDateModel, dateProp);
		startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
		startDatePicker.setPreferredSize(new Dimension(130,50));
		startDatePicker.setBorder(BorderFactory.createTitledBorder("Start date:"));
		
		endDateModel = new UtilDateModel();
		endDatePanel = new JDatePanelImpl(endDateModel, dateProp);
		endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
		endDatePicker.setPreferredSize(new Dimension(130,50));
		endDatePicker.setBorder(BorderFactory.createTitledBorder("End date:"));
		
		searchField = new JTextField(20);
		search = new JButton("Search:");
		
		
		table = new JTable(Control.populateOrders());

		
		
		
		
		//Adding to panels

		NorthPanIn.add(startDatePicker);
		NorthPanIn.add(endDatePicker);
		NorthPanIn.add(search);
		NorthPanIn.add(searchField);
		SouthPanIn.add(accept);
		
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
