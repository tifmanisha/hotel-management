package com.hotelmanagement.jsf.jdbc;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Reservation {
	private Integer idRes;
	private int client;
	private String fnameClient;
	private String lnameClient;
	private int chambre;
	private String nameChambre;
	private String dateEntry;
	private String dateExit;
	int count;
	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Reservation(){
		
	}

	public Reservation(Integer idRes, int client, int chambre, String dateEntry, String dateExit,String nameChambre,String fnameClient,String lnameClient) {
		super();
		this.idRes = idRes;
		this.client = client;
		this.chambre = chambre;
		this.dateEntry = dateEntry;
		this.dateExit = dateExit;
		this.nameChambre = nameChambre;
		this.fnameClient = fnameClient;
		this.lnameClient = lnameClient;
	}

	public Integer getIdRes() {
		return idRes;
	}

	public void setIdRes(Integer idRes) {
		this.idRes = idRes;
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
		return "reservation [res_id=" + idRes + ", res_client_id=" + client + ", res_chambre_id=" + chambre + ", res_date_entry=" + dateEntry + ", res_date_exit=" + dateExit + "]";
	}

	
}
