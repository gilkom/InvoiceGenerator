package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.*;
import model.Customer;

public class AddingIssuer {
	
	public AddingIssuer(JFrame jd, JTextArea issuerData) {

		JDialog dial =new JDialog(jd,"Set issuer: ", JDialog.DEFAULT_MODALITY_TYPE);
		dial.setLayout(new BorderLayout());
		dial.setPreferredSize(new Dimension(600,200));
		dial.setResizable(false);
		dial.setLocation(100,100);


		
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
	
//Button accepting customer values typed in the panel
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//message returns the String with error message when some of the fields are not right
				DataMatcher d = new DataMatcher();
				String message = d.custPattern(nameField.getText(), streetField.getText(),
						cityField.getText(), postCodeField.getText(), nipField.getText());
				
					//adding customer if there is no error message or open JOptionPane with error		
				if(message.matches("ok")) {
					Customer cust = Control.createIssuer(nameField.getText(), streetField.getText(),
							cityField.getText(), postCodeField.getText(), nipField.getText());
					
					issuerData.setText(cust.toString());
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