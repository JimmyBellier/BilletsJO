package com.example.demo.jobs;

import com.example.demo.dao.*;
import com.example.demo.entities.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service pour gérer les opérations liées aux participants.
 * Ce service permet de vérifier l'existence d'un email, inscrire et désengager les participants des épreuves,
 * consulter les résultats des participants et de leurs délégations, et consulter le programme des épreuves.
 */
@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private EpreuveRepository epreuveRepository;
    @Autowired
    private ParticipeRepository participeRepository;
    @Autowired
    private ResultatRepository resultatRepository;
    @Autowired
    private DelegationRepository delegationRepository;

    /**
     * Vérifie si un email existe dans le système.
     *
     * @param email l'email à vérifier
     * @return true si l'email existe, false sinon
     */
    public boolean verifierEmailExist(String email) {
        return participantRepository.findByEmail(email).isPresent();
    }

    /**
     * Inscrit un participant à une épreuve spécifique.
     *
     * @param email     l'email du participant
     * @param idEpreuve l'identifiant de l'épreuve
     * @return un message de confirmation de l'inscription
     */
    @Transactional
    public String inscrireEpreuve(String email, long idEpreuve) {
        Participant participant = participantRepository.findByEmail(email).orElseThrow(()
                -> new EntityNotFoundException("Participant non trouvé avec l'email : " + email));
        ;
        Epreuve epreuve = epreuveRepository.findById(idEpreuve).orElseThrow(()
                -> new EntityNotFoundException("Epreuve non trouvée avec l'id : " + idEpreuve));

        int nbTotalDejaInscrit = participeRepository.countByEpreuve_Id(idEpreuve);

        Delegation delegation = participant.getDelegation();

        if (delegation == null) {
            return ("Le participant n'est associé à aucune délégation.");
        }

        boolean alreadyExists = participeRepository.findByDelegation_IdAndEpreuve_Id(delegation.getId(), idEpreuve).isPresent();

        // Vérifier si l'inscription est possible (avant 10 jours de la date de l'épreuve)
        LocalDate now = LocalDate.now();
        LocalDate dateEpreuve = epreuve.getDate();
        if (ChronoUnit.DAYS.between(now, dateEpreuve) > 10) {
            if (epreuve.getNb_delegations() > nbTotalDejaInscrit) {
                if (!alreadyExists) {
                    Participe participes = new Participe();
                    participes.setEpreuve(epreuve);
                    participes.setDelegation(delegation);
                    participes.setEtat("Participe");

                    participeRepository.save(participes);

                } else {
                    return "La délégation est déjà inscrite à cette épreuve.";
                }
            } else {
                return "Nombre total de délégations déjà dépassé pour l'épreuve.";
            }
        } else {
            return "inscription est fermée 10 jours avant la date de l'épreuve.";
        }
        return "La délégation est bien inscrite";
    }

    /**
     * Désengage un participant d'une épreuve spécifique.
     *
     * @param email     l'email du participant
     * @param idEpreuve l'identifiant de l'épreuve
     * @return un message de confirmation du désengagement
     */
    @Transactional
    public String desengagerEpreuve(String email, long idEpreuve) {
        // Récupérer l'épreuve
        Epreuve epreuve = epreuveRepository.findById(idEpreuve)
                .orElseThrow(() -> new EntityNotFoundException("Epreuve non trouvée avec l'id : " + idEpreuve));
        Participant participant = participantRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Participant non trouvé avec l'email : " + email));
        Delegation delegation = delegationRepository.findById(participant.getDelegation().getId())
                .orElseThrow(() -> new EntityNotFoundException("Délégation non trouvée avec l'id : " + participant.getDelegation().getId()));

        // Vérifier si la date de l'épreuve est dans les 10 jours
        LocalDate now = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(now, epreuve.getDate());

        // Récupérer la participation
        Participe participation = participeRepository.findByDelegation_IdAndEpreuve_Id(delegation.getId(), idEpreuve)
                .orElseThrow(() -> new EntityNotFoundException("Relation participe non trouvée entre l'id de la délégation : " + delegation.getId() + ", et l'id de l'épreuve : " + idEpreuve));

        if (daysBetween <= 10) {
            // Si dans les 10 jours, marquer comme forfait
            participation.setEtat("forfait");
            participeRepository.save(participation);
            return "Participant désengagé et marqué comme forfait";
        } else {
            // Sinon, supprimer la participation
            participeRepository.delete(participation);
            return "Participant désengagé avec succès";
        }
    }

    /**
     * Consulte les résultats d'un participant spécifique.
     *
     * @param email l'email du participant
     * @return une liste de résultats
     */
    public List<Resultat> consulterResultatsParticipant(String email) {
        Participant participant = participantRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Participant non trouvé avec l'email : " + email));
        return resultatRepository.findByParticipant(participant);
    }

    /**
     * Consulte les résultats de la délégation d'un participant.
     *
     * @param email l'email du participant
     * @return une liste de résultats
     */
    public List<Resultat> consulterResultatsParDelegation(String email) {
        Participant participant = participantRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Participant non trouvé avec l'email : " + email));
        long idDelegation = participant.getDelegation().getId();
        Delegation delegation = delegationRepository.findById(idDelegation)
                .orElseThrow(() -> new EntityNotFoundException("Delegation non trouvée avec l'id : " + idDelegation));
        return resultatRepository.findByParticipant_Delegation(delegation);
    }

    /**
     * Consulte le programme de toutes les épreuves disponibles.
     *
     * @return une liste d'épreuves
     */
    public List<Epreuve> consulterEpreuveDisponible() {
        Iterable<Epreuve> epreuves = epreuveRepository.getEpreuveDisponible();
        return StreamSupport.stream(epreuves.spliterator(), false)
                .collect(Collectors.toList());
    }
}
