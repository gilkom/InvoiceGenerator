package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.*;
import invoice.*;

public class AddingCustomer {
	
	public AddingCustomer(JTable tab) {
		
		JFrame f = new JFrame("New customer:");
		f.setLayout(new BorderLayout());
		
		JPanel pan = new JPanel(new FlowLayout(10));
		pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel nameLabel = new JLabel("Name:");
		JTextArea nameField = new JTextArea(3,50);
		(nameField).setBorder(new JTextField().getBorder());
		nameField.setLineWrap(true);
		
		JLabel streetLabel = new JLabel("Street:");
		JTextField streetField = new JTextField(25);
		
		JLabel cityLabel = new JLabel("City:");
		JTextField cityField = new JTextField(20);
		
		JLabel postCodeLabel = new JLabel("Post code:");
		JTextField postCodeField = new JTextField(5);
	
		JLabel nipLabel = new JLabel("Nip:");
		nipLabel.setBorder(new EmptyBorder(0,100,0,0));
		JTextField nipField = new JTextField(10);
		
		JPanel butPan = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,10));
	
		
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Control.addCustomer(nameField.getText(), streetField.getText(),
						cityField.getText(), postCodeField.getText(), nipField.getText());
				tab.setModel(Control.populateCustomer());
				
				//setting table size
				tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				tab.getColumnModel().getColumn(0).setPreferredWidth(40);
				tab.getColumnModel().getColumn(1).setPreferredWidth(300);
				tab.getColumnModel().getColumn(2).setPreferredWidth(119);
				tab.getColumnModel().getColumn(3).setPreferredWidth(100);
				tab.getColumnModel().getColumn(4).setPreferredWidth(60);
				tab.getColumnModel().getColumn(5).setPreferredWidth(80);
			}
		});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.dispose();	
			}
		});
		
		
		pan.add(nameLabel);
		pan.add(nameField);	
		pan.add(streetLabel);
		pan.add(streetField);
		pan.add(cityLabel);
		pan.add(cityField);
		pan.add(postCodeLabel);
		pan.add(postCodeField);
		pan.add(nipLabel);
		pan.add(nipField);
		butPan.add(accept);
		butPan.add(cancel);
		f.add(pan, BorderLayout.CENTER);
		f.add(butPan, BorderLayout.SOUTH);
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
			f.pack();
			f.setVisible(true);
			f.setSize(600,200);
			f.setLocation(100, 100);
			f.setResizable(false);
			}
		});
	}
}