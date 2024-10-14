package com.example.demo.controllers;

import com.example.demo.dto.EpreuveDTO;
import com.example.demo.dto.ResultatDTO;
import com.example.demo.entities.Controleur;
import com.example.demo.entities.Delegation;
import com.example.demo.entities.InfrastructureSportive;
import com.example.demo.entities.Participant;
import com.example.demo.jobs.OrganisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Contrôleur REST pour gérer les opérations liées aux organisateurs.
 */
@RestController
@RequestMapping("/api/organisateurs")
public class OrganisateurController {

    @Autowired
    private OrganisateurService organisateurService;

    /**
     * Connecte un organisateur en vérifiant son email.
     *
     * @param email   l'email de l'organisateur
     * @param session la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la connexion
     */
    @PostMapping("/connexion")
    public ResponseEntity<String> connecterOrganisateur(@RequestParam String email, HttpSession session) {
        boolean existe = organisateurService.verifierEmailExist(email);
        if (existe) {
            session.setAttribute("email", email);
            return ResponseEntity.ok("Connexion réussie.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trouvé.");
        }
    }

    /**
     * Déconnecte un organisateur en supprimant son email de la session.
     *
     * @param session la session HTTP
     * @return une réponse HTTP indiquant le succès de la déconnexion
     */
    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnecterOrganisateur(HttpSession session) {
        session.removeAttribute("email");
        return ResponseEntity.ok("Déconnexion réussie.");
    }

