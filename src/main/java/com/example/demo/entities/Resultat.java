// Résultat.java
package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entité représentant un résultat d'un participant dans une épreuve.
 */
@Getter
@Setter
@Entity
public class Resultat {

    /**
     * L'identifiant unique du résultat.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Les points obtenus par le participant dans l'épreuve.
     */
    private double point;

    /**
     * La position du participant dans l'épreuve.
     */
    private int position;

    /**
     * Le participant associé à ce résultat.
     */
    @ManyToOne
    @JoinColumn(name = "idParticipant")
    @JsonIgnore
    private Participant participant;

    /**
     * L'épreuve associée à ce résultat.
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

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Epreuve getEpreuve() {
		return epreuve;
	}

	public void setEpreuve(Epreuve epreuve) {
		this.epreuve = epreuve;
	}


}