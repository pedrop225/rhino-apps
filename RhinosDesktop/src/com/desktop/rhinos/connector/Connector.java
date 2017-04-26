package com.desktop.rhinos.connector;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Consultancy;
import com.android.rhinos.gest.RhFile;
import com.android.rhinos.gest.Service;
import com.android.rhinos.gest.User;

public interface Connector {

	public static class App {
		
		public static final String external_path = "http://localhost/services";
		public static Connector CONNECTOR = DerbyConnector.getInstance();
		public static final User USER = new User();
		
		public static final Font DEFAULT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 11);
		public static final Color LIGHT_GREEN = new Color(57, 134, 90, 40);
		public static final Color APP_GREEN = new Color(57, 134, 90);
	}
		
	public boolean login(String user, String password);
	
	public void clearCampaigns();
	
	public boolean addCampaign(Campaign camp);
	public ArrayList<Campaign> getCampaigns(User u);
		
	public boolean addClient(Client c);
	public boolean editClient(Client c);
	
	public ArrayList<Client> getClients(User u);
	public ArrayList<Client> getCampaignClients(Campaign c, User u);
	
	public Client clientExists(String _id);
	
	public boolean addService(int userId, Service s, Client c);
	public ArrayList<Service> getServices(String _id);

	public void deleteService(Service service);
	
	public void editService(Service s);
	
	public void editServiceCommission(int serviceId, double commission);
	
	public ArrayList<Service> getUserServices(User u);
	
	public ArrayList<Service> getUserServicesByDate(User u, Date date_in, Date date_out, int date_type);
	
	public void deleteClient(String id);

	public void changePassword(String user, String newpass);

	public boolean createAccount(User u, String pass, boolean sendMail);

	public String getCurrentVersion();

	public ArrayList<User> getUsers();
	
	public User getUserById(int id);

	public ArrayList<String> getAuthorizedCampaigns(User user);

	public void grantCampaignPermission(User user, Campaign campaign);

	public void removeCampaingPermission(User user, Campaign campaign);
	
	public ArrayList<Consultancy> getConsultancy();
	
	public Consultancy getConsultancy(int id);
	
	public boolean editConsultancy(Consultancy c);
	
	public void deleteConsultancy(int id);
	
	public void deleteAccount(int id);
	
	public ArrayList<User> getUserChildren(User user);
	
	public void editUserParent(int extId, int idParent, double comm);
	
	public ArrayList<ArrayList<User>> getUserStructure(User user);

	public boolean addConsultancy(Consultancy consultancy);
	
	//MIGRATE
	public ArrayList<HashMap<String, Object>> getLoginInfo();
	public ArrayList<HashMap<String, Object>> getUsersInfo();
	public ArrayList<HashMap<String, Object>> getServicesInfo();
	public ArrayList<HashMap<String, Object>> getClientsInfo();
	public ArrayList<HashMap<String, Object>> getAddressInfo();
	public ArrayList<HashMap<String, Object>> getCampaignsInfo();
	public ArrayList<HashMap<String, Object>> getCampinfoInfo();
	public ArrayList<HashMap<String, Object>> getPermissionsInfo();
	public ArrayList<HashMap<String, Object>> getDocumentsTableInfo();
		
	public void setLoginInfo(ArrayList<HashMap<String, Object>> h);
	public void setUsersInfo(ArrayList<HashMap<String, Object>> h);
	public void setServicesInfo(ArrayList<HashMap<String, Object>> h);
	public void setClientsInfo(ArrayList<HashMap<String, Object>> h);
	public void setAddressInfo(ArrayList<HashMap<String, Object>> h);
	public void setCampaignsInfo(ArrayList<HashMap<String, Object>> h);
	public void setCampinfoInfo(ArrayList<HashMap<String, Object>> h);
	public void setPermissionsInfo(ArrayList<HashMap<String, Object>> h);
	public void setDocumentsTableInfo(ArrayList<HashMap<String, Object>> h);
	
	public void addDocument(File f, int toModify, String r);

	public User getUserParent(int extId);

	public void deleteDocument(int id);

	public File getDocument(int idDocument);

	public ArrayList<RhFile> getDocumentsInfo(int idService);	
}
