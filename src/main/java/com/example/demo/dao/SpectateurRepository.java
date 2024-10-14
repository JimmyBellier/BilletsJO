package com.example.demo.dao;

import com.example.demo.entities.Spectateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour gérer les opérations CRUD pour l'entité Spectateur.
 */
@Repository
public interface SpectateurRepository extends CrudRepository<Spectateur, Long> {

    /**
     * Recherche un spectateur par son email.
     *
     * @param email l'email du spectateur
     * @return un Optional contenant le spectateur s'il est trouvé, ou vide sinon
     */
    Optional<Spectateur> findByEmail(String email);

    /**
     * Supprime un spectateur par son email.
     *
     * @param email l'email du spectateur à supprimer
     */
    void deleteByEmail(String email);
}
