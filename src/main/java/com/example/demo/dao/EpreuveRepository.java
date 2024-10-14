package com.example.demo.dao;

import com.example.demo.entities.Epreuve;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour gérer les opérations CRUD pour l'entité Epreuve.
 */
@Repository
public interface EpreuveRepository extends CrudRepository<Epreuve, Long> {

    /**
     * Calcule le nombre total de places disponibles pour toutes les épreuves.
     *
     * @return le nombre total de places disponibles
     */
    @Query("SELECT SUM(e.nb_billets) FROM Epreuve e")
    int getTotalPlacesDisponibles();

    /**
     * Calcule le chiffre d'affaires total des billets payés.
     *
     * @return le chiffre d'affaires total
     */
    @Query("SELECT SUM(b.prix) FROM Billet b WHERE b.etat = 'Payé'")
    Double getChiffreAffaires();

    /**
     * Compte le nombre total de billets payés.
     *
     * @return le nombre total de ventes
     */
    @Query("SELECT COUNT(*) FROM Billet b WHERE b.etat = 'Payé'")
    int getTotalVentes();

    /**
     * Calcule le pourcentage de billets payés par rapport au nombre total de billets disponibles.
     *
     * @return le pourcentage de billets payés en {@code Double}.
     */
    @Query("SELECT (paid_billets.paid_count * 1.0 / total_billets.total_count) * 100 AS percentage_paid " +
            "FROM (SELECT COUNT(b.id) AS paid_count FROM Billet b WHERE b.etat = 'Payé') AS paid_billets, " +
            "(SELECT SUM(e.nb_billets) AS total_count FROM Epreuve e) AS total_billets")
    Double getPourcentageBilletsVendus();

    /**
     * Récupère la liste des épreuves disponibles.
     * Une épreuve est considérée comme disponible si le nombre de délégations inscrites
     * (via la table Participe) est inférieur au nombre maximum de délégations autorisées (nb_delegations).
     *
     * @return la liste des épreuves disponibles
     */
    @Query("SELECT e FROM Epreuve e WHERE e.nb_delegations > (SELECT COUNT(p) FROM Participe p WHERE p.epreuve = e)")
    List<Epreuve> getEpreuveDisponible();}
