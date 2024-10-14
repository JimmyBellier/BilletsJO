package com.example.demo.jobs;

import com.example.demo.dao.BilletRepository;
import com.example.demo.dao.ControleurRepository;
import com.example.demo.entities.Billet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Service pour gérer les opérations liées aux contrôleurs.
 * Ce service permet de vérifier l'existence d'un email et de vérifier la validité des billets.
 */
@Service
public class ControleurService {

    @Autowired
    private BilletRepository billetRepository;

    @Autowired
    private ControleurRepository controleurRepository;

    /**
     * Vérifie si un email existe dans le système.
     *
     * @param email l'email à vérifier
     * @return true si l'email existe, false sinon
     */
    public boolean verifierEmailExist(String email) {
        return controleurRepository.findByEmail(email).isPresent();
    }

    /**
     * Vérifie la validité d'un billet.
     *
     * @param idBillet l'identifiant du billet à vérifier
     * @return true si le billet est payé, false sinon
     */
    public boolean verifierBillet(long idBillet) {
        Optional<Billet> billet = billetRepository.findById(idBillet);
        return billet.map(b -> "Payé".equals(b.getEtat())).orElse(false);
    }
}
