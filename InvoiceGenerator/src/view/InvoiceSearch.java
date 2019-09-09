package view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.*;

public class InvoiceSearch {
	JDialog dialInv;
	JPanel NorthPan;
	JPanel NorthPanIn;
	JPanel CenterPan;
	JPanel CenterPanIn;
	JButton accept;
	
	
	public InvoiceSearch(JFrame frame){
		dialInv = new JDialog(
				frame, "Invoice search:", JDialog.DEFAULT_MODALITY_TYPE);
		dialInv.setLayout(new BorderLayout(5,0));
		dialInv.setPreferredSize(new Dimension(600,400));
		dialInv.setResizable(false);
		dialInv.setLocation(100,100);;
		dialInv.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		NorthPan = new JPanel(new BorderLayout(5,5));
		NorthPan.setPreferredSize(new Dimension(100,50));
		NorthPan.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		NorthPanIn = new JPanel(new GridLayout());
		NorthPanIn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		CenterPan = new JPanel();
		CenterPan.setBorder(BorderFactory.createEmptyBorder(0,5,5,5));
		CenterPanIn = new JPanel();
		CenterPanIn.setPreferredSize(new Dimension(580,290));
		CenterPanIn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		accept = new JButton("Accept");
		
		
		NorthPanIn.add(accept);
		
		NorthPan.add(NorthPanIn);
		CenterPan.add(CenterPanIn);
		
		dialInv.add(NorthPan,BorderLayout.NORTH);
		dialInv.add(CenterPan, BorderLayout.CENTER);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dialInv.pack();
				dialInv.setVisible(true);
			}
		});
		
	}

}
