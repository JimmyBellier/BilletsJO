package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entité représentant une délégation.
 * Une délégation peut participer à plusieurs épreuves et avoir plusieurs participants.
 */
@Getter
@Setter
@Entity
public class Delegation {

    /**
     * L'identifiant unique de la délégation.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Le nom de la délégation.
     */
    private String nom;

    /**
     * Le nombre de médailles d'or remportées par la délégation.
     */
    private int nb_medaille_or;

    /**
     * Le nombre de médailles d'argent remportées par la délégation.
     */
    private int nb_medaille_argent;

    /**
     * Le nombre de médailles de bronze remportées par la délégation.
     */
    private int nb_medaille_bronze;

    /**
     * La liste des participations de la délégation aux épreuves.
     * La relation est en cascade et les participations orphelines seront supprimées.
     */
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Participe> participes;

    /**
     * La liste des participants appartenant à la délégation.
     * La relation est en cascade et les participants orphelins seront supprimés.
     */
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Participant> participants;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNb_medaille_or() {
		return nb_medaille_or;
	}

	public void setNb_medaille_or(int nb_medaille_or) {
		this.nb_medaille_or = nb_medaille_or;
	}

	public int getNb_medaille_argent() {
		return nb_medaille_argent;
	}

	public void setNb_medaille_argent(int nb_medaille_argent) {
		this.nb_medaille_argent = nb_medaille_argent;
	}

	public int getNb_medaille_bronze() {
		return nb_medaille_bronze;
	}

	public void setNb_medaille_bronze(int nb_medaille_bronze) {
		this.nb_medaille_bronze = nb_medaille_bronze;
	}

	public List<Participe> getParticipes() {
		return participes;
	}

	public void setParticipes(List<Participe> participes) {
		this.participes = participes;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}
    
    
}
