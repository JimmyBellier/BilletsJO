package com.example.demo.controllers;

import com.example.demo.entities.Spectateur;
import com.example.demo.jobs.SpectateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour gérer les opérations liées aux spectateurs.
 */
@RestController
@RequestMapping("/api/spectateurs")
public class SpectateurController {

    @Autowired
    private SpectateurService spectateurService;

    /**
     * Connecte un spectateur en vérifiant son email.
     *
     * @param email   l'email du spectateur
     * @param session la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la connexion
     */
    @PostMapping("/connexion")
    public ResponseEntity<String> connecterSpectateur(@RequestParam String email, HttpSession session) {
        boolean existe = spectateurService.verifierEmailExist(email);
        if (existe) {
            session.setAttribute("email", email);
            return ResponseEntity.ok("Connexion réussie.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trouvé.");
        }
    }

    /**
     * Déconnecte un spectateur en supprimant son email de la session.
     *
     * @param session la session HTTP
     * @return une réponse HTTP indiquant le succès de la déconnexion
     */
    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnecterSpectateur(HttpSession session) {
        session.removeAttribute("email");
        return ResponseEntity.ok("Déconnexion réussie.");
    }

    /**
     * Inscrit un nouveau spectateur.
     *
     * @param spectateur le spectateur à inscrire
     * @return une réponse HTTP indiquant le succès de l'inscription
     */
    @PostMapping
    public ResponseEntity<String> inscrire(@RequestBody Spectateur spectateur) {
        spectateurService.inscription(spectateur);
        return ResponseEntity.ok("Spectateur créé");
    }

    /**
     * Supprime le compte d'un spectateur.
     *
     * @param session la session HTTP
     * @return une réponse HTTP indiquant le succès de la suppression du compte
     */
    @DeleteMapping
    public ResponseEntity<String> supprimerCompte(HttpSession session) {
        if (session.getAttribute("email") != null) {
            spectateurService.supprimerCompte(session.getAttribute("email").toString());
            session.removeAttribute("email");
            return ResponseEntity.ok("Compte bien supprimé.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Consulte le programme des épreuves.
     *
     * @param session la session HTTP
     * @return une réponse HTTP contenant le programme des épreuves ou une erreur si non autorisé
     */
    @GetMapping("/programme")
    public ResponseEntity<?> consulterProgramme(HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.consulterProgramme());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Liste les billets d'un spectateur.
     *
     * @param session la session HTTP
     * @return une réponse HTTP contenant la liste des billets ou une erreur si non autorisé
     */
    @GetMapping("/billet")
    public ResponseEntity<?> listerBillets(HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.listerBillets(session.getAttribute("email").toString()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Réserve un billet pour une épreuve.
     *
     * @param idEpreuve l'identifiant de l'épreuve
     * @param session   la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la réservation
     */
    @PostMapping("/billet")
    public ResponseEntity<?> reserverBillet(@RequestParam long idEpreuve, HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.reserverBillet(idEpreuve, session.getAttribute("email").toString()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Paie un billet réservé.
     *
     * @param idBillet l'identifiant du billet
     * @param session  la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec du paiement
     */
    @PostMapping("/billet/payer")
    public ResponseEntity<?> payerBillet(@RequestParam long idBillet, HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.payerBillet(idBillet, session.getAttribute("email").toString()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

    /**
     * Annule une réservation de billet.
     *
     * @param idBillet l'identifiant du billet
     * @param session  la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de l'annulation
     */
    @PostMapping("/billet/annuler")
    public ResponseEntity<?> annulerReservation(@RequestParam long idBillet, HttpSession session) {
        if (session.getAttribute("email") != null) {
            return ResponseEntity.ok(spectateurService.annulerReservation(idBillet, session.getAttribute("email").toString()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }

}
