package com.desktop.rhinos.connector;

public class ImportDB {

	private Connector source;
	private Connector target;
	
	public ImportDB(Connector source, Connector target) {
		this.source = source;
		this.target = target;
	}
	
	public void importInfo() {
		target.setLoginInfo(source.getLoginInfo());
		target.setUsersInfo(source.getUsersInfo());
		target.setServicesInfo(source.getServicesInfo());
		target.setClientsInfo(source.getClientsInfo());
		target.setAddressInfo(source.getAddressInfo());
	}
}
