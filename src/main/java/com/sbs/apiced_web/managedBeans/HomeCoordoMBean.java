/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Usersnotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.UsersNotifManager;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

/**
 *
 * @author samuel
 */
@Named(value = "homeCoordoMBean")
@ViewScoped
public class HomeCoordoMBean implements Serializable {

    private List<Usersnotifs> listeNotifs;
    private Usersnotifs selectedNotif;
    private Long mcEnAttenteDeValidation;
    private Long totalMcs;
    private Long totalMcAddToday;
    private Long nbrePaiementEnAttente;
    private Long nbreTotalPaiements;

    @EJB
    private NotifsManager notifsMgr;
    @EJB
    private UsersNotifManager usersNotifMgr;
    @EJB
    private AuditlogManager audit;

    /**
     * Creates a new instance of HomeCoordoMBean
     */
    public HomeCoordoMBean() {
    }

    public Long getTotalMcs() {
        return totalMcs;
    }

    public void setTotalMcs(Long totalMcs) {
        this.totalMcs = totalMcs;
    }
    
    public List<Usersnotifs> getListeNotifs() {
        return listeNotifs;
    }

    public void setListeNotifs(List<Usersnotifs> listeNotifs) {
        this.listeNotifs = listeNotifs;
    }

    public Usersnotifs getSelectedNotif() {
        return selectedNotif;
    }

    public void setSelectedNotif(Usersnotifs selectedNotif) {
        this.selectedNotif = selectedNotif;
    }

    public NotifsManager getNotifsMgr() {
        return notifsMgr;
    }

    public void setNotifsMgr(NotifsManager notifsMgr) {
        this.notifsMgr = notifsMgr;
    }

    public Long getMcEnAttenteDeValidation() {
        return mcEnAttenteDeValidation;
    }

    public void setMcEnAttenteDeValidation(Long mcEnAttenteDeValidation) {
        this.mcEnAttenteDeValidation = mcEnAttenteDeValidation;
    }

    public Long getTotalMcAddToday() {
        return totalMcAddToday;
    }

    public void setTotalMcAddToday(Long totalMcAddToday) {
        this.totalMcAddToday = totalMcAddToday;
    }



    public Long getNbrePaiementEnAttente() {
        return nbrePaiementEnAttente;
    }

    public void setNbrePaiementEnAttente(Long nbrePaiementEnAttente) {
        this.nbrePaiementEnAttente = nbrePaiementEnAttente;
    }

    public Long getNbreTotalPaiements() {
        return nbreTotalPaiements;
    }

    public void setNbreTotalPaiements(Long nbreTotalPaiements) {
        this.nbreTotalPaiements = nbreTotalPaiements;
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

    @PostConstruct
    public void init() {

        //recup du user en session pour recup de son id 
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Utilisateur userConnected = (Utilisateur) session.getAttribute("utilisateurConnecte");
        //liste des notif de l'emet
        listeNotifs = notifsMgr.oneUserListeNotifications(BigInteger.valueOf(userConnected.getIdutilisateur()));

        mcEnAttenteDeValidation = notifsMgr.nbreMcEnAttenteValidation();
        totalMcs =   notifsMgr.nbreTotalMc();
        //le nbre de mc cree a une date en cours ...
        totalMcAddToday = notifsMgr.nbreMcCreerToday();
        nbrePaiementEnAttente = notifsMgr.qtesPaiementEnAttenteDeValidation();
        nbreTotalPaiements = notifsMgr.totalPaiement();

    }

    public void archiverNotif() {
        System.out.println("la notif a archiver " + selectedNotif.getId());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        //System.out.println("UTILISATEUR EN SESSION : " + session.getAttribute("utilisateur"));
        String userCo = (String) session.getAttribute("utilisateur");
        saveLog("la notification : " + selectedNotif.getTitre() + " marqué comme lue par l'utilisateur  : " + userCo, DateOfDay());
        //msgSuccesDeleteUser();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        selectedNotif.setEtat(BigInteger.ONE);
        usersNotifMgr.updateUserNotif(selectedNotif);
        listeNotifs.remove(selectedNotif);
        selectedNotif = null;
    }

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
