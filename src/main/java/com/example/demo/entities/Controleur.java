// Spectateur.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * Entité représentant un contrôleur.
 * Un contrôleur est responsable de vérifier la validité des billets des spectateurs.
 */
@Getter
@Setter
@Entity
public class Controleur {

    /**
     * L'email du contrôleur (utilisé comme identifiant unique).
     */
    @Id
    private String email;

    /**
     * L'email du contrôleur (utilisé comme identifiant unique).
     */
    private String nom;

    /**
     * Le prénom du contrôleur.
     */
    private String prenom;

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

    
}
