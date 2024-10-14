package com.example.demo.controllers;

import com.example.demo.jobs.AllUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour gérer les opérations accessibles à tous les utilisateurs.
 */
@RestController
@RequestMapping("/api")
public class AllUsersController {

    @Autowired
    private AllUsersService allUsersService;

    /**
     * Récupère le classement général des délégations.
     *
     * @return une réponse HTTP contenant la liste des délégations triées par nombre de médailles
     */
    @GetMapping("/classement")
    public ResponseEntity<?> classementGeneral() {
        return ResponseEntity.ok(allUsersService.classementGeneral());
    }
}
