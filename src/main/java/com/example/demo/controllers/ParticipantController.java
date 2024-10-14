package com.example.demo.controllers;

import com.example.demo.entities.Resultat;
import com.example.demo.jobs.ParticipantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer les opérations liées aux participants.
 */
@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    /**
     * Connecte un participant en vérifiant son email.
     *
     * @param email   l'email du participant
     * @param session la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la connexion
     */
    @PostMapping("/connexion")
    public ResponseEntity<String> connecterParticipant(@RequestParam String email, HttpSession session) {
        boolean existe = participantService.verifierEmailExist(email);
        if (existe) {
            session.setAttribute("email", email);
            return ResponseEntity.ok("Connexion réussie.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trouvé.");
        }
    }

    /**
     * Déconnecte un participant en supprimant son email de la session.
     *
     * @param session la session HTTP
     * @return une réponse HTTP indiquant le succès de la déconnexion
     */
    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnecterParticipant(HttpSession session) {
        session.removeAttribute("email");
        return ResponseEntity.ok("Déconnexion réussie.");
    }

    /**
     * Inscrit un participant à une épreuve.
     *
     * @param idEpreuve l'identifiant de l'épreuve
     * @param session   la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de l'inscription
     */
    @PostMapping("/inscrire")
    public ResponseEntity<String> inscriptionEpreuve(@RequestParam long idEpreuve, HttpSession session) {
        if (session.getAttribute("email") != null) {
            String response = participantService.inscrireEpreuve(session.getAttribute("email").toString(), idEpreuve);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Une erreur est survenue");
        }
    }

    /**
     * Désengage un participant d'une épreuve.
     *
     * @param idEpreuve l'identifiant de l'épreuve
     * @param session   la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec du désengagement
     */
    @PostMapping("/desengager")
    public ResponseEntity<String> desengagerEpreuve(@RequestParam long idEpreuve, HttpSession session) {
        if (session.getAttribute("email") != null) {
            String response = participantService.desengagerEpreuve(session.getAttribute("email").toString(), idEpreuve);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Une erreur est survenue");
        }
    }

    /**
     * Consulte les résultats d'un participant.
     *
     * @param session la session HTTP
     * @return une réponse HTTP contenant la liste des résultats ou une erreur si non autorisé
     */
    @GetMapping("/resultats")
    public ResponseEntity<List<Resultat>> consulterResultatsParticipant(HttpSession session) {
        if (session.getAttribute("email") != null) {
            List<Resultat> resultats = participantService.consulterResultatsParticipant(session.getAttribute("email").toString());
            return ResponseEntity.ok(resultats);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    /**
     * Consulte les résultats de la délégation d'un participant.
     *
     * @param session la session HTTP
     * @return une réponse HTTP contenant la liste des résultats de la délégation ou une erreur si non autorisé
     */
    @GetMapping("/resultats/delegation")
    public ResponseEntity<List<Resultat>> consulterResultatsDelegation(HttpSession session) {
        if (session.getAttribute("email") != null) {
            List<Resultat> resultats = participantService.consulterResultatsParDelegation(session.getAttribute("email").toString());
            return ResponseEntity.ok(resultats);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    /**
     * Consulte le programme des épreuves.
     *
     * @param session la session HTTP
     * @return une réponse HTTP contenant le programme des épreuves ou une erreur si non autorisé
     */
    @GetMapping("/programme")
    public ResponseEntity<?> consulterEpreuveDisponible(HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(participantService.consulterEpreuveDisponible());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

}
