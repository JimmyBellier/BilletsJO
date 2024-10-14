package com.example.demo.dao;

import com.example.demo.entities.Controleur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour gérer les opérations CRUD pour l'entité Controleur.
 */
@Repository
public interface ControleurRepository extends CrudRepository<Controleur, Long> {

    /**
     * Recherche un contrôleur par son email.
     *
     * @param email l'email du contrôleur
     * @return un Optional contenant le contrôleur s'il est trouvé, ou vide sinon
     */
    Optional<Controleur> findByEmail(String email);
}
