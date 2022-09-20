package com.hotelmanagement.jsf.jdbc;

import javax.faces.bean.ManagedBean;


@ManagedBean
public class Utilisateur {
	private Integer idUtil;
	private String fnameUtil;
	private String lnameUtil;
	private String emailUtil;
	private String roleUtil;
	private String passwordUtil;
	
	public Utilisateur(){
		
	}

	public Utilisateur(Integer idUtil, String fnameUtil, String lnameUtil, String emailUtil, String roleUtil, String passwordUtil) {
		super();
		this.idUtil = idUtil;
		this.fnameUtil = fnameUtil;
		this.lnameUtil = lnameUtil;
		this.emailUtil = emailUtil;
		this.roleUtil = roleUtil;
		this.passwordUtil = passwordUtil;
	}



	public Integer getIdUtil() {
		return idUtil;
	}
	

	public void setIdUtil(Integer idUtil) {
		this.idUtil = idUtil;
	}

	public String getFnameUtil() {
		return fnameUtil;
	}

	public void setFnameUtil(String fnameUtil) {
		this.fnameUtil = fnameUtil;
	}

	public String getLnameUtil() {
		return lnameUtil;
	}

	public void setLnameUtil(String lnameUtil) {
		this.lnameUtil = lnameUtil;
	}

	public String getEmailUtil() {
		return emailUtil;
	}

	public void setEmailUtil(String emailUtil) {
		this.emailUtil = emailUtil;
	}
	

	public String getRoleUtil() {
		return roleUtil;
	}

	public void setRoleUtil(String roleUtil) {
		this.roleUtil = roleUtil;
	}

	public String getPasswordUtil() {
		return passwordUtil;
	}

	public void setPasswordUtil(String passwordUtil) {
		this.passwordUtil = passwordUtil;
	}

	@Override
	public String toString() {
		return "utilisateur [util_id=" + idUtil + ", util_fname=" + fnameUtil + ", util_lname=" + lnameUtil
				+ ", util_email=" + emailUtil + ", util_role=" + roleUtil + ", util_password=" + passwordUtil + "]";
	}	

}
