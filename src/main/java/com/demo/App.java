package com.demo;

/**
 * Hello world!
 *
 */
import com.demo.entites.Salle;
import com.demo.entites.Utilisateur;
import com.demo.service.SalleService;
import com.demo.service.UtilisateurService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-salles");

        UtilisateurService utilisateurService = new UtilisateurService(emf);
        SalleService salleService = new SalleService(emf);

        try {
            System.out.println("\n=== Test CRUD Utilisateur ===");
            testCrudUtilisateur(utilisateurService);

            System.out.println("\n=== Test CRUD Salle ===");
            testCrudSalle(salleService);

        } finally {
            emf.close();
        }
    }

    private static void testCrudUtilisateur(UtilisateurService service) {
        // Création
        System.out.println("Création d'utilisateurs...");
        Utilisateur u1 = new Utilisateur("Benali", "Karim", "karim.benali@gmail.com");
        u1.setDateNaissance(LocalDate.of(1992, 3, 8));
        u1.setTelephone("+212661234567");

        Utilisateur u2 = new Utilisateur("Alaoui", "Salma", "salma.alaoui@gmail.com");
        u2.setDateNaissance(LocalDate.of(1988, 11, 25));
        u2.setTelephone("+212670998877");

        Utilisateur u3 = new Utilisateur("Lefevre", "Hugo", "hugo.lefevre@gmail.com");
        u3.setDateNaissance(LocalDate.of(1995, 7, 2));
        u3.setTelephone("+33655443322");

        service.save(u1);
        service.save(u2);
        service.save(u3);

        // Lecture
        System.out.println("\nLecture de tous les utilisateurs :");
        service.findAll().forEach(System.out::println);

        System.out.println("\nRecherche d'un utilisateur par ID :");
        Optional<Utilisateur> utilisateurOpt = service.findById(2L);
        utilisateurOpt.ifPresent(System.out::println);

        System.out.println("\nRecherche d'un utilisateur par email :");
        Optional<Utilisateur> utilisateurParEmail = service.findByEmail("hugo.lefevre@gmail.com");
        utilisateurParEmail.ifPresent(System.out::println);

        // Mise à jour
        System.out.println("\nMise à jour d'un utilisateur :");
        utilisateurOpt.ifPresent(utilisateur -> {
            utilisateur.setTelephone("+212600112233");
            service.update(utilisateur);
            System.out.println("Utilisateur mis à jour : " + utilisateur);
        });

        // Suppression
        System.out.println("\nSuppression d'un utilisateur :");
        service.deleteById(3L);
        System.out.println("Utilisateur avec ID=3 supprimé");

        System.out.println("\nListe des utilisateurs après suppression :");
        service.findAll().forEach(System.out::println);
    }

    private static void testCrudSalle(SalleService service) {
        // Création
        System.out.println("Création de salles...");
        Salle s1 = new Salle("Laboratoire L12", 25);
        s1.setDescription("Laboratoire informatique avec 25 postes");
        s1.setEtage(0);

        Salle s2 = new Salle("Salle de conférence D400", 80);
        s2.setDescription("Salle équipée pour la visioconférence");
        s2.setEtage(4);

        Salle s3 = new Salle("Salle de réunion E110", 12);
        s3.setDescription("Salle de réunion avec tableau interactif");
        s3.setEtage(1);
        s3.setDisponible(false);

        Salle s4 = new Salle("Grand Auditorium F001", 300);
        s4.setDescription("Auditorium principal pour les cérémonies");
        s4.setEtage(0);

        service.save(s1);
        service.save(s2);
        service.save(s3);
        service.save(s4);

        // Lecture
        System.out.println("\nLecture de toutes les salles :");
        service.findAll().forEach(System.out::println);

        System.out.println("\nRecherche d'une salle par ID :");
        Optional<Salle> salleOpt = service.findById(1L);
        salleOpt.ifPresent(System.out::println);

        System.out.println("\nRecherche des salles non disponibles :");
        service.findByDisponible(false).forEach(System.out::println);

        System.out.println("\nRecherche des salles avec capacité minimum de 100 :");
        service.findByCapaciteMinimum(100).forEach(System.out::println);

        // Mise à jour
        System.out.println("\nMise à jour d'une salle :");
        salleOpt.ifPresent(salle -> {
            salle.setCapacite(40);
            salle.setDescription("Laboratoire informatique rénové avec 40 postes");
            service.update(salle);
            System.out.println("Salle mise à jour : " + salle);
        });

        // Suppression
        System.out.println("\nSuppression d'une salle :");
        service.deleteById(4L);
        System.out.println("Salle avec ID=4 supprimée");

        System.out.println("\nListe des salles après suppression :");
        service.findAll().forEach(System.out::println);
    }
}