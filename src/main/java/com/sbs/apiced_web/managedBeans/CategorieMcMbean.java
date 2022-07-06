/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Categorie;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.CategorieMcManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author samuel
 */
@Named(value = "categorieMcMbean")
@ViewScoped
public class CategorieMcMbean implements Serializable{
    
    private String libelleCategorie;
    private BigDecimal montantSubside;
    private String datecreation;
    private String dateModification;
    private List<Categorie> listeCategories;
    private Categorie selectedCategorie;
    private List<Categorie> selectedCategories;
    @EJB
    private AuditlogManager audit;
    @EJB
    private CategorieMcManager cateManager;
    

    /**
     * Creates a new instance of CategorieMcMbean
     */
    public CategorieMcMbean() {
    }

    public CategorieMcMbean(String libelleCategorie, BigDecimal montantSubside, String datecreation, String dateModification) {
        this.libelleCategorie = libelleCategorie;
        this.montantSubside = montantSubside;
        this.datecreation = datecreation;
        this.dateModification = dateModification;
    }

    public List<Categorie> getListeCategories() {
        return listeCategories;
    }

    public void setListeCategories(List<Categorie> listeCategories) {
        this.listeCategories = listeCategories;
    }

    public Categorie getSelectedCategorie() {
        return selectedCategorie;
    }

    public void setSelectedCategorie(Categorie selectedCategorie) {
        this.selectedCategorie = selectedCategorie;
    }

    public List<Categorie> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<Categorie> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }
    
    
    
    public String getLibelleCategorie() {
        return libelleCategorie;
    }

    public void setLibelleCategorie(String libelleCategorie) {
        this.libelleCategorie = libelleCategorie;
    }

    public BigDecimal getMontantSubside() {
        return montantSubside;
    }

    public void setMontantSubside(BigDecimal montantSubside) {
        this.montantSubside = montantSubside;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public String getDateModification() {
        return dateModification;
    }

    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }
    
    @PostConstruct
    public void init(){
        listeCategories = cateManager.allMcCategorie();
    }
    
        /**
     * methode de suppression d'un opérateur 
     */
    public void deleteCategorie(){
        System.out.println("suppression d'un opearteur"+selectedCategorie.getLibelle());
        msgDeleteCategorie();
        PrimeFaces.current().executeScript("PF('deleteOperateurDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }
    
    /**
     * methode de maj d'un opérateur telco
     */
    public void updateCategorieMc() {
        System.out.println("modif en cours du profil "+selectedCategorie.getLibelle());
        // recuperation du user en session 
        //recuperation de la facecontext pour travailler avec le context courant de la requette
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
//        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
//        //System.out.println("loggin de l'action de l'utilisateur : " + session.getAttribute("utilisateur"));
//        Utilisateur userCo = (Utilisateur) session.getAttribute("utilisateurConnecte");
//        Auditlog log = new Auditlog();
//        log.setAuteurIdutilisateur(userCo);
//        log.setLogin(userCo.getLogin());
//        log.setAction("modification de l'opérateur télécom  : " + selectedOperateur.getNom());
//        log.setDateaction(DateOfDay());
//        audit.persist(log);
//        opTelcoManager.persist(selectedOperateur);

        msgSuccesModif();
        //maj de la liste des profils
        PrimeFaces.current().executeScript("PF('manageOperateurDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }
    
       /**
     * méthode de creation d'un nouvel opérateur télécom
     */
    public void addCategorie(){
        System.out.println("creation d'un nouvel opérateur");
        msgSuccessAdd();
        PrimeFaces.current().executeScript("PF('addOperateurDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }
    
    
    
    
    
      public void msgDeleteCategorie(){
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"La catégorie à été supprimée ","succès"));
      }
    
        //msg de succes de modification d'une categorie de mc
      public void msgSuccesModif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "les infos de la catégorie ont été modifiées", "succès"));
    }
      
      //msg de succès de creation d'une new categorie
      public void msgSuccessAdd(){
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"la nouvelle catégorie à été ajouter a la base de données","succès"));
      }
      
}
