/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Profil;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.ProfilsManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.PrimeFaces;

/**
 *
 * @author samuel
 */
@Named(value = "usersMBean")
@ViewScoped
public class UsersMBean implements Serializable {

    private String nomUtilisateur;
    private String prenomsUtilisateur;
    private String motDePass;
    private String confirmation;
    private List<Profil> listeProfils;
    private Profil profilUtilisateur;
    private String loginUtilisateur;
    private String emailUtilisateur;
    private Utilisateur user;
    private String dateCreaUtilisateur;
    private String dateModification;
    private String dateDesactivation;
    private String dateActivation;
    private List<String> listeNomsProfils;
    private List<Utilisateur> listeUtilisateurs;
    private Utilisateur selectedUser;
    private String nomUser;
    private Integer idUtilisateur;
    private Boolean compteActif;
    private Boolean defaultUserState = Boolean.FALSE;
    private Profil profilPrMenu;
    private Utilisateur theUser;
    @EJB
    private UtilisateurManager utilisateurManager;
    @EJB
    private UtilisateurManager userMgr;
    @EJB
    private ProfilsManager profilManager;
    @EJB
    private AuditlogManager audit;

    private Converter profilConverter = new Converter() {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            System.out.println("value "+value);
           // return profilManager.getProfilByName(value);
           return profilManager.findTheProfilById(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            //System.out.println("value :"+value.toString());
            return ((Profil) value).getIdprofil().toString();
        }
    };

    public Profil getProfilUtilisateur() {
        return profilUtilisateur;
    }

    public void setProfilUtilisateur(Profil profilUtilisateur) {
        this.profilUtilisateur = profilUtilisateur;
    }

    
    
    public Profil getProfilPrMenu() {
        return profilPrMenu;
    }

    public void setProfilPrMenu(Profil profilPrMenu) {
        this.profilPrMenu = profilPrMenu;
    }

    public Utilisateur getTheUser() {
        return theUser;
    }

