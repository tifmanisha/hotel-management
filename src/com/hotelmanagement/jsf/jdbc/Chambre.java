package com.hotelmanagement.jsf.jdbc;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.file.UploadedFile;

@ManagedBean
public class Chambre {
	private Integer idChambre;
	private String nameChambre;
	private String imageChambre ="room2.jpg";
	private String classeChambre;
	private String etatChambre="Free";
	private double prixChambre;
	int countFree;
	int countOccupied;

	public int getCountFree() {
		return countFree;
	}

	public void setCountFree(int countFree) {
		this.countFree = countFree;
	}

	public int getCountOccupied() {
		return countOccupied;
	}

	public void setCountOccupied(int countOccupied) {
		this.countOccupied = countOccupied;
	}

	UploadedFile file;
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Chambre(){
		
	}

	public Chambre(Integer idChambre, String nameChambre, String imageChambre, String classeChambre, String etatChambre, double prixChambre) {
		super();
		this.idChambre = idChambre;
		this.nameChambre = nameChambre;
		this.imageChambre = imageChambre;
		this.classeChambre = classeChambre;
		this.etatChambre = etatChambre;
		this.prixChambre = prixChambre;
	}

	public Integer getIdChambre() {
		return idChambre;
	}

	public void setIdChambre(Integer idChambre) {
		this.idChambre = idChambre;
	}

	public String getNameChambre() {
		return nameChambre;
	}

	public void setNameChambre(String nameChambre) {
		this.nameChambre = nameChambre;
	}

	public String getImageChambre() {
		return imageChambre;
	}

	public void setImageChambre(String imageChambre) {
		this.imageChambre = imageChambre;
	}

	public String getClasseChambre() {
		return classeChambre;
	}

	public void setClasseChambre(String classeChambre) {
		this.classeChambre = classeChambre;
	}

	public String getEtatChambre() {
		return etatChambre;
	}

	public void setEtatChambre(String etatChambre) {
		this.etatChambre = etatChambre;
	}

	public double getPrixChambre() {
		return prixChambre;
	}

	public void setPrixChambre(double prixChambre) {
		this.prixChambre = prixChambre;
	}

	@Override
	public String toString() {
		return "chambre [chambre_id=" + idChambre + ",chambre_name=" + nameChambre + ", chambre_image=" + imageChambre + ", chambre_classe=" + classeChambre
				+ ", chambre_etat=" + etatChambre + ", chambre_prix=" + prixChambre + "]";
	}
	
	
}
