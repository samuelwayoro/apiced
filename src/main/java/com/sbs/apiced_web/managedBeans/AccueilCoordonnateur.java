/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Paiement;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Usersnotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.PaiementMensuelManager;
import com.sbs.apiced_web.services.TypeNotifManager;
import com.sbs.apiced_web.services.UsersNotifManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samuel
 */
@Named(value = "accueilCoordonnateur")
@ViewScoped
public class AccueilCoordonnateur implements Serializable {

    private String nomUser;
    private String prenomsUser;
    private String loginUser;
    private String passwordUser;
    private String actualPassword;
    private String newPassOne;
    private String newPassTwo;
    private Integer idUser;
    private Utilisateur utilisateur = new Utilisateur();
    private List<Usersnotifs> listeNotifs;
    private List<Notifications> listeDemandePaiementEnAttente;
    private List<Paiement> listePaiementEnAttente;
    private List<Notifications> listeNotifsCoordonnateur;
    private List<Notifications> listeNotifsCoordoCreaMcs;
    private List<Notifications> listeNotifsCoordoCreaPaie;
    private Long notifEnAttente;
    private Long nbreDmdPaieEnAttente;
    private Long mcEnAttenteDeValidation;
    private Long qtesNotifCoordonnateur;
    private Usersnotifs selectedNotif;
    private Long qtesNotifsCoordoCreaMaitre;
    private Long qtesNotifsCoordoCreaPaie;
    private Long qteMcvalides;
    private Long qtesPaiementAvalider;
    private Long qtesPaiementValides;
    private Long mcCreeValides;
    private Long totalMc;
    private Long totalMcValides;
    private Long nbrePaiementEmisValides;
    private Long nbreTotalPaiements;
    private Long nbrePaiementEnAttente;
    private Long qteMcAvalider;

        
    @EJB
    private AuditlogManager auditMgr;
    @EJB
    private UtilisateurManager userMgr;
    @EJB
    private NotifsManager notifsMgr;
    @EJB
    private TypeNotifManager typeNotifMgr;
    @EJB
    private PaiementMensuelManager paieMgr;
    @EJB
    private UsersNotifManager usersNotifMgr;
    @EJB
    private AuditlogManager audit;
    
    /**
     * Creates a new instance of AccueilCoordonnateur
     */
    public AccueilCoordonnateur() {
        
    }
    

    @PostConstruct
    public void init() {

        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        System.out.println("UTILISATEUR EN SESSION : " + session.getAttribute("utilisateur"));
        Utilisateur userConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");

        Typenotifs notifCreaMc = typeNotifMgr.getOneTypeNotif(BigDecimal.ONE);
        Typenotifs notifPaieEnAttente = typeNotifMgr.getOneTypeNotif(BigDecimal.valueOf(4));
        Typenotifs notifPaieValides = typeNotifMgr.getOneTypeNotif(BigDecimal.valueOf(11));

        //notification de type creation de paiement
        BigDecimal idtype = BigDecimal.valueOf(4);
        Typenotifs typeDemandePaiement = typeNotifMgr.getOneTypeNotif(idtype);

        //liste des notif de l'emet
        listeNotifs = notifsMgr.oneUserListeNotifications(BigInteger.valueOf(userConnecte.getIdutilisateur()));
        //liste des notif de demande de paiements en attente de validation 
        listeDemandePaiementEnAttente = notifsMgr.listeDmdPaieEnAttente(typeDemandePaiement);

        //nbre de demandes de paiement en attente de validation par le coordo
        nbreDmdPaieEnAttente = notifsMgr.nbreDmdPaieEnAttente(notifPaieEnAttente);
        //nbre total de demandes (paiement / crea mc en attente de validaion)
        //notifEnAttente = notifsMgr.nbreNotifsEnAttente(userCo(), 0);

        mcEnAttenteDeValidation = notifsMgr.nbreMcEnAttenteValidation();
        //mcCreeValides = notifsMgr.nbreMcEnAttente(userCo(), Boolean.TRUE);

        totalMc = notifsMgr.nbreTotalMc();
        totalMcValides = notifsMgr.nbreMcEnAttenteValidation();

        int x = 1;
        // nbrePaiementEmisValides = notifsMgr.qtesPaiementEmisParEmetteur(userCo(), BigInteger.valueOf(x));
        nbrePaiementEnAttente = notifsMgr.qtesPaiementEnAttenteDeValidation();

        /**
         * STATS DU COORDONNATEUR POUR DASHBOARD
         */
        qtesNotifCoordonnateur = notifsMgr.nbreNotifsCoordonnateur(); //total des notifs du coordonateur

        listeNotifsCoordoCreaMcs = notifsMgr.notificationsCoordoCreaMaitre(notifCreaMc);
        qtesNotifsCoordoCreaMaitre = notifsMgr.nbreNotifsCoordoCreaMaitre(notifCreaMc);

        qteMcAvalider = notifsMgr.nbreDmdCreaMcEnAttente(notifCreaMc);

        qtesPaiementAvalider = notifsMgr.nbreDmdPaieEnAttente(notifPaieEnAttente);//nbre de paiement en attente de validation par le coordonnateur

        qteMcvalides = notifsMgr.nbreTotalMcValides(notifCreaMc);

        qtesPaiementValides = notifsMgr.nbreTotalPaieValides(); //nbre de paiement validés par le coordo

        qtesNotifsCoordoCreaPaie = notifsMgr.nbreNotifsCoordoCreaMaitre(typeDemandePaiement);
        listeNotifsCoordoCreaPaie = notifsMgr.notifCoordoCreaPaie(typeDemandePaiement);

        nbreTotalPaiements = notifsMgr.totalPaiement();
    }

