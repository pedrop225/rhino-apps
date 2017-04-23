package com.desktop.rhinos.connector;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Consultancy;
import com.android.rhinos.gest.Service;
import com.android.rhinos.gest.User;

public class DerbyConnector implements Connector {	
	
	private static DerbyConnector INSTANCE;
	
	public synchronized static DerbyConnector getInstance() {
		return (INSTANCE != null) ? INSTANCE : new DerbyConnector();
	}
	
	private DerbyConnector(){
		
		try {
			boolean createDB = !new File("RHINOS.DB").exists();
			
			Connection conn = DriverManager.getConnection("jdbc:derby:./RHINOS.DB;create="+createDB);

			if (createDB) {
				Statement st = conn.createStatement();
				st.execute("CREATE TABLE Login (id INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 100),"
											+ "	username VARCHAR(20),"
											+ "	password VARCHAR(20))");
				
				
				st.execute("CREATE TABLE Users (id INT PRIMARY KEY,"
											+ " type INT,"
											+ " name VARCHAR(40),"
											+ "	mail VARCHAR(40))");
				
			}
			System.out.println("hello derby");
			MySqlConnector.getInstance().getLoginTable();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean login(String user, String password) {
		
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Client> getCampaignClients(Campaign c, User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client clientExists(String _id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addService(int userId, Service s, Client c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Service> getServices(String _id) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
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
		return null;
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
	public ArrayList<ArrayList<String>> getLoginTable() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void getUsersTable() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLoginTable(ArrayList<ArrayList<String>> log) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setUsersTable() {
		// TODO Auto-generated method stub
		
	}
	
}
