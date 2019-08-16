package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.*;
import invoice.*;
import model.*;

public class EditingCustomer {
	public EditingCustomer(JTable tab, JFrame fr, int rowIndex) {
		
		JDialog dial = new JDialog(fr, "Edit customer: ", JDialog.DEFAULT_MODALITY_TYPE);
		dial.setLayout(new BorderLayout());
		dial.setPreferredSize(new Dimension(600,200));;
		dial.setResizable(false);
		dial.setLocation(100,100);
		
		//Panel with fields of customer
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
		
		
		//selected customer data in editable fields
		nameField.setText(tab.getValueAt((rowIndex),1).toString());
		streetField.setText(tab.getValueAt((rowIndex),2).toString());
		cityField.setText(tab.getValueAt((rowIndex),3).toString());
		postCodeField.setText(tab.getValueAt((rowIndex),4).toString());
		nipField.setText(tab.getValueAt((rowIndex),5).toString());
		//Control.selectCustomer(custId);
		
		
		
		//Panel with buttons: accept and cancel
		JPanel butPan = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,10));
		
		//Button accepting customer values edited in the panel
				JButton accept = new JButton("Accept");
				accept.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int custEdId = Integer.parseInt(tab.getValueAt((rowIndex),0).toString());
						//message returns the String with error message when some of the fields are not right
						DataMatcher d = new DataMatcher();
						String message = d.custPattern(nameField.getText(), streetField.getText(),
								cityField.getText(), postCodeField.getText(), nipField.getText());
						
							//adding customer if there is no error message or open JOptionPane with error		
						if(message.matches("ok")) {
							Control.updCustomer(custEdId, nameField.getText(), streetField.getText(),
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
							
							//selecting updated row				
							tab.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
							tab.scrollRectToVisible(new Rectangle(tab.getCellRect(rowIndex, 0,  true)));
							
							dial.dispose();
						}else {
							JOptionPane.showMessageDialog(null, message);
						}
						
					}
				});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dial.dispose();	
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
		dial.add(pan, BorderLayout.CENTER);
		dial.add(butPan, BorderLayout.SOUTH);
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
			dial.pack();
			dial.setVisible(true);
			}
		});
	}
	
	
}
