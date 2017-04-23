package com.desktop.rhinos.connector;

public class ImportDB {

	private Connector source;
	private Connector target;
	
	public ImportDB(Connector source, Connector target) {
		this.source = source;
		this.target = target;
	}
	
	public void iLoginTable() {
		target.setLoginTable(source.getLoginTable());
	}
}
