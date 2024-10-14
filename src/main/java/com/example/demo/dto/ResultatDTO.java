package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;


/**
 * DTO (Data Transfer Object) pour les résultats.
 * Ce DTO est utilisé pour transférer les données des résultats entre les couches de l'application.
 */
@Getter
@Setter
public class ResultatDTO {

    /**
     * L'email du participant associé au résultat.
     */
    private String emailParticipant;

    /**
     * L'identifiant de l'épreuve associée au résultat.
     */
    private Long idEpreuve;

    /**
     * Les points obtenus par le participant dans l'épreuve.
     */
    private Integer point;

    /**
     * La position du participant dans l'épreuve.
     */
    private Integer position;

	public String getEmailParticipant() {
		return emailParticipant;
	}

	public void setEmailParticipant(String emailParticipant) {
		this.emailParticipant = emailParticipant;
	}

	public Long getIdEpreuve() {
		return idEpreuve;
	}

	public void setIdEpreuve(Long idEpreuve) {
		this.idEpreuve = idEpreuve;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

}
