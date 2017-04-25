package com.desktop.rhinos.connector;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Consultancy;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.RhFile;
import com.android.rhinos.gest.Service;
import com.android.rhinos.gest.User;

public class DerbyConnector implements Connector {	
	
	private static DerbyConnector INSTANCE;
	private Connection conn;
	
	public synchronized static DerbyConnector getInstance() {
		return (INSTANCE != null) ? INSTANCE : new DerbyConnector();
	}
	
	private Connection createConnection(boolean create) throws SQLException {
		return DriverManager.getConnection("jdbc:derby:./RHINOS.DB;create="+create+";territory=es_ES;collation=TERRITORY_BASED");
	}
	
	private void shutdownConnection() {
		try {
			DriverManager.getConnection("jdbc:derby:./RHINOS.DB;shutdown=true");
		} 
		catch (SQLException e) {}
	}
	
	private DerbyConnector(){
		
		try {
			boolean createDB = !new File("RHINOS.DB").exists();
			conn = createConnection(createDB);

			if (createDB) {
				Statement st = conn.createStatement();
				st.executeUpdate("CREATE TABLE login (id INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
											+ "	username VARCHAR(20),"
											+ "	password VARCHAR(20))");
				
				st.executeUpdate("CREATE TABLE users (id INT PRIMARY KEY,"
											+ " type INT,"
											+ " name VARCHAR(40),"
											+ "	mail VARCHAR(40))");
				
				st.executeUpdate("CREATE TABLE services(id INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
												+ " idUser INT,"
												+ " idClient VARCHAR(20),"
												+ " service VARCHAR(20),"
												+ " campaign VARCHAR(20),"
												+ " commission DOUBLE,"
												+ " date DATE,"
												+ " expiry DATE,"
												+ " state SMALLINT,"
												+ " referencia VARCHAR(20),"
												+ " f_pago SMALLINT,"
												+ " p_neta DOUBLE,"
												+ " ccc VARCHAR(30),"
												+ " cartera SMALLINT,"
												+ " anualizar SMALLINT,"
												+ " notes LONG VARCHAR)");
				
				st.executeUpdate("CREATE TABLE clients(idClient VARCHAR(40) PRIMARY KEY NOT NULL,"
												+ " b_date DATE,"
												+ " name VARCHAR(80),"
												+ " tlf_1 VARCHAR(20),"
												+ " tlf_2 VARCHAR(20),"
												+ " mail VARCHAR(40),"
												+ " consultancy INT)");
				
				st.executeUpdate("CREATE TABLE address(id VARCHAR(20) PRIMARY KEY NOT NULL,"
												+ " tipo_via VARCHAR(20),"
												+ " nombre_via VARCHAR(40),"
												+ " numero VARCHAR(20),"
												+ " portal VARCHAR(20),"
												+ " escalera VARCHAR(20),"
												+ " piso VARCHAR(20),"
												+ " puerta VARCHAR(20),"
												+ " poblacion VARCHAR(20),"
												+ " municipio VARCHAR(20),"
												+ " cp VARCHAR(20))");
				
				st.close();
			}
		}
		catch (SQLException e) {e.printStackTrace();}		
	}
	
	@Override
	public boolean login(String user, String password) {
		boolean toRet = false;
		try {
			Statement st = conn.createStatement();
			ResultSet r = st.executeQuery("SELECT * FROM login NATURAL JOIN users WHERE username='"+user+"' "
											+ "AND password='"+password+"'");
			if (r.next()) {
	        	App.USER.setExtId(r.getInt("id"));
	        	App.USER.setType(r.getInt("type"));
	        	App.USER.setUser(user);
	        	App.USER.setName(r.getString("name"));
	        	App.USER.setMail(r.getString("mail"));
	        	toRet = true;
			}
			
			st.close();
		} 
		catch (SQLException e) {e.printStackTrace();}
		return toRet;
	}

