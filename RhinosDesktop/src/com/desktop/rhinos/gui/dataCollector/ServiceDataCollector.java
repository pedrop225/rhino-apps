package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Service;
import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.DocumentsDialog;
import com.desktop.rhinos.gui.Util;
import com.toedter.calendar.JDateChooser;

public class ServiceDataCollector extends JDialog {

	private static final long serialVersionUID = 1L;

	//modo en que se oculta la ventana (ACEPTAR/CANCELAR)
	public static final int ACCEPTED = 0;
	public static final int CANCELLED = -1;
	
	//selector de usuarios
	private UChooserLauncher uchooser;

	//1º columna de datos (LABELS)
	private JLabel labCampaign;
	private JLabel labService;
	private JLabel labCommission;
	private JLabel labDate;
	private JLabel labExpiry;
	private JLabel labState;
	 
	//1º columna de datos (DATA)
	private JComboBox<Object> campaign;
	private JComboBox<Service> service;
	private CommissionEditor commission;
	private JDateChooser dch;
	private JDateChooser expiryDch;
	private JComboBox<String> state;
	
	//2º columna de datos (ETIQUETAS)
	private JLabel labRef;
	private JLabel labPago;
	private JLabel labPrimaAnual;
	private JLabel labCcc;
	private JLabel labCartera;
	private JLabel labAnualizar;
	
	//2º columna de datos (DATA)
	private JTextField ref;
	private JComboBox<String> pago;
	private JTextField primaAnual;
	private JFormattedTextField ccc;
	private JCheckBox cartera;
	private JCheckBox anualizar;
	
	//grids contenedores para las etiquetas anteriores
	private JPanel labPanel;
	private JPanel dataPanel;
	private JPanel labPanel2;
	private JPanel dataPanel2;
	
	/*
	 * panel central, engloba las columnas de datos y el textarea para las notas
	 * del servicio.
	 */
	private JPanel centerPanel;
	
	/*
	 * Engloba las columnas de datos
	 * */
	private JPanel c;
	
	//notas del servicio
	private JTextArea notes;
	
	//botones del módulo
	private JButton btnDocs;
	private JButton btnScan;
	private JButton btnPdf;	
	private JButton accept;

	//indica el modo en que se oculta la ventana (ACEPTAR/CANCELAR)
	private int exitMode = CANCELLED;
	
	//id del cliente propietario del servicio
	private String clientId;
	
	/* 	guarda el indice externo del servicio a modificar. En caso de ser -1, inserta
	 * 	un nuevo registro en la base de datos. De otra manera modificará el registro ya
	 * 	existente.
	 */
	private int toModify = -1;
	
