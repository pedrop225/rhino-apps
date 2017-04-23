package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.desktop.rhinos.connector.Connector.App;

@SuppressWarnings("serial")
public abstract class ChooserLauncher extends JPanel {
	
	protected JTextField t_field;
	protected JButton btn;
	
	public ChooserLauncher() {
		super(new BorderLayout());
		t_field = new JTextField(25);
		t_field.setEditable(false);
		t_field.setFont(App.DEFAULT_FONT);

		btn = new JButton();
		btn.setFocusable(false);
		
		add(t_field);
		add(btn, BorderLayout.EAST);
	}
	
	public void setFieldsEditable(boolean e) {
		btn.setEnabled(e);
	}
	
	public void setTextFieldColumns(int columns) {
		t_field.setColumns(columns);
	}
}
