package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) pour les épreuves.
 * Ce DTO est utilisé pour transférer les données des épreuves entre les couches de l'application.
 */
@Getter
@Setter
public class EpreuveDTO {

    /**
     * L'identifiant de l'épreuve.
     */
    private Long idEpreuve;

    /**
     * Le nom de l'épreuve.
     */
    private String nom;

    /**
     * La date de l'épreuve.
     */
    private LocalDate date;

    /**
     * Le nombre maximum de délégations participant à l'épreuve.
     */
    private Integer nbDelegations;

    /**
     * Le nombre de billets disponibles pour l'épreuve.
     */
    private Integer nbBillets;

    /**
     * Le prix du billet pour l'épreuve.
     */
    private Double prix;

    /**
     * L'identifiant de l'infrastructure sportive où se déroule l'épreuve.
     */
    private Long idInfrastructure;

	public Long getIdEpreuve() {
		return idEpreuve;
	}

	public void setIdEpreuve(Long idEpreuve) {
		this.idEpreuve = idEpreuve;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getNbDelegations() {
		return nbDelegations;
	}

	public void setNbDelegations(Integer nbDelegations) {
		this.nbDelegations = nbDelegations;
	}

	public Integer getNbBillets() {
		return nbBillets;
	}

	public void setNbBillets(Integer nbBillets) {
		this.nbBillets = nbBillets;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public Long getIdInfrastructure() {
		return idInfrastructure;
	}

	public void setIdInfrastructure(Long idInfrastructure) {
		this.idInfrastructure = idInfrastructure;
	}
    
    

}
