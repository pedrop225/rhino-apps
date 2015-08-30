package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InvoiceDataCollector extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private CChooserLauncher ccl;
	private JTextField invNum;
	private UChooserLauncher ucl;
	
	public InvoiceDataCollector() {
		init();
	}
	
	private void init() {
		setLayout(new BorderLayout(5, 0));
		
		JPanel lbls = new JPanel(new GridLayout(0, 1, 0, 2));
		JPanel info = new JPanel(new GridLayout(0, 1, 0, 2));
		
		lbls.add(new JLabel("Campaña"));
		lbls.add(new JLabel("Nº Factura"));
		lbls.add(new JLabel("Usuario"));
		
		ccl = new CChooserLauncher();
		invNum = new JTextField();
		ucl = new UChooserLauncher();
		
		info.add(ccl);
		info.add(invNum);
		info.add(ucl);
		
		add(lbls, BorderLayout.WEST);
		add(info);
		add(Box.createHorizontalStrut(100), BorderLayout.EAST);
	}
}
