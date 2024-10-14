package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entité représentant la participation d'une délégation à une épreuve.
 */
@Getter
@Setter
@Entity
public class Participe {

    /**
     * L'identifiant unique de la participation.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * L'état de la participation (e.g., "Participe", "Forfait").
     */
    private String etat;

    /**
     * L'épreuve à laquelle la délégation participe.
     */
    @ManyToOne
    @JoinColumn(name = "idEpreuve")
    @JsonIgnore
    private Epreuve epreuve;

    /**
     * La délégation qui participe à l'épreuve.
     */
    @ManyToOne
    @JoinColumn(name = "idDelegation")
    @JsonIgnore
    private Delegation delegation;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Epreuve getEpreuve() {
		return epreuve;
	}

	public void setEpreuve(Epreuve epreuve) {
		this.epreuve = epreuve;
	}

	public Delegation getDelegation() {
		return delegation;
	}

	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}
    
    
}
