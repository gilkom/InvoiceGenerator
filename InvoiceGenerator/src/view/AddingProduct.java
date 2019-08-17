package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.*;
import invoice.*;

public class AddingProduct {
	
	public AddingProduct(JTable tab, JFrame fr) {
		
		//JFrame f = new JFrame("New customer:");
		
		
		JDialog dial =new JDialog(fr,"New product: ", JDialog.DEFAULT_MODALITY_TYPE);
		dial.setLayout(new BorderLayout());
		dial.setPreferredSize(new Dimension(600,180));
		dial.setResizable(false);
		dial.setLocation(100,100);


		
		JPanel pan = new JPanel(new FlowLayout(10));

		
		pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel nameLabel = new JLabel("Name:");
		JTextArea nameField = new JTextArea(2,50);
		(nameField).setBorder(new JTextField().getBorder());
		nameField.setLineWrap(true);
		
		JLabel priceLabel = new JLabel("Price:");
		JTextField priceField = new JTextField(10);
	
		JLabel taxLabel = new JLabel("Tax(%):");
		//taxLabel.setBorder(new EmptyBorder(0,100,0,0));
		JTextField taxField = new JTextField(4);
		
		JPanel butPan = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,10));
	
//Button accepting customer values typed in the panel
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//message returns the String with error message when some of the fields are not right
				DataMatcher d = new DataMatcher();
				double priceF = Double.valueOf(priceField.getText());
				int taxF = Integer.valueOf(taxField.getText());
				
				String message = d.prodPattern(nameField.getText(), priceF,taxF);
				
					//adding customer if there is no error message or open JOptionPane with error		
				if(message.matches("ok")) {
					Control.addProduct(nameField.getText(), priceF, taxF);
					tab.setModel(Control.populateProduct());
					
					//setting table size
					tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					tab.getColumnModel().getColumn(0).setPreferredWidth(40);
					tab.getColumnModel().getColumn(1).setPreferredWidth(500);
					tab.getColumnModel().getColumn(2).setPreferredWidth(110);
					tab.getColumnModel().getColumn(3).setPreferredWidth(50);
					
					//selecting last row
					int rowCount = tab.getRowCount();					
					tab.getSelectionModel().setSelectionInterval(rowCount-1, rowCount-1);
					tab.scrollRectToVisible(new Rectangle(tab.getCellRect(rowCount-1, 0,  true)));
					
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