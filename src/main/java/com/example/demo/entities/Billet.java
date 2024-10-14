package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entité représentant un billet pour une épreuve.
 * Un billet est associé à un spectateur et une épreuve.
 * crée requete de creation de la table Billet dans la base de données avec les attributs id, prix, remboursement, etat, spectateur_email, idEpreuve
 * 
 */
@Getter
@Setter
@Entity
public class Billet {

    /**
     * L'identifiant unique du billet.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Le prix du billet.
     */
    private double prix;

    /**
     * Le montant du remboursement du billet.
     */
    private double remboursement;

    /**
     * L'état du billet.
     * 3 états possibles :
     * - Réservé
     * - Annulé
     * - Payé
     */
    private String etat;

    /**
     * Le spectateur associé à ce billet.
     * Un spectateur peut avoir plusieurs billets.
     */
    @ManyToOne
    @JoinColumn(name = "spectateur_email")
    @JsonIgnore
    private Spectateur spectateur;

    /**
     * L'épreuve associée à ce billet.
     */
    @ManyToOne
    @JoinColumn(name = "idEpreuve")
    @JsonIgnore
    private Epreuve epreuve;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public double getRemboursement() {
		return remboursement;
	}

	public void setRemboursement(double remboursement) {
		this.remboursement = remboursement;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Spectateur getSpectateur() {
		return spectateur;
	}

	public void setSpectateur(Spectateur spectateur) {
		this.spectateur = spectateur;
	}

	public Epreuve getEpreuve() {
		return epreuve;
	}

	public void setEpreuve(Epreuve epreuve) {
		this.epreuve = epreuve;
	}
    
    

}
