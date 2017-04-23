package com.desktop.rhinos.gui.dataCollector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.Connector.App;
import com.desktop.rhinos.gui.UserHierarchyDialog;

@SuppressWarnings("serial")
class UChooserLauncher extends ChooserLauncher {
	
	private UserHierarchyDialog uhd;
	private User user;
	
	public UChooserLauncher() {
		btn.setIcon(new ImageIcon(UChooserLauncher.class.getResource("/icons/hierarchy.png")));
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				uhd = new UserHierarchyDialog();
				uhd.setUser(App.USER);
				uhd.setVisible(true);
				
				new Thread() {
					public void run() {
						while (uhd.isVisible()) {
							try {
								Thread.sleep(500);
							}
							catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						if (uhd.getExitMode() == JOptionPane.OK_OPTION) {
							setUser(uhd.getSelectedUser());
						}
					};
				}.start();
			}
		});
	}
	
	public User getSelectedUser() {
		return user;
	}
	
	public void setUser(User u) {
		user = u;
		t_field.setText((u != null) ? user.getName().toUpperCase() : "");
	}
}