    public void setTheUser(Utilisateur theUser) {
        this.theUser = theUser;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public UtilisateurManager getUserMgr() {
        return userMgr;
    }

    public void setUserMgr(UtilisateurManager userMgr) {
        this.userMgr = userMgr;
    }

    public String getDateCreaUtilisateur() {
        return dateCreaUtilisateur;
    }

    public void setDateCreaUtilisateur(String dateCreaUtilisateur) {
        this.dateCreaUtilisateur = dateCreaUtilisateur;
    }

    public Converter getProfilConverter() {
        return profilConverter;
    }

    public void setProfilConverter(Converter profilConverter) {
        this.profilConverter = profilConverter;
    }

    public Utilisateur getUser() {
        return user;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public String getLoginUtilisateur() {
        return loginUtilisateur;
    }

    public void setLoginUtilisateur(String loginUtilisateur) {
        this.loginUtilisateur = loginUtilisateur;
    }

    public List<Utilisateur> getListeUtilisateurs() {
        return listeUtilisateurs;
    }

    public void setListeUtilisateurs(List<Utilisateur> listeUtilisateurs) {
        this.listeUtilisateurs = listeUtilisateurs;
    }

    /**
     * Creates a new instance of UsersMBean
     */
    public UsersMBean() {
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomsUtilisateur() {
        return prenomsUtilisateur;
    }

    public void setPrenomsUtilisateur(String prenomsUtilisateur) {
        this.prenomsUtilisateur = prenomsUtilisateur;
    }

    public String getMotDePass() {
        return motDePass;
    }

    public void setMotDePass(String motDePass) {
        this.motDePass = motDePass;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

//    public String getProfilUtilisateur() {
//        return profilUtilisateur;
//    }
//
//    public void setProfilUtilisateur(String profilUtilisateur) {
//        this.profilUtilisateur = profilUtilisateur;
//    }

    public Utilisateur getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Utilisateur selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getDateModification() {
        return dateModification;
    }

    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }

    public String getDateDesactivation() {
        return dateDesactivation;
    }

    public void setDateDesactivation(String dateDesactivation) {
        this.dateDesactivation = dateDesactivation;
    }

    public String getDateActivation() {
        return dateActivation;
    }

    public void setDateActivation(String dateActivation) {
        this.dateActivation = dateActivation;
    }

    public Boolean getCompteActif() {
        return compteActif;
    }

    public void setCompteActif(Boolean compteActif) {
        this.compteActif = compteActif;
    }

    //retourne la liste de tous les profils
    public List<Profil> getProfils() {
        if (listeProfils == null) {
            listeProfils = profilManager.getAllProfils();
        }
        return listeProfils;
    }

    @PostConstruct
    public void init() {
        // recuperation du user en session 
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        String loginUserCo = session.getAttribute("utilisateur").toString();
        System.out.println("le login du user connecte : " + loginUserCo);
        this.listeUtilisateurs = this.utilisateurManager.getAllUsersWithoutUserCo(loginUserCo);

    }

    //retourne la liste de tous les noms de profils
    public List<String> getListNomsProfils() {
        if (listeNomsProfils == null) {
            listeNomsProfils = profilManager.getAllProfilsName();
        }
        return listeNomsProfils;
    }

    //retouner la liste de tous les profils
    public List<Profil> getListProfils() {
        if (listeProfils == null) {
            listeProfils = profilManager.getAllProfils();
        }
        return listeProfils;
    }

    //redirection a la page de la liste des profils 
    public String retour() {
        System.out.println("je me retourne a la liste des utilisateurs");
        //return "gestionProfils?faces-redirect=true";
        return "gestionUtilisateurs?faces-redirect=true";
    }

    /**
     * methode d'ajout d'un nouvel utilisateur
     */
    public void addNewUtilisateur() {
        theUser = new Utilisateur();
        theUser.setNom(nomUtilisateur);
        theUser.setPrenoms(prenomsUtilisateur);
        theUser.setLogin(loginUtilisateur);
        theUser.setActiver(defaultUserState);
        theUser.setEtatconnexion("0");
        theUser.setEmail(emailUtilisateur);
        //recuperation du profil du user a partir du nom de son profil 
        //Profil profilUser = profilManager.getProfilByName(this.profilUtilisateur);
        //theUser.setProfilIdprofil(profilUser);
        theUser.setProfilIdprofil(this.profilUtilisateur);
        
        theUser.setDatecreation(DateOfDay());

        Utilisateur u = userMgr.verifEmailUser(theUser.getEmail());
        if (u != null) {
            msgExistanceUserEnBd();
            System.out.println("attention cet utilisateur existe deja en bd ");
        } else {
            //generation du mot de passe temporaire
            char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\\\|;:\\'\\\",<.>/?").toCharArray();
            String randomStr = RandomStringUtils.random(8, 0, possibleCharacters.length - 1, false, false, possibleCharacters, new SecureRandom());
            System.out.println("le mot de passe généré est : " + randomStr);
            //theUser.setMotdepass(randomStr);
//        String randomStr = "admin123";
//        utilisation de DigestUtil
            theUser.setMotdepass(DigestUtils.shaHex(randomStr));
            userMgr.persist(theUser);
            listeUtilisateurs.add(theUser);
            //msgSuccesCreationUser();
             FacesMessage msg = new FacesMessage("utilisateur crée avec succès , Login : " + theUser.getLogin() + "  mot de passe : " + randomStr);
             FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        PrimeFaces.current().executeScript("PF('addUserDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
            clearChamps();

    }
    
    /**
     * suppresion d'un profil dans la base
     */
    public void deleteUtilisateur() {
        System.out.println("***********suppression de l'utilisateur qui a le login suivant   :" + selectedUser.getLogin());
        msgSuccesDeleteUser();
            PrimeFaces.current().executeScript("PF('deleteProductDialog').hide()");
            PrimeFaces.current().ajax().update(":form:messages", ":form:dt-products");
            //profilManager.deleteProfil(selectedUser);
           utilisateurManager.deleteUser(selectedUser);
            //profils.remove(leProfil);
           listeUtilisateurs.remove(selectedUser);
            //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
           HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        System.out.println("UTILISATEUR EN SESSION : " + session.getAttribute("utilisateur"));
        String userCo = (String) session.getAttribute("utilisateur");
           saveLog("suppression de l'utilisateur : "+selectedUser.getNom()+" par l'utilisateur : "+userCo, DateOfDay());

    }



    /**
     * methode permettant de faire un update sur un utilisateur selectionné
     */
    public void updateUtilisateur() {
        System.out.println("mise a jour en cours de l'utilisateur ..." + selectedUser.getNom() + " le profil :" + selectedUser.getProfilIdprofil());
        //pro = profilManager.updateProfil(pro);
        //selectedUser = userMgr.updateUtilisateur(selectedUser);
        LocalDateTime dt = LocalDateTime.now();
        //System.out.println("la valeur de la date : "+dt);
        //definition du patterne 
        DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        //affichage avec nouveau format 
        System.out.println("le bon format c'est : " + dt.format(datePattern));
        String dateModif = dt.format(datePattern);
        selectedUser.setDatemodification(dateModif);
        //recuperation de la valeur de activé du user selectionné dans le tableau 
        compteActif = selectedUser.getActiver();
        System.out.println("la valeur de l'activation : " + compteActif);
        //recuperation de la valeur de activé user en bd 
        Boolean statusActifEnBd = utilisateurManager.recupStatuActifUser(selectedUser);
        System.out.println("la valeur de l'activation en base de donees  : " + statusActifEnBd);
        //si la valeur envoyé est activé et que en bd c désactivé je modifi la date activation
        if ((compteActif.equals(Boolean.TRUE)) && (statusActifEnBd.equals(Boolean.FALSE))) {
            selectedUser.setDateactivation(dateModif);
        } else if ((compteActif.equals(Boolean.FALSE)) && (statusActifEnBd.equals(Boolean.TRUE))) {        //sinon je modifie la valeur de la date de desactivation
            selectedUser.setDatedesactivation(dateModif);
        }
        selectedUser.setProfilIdprofil(this.profilUtilisateur);
        //modification du profil au cas ou 
        //recup du profil en fonction du nom selectionné
//        Profil newProfil = utilisateurManager.recupProfil(profilUtilisateur);
  //      selectedUser.setProfilIdprofil(newProfil);

        // recuperation du user en session 
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        System.out.println("loggin de l'action de l'utilisateur : " + session.getAttribute("utilisateur"));
        Utilisateur userCo = (Utilisateur) session.getAttribute("utilisateurConnecte");
        Auditlog log = new Auditlog();
        log.setAuteurIdutilisateur(userCo);
        log.setLogin(userCo.getLogin());
        log.setAction("mise a jour du compte utilisateur  : " + selectedUser.getLogin()+" par l'utilisateur "+userCo.getLogin());
//  System.out.println("date et heure :"+dt.format(datePattern));
        log.setDateaction(dateModif);
        audit.persist(log);
        userMgr.updateUtilisateur(selectedUser);
        //return "gestionProfils";
        msgSuccesModif();
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    //formatage des messages de traitements
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    //message d'erreur de création d'un utilisateur
    public void msgError() {
        addMessage(FacesMessage.SEVERITY_ERROR, "erreur", "erreur de traitement");
    }

    //message de succes de création d'un utilisateur
    public void msgSucces() {
        addMessage(FacesMessage.SEVERITY_INFO, "succès", "traitement exécuté avec succès");
    }

    public void msgExistanceUserEnBd() {
        //  addMessage(FacesMessage.SEVERITY_ERROR, "erreur", "un utilisateur existe déjà avec ce login");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur :veuillez utiliser un autre login", "..."));
    }

    //message de succès de création d'un utilisateur
    public void msgSuccesCreationUser() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'utilisateur à été crée avec succès , veuillez l'activer !", "son mot de passe est : "));
    }

    //message de succès de suppression d'un utilisateur
    public void msgSuccesDeleteUser() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'utilisateur à été supprimé !", "Succès"));
    }
    
    //message d'erreur de suppression
     public void msgErroDeleteUser() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "L'utilisateur ne peux être supprimé !", "Error"));
    }

    public void msgSuccesModif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "l'utilisateur à été modifié", "succès"));
    }

    //redirection a la liste des utilisateurs
    public String retourListUtilisateurs() {
        //return "gestionUtilisateurs?faces-redirect=true";
        return "gestionUtilisateurs";
    }

    /**
     * methode renvoyant la date du jour formatée
     *
     * @return
     */
    public String DateOfDay() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String date = dt.format(dtFormat);
        return date;
    }

    public void saveLog(String msg, String date) {
        //trace dans la table auditlOg
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Utilisateur userConnected = (Utilisateur) session.getAttribute("utilisateurConnecte");
        String userLogin = userConnected.getLogin();
        Auditlog log = new Auditlog();
        log.setAuteurIdutilisateur(userConnected);
        log.setLogin(userLogin);
        log.setAction(msg);
        log.setDateaction(date);
        audit.persist(log);
    }

    private void clearChamps() {
        this.nomUtilisateur = null;
        this.prenomsUtilisateur = null;
        this.loginUtilisateur = null;
        this.defaultUserState = null;
        this.emailUtilisateur = null;
        this.dateActivation =null;
    }

}
