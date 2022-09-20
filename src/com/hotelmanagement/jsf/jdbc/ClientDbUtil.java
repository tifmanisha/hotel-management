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

public class ClientDbUtil {
	
	private static ClientDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/hotel_management";
	
	public static ClientDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new ClientDbUtil();
		}
		
		return instance;
	}
	
	private ClientDbUtil() throws Exception {		
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

			String sql = "select count(*) from client ";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);
			myRs.next();
			int count = myRs.getInt(1);
			
			return count;
	}
		
	public List<Client> getClients() throws Exception {

		List<Client> Clients = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from client order by client_fname";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("client_id");
				String fnameClient = myRs.getString("client_fname");
				String lnameClient = myRs.getString("client_lname");
				int telClient = myRs.getInt("client_tel");
				String emailClient = myRs.getString("client_email");

				// create new student object
				Client tempClient = new Client(id,fnameClient,lnameClient,emailClient,telClient);

				// add it to the list of students
				Clients.add(tempClient);
			}
			
			return Clients;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public List<Client> getClientName() throws Exception {

		List<Client> Clients = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select client_fname from client";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				Client tempClient = new Client();
				tempClient.setFnameClient(myRs.getString("client_fname"));

				// create new student object
				

				// add it to the list of students
				Clients.add(tempClient);
			}
			
			return Clients;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addClient(Client theClient) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into client(client_fname,client_lname,client_email,client_tel) values(?,?,?,?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theClient.getFnameClient());
			myStmt.setString(2, theClient.getLnameClient());
			myStmt.setString(3, theClient.getEmailClient());
			myStmt.setInt(4, theClient.getTelClient());
			
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public Client getClient(int idClient) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from client where client_id=?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, idClient);
			
			myRs = myStmt.executeQuery();

			Client theClient = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("client_id");
				String fnameClient = myRs.getString("client_fname");
				String lnameClient = myRs.getString("client_lname");
				int telClient = myRs.getInt("client_tel");
				String emailClient = myRs.getString("client_email");

				theClient = new Client(id,fnameClient,lnameClient,emailClient,telClient);
			}
			else {
				throw new Exception("Could not find Client id: " + idClient);
			}

			return theClient;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void updateClient(Client theClient) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update client "
						+ " set client_fname=? ,client_lname=? ,client_email=? ,client_tel=? "
						+ " where client_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theClient.getFnameClient());
			myStmt.setString(2, theClient.getLnameClient());
			myStmt.setString(3, theClient.getEmailClient());
			myStmt.setInt(4, theClient.getTelClient());
			myStmt.setInt(5, theClient.getIdClient());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void deleteClient(int idClient) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from client where client_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, idClient);
			
			myStmt.execute();
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
