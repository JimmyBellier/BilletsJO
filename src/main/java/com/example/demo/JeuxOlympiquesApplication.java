package com.example.demo;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dto.EpreuveDTO;
import com.example.demo.dto.ResultatDTO;
import com.example.demo.entities.Controleur;
import com.example.demo.entities.Delegation;
import com.example.demo.entities.Epreuve;
import com.example.demo.entities.InfrastructureSportive;
import com.example.demo.entities.Organisateur;
import com.example.demo.entities.Participant;
import com.example.demo.entities.Spectateur;
import com.example.demo.jobs.AdminService;
import com.example.demo.jobs.ControleurService;
import com.example.demo.jobs.OrganisateurService;
import com.example.demo.jobs.ParticipantService;
import com.example.demo.jobs.SpectateurService;

@SpringBootApplication
public class JeuxOlympiquesApplication implements CommandLineRunner {

    @Autowired
    private AdminService adminService;
    @Autowired
    private SpectateurService spectateurService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private ControleurService controleurService;
    @Autowired
    private OrganisateurService organisateurService;

    public static void main(String[] args) {
        SpringApplication.run(JeuxOlympiquesApplication.class, args);
    }

    /**
     * Cette partie permet à la fois d'initialiser la base de données,
     * mais aussi de tester les fonctions directement depuis les services.
     *
     * Note : elle ne couvre pas tous les cas, merci de ne pas se baser sur les fonctions ci-dessous.
     */
    @Override
    public void run(String... args) {

        /*
         *
         * Admin part
         *
         */

        Organisateur organisateur = new Organisateur();
        organisateur.setPrenom("Admin");
        organisateur.setNom("Root");
        organisateur.setRole("organiser");
        organisateur.setEmail("admin@root.fr");

        adminService.creerOrganisateur(organisateur);
        // Création d'une infrastructure sportive
        InfrastructureSportive stadeOlympique = new InfrastructureSportive();
        stadeOlympique.setNom("Stade Olympique");
        stadeOlympique.setAdresse("1234 Rue Olympique");
        stadeOlympique.setCapacite(10000);

        InfrastructureSportive stadeToulousain = new InfrastructureSportive();
        stadeToulousain.setNom("Stade Toulousain");
        stadeToulousain.setAdresse("1234 Rue Olympique");
        stadeToulousain.setCapacite(5000);

        adminService.creerInfrastructureSportive(stadeOlympique);
        adminService.creerInfrastructureSportive(stadeToulousain);

        /*
         *
         * Organisateur part 1
         *
         */

        // Création d'une épreuve
        EpreuveDTO epreuveSprint300m = new EpreuveDTO();
        epreuveSprint300m.setNom("300m sprint");
        epreuveSprint300m.setPrix(135.5);
        epreuveSprint300m.setNbBillets(1500);
        epreuveSprint300m.setDate(LocalDate.of(2024, 07, 7));
        epreuveSprint300m.setNbDelegations(15);
        epreuveSprint300m.setIdInfrastructure(stadeToulousain.getId());

        EpreuveDTO epreuveSprint100m = new EpreuveDTO();
        epreuveSprint100m.setNom("100m sprint");
        epreuveSprint100m.setPrix(115.5);
        epreuveSprint100m.setNbBillets(1000);
        epreuveSprint100m.setDate(LocalDate.of(2024, 07, 5));
        epreuveSprint100m.setNbDelegations(10);
        epreuveSprint100m.setIdInfrastructure(stadeOlympique.getId());
        
        EpreuveDTO epreuveNageC100m = new EpreuveDTO();
        epreuveNageC100m.setNom("natation 100m crolle");
        epreuveNageC100m.setPrix(115.5);
        epreuveNageC100m.setNbBillets(3000);
        epreuveNageC100m.setDate(LocalDate.of(2024, 07, 7));
        epreuveNageC100m.setNbDelegations(10);
        epreuveNageC100m.setIdInfrastructure(stadeOlympique.getId());

        Epreuve epreuve = organisateurService.creerEpreuve(epreuveSprint300m);
        organisateurService.creerEpreuve(epreuveNageC100m);
        organisateurService.creerEpreuve(epreuveSprint100m);

        Participant Jimmy = new Participant();
        Jimmy.setPrenom("Jimmy");
        Jimmy.setNom("Bellier");
        Jimmy.setEmail("jimmy@bellier.fr");

        Participant Quentin = new Participant();
        Quentin.setPrenom("Quentin");
        Quentin.setNom("Joly");
        Quentin.setEmail("quentin.joly@todo.fr");

        Participant Jose = new Participant();
        Jose.setPrenom("Jose");
        Jose.setNom("Garcia");
        Jose.setEmail("jose.garcia@studi.fr");

        organisateurService.creerParticipant(Jimmy);
        organisateurService.creerParticipant(Quentin);
        organisateurService.creerParticipant(Jose);

        Delegation france = new Delegation();
        france.setNom("France");
        Delegation usa = new Delegation();
        usa.setNom("USA");

        // Création d'un participant
        organisateurService.creerDelegation(france);
        organisateurService.creerDelegation(usa);
        organisateurService.setDelegation("jimmy@bellier.fr", 2);
        organisateurService.setDelegation("quentin.joly@todo.fr", 2);
        organisateurService.setDelegation("jose.garcia@studi.fr", 1);

        // Mettre en place le nombre de billets
        organisateurService.setNbBillets(epreuve.getId(), 30);

        organisateurService.setNbParticipants(epreuve.getId(), 450);

        // Création d'un contrôleur
        Controleur c = new Controleur();
        c.setPrenom("Patrick");
        c.setNom("Lemoine");
        c.setEmail("patrick.lemoine@controle.fr");

        organisateurService.creerControleur(c);

        /*
         *
         * Spectateur part
         *
         */

        // Use case: Il peut s’inscrire sur l’application

        Spectateur Lana = new Spectateur();
		Lana.setPrenom("Lana");
		Lana.setNom("Emia");
		Lana.setEmail("lana.emia@yahoo.fr");

		Spectateur Bernard = new Spectateur();
		Bernard.setPrenom("Bernard");
		Bernard.setNom("Bouvier");
		Bernard.setEmail("bernard.bouvier@yahoo.fr");

		Spectateur Alma = new Spectateur();
		Alma.setPrenom("Alma");
		Alma.setNom("Radouaine");
		Alma.setEmail("alma.radouaine@yahoo.fr");

		spectateurService.inscription(Lana);
        spectateurService.inscription(Bernard);
        spectateurService.inscription(Alma);

        // Use case: Consulter le programme des épreuves
        System.out.println("Programme : " + spectateurService.consulterProgramme());

        // Use case: Supprimer son compte
        spectateurService.supprimerCompte(Lana.getEmail());

        // Use case: Réserver des billets pour assister aux épreuves
        spectateurService.reserverBillet(epreuve.getId(), Bernard.getEmail());
        spectateurService.reserverBillet(epreuve.getId(), Bernard.getEmail());

        // Use case: payer en ligne
        spectateurService.payerBillet(1, Bernard.getEmail());

        //Use case: Annuler une réservation
        spectateurService.annulerReservation(2, Bernard.getEmail());

        /*
         *
         * Participant part
         *
         */

        // Use case: Consulter les épreuves disponibles
        System.out.println("Liste des épreuves : " + participantService.consulterEpreuveDisponible());

        // Use case: S'inscrire à des épreuves au nom de sa délégation
        participantService.inscrireEpreuve(Jose.getEmail(), epreuve.getId());
        participantService.inscrireEpreuve(Jimmy.getEmail(), epreuve.getId());
        participantService.inscrireEpreuve(Quentin.getEmail(), epreuve.getId());

        participantService.desengagerEpreuve(Jose.getEmail(), epreuve.getId());

        // Use case: consulter ses résultats
        System.out.println("Résultat du participant Jimmy : " + participantService.consulterResultatsParticipant(Jimmy.getEmail()));

        // Use case: classement de sa délégation
        System.out.println("Résultat de la délégation de Jimmy : " + participantService.consulterResultatsParDelegation(Jimmy.getEmail()));

        /*
         *
         * Contrôleur part
         *
         */

        controleurService.verifierBillet(1);

        /*
         *
         * Organisateur part 2
         *
         */

        ResultatDTO resultat = new ResultatDTO();
        resultat.setPosition(1);
        resultat.setPoint(12);
        resultat.setIdEpreuve(epreuve.getId());
        resultat.setEmailParticipant("jose.garcia@studi.fr");
        organisateurService.setResultat(resultat);

        System.out.println("Total des ventes : " + organisateurService.getTotalVentes());
        System.out.println("Total des places disponibles : " + organisateurService.getTotalPlacesDisponibles());
        System.out.println("Chiffre d'affaires : " + organisateurService.getChiffreAffaires());

        System.err.println("Server is running!");

    }
}
