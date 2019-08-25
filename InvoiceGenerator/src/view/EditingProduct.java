package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.*;

public class EditingProduct {
	public EditingProduct(JTable tab, JDialog dialProd, int rowIndex) {
		
		JDialog dial = new JDialog(dialProd, "Edit product: ", JDialog.DEFAULT_MODALITY_TYPE);
		dial.setLayout(new BorderLayout());
		dial.setPreferredSize(new Dimension(600,200));;
		dial.setResizable(false);
		dial.setLocation(100,100);
		
		//Panel with fields of customer
		JPanel pan = new JPanel(new FlowLayout(10));
		pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel nameLabel = new JLabel("Name:");
		JTextArea nameField = new JTextArea(2,50);
		(nameField).setBorder(new JTextField().getBorder());
		nameField.setLineWrap(true);
		
		JLabel priceLabel = new JLabel("Price:");
		JTextField priceField = new JTextField(10);
	
		JLabel taxLabel = new JLabel("Tax(%):");
		JTextField taxField = new JTextField(4);
		
		
		//selected customer data in editable fields
		nameField.setText(tab.getValueAt((rowIndex),1).toString());
		priceField.setText(tab.getValueAt((rowIndex),2).toString());
		taxField.setText(tab.getValueAt((rowIndex),3).toString());
		
		//Panel with buttons: accept and cancel
		JPanel butPan = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,10));
		
		//Button accepting product values edited in the panel
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//selecting productId
				try {
				int prodEdId = Integer.parseInt(tab.getValueAt((rowIndex),0).toString());
				//message returns the String with error message when some of the fields has not right pattern
				DataMatcher d = new DataMatcher();
				double priceF = Double.valueOf(priceField.getText());
				int taxF = Integer.valueOf(taxField.getText());
				
				String message = d.prodPattern(nameField.getText(),
						priceField.getText(),taxField.getText());
						
			//adding product if there is no error message or open JOptionPane with error message		
				if(message.matches("ok")) {
					Control.updProduct(prodEdId, nameField.getText(), priceF, taxF);
					tab.setModel(Control.populateProduct());
							
					//setting table size
					tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					tab.getColumnModel().getColumn(0).setPreferredWidth(40);
					tab.getColumnModel().getColumn(1).setPreferredWidth(500);
					tab.getColumnModel().getColumn(2).setPreferredWidth(110);
					tab.getColumnModel().getColumn(3).setPreferredWidth(50);
							
					//selecting updated row				
					tab.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
					tab.scrollRectToVisible(new Rectangle(tab.getCellRect(rowIndex, 0,  true)));
							
					dial.dispose();
				}else {
					JOptionPane.showMessageDialog(null, message);
				}
			}catch(NumberFormatException a) {
				JOptionPane.showMessageDialog(null,  "All fields have to be filled!");
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
		pan.add(priceLabel);
		pan.add(priceField);
		pan.add(taxLabel);
		pan.add(taxField);
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
