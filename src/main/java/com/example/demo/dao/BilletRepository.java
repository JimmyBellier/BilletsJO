package com.example.demo.dao;

import com.example.demo.entities.Billet;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Spectateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour gérer les opérations CRUD pour l'entité Billet.
 */
@Repository
public interface BilletRepository extends CrudRepository<Billet, Long> {

    /**
     * Compte le nombre de billets pour un spectateur et une épreuve donnés, excluant les billets annulés.
     *
     * @param spectateur le spectateur
     * @param epreuve    l'épreuve
     * @return le nombre de billets payés ou réservés
     */
    @Query("SELECT COUNT(b) FROM Billet b WHERE b.spectateur = :spectateur AND b.epreuve = :epreuve AND b.etat IN ('Payé', 'Réservé')")
    int countAllBySpectateurAndEpreuve(Spectateur spectateur, Epreuve epreuve);


    /**
     * Recherche un billet par son identifiant et l'email du spectateur.
     *
     * @param idBillet l'identifiant du billet
     * @param email    l'email du spectateur
     * @return un Optional contenant le billet s'il est trouvé, ou vide sinon
     */
    Optional<Billet> findByIdAndSpectateur_Email(long idBillet, String email);

    /**
     * Recherche tous les billets pour un spectateur donné par son email.
     *
     * @param email l'email du spectateur
     * @return une liste de billets pour le spectateur spécifié
     */
    List<Billet> findAllBySpectateur_Email(String email);

    /**
     * Vérifie si une épreuve est complète en termes de billets vendus.
     *
     * @param idEpreuve l'identifiant de l'épreuve
     * @return true si le nombre de billets vendus est égal ou supérieur au nombre maximum de billets, sinon false
     */
    @Query("SELECT CASE WHEN COUNT(b) >= e.nb_billets THEN true ELSE false END " +
            "FROM Billet b JOIN b.epreuve e WHERE e.id = :idEpreuve AND b.etat = 'Payé'")
    boolean isFull(long idEpreuve);
}
