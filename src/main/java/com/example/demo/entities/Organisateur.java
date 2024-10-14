// Organisateur.java
package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * Entité représentant un organisateur.
 */
@Getter
@Setter
@Entity
public class Organisateur {

    /**
     * L'email de l'organisateur (utilisé comme identifiant unique).
     */
    @Id
    private String email;

    /**
     * Le nom de l'organisateur.
     */
    private String nom;

    /**
     * Le prénom de l'organisateur.
     */
    private String prenom;

    /**
     * Le rôle de l'organisateur.
     */
    private String role;

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    
    
}