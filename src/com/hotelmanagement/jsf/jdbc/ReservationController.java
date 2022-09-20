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
public class ReservationController {
	
	private List<Reservation> reservations;
	private ReservationDbUtil reservationDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public ReservationController() throws Exception {
		reservations = new ArrayList<>();
		
		reservationDbUtil = ReservationDbUtil.getInstance();
	}
	
	public List<Reservation> getReservations() {
		return reservations;
	}
	public List<Reservation> getReservationName() {
		return reservations;
	}
	
	public int count() throws Exception{
		int count;
		count = reservationDbUtil.getCount();
		
		return count;
		
	}
	
	public void loadReservationCount() {

		logger.info("Loading Reservation count");
		
		reservations.clear();

		try {
			
			// get all students from database
			reservations = reservationDbUtil.getReservationCount();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Reservation count", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public void loadReservations() {

		logger.info("Loading Reservations");
		
		reservations.clear();

		try {
			
			// get all students from database
			reservations = reservationDbUtil.getReservations();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Reservations", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public void loadReservationName() {

		logger.info("Loading Reservation names");
		
		reservations.clear();

		try {
			
			// get all students from database
			reservations = reservationDbUtil.getReservationName();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Reservation name", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
		
	public String addReservation(Reservation theReservation) {

		logger.info("Adding Reservation: " + theReservation);

		try {
			
			// add student to the database
			reservationDbUtil.addReservation(theReservation);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Data Saved",""));
			
		} catch (Exception exc) {
			// send this to server logs
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Data not Saved",""));
			logger.log(Level.SEVERE, "Error adding Reservations", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "booking?faces-redirect=true";
	}

	public String loadReservation(int idReservation) {
		
		logger.info("loading Reservation: " + idReservation);
		
		try {
			// get student from database
			Reservation theReservation = reservationDbUtil.getReservation(idReservation);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("reservation", theReservation);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Reservation id:" + idReservation, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "updatebooking.xhtml";
	}	
	
	public String updateReservation(Reservation theReservation) {

		logger.info("updating Reservation: " + theReservation);
		
		try {
			
			// update student in the database
			reservationDbUtil.updateReservation(theReservation);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating Reservation: " + theReservation, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "booking?faces-redirect=true";		
	}
	
	public String deleteReservation(int idReservation) {

		logger.info("Deleting Reservation id: " + idReservation);
		
		try {

			// delete the student from the database
			reservationDbUtil.deleteReservation(idReservation);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting Reservation id: " + idReservation, exc);
			
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
