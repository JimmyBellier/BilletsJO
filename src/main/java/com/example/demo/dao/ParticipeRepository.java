package com.example.demo.dao;

import com.example.demo.entities.Participe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour gérer les opérations CRUD pour l'entité Participe.
 */
@Repository
public interface ParticipeRepository extends CrudRepository<Participe, Long> {

    /**
     * Recherche une participation par l'identifiant de la délégation et l'identifiant de l'épreuve.
     *
     * @param delegationId l'identifiant de la délégation
     * @param epreuveId    l'identifiant de l'épreuve
     * @return un Optional contenant la participation si elle est trouvée, ou vide sinon
     */
    Optional<Participe> findByDelegation_IdAndEpreuve_Id(long delegationId, long epreuveId);

    /**
     * Compte le nombre de billets pour une épreuve donnée.
     *
     * @param idEpreuve l'identifiant de l'épreuve.
     * @return le nombre de billets pour l'épreuve spécifiée.
     */
    int countByEpreuve_Id(long idEpreuve);

}
