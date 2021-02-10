package org.rousseau.jpa.exo2;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Commune")
public class Commune implements Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;	//Clé primaire
	
	@Column(length = 5)
	private String codeINSEE;
	
	@Column(length = 40)
	private String nomCommune;
	
	@Column(length = 5)
	private String codePostal;
	
	@Column(length = 40)
	private String libelleAcheminement;
		
	//********************
	//Getters & Setters :
	//********************
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodeINSEE() {
		return codeINSEE;
	}
	public void setCodeINSEE(String codeINSEE) {
		this.codeINSEE = codeINSEE;
	}
	public String getNomCommune() {
		return nomCommune;
	}
	public void setNomCommune(String nomCommune) {
		this.nomCommune = nomCommune;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getLibelleAcheminement() {
		return libelleAcheminement;
	}
	public void setLibelleAcheminement(String libelleAcheminement) {
		this.libelleAcheminement = libelleAcheminement;
	}
	
	
	//********************
	//toString :
	//********************
	@Override
	public String toString() {
		return "Commune [codeINSEE=" + codeINSEE + ", nomCommune=" + nomCommune + ", codePostal=" + codePostal
				+ ", libelleAcheminement=" + libelleAcheminement + "]";
	}
	
	//********************
	// Constructor :
	// ********************
	
	public Commune() {
		
	}
	
	public Commune(String codeINSEE, String nomCommune, String codePostal, String libelleAcheminement) {
		super();
		this.id = 0; //On laissera Hibernate choisir la valeur de l'ID
		this.codeINSEE = codeINSEE;
		this.nomCommune = nomCommune;
		this.codePostal = codePostal;
		this.libelleAcheminement = libelleAcheminement;
	}
}
