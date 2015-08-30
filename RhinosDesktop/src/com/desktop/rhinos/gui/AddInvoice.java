package com.desktop.rhinos.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import com.desktop.rhinos.gui.dataCollector.InvoiceDataCollector;
import com.desktop.rhinos.gui.table.ServiceTable;

public class AddInvoice extends JDialog {
 
	private static final long serialVersionUID = 1L;

	private InvoiceDataCollector idc;
	private ServiceTable services;
	
	public AddInvoice() {
		setModal(true);
		setTitle("Gestión de Facturas.");
		
		idc = new InvoiceDataCollector();
		idc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		services = new ServiceTable("NIF", "Titular", "Importe");

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(idc, BorderLayout.NORTH);
		getContentPane().add(services);
				
		pack();
		setLocationRelativeTo(null);
	}
}