    /**
     * Crée une nouvelle délégation.
     *
     * @param delegation la délégation à créer
     * @param session    la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la création de la délégation
     */
    @PostMapping("/delegation")
    public ResponseEntity<String> creerDelegation(@RequestBody Delegation delegation, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.creerDelegation(delegation);
            return ResponseEntity.status(HttpStatus.CREATED).body("Délégation créée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Supprime une délégation par son identifiant.
     *
     * @param idDelegation l'identifiant de la délégation
     * @param session      la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la suppression de la délégation
     */
    @DeleteMapping("/delegation")
    public ResponseEntity<String> supprimerDelegation(@RequestParam long idDelegation, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.supprimerDelegation(idDelegation);
            return ResponseEntity.ok("Délégation supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Crée une nouvelle épreuve.
     *
     * @param epreuveDTO les détails de l'épreuve à créer
     * @param session    la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la création de l'épreuve
     */
    @PostMapping("/epreuve")
    public ResponseEntity<String> creerEpreuve(@RequestBody EpreuveDTO epreuveDTO, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.creerEpreuve(epreuveDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Epreuve créée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Modifie une épreuve existante.
     *
     * @param epreuveDTO les détails de l'épreuve à modifier
     * @param session    la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la modification de l'épreuve
     */
    @PutMapping("/epreuve")
    public ResponseEntity<String> modifierEpreuve(@RequestBody EpreuveDTO epreuveDTO, HttpSession session) {
        if (session.getAttribute("email") != null) {
            String response = organisateurService.modifierEpreuve(epreuveDTO);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Supprime une épreuve par son identifiant.
     *
     * @param idEpreuve l'identifiant de l'épreuve
     * @param session   la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la suppression de l'épreuve
     */
    @DeleteMapping("/epreuve")
    public ResponseEntity<String> supprimerEpreuve(@RequestParam long idEpreuve, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.supprimerEpreuve(idEpreuve);
            return ResponseEntity.ok("Épreuve supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Crée un nouveau participant.
     *
     * @param participant le participant à créer
     * @param session     la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la création du participant
     */
    @PostMapping("/participant")
    public ResponseEntity<String> creerParticipant(@RequestBody Participant participant, HttpSession session) {
        if (session.getAttribute("email") != null) {
            Participant created = organisateurService.creerParticipant(participant);
            return ResponseEntity.status(HttpStatus.CREATED).body("Participant créé.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Supprime un participant par son email.
     *
     * @param email   l'email du participant
     * @param session la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la suppression du participant
     */
    @DeleteMapping("/participant")
    public ResponseEntity<String> supprimerParticipant(@RequestParam String email, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.supprimerParticipant(email);
            return ResponseEntity.ok("Participant supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Crée un nouveau contrôleur.
     *
     * @param controleur le contrôleur à créer
     * @param session    la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la création du contrôleur
     */
    @PostMapping("/controleur")
    public ResponseEntity<String> creerControleur(@RequestBody Controleur controleur, HttpSession session) {
        if (session.getAttribute("email") != null) {
            Controleur created = organisateurService.creerControleur(controleur);
            return ResponseEntity.status(HttpStatus.CREATED).body("Controleur créé.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Supprime un contrôleur par son email.
     *
     * @param email   l'email du contrôleur
     * @param session la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la suppression du contrôleur
     */
    @DeleteMapping("/controleur")
    public ResponseEntity<String> supprimerControleur(@RequestParam String email, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.supprimerControleur(email);
            return ResponseEntity.ok("Controleur supprimé.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Crée une nouvelle infrastructure sportive.
     *
     * @param infrastructureSportive le contrôleur à créer
     * @param session                la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la création.
     */
    @PostMapping("/infrastructure")
    public ResponseEntity<String> creerInfrastructure(@RequestBody InfrastructureSportive infrastructureSportive, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.creerInfrastructureSportive(infrastructureSportive);
            return ResponseEntity.status(HttpStatus.CREATED).body("Infrastructure créée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Supprime une infrastructure sportive par son id.
     *
     * @param idInfrastructure l'id de l'infrastructure sportive
     * @param session          la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la suppression.
     */
    @DeleteMapping("/infrastructure")
    public ResponseEntity<String> supprimerInfrastructure(@RequestParam long idInfrastructure, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.supprimerInfrastructure(idInfrastructure);
            return ResponseEntity.ok("Infrastructure supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Met à jour la date d'une épreuve.
     *
     * @param date      la nouvelle date de l'épreuve
     * @param idEpreuve l'identifiant de l'épreuve
     * @param session   la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la mise à jour de la date
     */
    @PostMapping("/epreuve/date")
    public ResponseEntity<String> setDate(@RequestParam LocalDate date, @RequestParam long idEpreuve, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.setDate(date, idEpreuve);
            return ResponseEntity.ok("Date mise-à-jour.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Met à jour le nombre de participants pour une épreuve.
     *
     * @param idEpreuve     l'identifiant de l'épreuve
     * @param nbParticipant le nouveau nombre de participants
     * @param session       la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la mise à jour du nombre de participants
     */
    @PostMapping("/epreuve/nbparticipants")
    public ResponseEntity<String> setNbParticipants(@RequestParam long idEpreuve, @RequestParam int nbParticipant, HttpSession session) {
        if (session.getAttribute("email") != null) {
            String response = organisateurService.setNbParticipants(idEpreuve, nbParticipant);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Met à jour le nombre de billets pour une épreuve.
     *
     * @param idEpreuve l'identifiant de l'épreuve
     * @param nbBillets le nouveau nombre de billets
     * @param session   la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la mise à jour du nombre de billets
     */
    @PostMapping("/epreuve/nbbillets")
    public ResponseEntity<String> setNbBillets(@RequestParam long idEpreuve, @RequestParam int nbBillets, HttpSession session) {
        if (session.getAttribute("email") != null) {
            String response = organisateurService.setNbBillets(idEpreuve, nbBillets);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Associe un participant à une délégation.
     *
     * @param emailParticipant l'email du participant
     * @param idDelegation     l'identifiant de la délégation
     * @param session          la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la mise en relation
     */
    @PostMapping("/participant/delegation")
    public ResponseEntity<String> setDelegation(@RequestParam String emailParticipant, @RequestParam long idDelegation, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.setDelegation(emailParticipant, idDelegation);
            return ResponseEntity.ok("Mise en relation entre le participant et la délagation réalisée avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Met à jour le résultat d'une épreuve pour un participant.
     *
     * @param resultatDTO les détails du résultat à mettre à jour
     * @param session     la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la mise à jour du résultat
     */
    @PostMapping("/epreuve/resultat")
    public ResponseEntity<String> setResultat(@RequestBody ResultatDTO resultatDTO, HttpSession session) {
        if (session.getAttribute("email") != null) {
            organisateurService.setResultat(resultatDTO);
            return ResponseEntity.ok("Résultat mis-à-jour");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Récupère le nombre total de places disponibles pour les épreuves.
     *
     * @param session la session HTTP
     * @return une réponse HTTP contenant le nombre total de places disponibles ou une erreur si non autorisé
     */
    @GetMapping("/places-disponibles")
    public ResponseEntity<String> getTotalPlacesDisponibles(HttpSession session) {
        if (session.getAttribute("email") != null) {
            int placesDisponibles = organisateurService.getTotalPlacesDisponibles();
            return ResponseEntity.ok("Places disponibles : " + placesDisponibles);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Récupère le chiffre d'affaires total des billets payés.
     *
     * @param session la session HTTP
     * @return une réponse HTTP contenant le chiffre d'affaires ou une erreur si non autorisé
     */
    @GetMapping("/chiffre-affaires")
    public ResponseEntity<String> getChiffreAffaires(HttpSession session) {
        if (session.getAttribute("email") != null) {
            double chiffreAffaires = organisateurService.getChiffreAffaires();
            return ResponseEntity.ok("Chiffre d'affaires : " + chiffreAffaires);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Récupère le nombre total de ventes de billets.
     *
     * @param session la session HTTP
     * @return une réponse HTTP contenant le nombre total de ventes ou une erreur si non autorisé
     */
    @GetMapping("/total-ventes")
    public ResponseEntity<String> getTotalVentes(HttpSession session) {
        if (session.getAttribute("email") != null) {
            int totalVentes = organisateurService.getTotalVentes();
            return ResponseEntity.ok("Total des ventes : " + totalVentes);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Récupère le pourcentage de billets vendus (payés) par rapport au nombre total de billets disponibles.
     *
     * @param session la session HTTP
     * @return une réponse HTTP contenant le pourcentage de billets payés ou une erreur si non autorisé
     */
    @GetMapping("/pourcentage-billets-vendus")
    public ResponseEntity<String> getPourcentageBilletsVendus(HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(organisateurService.getPourcentageBilletsVendus() + " %");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Liste tous les participants.
     *
     * @param session la session HTTP
     * @return une réponse HTTP contenant la liste des participants ou une erreur si non autorisé
     */
    @GetMapping("/participant")
    public ResponseEntity<?> listerParticipant(HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(organisateurService.getListeParticipant());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Liste toutes les délégations.
     *
     * @param session la session HTTP
     * @return une réponse HTTP contenant la liste des délégations ou une erreur si non autorisé
     */
    @GetMapping("/delegation")
    public ResponseEntity<?> listerDelegation(HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(organisateurService.getListeDelegation());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

}
