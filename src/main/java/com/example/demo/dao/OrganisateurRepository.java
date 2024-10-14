package com.example.demo.dao;

import com.example.demo.entities.Organisateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour gérer les opérations CRUD pour l'entité Organisateur.
 */
@Repository
public interface OrganisateurRepository extends CrudRepository<Organisateur, Long> {

    /**
     * Recherche un organisateur par son email.
     *
     * @param email l'email de l'organisateur
     * @return un Optional contenant l'organisateur s'il est trouvé, ou vide sinon
     */
    Optional<Organisateur> findByEmail(String email);

}
