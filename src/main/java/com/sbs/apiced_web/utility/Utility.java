/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.utility;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Paiement;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Usersnotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.TypeNotifManager;
import com.sbs.apiced_web.services.UsersNotifManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samuel
 */
public class Utility {

    private static int numInterneMaitre;
    private static String matricule;
    private static Maitrecommunautaire mc = new Maitrecommunautaire();
    private Auditlog log = new Auditlog();
    private Notifications notif = new Notifications();
    private Typenotifs typeNotification = new Typenotifs();
    private Utilisateur userCo = new Utilisateur();
    private List<Utilisateur> listeUsers = new ArrayList<>();

    /**
     * EJB UTILISES
     *
     * @return
     */
    @EJB
    private static MaitreCoManager mcMgr;
    @EJB
    private AuditlogManager auditMgr;
    @EJB
    private TypeNotifManager typeNotifMgr;
    @EJB
    private NotifsManager notifMgr;
    @EJB
    private UtilisateurManager utilisateurMgr;
    @EJB
    private UsersNotifManager userNotifMgr;

    public Typenotifs getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(Typenotifs typeNotification) {
        this.typeNotification = typeNotification;
    }

    public Utilisateur getUserCo() {
        return userCo;
    }

    public void setUserCo(Utilisateur userCo) {
        this.userCo = userCo;
    }

    public TypeNotifManager getTypeNotifMgr() {
        return typeNotifMgr;
    }

    public void setTypeNotifMgr(TypeNotifManager typeNotifMgr) {
        this.typeNotifMgr = typeNotifMgr;
    }

    public static int getNumInterneMaitre() {
        return numInterneMaitre;
    }

    public static void setNumInterneMaitre(int numInterneMaitre) {
        Utility.numInterneMaitre = numInterneMaitre;
    }

    public String getMatricule() {
        return matricule;
    }

    public Maitrecommunautaire getMc() {
        return mc;
    }

    public Utility() {
        numInterneMaitre++;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        userCo = (Utilisateur) session.getAttribute("utilisateurConnecte");
    }

    public static MaitreCoManager getMcMgr() {
        return mcMgr;
    }

    public static void setMcMgr(MaitreCoManager mcMgr) {
        Utility.mcMgr = mcMgr;
    }

    public List<Utilisateur> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(List<Utilisateur> listeUsers) {
        this.listeUsers = listeUsers;
    }

    public AuditlogManager getAuditMgr() {
        return auditMgr;
    }

    public void setAuditMgr(AuditlogManager auditMgr) {
        this.auditMgr = auditMgr;
    }

    public NotifsManager getNotifMgr() {
        return notifMgr;
    }

    public void setNotifMgr(NotifsManager notifMgr) {
        this.notifMgr = notifMgr;
    }

    public UtilisateurManager getUtilisateurMgr() {
        return utilisateurMgr;
    }

    public void setUtilisateurMgr(UtilisateurManager utilisateurMgr) {
        this.utilisateurMgr = utilisateurMgr;
    }

    public UsersNotifManager getUserNotifMgr() {
        return userNotifMgr;
    }

    public void setUserNotifMgr(UsersNotifManager userNotifMgr) {
        this.userNotifMgr = userNotifMgr;
    }

    /*
     * Methode de Bourrage à Gauche de Zero par defaut : provient de webclring
     */
    static String bourrageGZero() {
//        if (chaine == null) {
//            chaine = "";
//        }
        String chaine = String.valueOf(numInterneMaitre);
        chaine = chaine.trim();
        while (chaine.length() < 10) {
            chaine = "0" + chaine;
        }
        return chaine;
    }

    /**
     * generation des matricules des MCS
     */
    public static void generationMatricule() {

        String prefixMC = "MC";
        //dernier ID 
        BigDecimal x = (BigDecimal) mcMgr.getLastMcId();
        Integer i = x.intValue();
        System.out.println("la valeur de son id   " + ++i);
        String lastMcId = i.toString();
        //initiaux Bailleur en fonction du choix 
        String initalBailleur;
        if (mc.getBailleur().equalsIgnoreCase("PARSET2")) {
            initalBailleur = "PRST";
        } else {
            initalBailleur = "PREA";
        }

        //recup du genre choisi
        String prefixGenre = mc.getGenre();
        String genre;
        if (prefixGenre.equalsIgnoreCase("M")) {
            genre = "M";
        } else {
            genre = "F";
        }
        //System.out.println("le genre du mc  :" + prefixGenre);
        matricule = prefixMC + initalBailleur + lastMcId + genre;
    }

    //sauvegarde d'un log 
//    public void saveLog(String msg, Utilisateur userConnecte) {
//        System.out.println("msg du log " + msg);
//        System.out.println("la date " + DateOfDay());
//        System.out.println("utilisateur connecte  " + userConnecte.getLogin());
//
//        log.setAuteurIdutilisateur(userConnecte);
//        log.setLogin(userConnecte.getLogin());
//        log.setAction(msg + " par l'utilisateur " + userConnecte.getLogin());
//        log.setDateaction(DateOfDay());
//        auditMgr.persist(log);
//    }

//    //sauvegarde d'une notification
//    public void savePaieNotif(String libelleNotif, String details, Integer idType, Paiement p, List<Utilisateur> userList) {
//        System.out.println("libelle  " + libelleNotif);
//        System.out.println("details  " + details);
//        System.out.println("idtype  " + idType);
//        System.out.println("paiement  " + p.getLibelle());
//        System.out.println("nbre de user pour notifs  " + userList.size());
//
//        BigDecimal typn = BigDecimal.valueOf(idType);
//        typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
//        notif.setDateresolution(DateOfDay());
//        notif.setLibelle(libelleNotif);
//        notif.setDetails(details);
//        notif.setDatecreation(DateOfDay());
//        notif.setEtat(BigInteger.ZERO);
//        notif.setTypenotif(typeNotification);
//        notif.setCreateur(userCo);
//        notif.setIdinfo(p.getIdpaiement().toString());
//        notifMgr.persist(notif);
//
//        listeUsers = utilisateurMgr.getAllActivedUsers();
//        //je recupère la dernière notif créée pour le setting a venir 
//        Integer lastNotifId = notifMgr.lastNotif();
//        //creation des btns 
//        for (Utilisateur u : listeUsers) {
//            Usersnotifs userNotif = new Usersnotifs();
//            userNotif.setDateinsert(DateOfDay());
//            userNotif.setEtat(BigInteger.ZERO);
//            userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
//            userNotif.setTitre(libelleNotif);
//            userNotif.setInformation(details);
//            userNotif.setCreateur(userCo.getLogin());
//            userNotif.setTypeusernotif("VALIDATION_PAIE_SUBSIDES");
//            //construction des btn en fonction des profils
//            if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
//                userNotif.setBtnvalidemc("false");
//                userNotif.setBtnvalidepaie("false");
//                userNotif.setBtndetail("true");
//            } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
//                userNotif.setBtnvalidemc("false");
//                userNotif.setBtnvalidepaie("true");
//                userNotif.setBtndetail("false");
//            } else {
//                userNotif.setBtnvalidemc("false");
//                userNotif.setBtnvalidepaie("false");
//                userNotif.setBtndetail("true");
//            }
//            //setter l'id du notif
//            userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
//            //on persist la notifUser pr finir
//            userNotifMgr.persist(userNotif);
//        }
//    }

    //date de journee en cours 
    public String DateOfDay() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String date = dt.format(dtFormat);
        return date;
    }

}
