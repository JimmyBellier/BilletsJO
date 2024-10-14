package com.example.demo.dao;

import com.example.demo.entities.Delegation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour gérer les opérations CRUD pour l'entité Delegation.
 */
@Repository
public interface DelegationRepository extends CrudRepository<Delegation, Long> {

    /**
     * Récupère toutes les délégations triées par nombre de médailles d'or, d'argent et de bronze.
     *
     * @return la liste des délégations triées
     */
    @Query("SELECT d FROM Delegation d ORDER BY d.nb_medaille_or DESC, d.nb_medaille_argent DESC, d.nb_medaille_bronze DESC")
    List<Delegation> findAllOrderByMedals();

}
