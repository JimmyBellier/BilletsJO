package com.example.demo.jobs;

import com.example.demo.dao.InfrastructureSportiveRepository;
import com.example.demo.dao.OrganisateurRepository;
import com.example.demo.entities.InfrastructureSportive;
import com.example.demo.entities.Organisateur;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service pour gérer les opérations de très haut niveau.
 * Ce service permet de créer des infrastructures sportives et des organisateurs.
 */
@Service
@Transactional
public class AdminService {

    @Autowired
    private InfrastructureSportiveRepository infrastructureSportiveRepository;

    @Autowired
    private OrganisateurRepository organisateurRepository;

    /**
     * Crée une nouvelle infrastructure sportive.
     *
     * @param infrastructureSportive l'infrastructure sportive à créer
     * @return l'infrastructure sportive créée
     */
    public void creerInfrastructureSportive(InfrastructureSportive infrastructureSportive) {
        infrastructureSportiveRepository.save(infrastructureSportive);
    }

    /**
     * Crée un nouvel organisateur.
     *
     * @param organisateur l'organisateur à créer
     */
    public void creerOrganisateur(Organisateur organisateur) {
        organisateurRepository.save(organisateur);
    }

}
