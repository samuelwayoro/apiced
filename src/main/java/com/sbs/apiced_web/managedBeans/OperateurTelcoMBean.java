/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Operateur;
import com.sbs.apiced_web.entities.Parametres;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.OperateurTelcoManager;
import com.sbs.apiced_web.services.ParamManager;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
@Named(value = "operateurTelcoMBean")
@ViewScoped
public class OperateurTelcoMBean implements Serializable {

    private String nomOperateur;
    private String nvoNomOpTelco;
    private String logo;
    private Integer qteMc;
    private List<Operateur> listOperateur;
    private List<Operateur> selectedOperateurs;
    private Operateur selectedOperateur;
    private Operateur Loperateur;
    private Parametres paramAbonnement;
    private Parametres paramPaiements;
    private Boolean etatAbonnementOp;
    private Boolean etatPaiementSubsides;
    private Integer QtesAbonneesTigo;
    private Integer QtesAbonneesAirtel;

    @EJB
    private AuditlogManager audit;
    @EJB
    private OperateurTelcoManager opTelcoManager;
    @EJB
    private ParamManager paramsManager;
    @EJB
    private MaitreCoManager mcMgr;

    /**
     * Creates a new instance of OperateurTelcoMBean
     */
    public OperateurTelcoMBean() {
    }

    public OperateurTelcoMBean(String nomOp, String logoOp, Integer qteMcOp) {
        nomOperateur = nomOp;
        logo = logoOp;
        qteMc = qteMcOp;

    }

    public MaitreCoManager getMcMgr() {
        return mcMgr;
    }

    public void setMcMgr(MaitreCoManager mcMgr) {
        this.mcMgr = mcMgr;
    }

    
    
    public String getNvoNomOpTelco() {
        return nvoNomOpTelco;
    }

    public void setNvoNomOpTelco(String nvoNomOpTelco) {
        this.nvoNomOpTelco = nvoNomOpTelco;
    }

    /**
     * retourne le nbre de maitre communautaire abonnés en fonction de
     * l'operateur
     *
     * @param op
     * @return
     */
    public Long qtesMaitresAbonnesById(String op) {
        return opTelcoManager.nbreAbonnesByIdOp(op);
    }

    public ParamManager getParamsManager() {
        return paramsManager;
    }

    public void setParamsManager(ParamManager paramsManager) {
        this.paramsManager = paramsManager;
    }

    @PostConstruct
    public void init() {
        listOperateur = opTelcoManager.getAllOperateur();

    }

    public Parametres getParamAbonnement() {
        return paramAbonnement;
    }

    public void setParamAbonnement(Parametres paramAbonnement) {
        this.paramAbonnement = paramAbonnement;
    }

    public Parametres getParamPaiements() {
        return paramPaiements;
    }

    public void setParamPaiements(Parametres paramPaiements) {
        this.paramPaiements = paramPaiements;
    }

    public Operateur getSelectedOperateur() {
        return selectedOperateur;
    }

    public void setSelectedOperateur(Operateur selectedOperateur) {
        this.selectedOperateur = selectedOperateur;
    }

    public List<Operateur> getSelectedOperateurs() {
        return selectedOperateurs;
    }

    public void setSelectedOperateurs(List<Operateur> selectedOperateurs) {
        this.selectedOperateurs = selectedOperateurs;
    }

    public List<Operateur> getListOperateur() {
        return listOperateur;
    }

    public void setListOperateur(List<Operateur> listOperateur) {
        this.listOperateur = listOperateur;
    }

    public String getNomOperateur() {
        return nomOperateur;
    }