	public ServiceDataCollector(String _c) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ServiceDataCollector.class.getResource("/icons/Globe/Globe_16x16.png")));
		clientId = _c;
		init();
		setLocationRelativeTo(null);
	}
	
	private void init() {
		setModal(true);
		setTitle("Configurar Servicio");
		setResizable(false);
		
		getContentPane().setLayout(new BorderLayout());
		centerPanel = new JPanel(new GridLayout(2, 1));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		c = new JPanel(new GridLayout(1,  2, 15, 0));
		c.setBorder(BorderFactory.createTitledBorder(" Servicio "));

		/*
		 * Etiquetas 1º columna (LABELS)
		 * */
		labState = new JLabel("Estado: ");
		labCampaign = new JLabel("Campaña: ");
		labService = new JLabel("Servicio: ");
		labCommission = new JLabel("Comision: ");
		labDate = new JLabel("Fecha: ");
		labExpiry = new JLabel("Vencimiento: ");
		
		/*
		 * 1º Columna de datos (DATA)
		 * */
		state = new JComboBox<String>(Service.STATES);
		campaign = new JComboBox<Object>(importUserCampaigns().toArray());
		service = new JComboBox<Service>();
		commission = new CommissionEditor();

		dch = new JDateChooser(new Date());
		dch.setFont(App.DEFAULT_FONT);
		dch.setDateFormatString("dd/MM/yyyy");
		dch.getDateEditor().setEnabled(false);
		
		expiryDch = new JDateChooser(new Date());
		expiryDch.setFont(App.DEFAULT_FONT);
		expiryDch.setDateFormatString("dd/MM/yyyy");
		expiryDch.getDateEditor().setEnabled(false);
		
		/*
		 * 2º Columna de datos (LABELS)
		 * */
		labRef = new JLabel("Referencia:");
		labPago = new JLabel("F. Pago:");
		labPrimaAnual = new JLabel("P. Anual:");
		labCcc = new JLabel("CCC:");
		labCartera = new JLabel("Cartera:");
		labAnualizar = new JLabel("Anualizar:");
		
		/*
		 * 2º Columna de datos (DATA)
		 * */
		ref = new JTextField();
		pago = new JComboBox<String>(Service.F_PAGO);
		primaAnual = new JTextField();
		cartera = new JCheckBox("", true);
		anualizar = new JCheckBox("", false);
		
		MaskFormatter mask;
		try {
			mask = new MaskFormatter("ES##-####-####-####-####-####");
			ccc = new JFormattedTextField(mask);
		}
		catch (ParseException e1) {
			ccc = new JFormattedTextField();
		}
		
		/*
		 * Notas del servicio
		 * */
		notes = new JTextArea();
		notes.setBackground(UIManager.getColor("Button.background"));
		notes.setEditable(false);
		notes.setTabSize(3);
		notes.setLineWrap(true);
		notes.setWrapStyleWord(true);
		
		//Setting fonts
		state.setFont(App.DEFAULT_FONT);
		campaign.setFont(App.DEFAULT_FONT);
		service.setFont(App.DEFAULT_FONT);
		commission.setFont(App.DEFAULT_FONT);
		
		ref.setFont(App.DEFAULT_FONT);
		pago.setFont(App.DEFAULT_FONT);
		primaAnual.setFont(App.DEFAULT_FONT);
		ccc.setFont(App.DEFAULT_FONT);
		
		notes.setFont(App.DEFAULT_FONT);
		
		//packing 
		labPanel = new JPanel(new GridLayout(0, 1, 0, 3));
		dataPanel = new JPanel(new GridLayout(0, 1, 0, 3));
		
		labPanel2 = new JPanel(new GridLayout(0, 1, 0, 3));
		dataPanel2 = new JPanel(new GridLayout(0, 1, 0, 3));
		
		labPanel.add(labState);
		labPanel.add(labCampaign);
		labPanel.add(labService);		
		labPanel.add(labCommission);
		labPanel.add(labDate);
		labPanel.add(labExpiry);
		
		dataPanel.add(state);
		dataPanel.add(campaign);
		dataPanel.add(service);
		dataPanel.add(commission);
		dataPanel.add(dch);
		dataPanel.add(expiryDch);
		
		labPanel2.add(labRef);
		labPanel2.add(labPago);
		labPanel2.add(labPrimaAnual);
		labPanel2.add(labCcc);
		labPanel2.add(labCartera);
		labPanel2.add(labAnualizar);
		
		dataPanel2.add(ref);
		dataPanel2.add(pago);
		dataPanel2.add(primaAnual);
		dataPanel2.add(ccc);
		dataPanel2.add(cartera);
		dataPanel2.add(anualizar);
		
		ref.setEnabled(false);
		pago.setEnabled(false);
		primaAnual.setEnabled(false);
		ccc.setEnabled(false);
		cartera.setEnabled(false);
		anualizar.setEnabled(false);
		
		JPanel col_1 = new JPanel(new BorderLayout(10, 5));
		JPanel col_2 = new JPanel(new BorderLayout(10, 5));
		
		col_1.add(labPanel, BorderLayout.WEST);
		col_1.add(dataPanel);
		
		col_2.add(labPanel2, BorderLayout.WEST);
		col_2.add(dataPanel2);
		
		c.add(col_1);
		c.add(col_2);
		
		centerPanel.add(c);
		
		//Selector de usuarios
		uchooser = new UChooserLauncher();
		getContentPane().add(Util.packInJP(new FlowLayout(FlowLayout.RIGHT), uchooser), BorderLayout.NORTH);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		//notas del servicio
		JPanel notesPanel = new JPanel(new BorderLayout());
		notesPanel.setBorder(BorderFactory.createTitledBorder(" Notas "));		
		notesPanel.add(new JScrollPane(notes));
		
		centerPanel.add(notesPanel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnDocs = new JButton("");
		btnDocs.setIcon(new ImageIcon(ServiceDataCollector.class.getResource("/icons/document.png")));
		buttonPanel.add(btnDocs);
		
		btnScan = new JButton("");
		btnScan.setIcon(new ImageIcon(ServiceDataCollector.class.getResource("/icons/scanner.png")));
		buttonPanel.add(btnScan);
		
		btnPdf = new JButton("");
		btnPdf.setIcon(new ImageIcon(ServiceDataCollector.class.getResource("/icons/pdf.png")));
		buttonPanel.add(btnPdf);
		
		accept = new JButton("Guardar");
		buttonPanel.add(accept);
		
		btnDocs.setEnabled(false);
		btnScan.setEnabled(false);
		btnPdf.setEnabled(false);
		
		//listeners
		updateServices();
		
		campaign.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateServices();
			}
		});
		
		service.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateCommission();
			}
		});
		
		btnDocs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DocumentsDialog(toModify).setVisible(true);
			}
		});
		
		btnScan.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Not Available yet.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		btnPdf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fch = new JFileChooser();
				fch.setFileFilter(new FileNameExtensionFilter("PDF", "pdf"));
				
				if (fch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File f = fch.getSelectedFile();
					
					String r = "";
					Scanner sc = new Scanner(campaign.getSelectedItem().toString());
					while (sc.hasNext())
						r += sc.next().charAt(0);
					sc.close();
					
					sc = new Scanner(service.getSelectedItem().toString());
					while (sc.hasNext())
						r += sc.next().charAt(0);
					sc.close();				
					r = r.toUpperCase();
					
					MySqlConnector.getInstance().addDocument(f, toModify, r);
				}
			}
		});
				
		accept.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitMode = ACCEPTED;
				Client client = new Client();
				client.setId(new Dni(clientId));
								
				if (checkData()) {
					
					Service ms = (Service)service.getSelectedItem();
					ms.setCampaign(((Campaign)campaign.getSelectedItem()).toString());
					ms.setDate(dch.getDate());
					ms.setExpiryDate(expiryDch.getDate());
					ms.setState(state.getSelectedIndex());
					ms.setNotes(notes.getText().trim().toUpperCase());
					
					if ((ms.getCommission() == -1) || (App.user.isRoot()))
						ms.setCommission(Double.parseDouble(commission.getText()));
					
					//toModify < 0 siempre que se inserte un servicio por primera vez
					if (toModify < 0) {
						User user = uchooser.getSelectedUser();
						/* si no hay usuario seleccionado se toma el usuario actual*/
						if (user == null)
							user = App.user;
						
						MySqlConnector.getInstance().addService(user.getExtId(), ms, client);
					}
					//modificacion del servicio.. toModify almacenara la id del servicio a modificar
					else {
						ms.setExtId(toModify);
						ms.setReferencia(ref.getText());
						ms.setF_pago(pago.getSelectedIndex());
						ms.setpNeta(Double.parseDouble(primaAnual.getText()));
						ms.setCcc(ccc.getText());
						ms.setCartera(cartera.isSelected());
						ms.setAnualizar(anualizar.isSelected());
						
						MySqlConnector.getInstance().editService(ms);
					}
					
					dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "Error: \""+commission.getText()+"\" no es una cifra válida..", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		pack();
	}
	
	/**
	 * Configura el ServiceDataCollector para editar servicios ya existentes
	 * Retira la posibilidad de determinar servicios como DEVUELTOS, el usuario debe
	 * dar de alta uno nuevo y devolverlo para que queden reflejados todos los movimientos.
	 * Boton de Guardar, cambiado a Modificar
	 * Se invalidan los dateChooser para evitar cambios en las fechas.
	 */
	public void setEditMode(Service s) {
		toModify = s.getExtId();
		
		state.setSelectedItem(Service.STATES[s.getState()]);
		
		int ind;
		for (ind = 0; ind < campaign.getItemCount(); ind++)
			if (((Campaign)campaign.getItemAt(ind)).getName().equals(s.getCampaign()))
				break;
		campaign.setSelectedIndex(ind);
		
		for (ind = 0; ind < service.getItemCount(); ind++)
			if (((Service)service.getItemAt(ind)).getService().equals(s.getService()))
				break;
		service.setSelectedIndex(ind);
		
		commission.setText(new DecimalFormat("0.00").format(s.getCommission()).replace(',', '.'));
		
		/*
		 * En caso de que el servicio seleccionado, pertenezca a una campaña
		 * actualmente no disponible para el usuario se lanzara una excepcion en
		 * este punto del programa.
		 * */
				
		dch.setDate(s.getDate());
		expiryDch.setDate(s.getExpiryDate());
		notes.setText(s.getNotes());
		
		ref.setText(s.getReferencia());
		pago.setSelectedIndex(s.getF_pago());
		primaAnual.setText(s.getPrima()+"");
		ccc.setText(s.getCcc());
		cartera.setSelected(s.isCartera());
		anualizar.setSelected(s.isAnualizar());
		
		if (!App.user.isRoot())
			commission.setEnabled(false); 
		
		campaign.setEnabled(false);
		service.setEnabled(false);
		dch.setEnabled(false);
		expiryDch.setEnabled(false);
		notes.setEditable(true);
		ref.setEnabled(true);
		pago.setEnabled(true);
		primaAnual.setEnabled(true);
		//CCC NOT AVAILABLE
		//ccc.setEnabled(true);
		cartera.setEnabled(true);
		anualizar.setEnabled(true);
		
		uchooser.setFieldsEditable(false);
		
		btnDocs.setEnabled(true);
		btnScan.setEnabled(true);
		btnPdf.setEnabled(true);
		
		accept.setText("Modificar");
	}
	
	private boolean checkData() {
		try {
			Double.parseDouble(commission.getText());
		}
		catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
	private ArrayList<Campaign> importUserCampaigns() {
		return MySqlConnector.getInstance().getCampaigns(App.user);
	}
	
	private void updateServices() {
		service.removeAllItems();
		
		for (Service s : ((Campaign)campaign.getSelectedItem()).getServices().values()) {
			service.addItem(s);
		}
	}
	
	private void updateCommission() {
		Service s = (Service)service.getSelectedItem();
		
		if ((s != null) && (s.getCommission() != -1)) {
			commission.setEnabled(false);
			commission.setText(s.getCommission()+"");
		}
		else {
			commission.setEnabled(true);
			commission.setText("");
		}
	}
	
	public int getExitMode() {
		return exitMode;
	}
	
	@SuppressWarnings("serial")
	class CommissionEditor extends JPanel {
		
		private JTextField tf_comm;
		private JButton bt_activate;
				
		public CommissionEditor() {
			super(new BorderLayout());
			tf_comm = new JTextField();
			tf_comm.setEnabled(false);
			tf_comm.setFont(App.DEFAULT_FONT);
			bt_activate = new JButton(new ImageIcon(CommissionEditor.class.getResource("/icons/modify.png")));

			bt_activate.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean e = tf_comm.isEnabled();
					tf_comm.setEnabled(!e);
					//edicion de comisiones
					if (e) {
						bt_activate.setIcon(new ImageIcon(CommissionEditor.class.getResource("/icons/modify.png")));
						if (checkData()) {
						
							MySqlConnector.getInstance().editServiceCommission(toModify, Double.parseDouble(getText()));
						}
						else {
							JOptionPane.showMessageDialog(null, "Error: \""+commission.getText()+"\" no es una cifra válida..", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else
						bt_activate.setIcon(new ImageIcon(CommissionEditor.class.getResource("/icons/validate.png")));
				}
			});
			
			add(tf_comm);
			add(bt_activate, BorderLayout.EAST);
		}
		
		public String getText() {
			return tf_comm.getText();
		}
		
		public void setColumns(int cols) {
			tf_comm.setColumns(cols);
		}
		
		public void setText(String t) {
			tf_comm.setText(t);
		}
		
		public void setEnabled(boolean e) {
			bt_activate.setEnabled(e);
		}
	}
}