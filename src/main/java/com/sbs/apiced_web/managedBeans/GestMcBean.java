/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.TypeNotifManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

/**
 *
 * @author samuel
 */
@Named(value = "gestMcBean")
@ViewScoped
public class GestMcBean implements Serializable {

    private List<Maitrecommunautaire> listeMcActif;
    private Maitrecommunautaire selectedMc;
    private String motifModif;
    private String userCo;
    private Typenotifs typeNotification;
    private Notifications notif = new Notifications();

    @EJB
    private MaitreCoManager mcMgr;
    @EJB
    private UtilisateurManager utilisateurMgr;
    @EJB
    private TypeNotifManager typeNotifMgr;
     @EJB
    private NotifsManager notifMgr;
     @EJB
    private AuditlogManager audit;

    public MaitreCoManager getMcMgr() {
        return mcMgr;
    }

    public void setMcMgr(MaitreCoManager mcMgr) {
        this.mcMgr = mcMgr;
    }

    public UtilisateurManager getUtilisateurMgr() {
        return utilisateurMgr;
    }

    public void setUtilisateurMgr(UtilisateurManager utilisateurMgr) {
        this.utilisateurMgr = utilisateurMgr;
    }

    public TypeNotifManager getTypeNotifMgr() {
        return typeNotifMgr;
    }

    public void setTypeNotifMgr(TypeNotifManager typeNotifMgr) {
        this.typeNotifMgr = typeNotifMgr;
    }

    public NotifsManager getNotifMgr() {
        return notifMgr;
    }

    public void setNotifMgr(NotifsManager notifMgr) {
        this.notifMgr = notifMgr;
    }

    public AuditlogManager getAudit() {
        return audit;
    }

    public void setAudit(AuditlogManager audit) {
        this.audit = audit;
    }

     
     
    public List<Maitrecommunautaire> getListeMcActif() {
        return listeMcActif;
    }
    public Maitrecommunautaire getSelectedMc() {
        return selectedMc;
    }

    public void setSelectedMc(Maitrecommunautaire selectedMc) {
        this.selectedMc = selectedMc;
    }

    public String getMotifModif() {
        return motifModif;
    }

    public void setMotifModif(String motifModif) {
        this.motifModif = motifModif;
    }

    public String getUserCo() {
        return userCo;
    }

    public void setUserCo(String userCo) {
        this.userCo = userCo;
    }

    public Typenotifs getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(Typenotifs typeNotification) {
        this.typeNotification = typeNotification;
    }

    public Notifications getNotif() {
        return notif;
    }

    public void setNotif(Notifications notif) {
        this.notif = notif;
    }
    
    

    /**
     * Creates a new instance of GestMcBean
     */
    public GestMcBean() {
    }

    public void init() {
        //recup de la liste des mc actif 
        listeMcActif = mcMgr.allActivedMcGest();

    }

    public void modifMatriculeWithMotif() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
        Utilisateur createur = utilisateurMgr.verif(userCo);
        //le matricule est deja changé
        //on stock la date de modif
        selectedMc.setDatemodifcompte(DateOfDay());
        //on persite le motif dans la table des notifs
        msgSuccesModifMatricule();
        mcMgr.updateMaitre(selectedMc);
        //auditlog
        saveLog("modification du matricule du maitre  " + selectedMc.getNom() , DateOfDay());
        BigDecimal typn = BigDecimal.valueOf(6);
        typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
        String details = "Mise à jour du  matricule du maitre  " + selectedMc.getNom() + "  " + selectedMc.getPrenoms();
        notif.setLibelle(motifModif);
        notif.setDetails(details);
        notif.setDatecreation(DateOfDay());
        notif.setDateresolution(DateOfDay());
        //notif.setEtat(BigInteger.ZERO);
        notif.setTypenotif(typeNotification);
        notif.setCreateur(createur);
        notif.setIdinfo(selectedMc.getId().toString());
        //save de la notif
        notifMgr.persist(notif);
        //maj du tableau des mc et raffraichissement de la vue 
        PrimeFaces.current().executeScript("PF('modifMatriculeDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");

    }

    public String DateOfDay() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String date = dt.format(dtFormat);
        return date;
    }

    public void msgSuccesModifMatricule() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "succès", "le matricule du maitre a été modifié"));
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
        log.setAction(msg + " par l'utilisateur " + userLogin);
        log.setDateaction(date);
        audit.persist(log);
    }
}