    public void setNomOperateur(String nomOperateur) {
        this.nomOperateur = nomOperateur;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getQteMc() {
        return qteMc;
    }

    public void setQteMc(Integer qteMc) {
        this.qteMc = qteMc;
    }

    public OperateurTelcoManager getOpTelcoManager() {
        return opTelcoManager;
    }

    public Operateur getLoperateur() {
        return Loperateur;
    }

    public void setLoperateur(Operateur Loperateur) {
        this.Loperateur = Loperateur;
    }

    public AuditlogManager getAudit() {
        return audit;
    }

    public void setAudit(AuditlogManager audit) {
        this.audit = audit;
    }

    public Boolean getEtatAbonnementOp() {
        return etatAbonnementOp;
    }

    public void setEtatAbonnementOp(Boolean etatAbonnementOp) {
        this.etatAbonnementOp = etatAbonnementOp;
    }

    public Boolean getEtatPaiementSubsides() {
        return etatPaiementSubsides;
    }

    public void setEtatPaiementSubsides(Boolean etatPaiementSubsides) {
        this.etatPaiementSubsides = etatPaiementSubsides;
    }

    public Integer getQtesAbonneesTigo() {
        return QtesAbonneesTigo;
    }

    public void setQtesAbonneesTigo(Integer QtesAbonneesTigo) {
        this.QtesAbonneesTigo = QtesAbonneesTigo;
    }

    public Integer getQtesAbonneesAirtel() {
        return QtesAbonneesAirtel;
    }

    public void setQtesAbonneesAirtel(Integer QtesAbonneesAirtel) {
        this.QtesAbonneesAirtel = QtesAbonneesAirtel;
    }

    /**
     * méthode de creation d'un nouvel opérateur télécom
     */
    public void addOperateurTelco() {
        Loperateur = new Operateur();
        Loperateur.setNom(this.nomOperateur);
        Loperateur.setActivationabonnement(etatAbonnementOp);
        Loperateur.setActivationpaiements(etatPaiementSubsides);
        System.out.println("creation d'un nouvel opérateur" + Loperateur.getNom() + " etat abonnement : " + Loperateur.getActivationabonnement() + " etat paiement :" + Loperateur.getActivationpaiements());
        //persister l'operateur
        opTelcoManager.persist(Loperateur);
        listOperateur.add(Loperateur);

        msgSuccesAddOperateur();
        PrimeFaces.current().executeScript("PF('addOperateurDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");

        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        System.out.println("UTILISATEUR EN SESSION : " + session.getAttribute("utilisateur"));
        String userCo = (String) session.getAttribute("utilisateur");
        saveLog("creation de l'opérateur " + nomOperateur + " par l'utilisateur : " + userCo, DateOfDay());
        clearChamps();
    }

    /**
     * methode de maj d'un opérateur telco
     */
    public void updateOperateurTelco() { //attention les logos ne sont pas encore geres 

        //si c vide traitement 
        if (this.nvoNomOpTelco.isEmpty()) {

            selectedOperateur.setNom(selectedOperateur.getNom());
            System.out.println("nom de l'opérateur : " + selectedOperateur.getNom());
            opTelcoManager.updateTelcoMc(selectedOperateur, selectedOperateur.getNom());
            //System.out.println("modification des infos de l'opérateur " + selectedOperateur.getNom());
            //System.out.println("etat : " + selectedOperateur.getActivationabonnement() + " " + selectedOperateur.getActivationpaiements());
            System.out.println("debut modif op");
            selectedOperateur.setDatemodif(DateOfDay());
            selectedOperateur.setLogo("NEAN");
            selectedOperateur.setActivationabonnement(selectedOperateur.getActivationabonnement());
            selectedOperateur.setActivationpaiements(selectedOperateur.getActivationpaiements());
            opTelcoManager.updateOperateur(selectedOperateur);
            System.out.println("fin modif op");
            msgSuccesModif();
            PrimeFaces.current().executeScript("PF('manageOperateurDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
            saveLog("mise a jour des informations de l'opérateur  " + selectedOperateur.getNom() + "   et modification du nom opérateur chez tous ses maitre communautaires ", DateOfDay());
            this.nvoNomOpTelco = null;

        } else {
            opTelcoManager.updateTelcoMc(selectedOperateur, this.nvoNomOpTelco);
            //recup liste des mcs
            List<Maitrecommunautaire> listeMcs = mcMgr.mcsByOpTelcoName(selectedOperateur.getNom());
            
            //System.out.println("modification des infos de l'opérateur " + selectedOperateur.getNom());
            //System.out.println("etat : " + selectedOperateur.getActivationabonnement() + " " + selectedOperateur.getActivationpaiements());
            System.out.println("debut modif op");
            selectedOperateur.setDatemodif(DateOfDay());
            selectedOperateur.setLogo("NEAN");
            selectedOperateur.setActivationabonnement(selectedOperateur.getActivationabonnement());
            selectedOperateur.setActivationpaiements(selectedOperateur.getActivationpaiements());
            selectedOperateur.setNom(nvoNomOpTelco);
            opTelcoManager.updateOperateur(selectedOperateur);
            System.out.println("fin modif op");
            msgSuccesModif();
            PrimeFaces.current().executeScript("PF('manageOperateurDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
            saveLog("mise a jour des informations de l'opérateur  " + selectedOperateur.getNom() + "   et modification du nom opérateur chez tous ses maitre communautaires ", DateOfDay());
            this.nvoNomOpTelco = null;
        }

    }

    /**
     * methode de suppression d'un opérateur
     */
    public void deleteOperateur() {
        //verif d'bonné pour cet opérateur 
        int verifAbonné;
        //si oui on ne peux pas supprimer : afficher msg d'erreur
        verifAbonné = opTelcoManager.verifMcsAbonnéChezOp(selectedOperateur.getNom());
        //System.out.println("resultat de la verif : "+verifAbonné);
        if (verifAbonné > 0) { //si oui impossible de le supprimer
            System.out.println("impossible de supprimer l'operateur");
            msgErrorDeleteOperateur();
        } else {//sinon on le supprime
            System.out.println("suppression de l'opearteur     : " + selectedOperateur.getNom());
            msgSuccesDeleteOperateur();
            //suppresion de l'op de la bd 
            opTelcoManager.deleteOperateur(selectedOperateur.getIdoperateur());
            //suppression dans la liste de operateur du tableau 
            listOperateur.remove(selectedOperateur);
            saveLog("suppression de l'opérateur  " + selectedOperateur.getNom(), DateOfDay());
            //actualisation du tableau 
            PrimeFaces.current().executeScript("PF('deleteOperateurDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        }

    }

    public String DateOfDay() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String date = dt.format(dtFormat);
        return date;
    }

    //msg de succes de modification opérateur
    public void msgSuccesModif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "les infos de l'opérateur ont été modifié", "succès"));
    }

    //msg de succès de creation d'un nouvel opérateur 
    public void msgSuccesAddOperateur() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "l'opérateur à été ajouter a la base de données", "succès"));
    }

    //msg de succès de suppression d'un opérateur
    public void msgSuccesDeleteOperateur() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'opérateur à été supprimé ", "succès"));
    }

    //msg de succès de suppression d'un opérateur
    public void msgErrorDeleteOperateur() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attention ", "l'oprateur ne peux être supprimé car il a des maitres abonnés"));
    }

    private void clearChamps() {
        this.nomOperateur = null;
        this.etatAbonnementOp = null;
        this.etatPaiementSubsides = null;
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
