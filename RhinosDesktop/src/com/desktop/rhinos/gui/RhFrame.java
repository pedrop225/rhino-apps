package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.desktop.rhinos.connector.Connector.App;
import com.desktop.rhinos.gui.dataCollector.AccountNumberCollector;
import com.desktop.rhinos.gui.table.ConsultancyTableDialog;
import com.desktop.rhinos.gui.table.UserTableDialog;

public class RhFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JMenuBar mBar;
	private JToolBar tBar;
	
	private JMenu mFile;
	private JMenuItem logOut;
	private JMenuItem exit;
	
	private JMenu mEdit;
	private JMenuItem edClients;
	private JMenu edService;
	private JMenuItem addService;
	private JMenuItem addInvoice;
	private JMenu edTools;
	private JMenuItem accountNumberChecker;
	private JMenu edConsultancy;
	private JMenuItem addConsultancy;
	private JMenuItem editConsultancy;
	private JMenu edCampaigns;
	private JMenuItem uptCampaigns;
	private JMenu edUsers;
	private JMenuItem addUser;
	private JMenuItem editUser;
	
	private JMenu mExport;
	private JMenuItem expExcel;
	
	private JMenu help;
	private JMenuItem about;
	
	private JButton bLogOut;
	private JButton bClients;
	private JButton bAddContract;
	private JButton bSettings;
	private JButton bHelp;
	
	private RhPanel rhPanel;
	
	private Logger log;
	
	private RhFrame _this = this;
		
	/**
	 * Constructor..
	 * Configura la ventana de la aplicacion centrada en medio de la pantalla.
	 * */
	public RhFrame() {
		init();
		setLocationRelativeTo(null);
	}

	/*
	 * Configura los elementos fundamentales, titulo, icono, y cierre
	 * */
	private void init() {
		setTitle("Rhinos Desktop");
		setIconImage(new ImageIcon(RhFrame.class.getResource("/icons/rhinos.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//creando ventana de login para el usuario
		log = new Logger(this);
		
		rhPanel = new RhPanel();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(rhPanel);
		
		//inicializando barra de menu
		initMenuBar();
		initToolBar();
		
		pack();
	}
	
	private void initToolBar() {
		tBar = new JToolBar();
		tBar.setBackground(Color.LIGHT_GRAY);
		tBar.setOrientation(SwingConstants.VERTICAL);
		tBar.setFloatable(false);
		tBar.setRollover(true);
		
		bLogOut = new JButton();
		bLogOut.setBackground(Color.LIGHT_GRAY);
		bLogOut.setFocusable(false);
		bLogOut.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/LogOut/Log Out_24x24.png")));
		bLogOut.addActionListener(logOut.getActionListeners()[0]);
		
		bClients = new JButton();
		bClients.setBackground(Color.LIGHT_GRAY);
		bClients.setFocusable(false);
		bClients.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/User/User_24x24.png")));
		bClients.addActionListener(edClients.getActionListeners()[0]);
		
		bAddContract = new JButton();
		bAddContract.setBackground(Color.LIGHT_GRAY);
		bAddContract.setFocusable(false);
		bAddContract.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Add/Add_24x24.png")));
		bAddContract.addActionListener(addService.getActionListeners()[0]);

		
		bSettings = new JButton();
		bSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		bSettings.setBackground(Color.LIGHT_GRAY);
		bSettings.setFocusable(false);
		bSettings.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Key/Key_24x24.png")));
		bSettings.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChangePassword(_this).setVisible(true);
			}
		});
		
		bHelp = new JButton();
		bHelp.setBackground(Color.LIGHT_GRAY);
		bHelp.setFocusable(false);
		bHelp.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Help/Help_24x24.png")));
		
		tBar.add(bLogOut);
		tBar.add(bClients);
		tBar.add(bAddContract);
		tBar.add(bSettings);
		tBar.add(bHelp);

		getContentPane().add(tBar, BorderLayout.WEST);
	}
	
	/*
	 * Configura los elementos fundamentales de la barra de menu
	 * */
	private void initMenuBar() {
		mBar = new JMenuBar();
		
		//----------------------------------------
		mFile = new JMenu("Archivo");
		mFile.setIcon(null);
		logOut = new JMenuItem("Cerrar Sesión");
		logOut.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/LogOut/Log Out_24x24.png")));
		exit = new JMenuItem("Salir");
		
		mFile.setFont(App.DEFAULT_FONT);
		logOut.setFont(App.DEFAULT_FONT);
		exit.setFont(App.DEFAULT_FONT);
		
		mFile.add(logOut);
		mFile.add(new JSeparator());
		mFile.add(exit);
		//----------------------------------------
		mEdit = new JMenu("Editar");
		edClients = new JMenuItem("Cliente .."); 
		edClients.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/User/User_24x24.png")));
		edService = new JMenu("Servicios");
		edService.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Globe/Globe_24x24.png")));
		addService = new JMenuItem("Añadir Servicio");
		addService.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Add/Add_16x16.png")));
		addInvoice = new JMenuItem("Añadir Factura");
		edTools = new JMenu("Herramientas");
		edTools.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/tools.png")));
		accountNumberChecker = new JMenuItem("Datos Bancarios");
		accountNumberChecker.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/bank.png")));
		edConsultancy = new JMenu("Asesorías");
		edConsultancy.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Archive/Archive_24x24.png")));
		addConsultancy = new JMenuItem("Añadir Asesoría");
		editConsultancy = new JMenuItem("Editar Asesorías");
		edCampaigns = new JMenu("Campañas");
		uptCampaigns = new JMenuItem("Actualizar");
		edUsers = new JMenu("Usuarios");
		addUser = new JMenuItem("Añadir Usuario");
		editUser = new JMenuItem("Editar Usuarios");
		
		mEdit.setFont(App.DEFAULT_FONT);
		edClients.setFont(App.DEFAULT_FONT);
		edService.setFont(App.DEFAULT_FONT);
		addService.setFont(App.DEFAULT_FONT);
		addInvoice.setFont(App.DEFAULT_FONT);
		edTools.setFont(App.DEFAULT_FONT);
		accountNumberChecker.setFont(App.DEFAULT_FONT);
		edConsultancy.setFont(App.DEFAULT_FONT);
		addConsultancy.setFont(App.DEFAULT_FONT);
		editConsultancy.setFont(App.DEFAULT_FONT);
		edCampaigns.setFont(App.DEFAULT_FONT);
		uptCampaigns.setFont(App.DEFAULT_FONT);
		edUsers.setFont(App.DEFAULT_FONT);
		addUser.setFont(App.DEFAULT_FONT);
		editUser.setFont(App.DEFAULT_FONT);
	
		mEdit.add(edClients);
		mEdit.add(edService);
		edService.add(addService);
		edService.add(addInvoice);
		mEdit.add(edTools);
		edTools.add(accountNumberChecker);
		mEdit.add(edConsultancy);
		edConsultancy.add(addConsultancy);
		edConsultancy.add(editConsultancy);
		mEdit.add(edCampaigns);
		mEdit.add(edUsers);
		edUsers.add(addUser);
		edUsers.add(editUser);
		edCampaigns.add(uptCampaigns);
		//---------------------------------------
		mExport = new JMenu("Exportar DB");
		expExcel = new JMenuItem(" Excel");
		expExcel.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/excel.png")));
		mExport.add(expExcel);
		
		mExport.setFont(App.DEFAULT_FONT);
		expExcel.setFont(App.DEFAULT_FONT);
		//----------------------------------------
		help = new JMenu("Ayuda");
		about = new JMenuItem("Acerca de ..");
		about.setIcon(new ImageIcon(RhFrame.class.getResource("/icons/Help/Help_24x24.png")));
		
		help.setFont(App.DEFAULT_FONT);
		about.setFont(App.DEFAULT_FONT);
		
		help.add(about);
		
		//----------------------------------------
		mBar.add(mFile);
		mBar.add(mEdit);
		mBar.add(mExport);
		mBar.add(help);
		
		setJMenuBar(mBar);
		
		//Configurando listeners
		initmBarListeners();
	}
	
	/*
	 * Comprueba los permisos para el usuario que accede al sistema.
	 * */
	private void chechUserType() {
		boolean r = App.USER.isRoot();
		
		//ocultando menus para usuarios NO root
		edConsultancy.setVisible(r);
		edCampaigns.setVisible(r);
		edUsers.setVisible(r);	
	}
	
	
	/*
	 * Lanzando listeners
	 * */
	private void initmBarListeners() {
		//----------------------------------------
		/*
		 * Accion para el boton de aceptar.
		 * En caso de un correcto logueo: 
		 * Oculta la ventana de login, eliminando los datos introducidos en ella.
		 * Lanza la actualizacion de los datos e informacion del usuario correspondiente.
		 * */
		log.getAcceptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	

				if (App.CONNECTOR.login(log.getUserString(), log.getPasswordString())) {
					log.setVisible(false);
					log.clear();
					showUserBanner();
					chechUserType();
					updateClientsTableData();
					
					rhPanel.validate();
					/*
					 * Solo actualizar la tabla a la que se accede por defecto, las siguientes se actualizarán
					 * automaticamente en el momento que se acceda a ellas.
					 * */
				}
			}
		});
		
		/*
		 * Se encarga del cierre de sesion desde la barra de menu.
		 * Deja el sistema listo para un nuevo loggin
		 * */
		logOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				App.USER.clear();
				rhPanel.clear();
				log.setVisible(true);
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		edClients.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddContract ac = new AddContract(_this);
				ac.setEditMode(true);
				ac.setVisible(true);
			}
		});
		
		addService.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rhPanel.addContract();
			}
		});
		
		addInvoice.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddInvoice ai = new AddInvoice();
				ai.setVisible(true);
			}
		});
		
		accountNumberChecker.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setTitle("Datos Bancarios");
				dialog.setModal(true);
				
				dialog.getContentPane().add(Util.packInJP(new AccountNumberCollector()));

				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
		});
		
		addConsultancy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddConsultancy(_this).setVisible(true);
			}
		});
		
		editConsultancy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ConsultancyTableDialog(_this, null, true).setVisible(true);
			}
		});
		
		addUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddAccount(_this).setVisible(true);
			}
		});
		
		editUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new UserTableDialog(_this, true).setVisible(true);
			}
		});
		
		uptCampaigns.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new CampaignsUpdater().setVisible(true);
			}
		});
		
		expExcel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ExcelGenerator();
			}
		});
	}
	
	public void exit() {
		exit.doClick();
	}
	
	public void updateClientsTableData() {
		rhPanel.updateClientsTableData();
	}
	
	public void updateServicesTableData() {
		rhPanel.updateServicesTableData();
	}
	
	public void showUserBanner() {
		rhPanel.showUserBanner();
	}
	
	public Logger getLogger() {
		return log;
	}
}
