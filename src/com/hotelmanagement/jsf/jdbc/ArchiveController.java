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
public class ArchiveController {
	
	private List<Archive> archives;
	private ArchiveDbUtil archiveDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public ArchiveController() throws Exception {
		archives = new ArrayList<>();
		
		archiveDbUtil = ArchiveDbUtil.getInstance();
	}
	
	public List<Archive> getArchives() {
		return archives;
	}
	public List<Archive> getArchiveName() {
		return archives;
	}
	
	public int count() throws Exception{
		int count;
		count = archiveDbUtil.getCount();
		
		return count;
		
	}
	
	public void loadArchives() {

		logger.info("Loading Archives");
		
		archives.clear();

		try {
			
			// get all students from database
			archives = archiveDbUtil.getArchives();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Archives", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	public void loadArchiveName() {

		logger.info("Loading Archive names");
		
		archives.clear();

		try {
			
			// get all students from database
			archives = archiveDbUtil.getArchiveName();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Archive name", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
		
	public String addArchive(Archive theArchive) {

		logger.info("Adding Archive: " + theArchive);

		try {
			
			// add student to the database
			archiveDbUtil.addArchive(theArchive);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Data Saved",""));
			
		} catch (Exception exc) {
			// send this to server logs
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Data not Saved",""));
			logger.log(Level.SEVERE, "Error adding Archives", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "booking?faces-redirect=true";
	}

	public String loadArchive(int idArchive) {
		
		logger.info("loading Archive: " + idArchive);
		
		try {
			// get student from database
			Archive theArchive = archiveDbUtil.getArchive(idArchive);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("archive", theArchive);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Archive id:" + idArchive, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "updatebooking.xhtml";
	}	
	
	public String updateArchive(Archive theArchive) {

		logger.info("updating Archive: " + theArchive);
		
		try {
			
			// update student in the database
			archiveDbUtil.updateArchive(theArchive);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating Archive: " + theArchive, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "booking?faces-redirect=true";		
	}
	
	public String deleteArchive(int idArchive) {

		logger.info("Deleting Archive id: " + idArchive);
		
		try {

			// delete the student from the database
			archiveDbUtil.deleteArchive(idArchive);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting Archive id: " + idArchive, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "booking";	
	}	
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
