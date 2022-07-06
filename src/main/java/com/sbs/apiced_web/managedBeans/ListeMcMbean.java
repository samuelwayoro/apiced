/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.CategorieMcManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samuel
 */
@Named(value = "listeMcMbean")
@ViewScoped
public class ListeMcMbean implements Serializable{
    
    private List<Maitrecommunautaire> AllMc;
    private List<Maitrecommunautaire> allActifMc;
    private List<Maitrecommunautaire> listeMcEnAttenteDeValidation;
    
    
    
    @EJB
    private MaitreCoManager mcManager;
    @EJB
    private CategorieMcManager cateManager  ;
    
      /**
     * Creates a new instance of ListeMcMbean
     */
    public ListeMcMbean() {
    }

    public List<Maitrecommunautaire> getAllActifMc() {
        return allActifMc;
    }
    
    
    public MaitreCoManager getMcManager() {
        return mcManager;
    }

    public void setMcManager(MaitreCoManager mcManager) {
        this.mcManager = mcManager;
    }




    
    
    @PostConstruct
    public void init(){
        AllMc = mcManager.allMc();
        allActifMc = mcManager.maitreCoWalletActif();
        listeMcEnAttenteDeValidation = mcManager.listeMcEnAttenteDeValidation(userCo(),Boolean.FALSE);
    }
    
    
    public List<Maitrecommunautaire> getAllMc() {
        return AllMc;
    }

    public void setAllMc(List<Maitrecommunautaire> AllMc) {
        this.AllMc = AllMc;
    }

    public List<Maitrecommunautaire> getListeMcEnAttenteDeValidation() {
        return listeMcEnAttenteDeValidation;
    }

    public void setListeMcEnAttenteDeValidation(List<Maitrecommunautaire> listeMcEnAttenteDeValidation) {
        this.listeMcEnAttenteDeValidation = listeMcEnAttenteDeValidation;
    }

    public CategorieMcManager getCateManager() {
        return cateManager;
    }

    public void setCateManager(CategorieMcManager cateManager) {
        this.cateManager = cateManager;
    }
    
    
    public Utilisateur userCo(){
          FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Utilisateur userConnected = (Utilisateur) session.getAttribute("utilisateurConnecte");
        return userConnected;
    }    

  
    
}
