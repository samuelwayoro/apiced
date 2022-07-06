/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.ParamManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.omnifaces.util.Faces;

/**
 *
 * @author samuel
 */
@Named(value = "loginMBean")
@SessionScoped
public class LoginMBean implements Serializable {

    private String lien;
    private String login;
    private String motDePasse;
    private Utilisateur utilisateur;
    private Boolean utilisateurEstConnecte;
    private boolean connected;
    @ManagedProperty("#{navigationBean}")
    private NavigationBean navigationManager;
    @EJB
    private UtilisateurManager utilisateurManager;
    @EJB
    private AuditlogManager audit;
    @EJB
    private ParamManager param;

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * Creates a new instance of LoginMBean
     */
    public LoginMBean() {
    }

    public Boolean getUtilisateurEstConnecte() {
        return utilisateurEstConnecte;
    }

    public void setUtilisateurEstConnecte(Boolean utilisateurEstConnecte) {
        this.utilisateurEstConnecte = utilisateurEstConnecte;
    }

    public UtilisateurManager getUtilisateurManager() {
        return utilisateurManager;
    }

    public void setUtilisateurManager(UtilisateurManager utilisateurManager) {
        this.utilisateurManager = utilisateurManager;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    //lien vers la page d'accueil
    public String pageAccueil(String lien) {
        if (("template").equals(lien)) {
            return "template";
        }
        return "login";
    }

    //connexion utilisateur
    public String connexionUtilisateur() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        //System.out.println("entree dans la fonction login avec les parametres : " + this.login + " " + this.motDePasse);
        String retour;
        utilisateur = utilisateurManager.findByUserLogin(this.login);
        if (utilisateur != null) {
            System.out.println("login = " + this.login + "  mot de passe  :" + this.motDePasse);
            //DigestUtils.shaHex(this.motDePasse))  
            if ((utilisateur.getLogin().equals(this.login)) && (utilisateur.getMotdepass().equals(DigestUtils.shaHex(this.motDePasse)))) {
                //System.out.println("le mot de passe est egal a celui dans la bd ");
                connected = true;
                //utilisateur.setEtatconnexion("1");
                //utilisateurManager.majEtatConnexion(utilisateur);
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("utilisateurEstConnecte", connected);
                session.setAttribute("utilisateur", utilisateur.getLogin());
                session.setAttribute("utilisateurConnecte", utilisateur);
                //initialisation du profil de l'utilisateur pour les liens de menu 
                session.setAttribute("profilUtilisateur", utilisateur.getProfilIdprofil());
                //System.out.println("l'utilisateur est connecte :  " + session.getAttribute("utilisateurEstConnecte"));
                //System.out.println("le profil de l'utilisateur est : " + utilisateur.getProfilIdprofil().getNom() + "    et son accees a la page d'accueil est  :" + utilisateur.getProfilIdprofil().getAccesaccueil());
                //enregistrement dans la table des log 
                Auditlog log = new Auditlog();
                log.setAuteurIdutilisateur(utilisateur);
                log.setLogin(utilisateur.getLogin());
                log.setAction("connection a l'application du user : " + utilisateur.getLogin());
                //System.out.println("date et heure :" + dt.format(datePattern));
                log.setDateaction(dt.format(datePattern));
                audit.persist(log);
                System.out.println("le libelle du profil du user est : "+utilisateur.getProfilIdprofil().getLibelle());
                //retour = "private/accueil.xhtml?faces-redirect=true";                
                //personnalisation de la page d'accueil 
                if (utilisateur.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                    retour = "private/accueilEmetteur.xhtml?faces-redirect=true";
                } else if (utilisateur.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                    System.out.println("je suis bien un coordonnateur");
                    retour = "private/accueilCoordonnatteur.xhtml?faces-redirect=true";
                }else if(utilisateur.getProfilIdprofil().getLibelle().equalsIgnoreCase("partenaire")){
                    retour = "private/accueilPartenaire.xhtml?faces-redirect=true";
                }
                else {//si il est utilisateur partenaire
                    retour = "private/accueil.xhtml?faces-redirect=true";
                }
            } else {
                retour = null;
                FacesMessage msg = new FacesMessage("erreur de connexion , login ou mot de passe incorrects ");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                System.out.println("login ou mdp incorrects");
            }
        } else {
            //retour = "login";
            retour = null;
            FacesMessage msg = new FacesMessage("erreur de connexion , login ou mot de passe incorrects ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            //System.out.println("cet utilisateur n'existe pas en bd");
        }
        return retour;
    }

    //deconnexion utilisateur 
    public void deconnexion() {
        //utilisateur.setEtatconnexion("0");
        //flag l'etat connexion a 0 en bd 
        //utilisateurManager.majEtatConnexion(utilisateur);
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        System.out.println("déconnexion de l'utilisateur : " + session.getAttribute("utilisateur"));
        //destruction de la session en cours
        session.invalidate();

        //enregistrement dans la table des log 
        Auditlog log = new Auditlog();
        log.setAuteurIdutilisateur(utilisateur);
        log.setLogin(utilisateur.getLogin());
        log.setAction("déconnection de l'application de l'utilisateur " + utilisateur.getLogin());
        System.out.println("afficha de l'adresse ip de l'utilisateur : " + Faces.getRemoteAddr());
        //System.out.println("date et heure :"+dt.format(datePattern));
        log.setDateaction(dt.format(datePattern));
        audit.persist(log);

        //redirection du user a la page de connexion
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
        } catch (IOException e) {
            Logger.getLogger(LoginMBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
