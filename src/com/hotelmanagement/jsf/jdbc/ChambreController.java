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
public class ChambreController {

	private List<Chambre> chambres;
	private ChambreDbUtil chambreDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private Chambre selectedChambre;
	
	public Chambre getSelectedChambre() {
		return selectedChambre;
	}

	public void setSelectedChambre(Chambre selectedChambre) {
		this.selectedChambre = selectedChambre;
	}
	
	public ChambreController() throws Exception {
		chambres = new ArrayList<>();
		
		chambreDbUtil = ChambreDbUtil.getInstance();
	}
	
	public List<Chambre> getChambres() {
		return chambres;
	}

	public List<Chambre> getFreeChambres() {
		return chambres;
	}
	
	public List<Chambre> getOccupiedChambres() {
		return chambres;
	}
	
	public int countFree() throws Exception{
		int count;
		count = chambreDbUtil.getCountFree();
		
		return count;
		
	}
	
	public int countOccupied() throws Exception{
		int count;
		count = chambreDbUtil.getCountOccupied();
		
		return count;
		
	}
	
	public void loadChambres() {

		logger.info("Loading chambres");
		
		chambres.clear();

		try {
			
			// get all students from database
			chambres = chambreDbUtil.getChambres();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading chambres", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public void loadFreeChambres() {

		logger.info("Loading Free chambres");
		
		chambres.clear();

		try {
			
			// get all students from database
			chambres = chambreDbUtil.getFreeChambres();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading free chambres", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public void loadOccupiedChambres() {

		logger.info("Loading Occupied chambres");
		
		chambres.clear();

		try {
			
			// get all students from database
			chambres = chambreDbUtil.getOccupiedChambres();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading occupied chambres", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
		
	public String addChambre(Chambre theChambre) {

		logger.info("Adding chambre: " + theChambre);

		try {
			
			// add student to the database
			chambreDbUtil.addChambre(theChambre);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Data Saved",""));
			logger.log(Level.SEVERE, "added successfully chambres");
		} catch (Exception exc) {
			// send this to server logs
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Data not Saved",""));
			logger.log(Level.SEVERE, "Error adding chambres", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "room?faces-redirect=true";
	}

	public String loadChambre(int idChambre) {
		
		logger.info("loading Chambre: " + idChambre);
		
		try {
			// get student from database
			Chambre theChambre = chambreDbUtil.getChambre(idChambre);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("chambre", theChambre);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading chambre id:" + idChambre, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "updateroom.xhtml";
	}	
	
	public String updateChambre(Chambre theChambre) {

		logger.info("updating Chambre: " + theChambre);
		
		try {
			
			// update student in the database
			chambreDbUtil.updateChambre(theChambre);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating Chambre: " + theChambre, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "room?faces-redirect=true";		
	}
	
	public String deleteChambre(int idChambre) {

		logger.info("Deleting Chambre id: " + idChambre);
		
		try {

			// delete the student from the database
			chambreDbUtil.deleteChambre(idChambre);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting Chambre id: " + idChambre, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "list-students";	
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
