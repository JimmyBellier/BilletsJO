package com.example.demo.controllers;

import com.example.demo.jobs.ControleurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour gérer les opérations liées aux contrôleurs.
 */
@RestController
@RequestMapping("/api/controleurs")
public class ControleurController {

    @Autowired
    private ControleurService controleurService;

    /**
     * Connecte un contrôleur en vérifiant son email.
     *
     * @param email   l'email du contrôleur
     * @param session la session HTTP
     * @return une réponse HTTP indiquant le succès ou l'échec de la connexion
     */
    @PostMapping("/connexion")
    public ResponseEntity<String> connecterControleur(@RequestParam String email, HttpSession session) {
        boolean existe = controleurService.verifierEmailExist(email);
        if (existe) {
            session.setAttribute("email", email);
            return ResponseEntity.ok("Connexion réussie.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trouvé.");
        }
    }

    /**
     * Déconnecte un contrôleur en supprimant son email de la session.
     *
     * @param session la session HTTP
     * @return une réponse HTTP indiquant le succès de la déconnexion
     */
    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnecterControleur(HttpSession session) {
        session.removeAttribute("email");
        return ResponseEntity.ok("Déconnexion réussie.");
    }

    /**
     * Vérifie la validité d'un billet.
     *
     * @param idBillet l'identifiant du billet
     * @param session  la session HTTP
     * @return une réponse HTTP indiquant si le billet est valide ou non, ou une erreur si non autorisé
     */
    @PostMapping("/verifier-billet")
    public ResponseEntity<String> verifierBillet(@RequestParam long idBillet, HttpSession session) {
        if (session.getAttribute("email") != null) {
            boolean valide = controleurService.verifierBillet(idBillet);
            if (valide) {
                return ResponseEntity.ok("Billet valide.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Billet invalide.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé. Veuillez vous connecter.");
        }
    }
}
