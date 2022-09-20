package com.hotelmanagement.jsf.jdbc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.apache.commons.io.FileUtils;

public class ChambreDbUtil {
	
	private static ChambreDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/hotel_management";
	
	public static ChambreDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new ChambreDbUtil();
		}
		
		return instance;
	}
	
	private ChambreDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
	
	public int getCountFree() throws Exception{
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		
			myConn = getConnection();

			String sql = "select count(*) from chambre where chambre_etat='Free' ";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);
			myRs.next();
			int count = myRs.getInt(1);
			
			return count;
	}
	
	public int getCountOccupied() throws Exception{
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		
			myConn = getConnection();

			String sql = "select count(*) from chambre where chambre_etat='occupied' ";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);
			myRs.next();
			int count = myRs.getInt(1);
			
			return count;
	}
		
	public List<Chambre> getChambres() throws Exception {

		List<Chambre> Chambres = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from chambre";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int idChambre = myRs.getInt("chambre_id");
				String nameChambre = myRs.getString("chambre_name");
				String imageChambre = myRs.getString("chambre_image");
				String classChambre = myRs.getString("chambre_classe");
				String etatChambre = myRs.getString("chambre_etat");
				double priceChambre = myRs.getDouble("chambre_prix");

				// create new chambre object
				Chambre tempChambre = new Chambre(idChambre,nameChambre,imageChambre,classChambre,etatChambre,priceChambre);

				// add it to the list of chambre
				Chambres.add(tempChambre);
			}
			
			return Chambres;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public List<Chambre> getFreeChambres() throws Exception {

		List<Chambre> Chambres = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from chambre where chambre_etat='Free' ";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int idChambre = myRs.getInt("chambre_id");
				String nameChambre = myRs.getString("chambre_name");
				String imageChambre = myRs.getString("chambre_image");
				String classChambre = myRs.getString("chambre_classe");
				String etatChambre = myRs.getString("chambre_etat");
				double priceChambre = myRs.getDouble("chambre_prix");

				// create new chambre object
				Chambre tempChambre = new Chambre(idChambre,nameChambre,imageChambre,classChambre,etatChambre,priceChambre);

				// add it to the list of chambre
				Chambres.add(tempChambre);
			}
			
			return Chambres;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public List<Chambre> getOccupiedChambres() throws Exception {

		List<Chambre> Chambres = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from chambre where chambre_etat='Occupied'";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int idChambre = myRs.getInt("chambre_id");
				String nameChambre = myRs.getString("chambre_name");
				String imageChambre = myRs.getString("chambre_image");
				String classChambre = myRs.getString("chambre_classe");
				String etatChambre = myRs.getString("chambre_etat");
				double priceChambre = myRs.getDouble("chambre_prix");

				// create new chambre object
				Chambre tempChambre = new Chambre(idChambre,nameChambre,imageChambre,classChambre,etatChambre,priceChambre);

				// add it to the list of chambre
				Chambres.add(tempChambre);
			}
			
			return Chambres;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void upload(){
		Chambre chambre=new Chambre();
		if(chambre.file!=null){
			try {
				FacesContext context= FacesContext.getCurrentInstance();
				ServletContext servletcontext= (ServletContext)context.getExternalContext().getContext();
				String dbpath=servletcontext.getRealPath("/");
				String webcut= dbpath.substring(0, dbpath.lastIndexOf("\\"));
				String biuldcut= webcut.substring(0, webcut.lastIndexOf("\\"));
			   	String mainURLPath= biuldcut.substring(0, biuldcut.lastIndexOf("\\"));
				
				InputStream inputStrim= chambre.file.getInputStream();
				String path= mainURLPath+"\\hotel_management\\WebContent\\resourses\\images\\"+chambre.file.getFileName();
				File destFile=new File(path);
				
				if(!destFile.exists()){
					FileUtils.copyInputStreamToFile(inputStrim, destFile);
				}
				chambre.setImageChambre(chambre.file.getFileName().toLowerCase());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void addChambre(Chambre theChambre) throws Exception {
		upload();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		

		try {
			myConn = getConnection();

			String sql = "insert into chambre(chambre_name,chambre_image,chambre_classe,chambre_etat,chambre_prix) values(?,?,?,?,?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theChambre.getNameChambre());
			myStmt.setString(2, theChambre.getImageChambre());
			myStmt.setString(3, theChambre.getClasseChambre());
			myStmt.setString(4, theChambre.getEtatChambre());
			myStmt.setDouble(5, theChambre.getPrixChambre());
			
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public Chambre getChambre(int idChambre) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from chambre where chambre_id=?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, idChambre);
			
			myRs = myStmt.executeQuery();

			Chambre theChambre = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("chambre_id");
				String name = myRs.getString("chambre_name");
				String image = myRs.getString("chambre_image");
				String classe = myRs.getString("chambre_classe");
				String etat = myRs.getString("chambre_etat");
				double price = myRs.getDouble("chambre_prix");

				theChambre = new Chambre(id,name,image,classe,etat,price);
			}
			else {
				throw new Exception("Could not find Chambre id: " + idChambre);
			}

			return theChambre;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void updateChambre(Chambre theChambre) throws Exception {
		upload();
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update chambre "
						+ " set chambre_id=?,chambre_image=?,chambre_classe=?,chambre_etat=?,chambre_prix=?"
						+ " where chambre_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, theChambre.getIdChambre());
			myStmt.setString(2, theChambre.getImageChambre());
			myStmt.setString(3, theChambre.getClasseChambre());
			myStmt.setString(4, theChambre.getEtatChambre());
			myStmt.setDouble(5, theChambre.getPrixChambre());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void deleteChambre(int idChambre) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from chambre where chambre_id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, idChambre);
			
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
