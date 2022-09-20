package com.hotelmanagement.jsf.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UtilisateurDbUtil {
	
	private static UtilisateurDbUtil instance;
	private static DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/hotel_management";
	
	public static UtilisateurDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new UtilisateurDbUtil();
		}
		
		return instance;
	}
	
	private UtilisateurDbUtil() throws Exception {		
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

			String sql = "select count(*) from utilisateur ";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);
			myRs.next();
			int count = myRs.getInt(1);
			
			return count;
	}
	
	public static boolean validate(String email, String password) throws Exception  {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "Select util_email, util_password from utilisateur where util_email = ? and util_password = ?";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, email);
			myStmt.setString(2, password);
			
			myRs = myStmt.executeQuery();

			if (myRs.next()) {
				//result found, means valid inputs
				return true;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		}finally {
				close (myConn, myStmt, myRs);
			}
		return false;
	}

		
	public List<Utilisateur> getUtilisateurs() throws Exception {

		List<Utilisateur> Utilisateurs = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from utilisateur order by util_fname";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int idUtil = myRs.getInt("util_id");
				String fnameUtil = myRs.getString("util_fname");
				String lnameUtil = myRs.getString("util_lname");
				String emailUtil = myRs.getString("util_email");
				String roleUtil = myRs.getString("util_role");
				String passUtil = myRs.getString("util_password");

				// create new student object
				Utilisateur tempUtilisateur = new Utilisateur(idUtil,fnameUtil,lnameUtil,emailUtil,roleUtil,passUtil);

				// add it to the list of students
				Utilisateurs.add(tempUtilisateur);
			}
			
			return Utilisateurs;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public List<Utilisateur> getUtilisateurName() throws Exception {

		List<Utilisateur> Utilisateurs = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select util_fname from utilisateur";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				Utilisateur tempUtilisateur = new Utilisateur();
				tempUtilisateur.setFnameUtil(myRs.getString("util_fname"));

				// create new student object
				

				// add it to the list of students
				Utilisateurs.add(tempUtilisateur);
			}
			
			return Utilisateurs;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addUtilisateur(Utilisateur theUtilisateur) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into utilisateur(util_fname,util_lname,util_email,util_role,util_password) values(?,?,?,?,?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theUtilisateur.getFnameUtil());
			myStmt.setString(2, theUtilisateur.getLnameUtil());
			myStmt.setString(3, theUtilisateur.getEmailUtil());
			myStmt.setString(4, theUtilisateur.getRoleUtil());
			myStmt.setString(5, theUtilisateur.getPasswordUtil());
			
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	
	public Utilisateur getUtilisateur(int idUtil) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from utilisateur where util_id=?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, idUtil);
			
			myRs = myStmt.executeQuery();

			Utilisateur theUtilisateur = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("util_id");
				String fnameUtil = myRs.getString("util_fname");
				String lnameUtil = myRs.getString("util_lname");
				String emailUtil = myRs.getString("util_email");
				String roleUtil = myRs.getString("util_role");
				String passUtil = myRs.getString("util_password");

				theUtilisateur = new Utilisateur(id,fnameUtil,lnameUtil,emailUtil,roleUtil,passUtil);
			}
			else {
				throw new Exception("Could not find Utilisateur id: " + idUtil);
			}

			return theUtilisateur;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void updateUtilisateur(Utilisateur theUtilisateur) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update utilisateur set util_fname=?, util_lname=?, util_email=?, util_role=?, util_password=? where util_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theUtilisateur.getFnameUtil());
			myStmt.setString(2, theUtilisateur.getLnameUtil());
			myStmt.setString(3, theUtilisateur.getEmailUtil());
			myStmt.setString(4, theUtilisateur.getRoleUtil());
			myStmt.setString(5, theUtilisateur.getPasswordUtil());
			myStmt.setInt(6, theUtilisateur.getIdUtil());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void deleteUtilisateur(int idUtil) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from utilisateur where util_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, idUtil);
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}		
	}	
	
	private static Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();
		
		return theConn;
	}
	
	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}
	
	private static void close(Connection theConn, Statement theStmt, ResultSet theRs) {

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
