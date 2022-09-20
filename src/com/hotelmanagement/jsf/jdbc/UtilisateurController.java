package com.hotelmanagement.jsf.jdbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@ManagedBean
@SessionScoped
public class UtilisateurController implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	private List<Utilisateur> utilisateurs;
	private UtilisateurDbUtil utilisateurDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public UtilisateurController() throws Exception {
		utilisateurs = new ArrayList<>();
		
		utilisateurDbUtil = UtilisateurDbUtil.getInstance();
	}
	
	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}
	public List<Utilisateur> getUtilisateurName() {
		return utilisateurs;
	}
	
	public int count() throws Exception{
		int count;
		count = utilisateurDbUtil.getCount();
		
		return count;
		
	}
	
	public String validateEmailPassword(String user,String pwd) throws Exception {
		boolean valid = UtilisateurDbUtil.validate(user, pwd);
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", user);
			return "index";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect email or Passowrd",
							"Please enter correct username or Password"));
			return "login";
		}
	}
	
	//logout event, invalidate session
	public String logout() {
			HttpSession session = SessionUtils.getSession();
			session.invalidate();
			return "login";
	}

	public void loadUtilisateurs() {

		logger.info("Loading Utilisateurs");
		
		utilisateurs.clear();

		try {
			
			// get all students from database
			utilisateurs = utilisateurDbUtil.getUtilisateurs();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Utilisateurs", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	public void loadUtilisateurName() {

		logger.info("Loading Utilisateur names");
		
		utilisateurs.clear();

		try {
			
			// get all students from database
			utilisateurs = utilisateurDbUtil.getUtilisateurName();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Utilisateur name", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
		
	public String addUtilisateur(Utilisateur theUtilisateur) {

		logger.info("Adding Utilisateur: " + theUtilisateur);

		try {
			
			// add student to the database
			utilisateurDbUtil.addUtilisateur(theUtilisateur);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Data Saved",""));
			
		} catch (Exception exc) {
			// send this to server logs
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Data not Saved",""));
			logger.log(Level.SEVERE, "Error adding utilisateurs", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "user?faces-redirect=true";
	}

	public String loadUtilisateur(int idUtil) {
		
		logger.info("loading Utilisateur: " + idUtil);
		
		try {
			// get student from database
			Utilisateur theUtilisateur = utilisateurDbUtil.getUtilisateur(idUtil);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("utilisateur", theUtilisateur);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Utilisateur id:" + idUtil, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "updateuser.xhtml";
	}	
	
	public String updateUtilisateur(Utilisateur theUtilisateur) {

		logger.info("updating Utilisateur: " + theUtilisateur);
		
		try {
			
			// update student in the database
			utilisateurDbUtil.updateUtilisateur(theUtilisateur);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating Utilisateur: " + theUtilisateur, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "user?faces-redirect=true";		
	}
	
	public String deleteUtilisateur(int idUtil) {

		logger.info("Deleting Utilisateur id: " + idUtil);
		
		try {

			// delete the student from the database
			utilisateurDbUtil.deleteUtilisateur(idUtil);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting Utilisateur id: " + idUtil, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "user";	
	}	
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