    public UsersNotifManager getUsersNotifMgr() {
        return usersNotifMgr;
    }

    public void setUsersNotifMgr(UsersNotifManager usersNotifMgr) {
        this.usersNotifMgr = usersNotifMgr;
    }

    public AuditlogManager getAudit() {
        return audit;
    }

    public void setAudit(AuditlogManager audit) {
        this.audit = audit;
    }


    public Long getQtesPaiementValides() {
        return qtesPaiementValides;
    }

    public void setQtesPaiementValides(Long qtesPaiementValides) {
        this.qtesPaiementValides = qtesPaiementValides;
    }

    public Long getTotalMcValides() {
        return totalMcValides;
    }

    public void setTotalMcValides(Long totalMcValides) {
        this.totalMcValides = totalMcValides;
    }

    public Long getQtesPaiementAvalider() {
        return qtesPaiementAvalider;
    }

    public void setQtesPaiementAvalider(Long qtesPaiementAvalider) {
        this.qtesPaiementAvalider = qtesPaiementAvalider;
    }

    public Long getQteMcAvalider() {
        return qteMcAvalider;
    }

    public void setQteMcAvalider(Long qteMcAvalider) {
        this.qteMcAvalider = qteMcAvalider;
    }

    public List<Notifications> getListeNotifsCoordoCreaMcs() {
        return listeNotifsCoordoCreaMcs;
    }

    public void setListeNotifsCoordoCreaMcs(List<Notifications> listeNotifsCoordoCreaMcs) {
        this.listeNotifsCoordoCreaMcs = listeNotifsCoordoCreaMcs;
    }

    public Usersnotifs getSelectedNotif() {
        return selectedNotif;
    }

    public void setSelectedNotif(Usersnotifs selectedNotif) {
        this.selectedNotif = selectedNotif;
    }

    public Long getQtesNotifsCoordoCreaMaitre() {
        return qtesNotifsCoordoCreaMaitre;
    }

