// Spectateur.java
package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entité représentant un spectateur.
 * Un spectateur peut réserver des billets pour des épreuves.
 */
@Getter
@Setter
@Entity
public class Spectateur {

    /**
     * L'email du spectateur (utilisé comme identifiant unique).
     */
    @Id
    private String email;

    /**
     * L'identifiant du spectateur.
     */
    private String nom;

    /**
     * Le prénom du spectateur.
     */
    private String prenom;

    /**
     * La liste des billets réservés par le spectateur.
     * La relation est en cascade et les billets orphelins seront supprimés.
     */
    @OneToMany(mappedBy = "spectateur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Billet> billets;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<Billet> getBillets() {
		return billets;
	}

	public void setBillets(List<Billet> billets) {
		this.billets = billets;
	}
    
    
}
