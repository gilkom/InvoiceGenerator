package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.*;

public class EditingItem {
	public EditingItem(JTable tab, JFrame invFrame, int rowIndex,
							int columnIndex, JTextField totalField) {
		
		JDialog dial = new JDialog(invFrame, "Edit item: ", JDialog.DEFAULT_MODALITY_TYPE);
		dial.setLayout(new BorderLayout());
		dial.setPreferredSize(new Dimension(600,200));;
		dial.setResizable(false);
		dial.setLocation(100,100);
		
		//Panel with fields of customer
		JPanel pan = new JPanel(new FlowLayout(10));
		pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel numberLabel = new JLabel("Item number:");
		JTextField numberField = new JTextField(3);
		
		JLabel nameLabel = new JLabel("Name:");
		JTextArea nameField = new JTextArea(3,35);
		(nameField).setBorder(new JTextField().getBorder());
		nameField.setLineWrap(true);
		
		JLabel quantityLabel = new JLabel("Quantity:");
		JTextField quantityField = new JTextField(4);
	
		JLabel priceLabel = new JLabel("Net price:");
		JTextField priceField = new JTextField(10);
		
		JLabel taxLabel = new JLabel("Tax(%):");
		JTextField taxField = new JTextField(2);
		
		
		//selected item data in editable fields
		numberField.setText(tab.getValueAt((rowIndex),0).toString());
		numberField.setEditable(false);
		numberField.setBackground(new Color(219,227,222));
		
		nameField.setText(tab.getValueAt((rowIndex),1).toString());
		nameField.setEditable(false);
		nameField.setBackground(new Color(219,227,222));
		
		quantityField.setText(tab.getValueAt((rowIndex),2).toString());
		priceField.setText(tab.getValueAt((rowIndex),3).toString());
		taxField.setText(tab.getValueAt((rowIndex),5).toString());
		
		//Panel with buttons: accept and cancel
		JPanel butPan = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,10));
		
		//Button accepting product values edited in the panel
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//selecting productId
				try {

				//message returns the String with error message when some of the fields has not right pattern
				DataMatcher d = new DataMatcher();

				String message = d.itemPattern(quantityField.getText(),
						priceField.getText(),taxField.getText());
						
			//adding product if there is no error message or open JOptionPane with error message		
				if(message.matches("ok")) {

					Control.updItem(tab ,columnIndex, rowIndex, 
							Integer.parseInt(quantityField.getText()),
							Double.parseDouble(priceField.getText()), 
							Integer.parseInt(taxField.getText()));

					//calculating and setting totalGross value
					int rowCounter = tab.getRowCount();
					double totalGross = 0;
					for(int i =0; i < rowCounter; i++)
						totalGross = totalGross + (Double)tab.getValueAt(i, 7);
					
					totalGross = Math.round(totalGross *100.0)/100.0;
					totalField.setText(Double.toString(totalGross));
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
		
		pan.add(numberLabel);
		pan.add(numberField);	
		pan.add(nameLabel);
		pan.add(nameField);	
		pan.add(quantityLabel);
		pan.add(quantityField);	
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
