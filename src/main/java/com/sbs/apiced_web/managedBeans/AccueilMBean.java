/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Categorie;
import com.sbs.apiced_web.entities.Usersnotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.CategorieMcManager;
import com.sbs.apiced_web.services.DashboardManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.UsersNotifManager;
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
import org.primefaces.PrimeFaces;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.pie.PieChartModel;

/**
 *
 * @author samuel
 */
@Named(value = "accueilMBean")
@ViewScoped
public class AccueilMBean implements Serializable {

    private Integer qtesTotalMc;
    private PieChartModel pieModel;
    private Integer qtesMcSenior;
    private Integer qtesMcintermediaire;
    private Integer qtesMcDebutant;
    private DonutChartModel donutModel;
    private Usersnotifs selectedNotif;
    private List<Usersnotifs> listeNotifs;

    @EJB
    private DashboardManager dmanager;
    @EJB
    private CategorieMcManager cManager;
    @EJB
    private MaitreCoManager maitreCoManager;
    @EJB
    private UsersNotifManager usersNotifMgr;
    @EJB
    private NotifsManager notifsMgr;
    @EJB
    private AuditlogManager audit;

    public List<Usersnotifs> getListeNotifs() {
        return listeNotifs;
    }

    public void setListeNotifs(List<Usersnotifs> listeNotifs) {
        this.listeNotifs = listeNotifs;
    }
    
    public MaitreCoManager getMaitreCoManager() {
        return maitreCoManager;
    }

    public void setMaitreCoManager(MaitreCoManager maitreCoManager) {
        this.maitreCoManager = maitreCoManager;
    }

    public UsersNotifManager getUsersNotifMgr() {
        return usersNotifMgr;
    }

    public void setUsersNotifMgr(UsersNotifManager usersNotifMgr) {
        this.usersNotifMgr = usersNotifMgr;
    }

    public NotifsManager getNotifsMgr() {
        return notifsMgr;
    }

    public void setNotifsMgr(NotifsManager notifsMgr) {
        this.notifsMgr = notifsMgr;
    }

    public AuditlogManager getAudit() {
        return audit;
    }

    public void setAudit(AuditlogManager audit) {
        this.audit = audit;
    }

    public Usersnotifs getSelectedNotif() {
        return selectedNotif;
    }

    public void setSelectedNotif(Usersnotifs selectedNotif) {
        this.selectedNotif = selectedNotif;
    }

    public DashboardManager getDmanager() {
        return dmanager;
    }

    public void setDmanager(DashboardManager dmanager) {
        this.dmanager = dmanager;
    }

    public CategorieMcManager getcManager() {
        return cManager;
    }

    public void setcManager(CategorieMcManager cManager) {
        this.cManager = cManager;
    }

    public Integer getQtesTotalMc() {
        return qtesTotalMc;
    }

    public void setQtesTotalMc(Integer qtesTotalMc) {
        this.qtesTotalMc = qtesTotalMc;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public Integer getQtesMcSenior() {
        return qtesMcSenior;
    }

    public void setQtesMcSenior(Integer qtesMcSenior) {
        this.qtesMcSenior = qtesMcSenior;
    }

    public Integer getQtesMcintermediaire() {
        return qtesMcintermediaire;
    }

    public void setQtesMcintermediaire(Integer qtesMcintermediaire) {
        this.qtesMcintermediaire = qtesMcintermediaire;
    }

    public Integer getQtesMcDebutant() {
        return qtesMcDebutant;
    }

    public void setQtesMcDebutant(Integer qtesMcDebutant) {
        this.qtesMcDebutant = qtesMcDebutant;
    }

    @PostConstruct
    public void init() {
        qtesTotalMc = dmanager.totalMc().intValue();

        Categorie debutant = cManager.getCategorieById(BigDecimal.ONE);
//        qtesMcDebutant = dmanager.qtesMcDebutant(debutant).intValue();
        
        //recup du user en session pour recup de son id 
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Utilisateur userConnected = (Utilisateur) session.getAttribute("utilisateurConnecte");
        //liste des notif de l'emet
        listeNotifs = notifsMgr.oneUserListeNotifications(BigInteger.valueOf(userConnected.getIdutilisateur()));
    }

  
    /**
     * archiver une notification
     */
    public void archiverNotif() {
//        System.out.println("la notif a archiver " + selectedNotif.getId());

        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        //System.out.println("UTILISATEUR EN SESSION : " + session.getAttribute("utilisateur"));
        String userCo = (String) session.getAttribute("utilisateur");
        saveLog("la notification : " + selectedNotif.getTitre() + " marqué comme lue par l'utilisateur  : " + userCo, DateOfDay());
        PrimeFaces.current().ajax().update("form:dt-products");
        selectedNotif.setEtat(BigInteger.ONE);
        usersNotifMgr.updateUserNotif(selectedNotif);
        listeNotifs.remove(selectedNotif);
        selectedNotif = null;

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
     
     
    //message de succès de suppression d'un utilisateur
    public void msgSuccesDeleteUser() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "notification archivée!", "Succès"));
    }

}
