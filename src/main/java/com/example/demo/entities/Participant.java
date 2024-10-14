// Participant.java
package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entité représentant un participant aux épreuves.
 */
@Getter
@Setter
@Entity
public class Participant {

    /**
     * L'email du participant (utilisé comme identifiant unique).
     */
    @Id
    private String email;

    /**
     * Le nom du participant.
     */
    private String nom;

    /**
     * Le prénom du participant.
     */
    private String prenom;

    /**
     * La délégation à laquelle appartient le participant.
     */
    @ManyToOne
    @JoinColumn(name = "idDelegation")
    @JsonIgnore
    private Delegation delegation;

    /**
     * La liste des résultats obtenus par le participant.
     * La relation est en cascade et les résultats orphelins seront supprimés.
     */
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Resultat> resultats;

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

	public Delegation getDelegation() {
		return delegation;
	}

	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}

	public List<Resultat> getResultats() {
		return resultats;
	}

	public void setResultats(List<Resultat> resultats) {
		this.resultats = resultats;
	}
    
    
}