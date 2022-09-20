package com.hotelmanagement.jsf.jdbc;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Client {
	private Integer idClient;
	private String fnameClient;
	private String lnameClient;
	private String emailClient;
	private int telClient;
	int count;
	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Client(){
		
	}

	public Client(Integer idClient, String fnameClient, String lnameClient, String emailClient, int telClient) {
		super();
		this.idClient = idClient;
		this.fnameClient = fnameClient;
		this.lnameClient = lnameClient;
		this.emailClient = emailClient;
		this.telClient = telClient;
	}

	public Integer getIdClient() {
		return idClient;
	}

	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
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

	public String getEmailClient() {
		return emailClient;
	}

	public void setEmailClient(String emailClient) {
		this.emailClient = emailClient;
	}

	public int getTelClient() {
		return telClient;
	}

	public void setTelClient(int telClient) {
		this.telClient = telClient;
	}

	@Override
	public String toString() {
		return "client [client_id=" + idClient + ", client_fname=" + fnameClient + ", client_lname=" + lnameClient
				+ ", client_email=" + emailClient + ", client_tel=" + telClient +"]";
	}
	
	
}
