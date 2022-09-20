package com.hotelmanagement.jsf.jdbc;

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


@ManagedBean
@SessionScoped
public class ClientController {
	
	private List<Client> clients;
	private ClientDbUtil clientDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public ClientController() throws Exception {
		clients = new ArrayList<>();
		
		clientDbUtil = ClientDbUtil.getInstance();
	}
	
	public List<Client> getClients() {
		return clients;
	}
	public List<Client> getClientName() {
		return clients;
	}
	
	public int count() throws Exception{
		int count;
		count = clientDbUtil.getCount();
		
		return count;
		
	}

	public void loadClients() {

		logger.info("Loading Clients");
		
		clients.clear();

		try {
			
			// get all students from database
			clients = clientDbUtil.getClients();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Clients", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	public void loadClientName() {

		logger.info("Loading Client names");
		
		clients.clear();

		try {
			
			// get all students from database
			clients = clientDbUtil.getClientName();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Client name", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
		
	public String addClient(Client theClient) {

		logger.info("Adding Client: " + theClient);

		try {
			
			// add student to the database
			clientDbUtil.addClient(theClient);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Data Saved",""));
			
		} catch (Exception exc) {
			// send this to server logs
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Data not Saved",""));
			logger.log(Level.SEVERE, "Error adding Clients", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "customer?faces-redirect=true";
	}

	public String loadClient(int idClient) {
		
		logger.info("loading Client: " + idClient);
		
		try {
			// get student from database
			Client theClient = clientDbUtil.getClient(idClient);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("client", theClient);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Client id:" + idClient, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "updatecustomer.xhtml";
	}	
	
	public String updateClient(Client theClient) {

		logger.info("updating Client: " + theClient);
		
		try {
			
			// update student in the database
			clientDbUtil.updateClient(theClient);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating Client: " + theClient, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "customer?faces-redirect=true";		
	}
	
	public String deleteClient(int idClient) {

		logger.info("Deleting Client id: " + idClient);
		
		try {

			// delete the student from the database
			clientDbUtil.deleteClient(idClient);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting Client id: " + idClient, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "customer";	
	}	
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
