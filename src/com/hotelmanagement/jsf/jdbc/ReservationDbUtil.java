package com.hotelmanagement.jsf.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ReservationDbUtil {
	
	private static ReservationDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/hotel_management";
	
	public static ReservationDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new ReservationDbUtil();
		}
		
		return instance;
	}
	
	private ReservationDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
	
	public int getCount() throws Exception{
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		
			myConn = getConnection();

			String sql = "select count(*) from reservation ";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);
			
			myRs.next();
			int count = myRs.getInt(1);
			
			return count;
	}
	
	public List<Reservation> getReservationCount() throws Exception {

		List<Reservation> Reservations = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select count(*) from reservation";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				Reservation tempReservation = new Reservation();
				tempReservation.setCount(myRs.getInt(1));

				// create new student object
				

				// add it to the list of students
				Reservations.add(tempReservation);
			}
			
			return Reservations;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public List<Reservation> getReservations() throws Exception {

		List<Reservation> Reservations = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from reservation R,client C,chambre Ch where R.res_client_id=C.client_id and R.res_chambre_id=Ch.chambre_id";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int idRes  = myRs.getInt("res_id");
				int idClient = myRs.getInt("res_client_id");
				int idChambre = myRs.getInt("res_chambre_id");
				String dateEntry = myRs.getString("res_date_entry");
				String dateExit = myRs.getString("res_date_exit");
				String nameChambre = myRs.getString("chambre_name");
				String fnameClient = myRs.getString("client_fname");
				String lnameClient = myRs.getString("client_lname");

				// create new student object
				Reservation tempReservation = new Reservation(idRes,idClient,idChambre,dateEntry,dateExit,nameChambre,fnameClient,lnameClient);

				// add it to the list of students
				Reservations.add(tempReservation);
			}
			
			return Reservations;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public List<Reservation> getReservationName() throws Exception {

		List<Reservation> Reservations = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select res_client_id from reservation";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				Reservation tempReservation = new Reservation();
				tempReservation.setClient(myRs.getInt("res_client_id"));

				// create new student object
				

				// add it to the list of students
				Reservations.add(tempReservation);
			}
			
			return Reservations;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addReservation(Reservation theReservation) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		PreparedStatement statement=null;

		try {
			myConn = getConnection();

			String sql = "insert into reservation(res_client_id,res_chambre_id,res_date_entry,res_date_exit) values(?,?,?,?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, theReservation.getClient());
			myStmt.setInt(2, theReservation.getChambre());
			myStmt.setString(3, theReservation.getDateEntry());
			myStmt.setString(4, theReservation.getDateExit());
			
			myStmt.execute();	
			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public Reservation getReservation(int idReservation) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from reservation R,client C,chambre Ch where R.res_client_id=C.client_id and R.res_chambre_id=Ch.chambre_id and R.res_id=?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, idReservation);
			
			myRs = myStmt.executeQuery();

			Reservation theReservation = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int idRes  = myRs.getInt("res_id");
				int idClient = myRs.getInt("res_client_id");
				int idChambre = myRs.getInt("res_chambre_id");
				String dateEntry = myRs.getString("res_date_entry");
				String dateExit = myRs.getString("res_date_exit");
				String nameChambre = myRs.getString("chambre_name");
				String fnameClient = myRs.getString("client_fname");
				String lnameClient = myRs.getString("client_lname");

				theReservation = new Reservation(idRes,idClient,idChambre,dateEntry,dateExit,nameChambre,fnameClient,lnameClient);
			}
			else {
				throw new Exception("Could not find Reservation id: " + idReservation);
			}

			return theReservation;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void updateReservation(Reservation theReservation) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update reservation set res_client_id=? ,res_chambre_id=? ,res_date_entry=? ,res_date_exit=? where res_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, theReservation.getClient());
			myStmt.setInt(2, theReservation.getChambre());
			myStmt.setString(3, theReservation.getDateEntry());
			myStmt.setString(4, theReservation.getDateExit());
			myStmt.setInt(5, theReservation.getIdRes());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void deleteReservation(int idReservation) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "INSERT INTO archive (ar_client_id,ar_chambre_id,ar_date_entry,ar_date_exit) SELECT res_client_id,res_chambre_id,res_date_entry,res_date_exit FROM reservation WHERE res_id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, idReservation);
			
			myStmt.execute();
			
			String sql1 = "DELETE FROM reservation WHERE res_id=?";
			
			PreparedStatement preparedStatement  = myConn.prepareStatement(sql1);
			
			// set params
			preparedStatement.setInt(1, idReservation);
			
			preparedStatement.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}	
	
	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();
		
		return theConn;
	}
	
	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}
	
	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}	
}