    public void setQtesNotifsCoordoCreaMaitre(Long qtesNotifsCoordoCreaMaitre) {
        this.qtesNotifsCoordoCreaMaitre = qtesNotifsCoordoCreaMaitre;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getPrenomsUser() {
        return prenomsUser;
    }

    public void setPrenomsUser(String prenomsUser) {
        this.prenomsUser = prenomsUser;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getActualPassword() {
        return actualPassword;
    }

    public void setActualPassword(String actualPassword) {
        this.actualPassword = actualPassword;
    }

    public String getNewPassOne() {
        return newPassOne;
    }

    public void setNewPassOne(String newPassOne) {
        this.newPassOne = newPassOne;
    }

    public String getNewPassTwo() {
        return newPassTwo;
    }

    public void setNewPassTwo(String newPassTwo) {
        this.newPassTwo = newPassTwo;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Usersnotifs> getListeNotifs() {
        return listeNotifs;
    }

    public void setListeNotifs(List<Usersnotifs> listeNotifs) {
        this.listeNotifs = listeNotifs;
    }

    public List<Notifications> getListeDemandePaiementEnAttente() {
        return listeDemandePaiementEnAttente;
    }

    public void setListeDemandePaiementEnAttente(List<Notifications> listeDemandePaiementEnAttente) {
        this.listeDemandePaiementEnAttente = listeDemandePaiementEnAttente;
    }

    public List<Paiement> getListePaiementEnAttente() {
        return listePaiementEnAttente;
    }

    public void setListePaiementEnAttente(List<Paiement> listePaiementEnAttente) {
        this.listePaiementEnAttente = listePaiementEnAttente;
    }

    public List<Notifications> getListeNotifsCoordonnateur() {
        return listeNotifsCoordonnateur;
    }

    public void setListeNotifsCoordonnateur(List<Notifications> listeNotifsCoordonnateur) {
        this.listeNotifsCoordonnateur = listeNotifsCoordonnateur;
    }

    public List<Notifications> getListeNotifsCoordoCreaPaie() {
        return listeNotifsCoordoCreaPaie;
    }

    public void setListeNotifsCoordoCreaPaie(List<Notifications> listeNotifsCoordoCreaPaie) {
        this.listeNotifsCoordoCreaPaie = listeNotifsCoordoCreaPaie;
    }

    public Long getNotifEnAttente() {
        return notifEnAttente;
    }

    public void setNotifEnAttente(Long notifEnAttente) {
        this.notifEnAttente = notifEnAttente;
    }

    public Long getNbreDmdPaieEnAttente() {
        return nbreDmdPaieEnAttente;
    }

    public void setNbreDmdPaieEnAttente(Long nbreDmdPaieEnAttente) {
        this.nbreDmdPaieEnAttente = nbreDmdPaieEnAttente;
    }

    public Long getMcEnAttenteDeValidation() {
        return mcEnAttenteDeValidation;
    }

    public void setMcEnAttenteDeValidation(Long mcEnAttenteDeValidation) {
        this.mcEnAttenteDeValidation = mcEnAttenteDeValidation;
    }

    public Long getQtesNotifCoordonnateur() {
        return qtesNotifCoordonnateur;
    }

    public void setQtesNotifCoordonnateur(Long qtesNotifCoordonnateur) {
        this.qtesNotifCoordonnateur = qtesNotifCoordonnateur;
    }

    public Long getMcCreeValides() {
        return mcCreeValides;
    }

    public void setMcCreeValides(Long mcCreeValides) {
        this.mcCreeValides = mcCreeValides;
    }

    public Long getTotalMc() {
        return totalMc;
    }

    public void setTotalMc(Long totalMc) {
        this.totalMc = totalMc;
    }

    public Long getNbrePaiementEmisValides() {
        return nbrePaiementEmisValides;
    }

    public void setNbrePaiementEmisValides(Long nbrePaiementEmisValides) {
        this.nbrePaiementEmisValides = nbrePaiementEmisValides;
    }

    public Long getNbreTotalPaiements() {
        return nbreTotalPaiements;
    }

    public void setNbreTotalPaiements(Long nbreTotalPaiements) {
        this.nbreTotalPaiements = nbreTotalPaiements;
    }

    public Long getNbrePaiementEnAttente() {
        return nbrePaiementEnAttente;
    }

    public void setNbrePaiementEnAttente(Long nbrePaiementEnAttente) {
        this.nbrePaiementEnAttente = nbrePaiementEnAttente;
    }

    public AuditlogManager getAuditMgr() {
        return auditMgr;
    }

    public void setAuditMgr(AuditlogManager auditMgr) {
        this.auditMgr = auditMgr;
    }

    public UtilisateurManager getUserMgr() {
        return userMgr;
    }

    public void setUserMgr(UtilisateurManager userMgr) {
        this.userMgr = userMgr;
    }

    public NotifsManager getNotifsMgr() {
        return notifsMgr;
    }

    public void setNotifsMgr(NotifsManager notifsMgr) {
        this.notifsMgr = notifsMgr;
    }

    public TypeNotifManager getTypeNotifMgr() {
        return typeNotifMgr;
    }

    public void setTypeNotifMgr(TypeNotifManager typeNotifMgr) {
        this.typeNotifMgr = typeNotifMgr;
    }

    public PaiementMensuelManager getPaieMgr() {
        return paieMgr;
    }

    public void setPaieMgr(PaiementMensuelManager paieMgr) {
        this.paieMgr = paieMgr;
    }

    public Long getQtesNotifsCoordoCreaPaie() {
        return qtesNotifsCoordoCreaPaie;
    }

    public void setQtesNotifsCoordoCreaPaie(Long qtesNotifsCoordoCreaPaie) {
        this.qtesNotifsCoordoCreaPaie = qtesNotifsCoordoCreaPaie;
    }

    public Long getQteMcvalides() {
        return qteMcvalides;
    }

    public void setQteMcvalides(Long qteMcvalides) {
        this.qteMcvalides = qteMcvalides;
    }

    //message de succès de suppression d'un utilisateur
    public void msgSuccesDeleteUser() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "notification archivée!", "Succès"));
    }
    
    
    //aller a la validation 
    public String valider(){
        System.out.println("aller a la validation...");
        return "maitresAValider.xhtml";
    }
//    
//      //mehode retournant true ou false en fonction de la valeur des attribut booleen
//    public String valeur(){
//        if (Boolean.TRUE.equals(selectedNotif.getBtnvalidemc())) {
//            return "true";
//        }else{
//            return "false";
//        }
//    }

    
    
       public void archiverNotif() {
        System.out.println("la notif a archiver " + selectedNotif.getId());
        //recuperation de la facecontext pour travailler avec le context courant de la requette
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
//        //System.out.println("UTILISATEUR EN SESSION : " + session.getAttribute("utilisateur"));
//        String userCo = (String) session.getAttribute("utilisateur");
//        saveLog("la notification : " + selectedNotif.getTitre() + " marqué comme lue par l'utilisateur  : " + userCo, DateOfDay());
//        //msgSuccesDeleteUser();
//        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
//        selectedNotif.setEtat(BigInteger.ONE);
//        usersNotifMgr.updateUserNotif(selectedNotif);
//        listeNotifs.remove(selectedNotif);
//        selectedNotif = null;
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

}
