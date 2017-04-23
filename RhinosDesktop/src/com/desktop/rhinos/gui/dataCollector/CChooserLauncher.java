package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.desktop.rhinos.connector.Connector.App;
import com.desktop.rhinos.connector.MySqlConnector;

public class CChooserLauncher extends ChooserLauncher {

	private static final long serialVersionUID = 1L;

	private String campaign;
	private CampaignListDialog cld;
	
	public CChooserLauncher() {
		btn.setIcon(new ImageIcon(UChooserLauncher.class.getResource("/icons/hierarchy.png")));
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cld = new CampaignListDialog();
				cld.setVisible(true);
				
				new Thread() {
					public void run() {
						while (cld.isVisible()) {
							try {
								Thread.sleep(500);
							}
							catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						if (cld.getExitMode() == JOptionPane.OK_OPTION) {
							setCampaign(cld.getSelectedCampaign());
						}
					};
				}.start();
			}
		});
	}
	
	public String getSelectedCampaign() {
		return campaign;
	}
	
	public void setCampaign(String camp) {
		campaign = camp;
		t_field.setText(campaign.toUpperCase());
	}
}

class CampaignListDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JList list;
	private String campaign;
	private JButton accept;
	
	private int exit_mode;
	
	public CampaignListDialog() {
		init();
	}
	
	private void init() {
		exit_mode = JOptionPane.CANCEL_OPTION;
		accept = new JButton("Aceptar");
		
		setModal(true);
		setMinimumSize(new java.awt.Dimension(400, 280));
		getContentPane().setLayout(new BorderLayout());
		
		ArrayList<String> camps = MySqlConnector.getInstance().getAuthorizedCampaigns(App.USER);
		list = new JList<>(camps.toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JPanel south = new JPanel();
		south.add(accept);
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exit_mode = JOptionPane.OK_OPTION;
				campaign = list.getSelectedValue().toString();
				
				setVisible(false);
			}
		});
		
		getContentPane().add(new JScrollPane(list));
		getContentPane().add(south, BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	public String getSelectedCampaign() {
		return campaign;
	}
	
	public int getExitMode() {
		return exit_mode;
	}
}
