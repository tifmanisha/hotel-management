package com.hotelmanagement.jsf.jdbc;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Archive {
	private Integer idAr;
	private int client;
	private String fnameClient;
	private String lnameClient;
	private int chambre;
	private String nameChambre;
	private String dateEntry;
	private String dateExit;
	
	public Archive(){
		
	}

	public Archive(Integer idAr, int client, int chambre, String dateEntry, String dateExit,String nameChambre,String fnameClient,String lnameClient) {
		super();
		this.idAr = idAr;
		this.client = client;
		this.chambre = chambre;
		this.dateEntry = dateEntry;
		this.dateExit = dateExit;
		this.nameChambre = nameChambre;
		this.fnameClient = fnameClient;
		this.lnameClient = lnameClient;
	}

	public Integer getIdAr() {
		return idAr;
	}

	public void setIdar(Integer idar) {
		this.idAr = idar;
	}

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
		this.client = client;
	}

	public int getChambre() {
		return chambre;
	}

	public void setChambre(int chambre) {
		this.chambre = chambre;
	}

	public String getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(String dateEntry) {
		this.dateEntry = dateEntry;
	}

	public String getDateExit() {
		return dateExit;
	}

	public void setDateExit(String dateExit) {
		this.dateExit = dateExit;
	}

	public String getNameChambre() {
		return nameChambre;
	}

	public void setNameChambre(String nameChambre) {
		this.nameChambre = nameChambre;
	}
	

	public String getFnameClient() {
		return fnameClient;
	}

	public void setFnameClient(String fnameClient) {
		this.fnameClient = fnameClient;
	}

	public String getLnameClient() {
		return lnameClient;
	}

	public void setLnameClient(String lnameClient) {
		this.lnameClient = lnameClient;
	}

	@Override
	public String toString() {
		return "archive [ar_id=" + idAr + ", ar_client_id=" + client + ", ar_chambre_id=" + chambre + ", ar_date_entry=" + dateEntry + ", ar_date_exit=" + dateExit + "]";
	}

	
}
