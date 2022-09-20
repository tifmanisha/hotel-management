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

public class ArchiveDbUtil {
	
	private static ArchiveDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/hotel_management";
	
	public static ArchiveDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new ArchiveDbUtil();
		}
		
		return instance;
	}
	
	private ArchiveDbUtil() throws Exception {		
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

			String sql = "select count(*) from archive ";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);
			myRs.next();
			int count = myRs.getInt(1);
			
			return count;
	}
	
	public List<Archive> getArchives() throws Exception {

		List<Archive> Archives = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from archive R,client C,chambre Ch where R.ar_client_id=C.client_id and R.ar_chambre_id=Ch.chambre_id";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int idRes  = myRs.getInt("ar_id");
				int idClient = myRs.getInt("ar_client_id");
				int idChambre = myRs.getInt("ar_chambre_id");
				String dateEntry = myRs.getString("ar_date_entry");
				String dateExit = myRs.getString("ar_date_exit");
				String nameChambre = myRs.getString("chambre_name");
				String fnameClient = myRs.getString("client_fname");
				String lnameClient = myRs.getString("client_lname");

				// create new student object
				Archive tempArchive = new Archive(idRes,idClient,idChambre,dateEntry,dateExit,nameChambre,fnameClient,lnameClient);

				// add it to the list of students
				Archives.add(tempArchive);
			}
			
			return Archives;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public List<Archive> getArchiveName() throws Exception {

		List<Archive> Archives = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select ar_client_id from archive";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				Archive tempArchive = new Archive();
				tempArchive.setClient(myRs.getInt("ar_client_id"));

				// create new student object
				

				// add it to the list of students
				Archives.add(tempArchive);
			}
			
			return Archives;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addArchive(Archive theArchive) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		PreparedStatement statement=null;

		try {
			myConn = getConnection();

			String sql = "insert into archive(ar_client_id,ar_chambre_id,ar_date_entry,ar_date_exit) values(?,?,?,?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, theArchive.getClient());
			myStmt.setInt(2, theArchive.getChambre());
			myStmt.setString(3, theArchive.getDateEntry());
			myStmt.setString(4, theArchive.getDateExit());
			
			myStmt.execute();	
			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public Archive getArchive(int idArchive) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from archive R,client C,chambre Ch where R.ar_client_id=C.client_id and R.ar_chambre_id=Ch.chambre_id and R.ar_id=?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, idArchive);
			
			myRs = myStmt.executeQuery();

			Archive theArchive = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int idRes  = myRs.getInt("ar_id");
				int idClient = myRs.getInt("ar_client_id");
				int idChambre = myRs.getInt("ar_chambre_id");
				String dateEntry = myRs.getString("ar_date_entry");
				String dateExit = myRs.getString("ar_date_exit");
				String nameChambre = myRs.getString("chambre_name");
				String fnameClient = myRs.getString("client_fname");
				String lnameClient = myRs.getString("client_lname");

				theArchive = new Archive(idRes,idClient,idChambre,dateEntry,dateExit,nameChambre,fnameClient,lnameClient);
			}
			else {
				throw new Exception("Could not find Archive id: " + idArchive);
			}

			return theArchive;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void updateArchive(Archive theArchive) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update archive set ar_client_id=? ,ar_chambre_id=? ,res_date_entry=? ,res_date_exit=? where ar_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, theArchive.getClient());
			myStmt.setInt(2, theArchive.getChambre());
			myStmt.setString(3, theArchive.getDateEntry());
			myStmt.setString(4, theArchive.getDateExit());
			myStmt.setInt(5, theArchive.getIdAr());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void deleteArchive(int idArchive) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		PreparedStatement statement=null;

		try {
			myConn = getConnection();

			String sql = "delete from archive where ar_id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, idArchive);
			
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
