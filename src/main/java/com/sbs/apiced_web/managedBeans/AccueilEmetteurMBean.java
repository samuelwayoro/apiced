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
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.UsersNotifManager;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

/**
 *
 * @author samuel
 */
@Named(value = "accueilEmetteurMBean")
@ViewScoped
public class AccueilEmetteurMBean implements Serializable {

    private Long abonneesAirtel;
    private Long abonneeTigo;
    private Long nbreRequestCreaMc;
    private Long nbreRequestDelMc;
    private Long nbreRequestUpdateMc;
    private Long nbreRequestSuspMc;
    private Usersnotifs selectedNotif;
    private List<Usersnotifs> listeNotifs;
    
    @EJB
    private MaitreCoManager maitreCoManager;
    @EJB
    private UsersNotifManager usersNotifMgr;
    @EJB
    private NotifsManager notifsMgr;
    @EJB
    private AuditlogManager audit;

    public AccueilEmetteurMBean() {
    }
    
    
    
    
    public List<Usersnotifs> getListeNotifs() {
        return listeNotifs;
    }

    public void setListeNotifs(List<Usersnotifs> listeNotifs) {
        this.listeNotifs = listeNotifs;
    }

    public Long getAbonneesAirtel() {
        return abonneesAirtel;
    }

    public void setAbonneesAirtel(Long abonneesAirtel) {
        this.abonneesAirtel = abonneesAirtel;
    }

    public Long getAbonneeTigo() {
        return abonneeTigo;
    }

    public void setAbonneeTigo(Long abonneeTigo) {
        this.abonneeTigo = abonneeTigo;
    }

    public MaitreCoManager getMaitreCoManager() {
        return maitreCoManager;
    }

    public void setMaitreCoManager(MaitreCoManager maitreCoManager) {
        this.maitreCoManager = maitreCoManager;
    }

    public Long getNbreRequestCreaMc() {
        return nbreRequestCreaMc;
    }

    public void setNbreRequestCreaMc(Long nbreRequestCreaMc) {
        this.nbreRequestCreaMc = nbreRequestCreaMc;
    }

    public Long getNbreRequestDelMc() {
        return nbreRequestDelMc;
    }

    public void setNbreRequestDelMc(Long nbreRequestDelMc) {
        this.nbreRequestDelMc = nbreRequestDelMc;
    }

    public Long getNbreRequestUpdateMc() {
        return nbreRequestUpdateMc;
    }

    public void setNbreRequestUpdateMc(Long nbreRequestUpdateMc) {
        this.nbreRequestUpdateMc = nbreRequestUpdateMc;
    }

    public Long getNbreRequestSuspMc() {
        return nbreRequestSuspMc;
    }

    public void setNbreRequestSuspMc(Long nbreRequestSuspMc) {
        this.nbreRequestSuspMc = nbreRequestSuspMc;
    }

    public Usersnotifs getSelectedNotif() {
        return selectedNotif;
    }

    public void setSelectedNotif(Usersnotifs selectedNotif) {
        this.selectedNotif = selectedNotif;
    }

    public UsersNotifManager getUsersNotifMgr() {
        return usersNotifMgr;
    }

    public void setUsersNotifMgr(UsersNotifManager usersNotifMgr) {
        this.usersNotifMgr = usersNotifMgr;
    }



    @PostConstruct
    public void init() {

        //recup du user en session pour recup de son id 
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Utilisateur userConnected = (Utilisateur) session.getAttribute("utilisateurConnecte");
        //liste des notif de l'emet
        listeNotifs = notifsMgr.oneUserListeNotifications(BigInteger.valueOf(userConnected.getIdutilisateur()));

        //
//        abonneeTigo = maitreCoManager.listeAbonnesTigo();
//        //
//        abonneesAirtel = maitreCoManager.listeAbonnesAirtel();
//        //nbre des demande de creation de mc en attente de validation 
//        nbreRequestCreaMc = maitreCoManager.QtesDemandeCreaMc();
//        //nbre de demande de suppression de mc 
//        nbreRequestDelMc = maitreCoManager.QtesDemandeDelMc();
//        //nbre de demande de modif
//        nbreRequestUpdateMc = maitreCoManager.QtesDemandeUpdateMc();
//        //nbre de demande de suspension 
//        nbreRequestSuspMc = maitreCoManager.QtesDemandeSuspension();

    }


    /**
     * archiver une notification
     */
    public void archiverNotif() {
        System.out.println("la notif a archiver " + selectedNotif.getId());
        //recuperation de la facecontext pour travailler avec le context courant de la requette
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

    //message de succès de suppression d'un utilisateur
    public void msgSuccesDeleteUser() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "notification archivée!", "Succès"));
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