	@Override
	public void clearCampaigns() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addCampaign(Campaign camp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Campaign> getCampaigns(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addClient(Client c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editClient(Client c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Client> getClients(User u) {
		ArrayList<Client> tr = new ArrayList<>();
		try {
	    	Statement st = conn.createStatement();
	    	ResultSet r;
	    	
	    	if (u.isRoot())
	    		r = st.executeQuery("SELECT idClient, name, b_date, tlf_1 FROM CLIENTS");
	    	else
	    		r = st.executeQuery("SELECT DISTINCT clients.idClient, name, b_date, tlf_1 "
	    									+ "FROM clients, services "
	    									+ "WHERE (clients.idClient = services.idClient) AND (idUser="+u.getExtId()+")");
	    	
			while (r.next()) {	
				Client cl = new Client();
				cl.setId(new Dni(r.getString("idClient")));
				cl.setBDate(new Date(r.getString("b_date").replace("-", "/")));
				cl.setName(r.getString("name"));
				cl.setTlf_1(r.getString("tlf_1"));
				
				tr.add(cl);
			}
	    }
	    catch (Exception e) {e.printStackTrace();}
		
		return tr;
	}

	@Override
	public ArrayList<Client> getCampaignClients(Campaign c, User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client clientExists(String id) {
		Client client = null;
		try {
			Statement st = conn.createStatement();
			ResultSet r = st.executeQuery("SELECT * FROM clients NATURAL JOIN address WHERE id='"+id+"'");
		
		    while (r.next()) {
				client = new Client();
				client.setId(new Dni(id));
				client.setBDate(new Date(r.getString("b_date").replace("-", "/")));
				
				client.setName(r.getString("name"));
				client.setTlf_1(r.getString("tlf_1"));
				client.setTlf_2(r.getString("tlf_2"));
				client.setMail(r.getString("mail"));
				client.setConsultancy(r.getInt("consultancy"));
				
				client.setDirTipoVia(r.getString("tipo_via"));
				client.setDirNombreVia(r.getString("nombre_via"));
				client.setDirNumero(r.getString("numero"));
				client.setDirPortal(r.getString("portal"));
				client.setDirEscalera(r.getString("escalera"));
				client.setDirPiso(r.getString("piso"));
				client.setDirPuerta(r.getString("puerta"));
				client.setDirPoblacion(r.getString("poblacion"));
				client.setDirMunicipio(r.getString("municipio"));
				client.setDirCp(r.getString("cp"));
			}
	    }
	    catch (SQLException e) {e.printStackTrace();}

		return client;
	}

	@Override
	public boolean addService(int userId, Service s, Client c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Service> getServices(String _id) {
		
		return null;
	}

	@Override
	public void deleteService(Service service) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editService(Service s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editServiceCommission(int serviceId, double commission) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Service> getUserServices(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Service> getUserServicesByDate(User u, Date date_in, Date date_out, int date_type) {
		ArrayList<Service> tr = new ArrayList<Service>();
		
		try {
			String d_type = (date_type == 0) ? "date" : "expiry";
			Statement st = conn.createStatement();
			ResultSet r = st.executeQuery (" SELECT services.id, clients.idClient, name, campaign, service, "
													+ "date, expiry, commission, state, notes, referencia, "
													+ "f_pago, p_neta, ccc, cartera, anualizar "
												+ "FROM Clients, Services "
												+ "WHERE (idUser="+u.getExtId()+" AND clients.id=services.idClient) "
												+ "AND ("+ d_type +" >= "+date_in.getDate()+" AND "+ d_type +" <= "+date_out.getDate()+") ORDER BY date");
			while (r.next()) {
				Service s = new Service(r.getString("service"), r.getDouble("commission"));
								
				s.setExtId(r.getInt("id"));
				s.setCampaign(r.getString("campaign"));
				s.setDate(new Date(r.getString("date").replace("-", "/")));
				s.setExpiryDate(new Date(r.getString("expiry").replace("-", "/")));
				s.setState(r.getInt("state"));
				s.setNotes(r.getString("notes"));
				
				s.setReferencia(r.getString("referencia"));
				s.setF_pago(r.getInt("f_pago"));
				s.setpNeta(r.getDouble("p_neta"));
				s.setCcc(r.getString("ccc"));
				s.setCartera(r.getInt("cartera") > 0);
				s.setAnualizar(r.getInt("anualizar") > 0);
				
				s.setId(new Dni(r.getString("idClient")));
				s.setTitular(r.getString("name"));
				
				tr.add(s);
			}
			
			st.close();
			
		}
		catch (SQLException e) {e.printStackTrace();}
		
		return tr;
	}

	@Override
	public void deleteClient(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changePassword(String user, String newpass) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean createAccount(User u, String pass, boolean sendMail) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCurrentVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getAuthorizedCampaigns(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void grantCampaignPermission(User user, Campaign campaign) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCampaingPermission(User user, Campaign campaign) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Consultancy> getConsultancy() {
		// TODO Auto-generated method stub
		return new ArrayList<Consultancy>();
	}

	@Override
	public Consultancy getConsultancy(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean editConsultancy(Consultancy c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteConsultancy(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAccount(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<User> getUserChildren(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editUserParent(int extId, int idParent, double comm) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<ArrayList<User>> getUserStructure(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addConsultancy(Consultancy consultancy) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ArrayList<HashMap<String, String>> getLoginInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<HashMap<String, String>> getUsersInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<HashMap<String, String>> getServicesInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<HashMap<String, String>> getClientsInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<HashMap<String, String>> getAddressInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addDocument(File f, int toModify, String r) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public User getUserParent(int extId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void deleteDocument(int id) {
		// TODO Auto-generated method stub
	
	}
	
	@Override
	public File getDocument(int idDocument) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<RhFile> getDocumentsInfo(int idService) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//MIGRATE
	@Override
	public void setLoginInfo(ArrayList<HashMap<String, String>> array) {
		try {
			Statement st = conn.createStatement();
			for (HashMap<String, String> h : array) {
				st.execute("INSERT INTO login (username, password) VALUES("
												+ "'"+h.get("user")+"',"
												+ "'"+h.get("password")+"')");
			}
			st.close();
			shutdownConnection();
			conn = createConnection(false);
		}
		catch (SQLException e) {}
	}
	
	@Override
	public void setUsersInfo(ArrayList<HashMap<String, String>> array) {
		try {
			Statement st = conn.createStatement();
			for (HashMap<String, String> h : array) {
				st.execute("INSERT INTO users (id, type, name, mail) VALUES("
												+h.get("id")+","
												+h.get("type")+","
												+"'"+h.get("name")+"',"
												+"'"+h.get("mail")+"')");
			}
			st.close();
			shutdownConnection();
			conn = createConnection(false);
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	
	@Override
	public void setClientsInfo(ArrayList<HashMap<String, String>> array) {
		try {
			Statement st = conn.createStatement();
			for (HashMap<String, String> h : array) {
				st.execute("INSERT INTO clients (idclient, b_date, name, tlf_1, tlf_2, mail, consultancy) "
									+ "VALUES('"+h.get("id")+"',"
												+"'"+h.get("b_date")+"',"
												+"'"+h.get("name")+"',"
												+"'"+h.get("tlf_1")+"',"
												+"'"+h.get("tlf_2")+"',"
												+"'"+h.get("mail")+"',"
												+h.get("consultancy")+")");
			}
			st.close();
			shutdownConnection();
			conn = createConnection(false);
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	
	@Override
	public void setServicesInfo(ArrayList<HashMap<String, String>> array) {
		try {
			Statement st = conn.createStatement();
			for (HashMap<String, String> h : array) {
				st.executeUpdate("INSERT INTO services (idUser, idClient, service, "
											+ "campaign, commission, date, expiry, "
											+ "state, referencia, f_pago, p_neta, "
											+ "ccc, cartera, anualizar, notes) "
											+ "VALUES(" +h.get("idUser")+","
														+"'"+h.get("idClient")+"',"
														+"'"+h.get("service")+"',"
														+"'"+h.get("campaign")+"',"
														+h.get("commission")+","
														+"'"+h.get("date")+"',"
														+"'"+h.get("expiry")+"',"
														+h.get("state")+","
														+"'"+h.get("referencia")+"',"
														+h.get("f_pago")+","
														+h.get("p_neta")+","
														+"'"+h.get("ccc")+"',"
														+h.get("cartera")+","
														+h.get("anualizar")+","
														+"'"+h.get("notes")+"')");
			}
			st.close();
			shutdownConnection();
			conn = createConnection(false);
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	
	@Override
	public void setAddressInfo(ArrayList<HashMap<String, String>> array) {
		try {
			Statement st = conn.createStatement();
			for (HashMap<String, String> h : array) {
				st.executeUpdate("INSERT INTO address (id, tipo_via, nombre_via, "
											+ "numero, portal, escalera, piso, "
											+ "puerta, poblacion, municipio, cp "
											+ "VALUES(" +"'"+h.get("id")+"',"
														+"'"+h.get("tipo_via")+"',"
														+"'"+h.get("nombre_via")+"',"
														+"'"+h.get("numero")+"',"
														+"'"+h.get("portal")+"',"
														+"'"+h.get("escalera")+"',"
														+"'"+h.get("piso")+"',"
														+"'"+h.get("puerta")+"',"
														+"'"+h.get("poblacion")+"',"
														+"'"+h.get("municipio")+"',"
														+"'"+h.get("cp")+"')");
			}
			st.close();
			shutdownConnection();
			conn = createConnection(false);
		}
		catch (SQLException e) {e.printStackTrace();}	
	}
}
