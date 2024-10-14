package com.example.demo.dao;

import com.example.demo.entities.Delegation;
import com.example.demo.entities.Participant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour gérer les opérations CRUD pour l'entité Participant.
 */
@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long> {

    /**
     * Recherche un participant par son email.
     *
     * @param email l'email du participant
     * @return un Optional contenant le participant s'il est trouvé, ou vide sinon
     */
    Optional<Participant> findByEmail(String email);

    /**
     * Recherche les participants par délégation.
     *
     * @param delegation la délégation des participants
     * @return une liste de participants appartenant à la délégation spécifiée
     */
    List<Participant> findByDelegation(Delegation delegation);
}
