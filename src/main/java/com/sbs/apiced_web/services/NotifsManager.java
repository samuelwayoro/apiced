/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Usersnotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author samuel
 */
@Stateless
public class NotifsManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;

    /**
     * methode de persistence d'une nouvelle notification
     *
     * @param notif
     */
    public void persist(Notifications notif) {
        try {
            //System.out.println("entree dans la methode persist de notificationManager");
            em.persist(notif);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(actual -> {
                System.out.println(actual.toString());
            });
        }
    }
    
    /**
     * retourner la dernière notification
     * @return 
     */
    public Integer lastNotif(){
        Query q = em.createQuery("SELECT MAX (n.idnotif) FROM Notifications n");
        return (Integer) q.getSingleResult();
    }

    /**
     * liste des notifications d'un utilisateur donné
     * @param userIdConnecte
     * @return
     */
    public List<Usersnotifs> oneUserListeNotifications(BigInteger userIdConnecte) {
        BigInteger etatZero = BigInteger.ZERO;
        Query q = em.createQuery("SELECT u FROM Usersnotifs u WHERE u.idutilisateur = :idUtilisateur AND u.etat = :etat ORDER BY u.id DESC ");
        q.setParameter("idUtilisateur", userIdConnecte);
        q.setParameter("etat", etatZero);
        return q.getResultList();
    }

    /**
     * *
     * renvoi le nbre de notification pour un utilisateur a afficher dans
     * l'icone en haut a droite (en rouge)
     *
     * @param userId
     * @param etatNotif
     * @return
     */
    public Long nbreNotifsEnAttente(BigInteger userId, Integer etatNotif) {
        //System.out.println("nbre des notifs en attente");
        Query q = em.createQuery("SELECT COUNT(u) FROM Usersnotifs u WHERE u.idutilisateur = :idUser and u.etat = :etatNotif ");
        q.setParameter("idUser", userId);
        q.setParameter("etatNotif", etatNotif);
        return (Long) q.getSingleResult();
    }
    
    /**
     * nbre de paiements en attente de validation par le coordo
     * @return 
     */
    public Long nbrePaiementsEnAttenteDeValidation(){
        Boolean etatp =  Boolean.FALSE ;
        Query q = em.createQuery("SELECT COUNT(p) FROM Paiement p WHERE p.validationcoordonnateur = :etat");
        q.setParameter("etat", etatp);
        return (Long) q.getSingleResult();
    }

    public List<Notifications> notificationsCoordoCreaMaitre(Typenotifs t) {
        int etatnotif = 0;
        Query q = em.createQuery("SELECT n FROM Notifications n WHERE n.etat = :etatNotif and n.typenotif = :typeNotif ");
        q.setParameter("etatNotif", etatnotif);
        q.setParameter("typeNotif", t);
        return q.getResultList();
    }

    //total des notifs du coordonnateur
    public Long nbreNotifsCoordonnateur() {
        Query q = em.createQuery("SELECT COUNT(n) FROM Notifications n WHERE n.etat = :etatNotif ");
        int etatnotif = 0;
        q.setParameter("etatNotif", etatnotif);
        return (Long) q.getSingleResult();
    }

    //nbre de notfis de creation de maitres 
    public Long nbreNotifsCoordoCreaMaitre(Typenotifs t) {
        Query q = em.createQuery("SELECT COUNT(n) FROM Notifications n WHERE n.etat = :etat and n.typenotif = :typeNotif");
        int etat = 0;
        q.setParameter("etat", etat);
        q.setParameter("typeNotif", t);

        return (Long) q.getSingleResult();
    }

    //nbre de notifs de creation de paiement 
    public Long nbreNotifsCoordoCreaPaiement(Typenotifs t) {
        Query q = em.createQuery("SELECT COUNT(n) FROM Notifications n WHERE n.etat =:etat and n.typenotif = :typen");
        int etat = 0;
        q.setParameter("etat", etat);
        q.setParameter("typen", t);
        return (Long) q.getSingleResult();

    }

    public List<Notifications> notifCoordoCreaPaie(Typenotifs type) {
        int etatnotif = 0;
        Query q = em.createQuery("SELECT n FROM Notifications n WHERE n.etat = :etatNotif and n.typenotif = :typeNotif ");
        q.setParameter("etatNotif", etatnotif);
        q.setParameter("typeNotif", type);
        return q.getResultList();
    }

    public Long nbreMcEnAttente(Boolean i) {
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.etatcomptemc = :i");
        q.setParameter("i", i);
        return (Long) q.getSingleResult();
    }
    
    //retourne le nbre de mc en attente de validation par le coordo
    public Long nbreMcEnAttenteValidation(){
        Boolean etat = Boolean.FALSE;
        Boolean validation = Boolean.FALSE;
        Query q = em.createQuery("SELECT COUNT (m) FROM Maitrecommunautaire m WHERE m.etatcomptemc = :etatNonOk and m.validationcoordonnateur = :validation");
        q.setParameter("etatNonOk", etat);
        q.setParameter("validation", validation);
        return (Long) q.getSingleResult();
    }
    
    //retourne le nombre de maitre communautaire créer aujourdhui 
    public Long nbreMcCreerToday(){
        String theDate = DateOfDay();
        Query q = em.createQuery("SELECT COUNT (m) FROM Maitrecommunautaire m WHERE m.datecreationcompte = :date ");
        q.setParameter("date", theDate);
        return (Long) q.getSingleResult();
    }

    public Long qtesPaiementEmisParEmetteur(Utilisateur userCo, Boolean etatValidation) {
        Query q = em.createQuery("SELECT COUNT(p) FROM Paiement p WHERE p.validationcoordonnateur = :etat and p.emeteur = :user");
        q.setParameter("etat", etatValidation);
        q.setParameter("user", userCo);
        return (Long) q.getSingleResult();
    }

    public Long totalPaiement() {
        Boolean et = Boolean.TRUE;
        String theDate = DateOfDay();
        Query q = em.createQuery("SELECT COUNT(p) FROM Paiement p where p.validationcoordonnateur = :etatValide and p.datevalidationcoordo = :dateV  ");
        q.setParameter("etatValide", et);
        q.setParameter("dateV", theDate);
        return (Long) q.getSingleResult();
    }

    public Long nbreTotalMc() {
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m");
        return (Long) q.getSingleResult();
    }

    public Long qtesPaiementsEnAttenteDeCoordo() {
        Boolean etatValidation = Boolean.FALSE;
        Query q = em.createQuery("SELECT COUNT(p) FROM Paiement p WHERE p.validationcoordonnateur = :etat");
        q.setParameter("etat", etatValidation);
        return (Long) q.getSingleResult();
    }

    public List<Notifications> listeDmdPaieEnAttente(Typenotifs t) {
        Query q = em.createQuery("SELECT n FROM Notifications n WHERE n.typenotif = :type and n.etat = :etatAtente");
        q.setParameter("type", t);
        q.setParameter("etatAtente", 0);
        return q.getResultList();
    }

    /**
     *
     * @param t
     * @return
     */
    public Long nbreDmdPaieEnAttente(Typenotifs t) {
        Query q = em.createQuery("SELECT COUNT(n) FROM Notifications n WHERE n.etat = :etat and n.typenotif = :type");
        q.setParameter("etat", 0);
        q.setParameter("type", t);
        return (Long) q.getSingleResult();
    }

    public Long nbreTotalMcValides(Typenotifs idtypeNotif) {
        Query q = em.createQuery("SELECT COUNT(n) FROM Notifications n WHERE n.etat = :etat and n.typenotif = :type");
        q.setParameter("etat", 1);
        q.setParameter("type", idtypeNotif);
        return (Long) q.getSingleResult();
    }

    public Long nbreDmdCreaMcEnAttente(Typenotifs notifCreaMc) {
        Query q = em.createQuery("SELECT COUNT(n) FROM Notifications n WHERE n.etat = :etat and n.typenotif = :type");
        q.setParameter("etat", 0);
        q.setParameter("type", notifCreaMc);
        return (Long) q.getSingleResult();
    }

    /**
     * nbre total des paiements validés par le coordo
     * @return 
     */
    public Long nbreTotalPaieValides() {
        Query q = em.createQuery("SELECT COUNT(p) FROM Paiement p WHERE p.validationcoordonnateur = :etat  ");
        q.setParameter("etat", Boolean.TRUE);
        return (Long) q.getSingleResult();
    }

    public Long qtesPaiementEnAttenteDeValidation() {
      Query q = em.createQuery("SELECT COUNT(p) FROM Paiement p WHERE p.validationcoordonnateur = :etat  ");
        q.setParameter("etat", Boolean.FALSE);
        return (Long) q.getSingleResult();
    }
    
        public Long qtesPaiementAEnvoyer() {
      Query q = em.createQuery("SELECT COUNT(p) FROM Paiement p WHERE p.validationcoordonnateur = :etat AND p.etatenvoiop = :etatOp ");
        q.setParameter("etat", Boolean.FALSE);
        q.setParameter("etatOp", Boolean.TRUE);
        return (Long) q.getSingleResult();
    }

    public Long totalMcEnAttenteDeValidation() {
        Boolean validation = Boolean.FALSE;
        //String etat = "actif";
                Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.validationcoordonnateur = :validation");
                q.setParameter("validation", validation);
                //q.setParameter("validation", validation);
        return (Long) q.getSingleResult();
    }

    public Long qtesPaiementsEnAttenteDenvoi() {
        Boolean etatValidation = Boolean.TRUE;
        Boolean etatEnvoiOp = Boolean.FALSE;
        Query q = em.createQuery("SELECT COUNT(p) FROM Paiement p WHERE p.validationcoordonnateur = :etatV and p.etatenvoiop = :etatEnv");
        q.setParameter("etatV", etatValidation);
        q.setParameter("etatEnv", etatEnvoiOp);
        return (Long) q.getSingleResult();
    }
    
        public String DateOfDay() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String date = dt.format(dtFormat);
        return date;
    }

}
