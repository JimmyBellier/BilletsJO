package com.example.demo.dao;

import com.example.demo.entities.Delegation;
import com.example.demo.entities.Participant;
import com.example.demo.entities.Resultat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour gérer les opérations CRUD pour l'entité Resultat.
 */
@Repository
public interface ResultatRepository extends CrudRepository<Resultat, Long> {

    /**
     * Recherche les résultats par délégation de participant.
     *
     * @param participant_delegation la délégation du participant
     * @return une liste de résultats pour la délégation spécifiée
     */
    List<Resultat> findByParticipant_Delegation(Delegation participant_delegation);

    /**
     * Recherche les résultats par participant.
     *
     * @param participant le participant
     * @return une liste de résultats pour le participant spécifié
     */
    List<Resultat> findByParticipant(Participant participant);

}
