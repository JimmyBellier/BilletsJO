package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Entité représentant une épreuve sportive.
 * Une épreuve peut avoir plusieurs billets, résultats et participations associées.
 */
@Getter
@Setter
@Entity
public class Epreuve {

    /**
     * L'identifiant unique de l'épreuve.
     */
    @Id
    @GeneratedValue
    private long id;

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
    private int nb_delegations;

    /**
     * Le nombre de billets disponibles pour l'épreuve.
     */
    private int nb_billets;

    /**
     * Le prix du billet pour l'épreuve.
     */
    private double prix;

    /**
     * L'infrastructure sportive qui accueille l'épreuve.
     */
    @ManyToOne
    @JoinColumn(name = "idInfrastructure")
    @JsonIgnore
    private InfrastructureSportive infrastructureSportive;

    /**
     * La liste des billets pour l'épreuve.
     * La relation est en cascade et les billets orphelins seront supprimés.
     */
    @OneToMany(mappedBy = "epreuve", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Billet> billets;

    /**
     * La liste des résultats pour l'épreuve.
     * La relation est en cascade et les résultats orphelins seront supprimés.
     */
    @OneToMany(mappedBy = "epreuve", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Resultat> resultats;

    /**
     * La liste des participations des délégations à l'épreuve.
     * La relation est en cascade et les participations orphelines seront supprimées.
     */
    @OneToMany(mappedBy = "epreuve", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Participe> participes;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getNb_delegations() {
		return nb_delegations;
	}

	public void setNb_delegations(int nb_delegations) {
		this.nb_delegations = nb_delegations;
	}

	public int getNb_billets() {
		return nb_billets;
	}

	public void setNb_billets(int nb_billets) {
		this.nb_billets = nb_billets;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public InfrastructureSportive getInfrastructureSportive() {
		return infrastructureSportive;
	}

	public void setInfrastructureSportive(InfrastructureSportive infrastructureSportive) {
		this.infrastructureSportive = infrastructureSportive;
	}

	public List<Billet> getBillets() {
		return billets;
	}

	public void setBillets(List<Billet> billets) {
		this.billets = billets;
	}

	public List<Resultat> getResultats() {
		return resultats;
	}

	public void setResultats(List<Resultat> resultats) {
		this.resultats = resultats;
	}

	public List<Participe> getParticipes() {
		return participes;
	}

	public void setParticipes(List<Participe> participes) {
		this.participes = participes;
	}
    
    
}
