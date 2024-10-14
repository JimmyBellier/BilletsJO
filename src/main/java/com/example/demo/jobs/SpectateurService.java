package com.example.demo.jobs;

import com.example.demo.dao.BilletRepository;
import com.example.demo.dao.EpreuveRepository;
import com.example.demo.dao.SpectateurRepository;
import com.example.demo.entities.Billet;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.Spectateur;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service pour gérer les opérations liées aux spectateurs.
 * Ce service permet l'inscription, la suppression de compte, la consultation du programme,
 * la réservation et le paiement des billets, ainsi que l'annulation des réservations.
 */
@Service
public class SpectateurService {

    /**
     * Variable représentant le nombre maximum de billets par épreuve
     */
    private static final int MAX_BILLETS_PAR_EPREUVE = 4;
    @Autowired
    private BilletRepository billetRepository;
    @Autowired
    private SpectateurRepository spectateurRepository;
    @Autowired
    private EpreuveRepository epreuveRepository;

    /**
     * Vérifie si un email existe dans le système.
     *
     * @param email l'email à vérifier
     * @return true si l'email existe, false sinon
     */
    public boolean verifierEmailExist(String email) {
        return spectateurRepository.findByEmail(email).isPresent();
    }

    /**
     * Inscrit un nouveau spectateur dans le système.
     *
     * @param spectateur le spectateur à inscrire
     */
    @Transactional
    public void inscription(Spectateur spectateur) {
        spectateurRepository.save(spectateur);
    }

    /**
     * Supprime le compte d'un spectateur basé sur son email.
     *
     * @param email l'email du spectateur dont le compte doit être supprimé
     */
    @Transactional
    public void supprimerCompte(String email) {
        spectateurRepository.deleteByEmail(email);
    }

    /**
     * Consulte le programme de toutes les épreuves disponibles.
     *
     * @return une liste d'épreuves
     */
    public List<Epreuve> consulterProgramme() {
        Iterable<Epreuve> epreuves = epreuveRepository.findAll();
        return StreamSupport.stream(epreuves.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Liste tous les billets d'un spectateur en fonction de son email.
     *
     * @param email l'email du spectateur dont les billets doivent être listés
     * @return une liste de billets associés à l'email du spectateur
     */
    public List<Billet> listerBillets(String email) {
        Iterable<Billet> billets = billetRepository.findAllBySpectateur_Email(email);
        return StreamSupport.stream(billets.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Réserve un billet pour une épreuve spécifique pour un spectateur donné.
     *
     * @param idEpreuve l'identifiant de l'épreuve
     * @param email     l'email du spectateur
     * @return un message de confirmation de la réservation
     */
    @Transactional
    public String reserverBillet(long idEpreuve, String email) {
        Billet billet = new Billet();
        Spectateur spectateur = spectateurRepository.findByEmail(email).orElseThrow(()
                -> new EntityNotFoundException("Spectateur non trouvé avec l'email' : " + email));
        ;
        Epreuve epreuve = epreuveRepository.findById(idEpreuve).orElseThrow(()
                -> new EntityNotFoundException("Epreuve non trouvée avec l'id' : " + idEpreuve));
        billet.setSpectateur(spectateur);
        billet.setEpreuve(epreuve);
        billet.setPrix(epreuve.getPrix());

        if (nombreBilletsPourEpreuve(billet.getSpectateur(), billet.getEpreuve()) >= MAX_BILLETS_PAR_EPREUVE) {
            return "Vous avez déjà réservé le nombre maximum de billets pour cette épreuve.";
        }

        if (billetRepository.isFull(idEpreuve)) {
            return "Plus de place disponible.";
        }

        billet.setEtat("Réservé");
        billetRepository.save(billet);
        return "Réservation confirmée.";
    }

    /**
     * Effectue le paiement pour un billet réservé.
     *
     * @param idBillet l'identifiant du billet à payer
     * @return un message de confirmation du paiement
     */
    @Transactional
    public String payerBillet(long idBillet, String email) {
        Billet billet = billetRepository.findByIdAndSpectateur_Email(idBillet, email).orElseThrow(()
                -> new EntityNotFoundException("Billet non trouvé avec l'id' : " + idBillet));
        ;
        if (Objects.equals(billet.getEtat(), "Réservé")) {
            billet.setEtat("Payé");
            billetRepository.save(billet);
            return "Paiement confirmée.";
        } else if (Objects.equals(billet.getEtat(), "Payé")) {
            return "Ce billet a déjà été payé";
        } else if (Objects.equals(billet.getEtat(), "Annulé")) {
            return "Ce billet a été annulé";
        } else {
            return "Une erreur est survenue sur votre billet";
        }
    }

    /**
     * Annule une réservation de billet pour un spectateur donné.
     *
     * @param idBillet l'identifiant du billet à annuler
     * @param email    l'email du spectateur
     * @return un message de confirmation de l'annulation
     */
    @Transactional
    public String annulerReservation(long idBillet, String email) {
        Billet billet = billetRepository.findByIdAndSpectateur_Email(idBillet, email).orElseThrow(()
                -> new EntityNotFoundException("Billet non trouvé avec l'id' : " + idBillet));
        ;
        if (Objects.equals(billet.getEtat(), "Payé")) {
            Epreuve epreuve = billet.getEpreuve();
            LocalDate dateActuelle = LocalDate.now();
            long joursAvantEpreuve = ChronoUnit.DAYS.between(dateActuelle, epreuve.getDate());
            double remboursement = calculerRemboursement(joursAvantEpreuve, billet.getPrix());

            billet.setEtat("Annulé");
            billet.setRemboursement(remboursement);
            billetRepository.save(billet);
            return "Votre billet est annulé et vous avez été remboursé de : " + remboursement + " €";
        } else if (Objects.equals(billet.getEtat(), "Réservé")) {
            billet.setEtat("Annulé");
            billetRepository.save(billet);
            return "Votre billet a été annulé avec succès";
        } else if (Objects.equals(billet.getEtat(), "Annulé")) {
            return "Votre billet a déjà été annulé";
        } else {
            return "Un problème est survenu sur votre billet";
        }
    }

    /**
     * Calcule le montant du remboursement en fonction du nombre de jours avant l'épreuve.
     *
     * @param joursAvantEpreuve le nombre de jours avant l'épreuve
     * @param prix              le prix initial du billet
     * @return le montant du remboursement
     */
    private double calculerRemboursement(long joursAvantEpreuve, double prix) {
        if (joursAvantEpreuve > 7) {
            return prix;
        } else if (joursAvantEpreuve >= 3) {
            return prix * 0.5;
        } else {
            return 0;
        }
    }

    /**
     * Compte le nombre de billets réservés par un spectateur pour une épreuve donnée.
     *
     * @param spectateur le spectateur
     * @param epreuve    l'épreuve
     * @return le nombre de billets réservés
     */
    private int nombreBilletsPourEpreuve(Spectateur spectateur, Epreuve epreuve) {
        return billetRepository.countAllBySpectateurAndEpreuve(spectateur, epreuve);
    }

}
