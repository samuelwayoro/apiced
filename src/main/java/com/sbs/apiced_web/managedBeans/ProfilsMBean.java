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
import java.io.Serializable;
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

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author samuel
 */
@Named(value = "profilsMBean")
@ViewScoped
public class ProfilsMBean implements Serializable {

    private Boolean accesaccueil;
    private Boolean accesannuairemc;
    private Boolean accesemissionpaie;
    private Boolean accessnouveaumc;
    private Boolean accesstatspaieparmc;
    private Boolean accesstatspaieparoperateurs;
    private Boolean accessuivipaie;
    private String datecreationProfil;
    private Boolean etatProfil;
    private String infosProfil;
    private String nomProfil;
    private List<Profil> profils;
    private List<Profil> selectedProfils;
  

    public List<Profil> getSelectedProfils() {
        return selectedProfils;
    }

    public void setSelectedProfils(List<Profil> selectedProfils) {
        this.selectedProfils = selectedProfils;
    }
    private Profil pro;
    private Profil selectedProfil;
    private Integer idProfil;

    public Integer getIdProfil() {
        return idProfil;
    }

    public void setIdProfil(Integer idProfil) {
        this.idProfil = idProfil;
    }

    @EJB
    private ProfilsManager profilManager;

    @EJB
    private AuditlogManager audit;
    
   

    /**
     * Creates a new instance of ProfilsMBean
     */
    public ProfilsMBean() {
        this.pro = new Profil();
    }

    @PostConstruct
    public void init() {
        profils = profilManager.getAllProfils();
    
    }

    public List<Profil> getProfils() {
        return profils;
    }

    public Profil getSelectedProfil() {
        return selectedProfil;
    }

    public void setSelectedProfil(Profil selectedProfil) {
        this.selectedProfil = selectedProfil;
    }

    public Boolean getAccesaccueil() {
        return accesaccueil;
    }

    public void setAccesaccueil(Boolean accesaccueil) {
        this.accesaccueil = accesaccueil;
    }

    public Boolean getAccesannuairemc() {
        return accesannuairemc;
    }

    public void setAccesannuairemc(Boolean accesannuairemc) {
        this.accesannuairemc = accesannuairemc;
    }

    public Boolean getAccesemissionpaie() {
        return accesemissionpaie;
    }

    public void setAccesemissionpaie(Boolean accesemissionpaie) {
        this.accesemissionpaie = accesemissionpaie;
    }

    public Boolean getAccessNouveauMc() {
        return accessnouveaumc;
    }

    public void setAccessNouveauMc(Boolean accessnouveaumc) {
        this.accessnouveaumc = accessnouveaumc;
    }

    public AuditlogManager getAudit() {
        return audit;
    }

    public void setAudit(AuditlogManager audit) {
        this.audit = audit;
    }

    public Boolean getAccessuivipaie() {
        return accessuivipaie;
    }

    public void setAccessuivipaie(Boolean accessuivipaie) {
        this.accessuivipaie = accessuivipaie;
    }

    public String getDatecreationProfil() {
        return datecreationProfil;
    }

    public void setDatecreationProfil(String datecreationProfil) {
        this.datecreationProfil = datecreationProfil;
    }

    public Boolean getEtatProfil() {
        return etatProfil;
    }

    public void setEtatProfil(Boolean etatProfil) {
        this.etatProfil = etatProfil;
    }

    public String getInfosProfil() {
        return infosProfil;
    }

    public void setInfosProfil(String infosProfil) {
        this.infosProfil = infosProfil;
    }

    public String getNomProfil() {
        return nomProfil;
    }

    public void setNomProfil(String nomProfil) {
        this.nomProfil = nomProfil;
    }

    public Profil getPro() {
        return pro;
    }

    public void setPro(Profil pro) {
        this.pro = pro;
    }

    public ProfilsManager getProfilManager() {
        return profilManager;
    }

    public void setProfilManager(ProfilsManager profilManager) {
        this.profilManager = profilManager;
    }

    public Boolean getAccesstatspaieparoperateurs() {
        return accesstatspaieparoperateurs;
    }

    public void setAccesstatspaieparoperateurs(Boolean accesstatspaieparoperateurs) {
        this.accesstatspaieparoperateurs = accesstatspaieparoperateurs;
    }

    public Boolean getAccesstatspaieparmc() {
        return accesstatspaieparmc;
    }

    public void setAccesstatspaieparmc(Boolean accesstatspaieparmc) {
        this.accesstatspaieparmc = accesstatspaieparmc;
    }

    public void loadProfil() {
        //retouver le profil en question grace a une methode find venant d'un ejb 
        this.pro = profilManager.getProfil(idProfil);
    }

//    public void nouveauProfil(){
//        this.pro = new Profil();
//    }
    //add new profil
    public void addProfil() {
        this.pro = new Profil();
        Boolean EtatProfil = Boolean.FALSE;
        this.accesaccueil = Boolean.TRUE;
        pro.setNom(nomProfil);
        pro.setInformations(infosProfil);
        pro.setAccesaccueil(this.accesaccueil);
        pro.setAccessuivipaie(this.accessuivipaie);
        pro.setAccesannuairemc(this.accesannuairemc);
        pro.setAccesemissionpaie(this.accesemissionpaie);
        pro.setAccesnouveaumc(this.accessnouveaumc);
        pro.setAccesstatspaieparmc(this.accesstatspaieparmc);
        pro.setAccesstatspaieparop(this.accesstatspaieparoperateurs);
        pro.setDatecreation(DateOfDay());
        pro.setEtat(EtatProfil);
        //empecher la création d'un profil qui existe déjà 
        //profil que je test 
        //System.out.println("le profil que je test "+pro.getNom());
        Profil profilName = profilManager.getProfilByName(pro.getNom());
        if (profilName != null) {
            msgErrorAlreadyExistProfil();
        }else{
             profilManager.persist(pro);
             profils.add(pro);
             //System.out.println("taille après :" + profils.size());
        msgSuccessProfilCreation(nomProfil);
        }        
        PrimeFaces.current().executeScript("PF('addProfilDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "addProfilDialog", "form:dt-products");
        //vider tous les champs 
        clearChamps();
        //logger
        saveLog("création d'un nouveau profil : " + pro.getNom(), DateOfDay());
//        pro = null;
    }


    /**
     * methode permettant de faire un update sur le profil
     *
     * @return
     */
    public String updateProfil() {
        System.out.println("modifi en cours...");
        pro = profilManager.updateProfil(pro);
        //return "gestionProfils";
        return "gestionProfils?faces-redirect=true";
    }

    /**
     * methode de maj d'un profil utilisateur
     */
    public void updateUserProfil() {
        //System.out.println("modif en cours du profil " + selectedProfil.getNom());
        LocalDateTime dt = LocalDateTime.now();
        //System.out.println("la valeur de la date : "+dt);
        //definition du patterne 
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        //affichage avec nouveau format 
        //System.out.println("le bon format c'est : " + dt.format(dtFormat));
        String dateModif = dt.format(dtFormat);
        // recuperation du user en session 
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        //System.out.println("loggin de l'action de l'utilisateur : " + session.getAttribute("utilisateur"));
        Utilisateur userCo = (Utilisateur) session.getAttribute("utilisateurConnecte");
        Auditlog log = new Auditlog();
        log.setAuteurIdutilisateur(userCo);
        log.setLogin(userCo.getLogin());
        log.setAction("modification du profil  : " + selectedProfil.getNom());
        log.setDateaction(dateModif);
        audit.persist(log);

        profilManager.updateProfil(selectedProfil);
        msgSuccesModif();
        //maj de la liste des profils
        PrimeFaces.current().executeScript("PF('manageProfilDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");

    }

    /**
     * suppresion d'un profil dans la base
     */
    public void deleteProfil() {
        //System.out.println("***********suppression du profil d'id :" + selectedProfil.getIdprofil());
        //verification de l'existance du profil en bd
        Profil leProfil = profilManager.verifProfil(selectedProfil.getNom());
        //System.out.println("le profil : "+leProfil+" existe bel et bien");
        //verification d'existance d'utilisateur ayant ce profil
        Integer nbreUserOfProfil = profilManager.verifProfilUsers(leProfil);
        if (nbreUserOfProfil>0) {
            msgErrorDeleteProfil();
            PrimeFaces.current().executeScript("PF('deleteProductDialog').hide()");
            PrimeFaces.current().ajax().update(":form:messages", ":form:dt-products");
        }else{
            msgSuccessDeleteProfil();
            PrimeFaces.current().executeScript("PF('deleteProductDialog').hide()");
            PrimeFaces.current().ajax().update(":form:messages", ":form:dt-products");
            System.out.println("supression en cours du profil : "+leProfil.getNom());
            profilManager.deleteProfil(leProfil.getIdprofil());
            profils.remove(leProfil);
            saveLog("suppression du profil : "+leProfil.getNom(), DateOfDay());
        }
        
    }

    //methode de vidage des champs creation nouveau profil 
    public void clearChamps() {
        this.nomProfil = null;
        this.infosProfil = null;
        this.accesaccueil = null;
        this.accessuivipaie = null;
        this.accesannuairemc = null;
        this.accesemissionpaie = null;
        this.accessnouveaumc = null;
        this.accesstatspaieparmc = null;
        this.accesstatspaieparoperateurs = null;
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
        log.setAction(msg +" par l'utilisateur "+userLogin);
        log.setDateaction(date);
        audit.persist(log);
    }

    public boolean hasSelectedProfils(){
        return this.selectedProfils !=null && !this.selectedProfils.isEmpty();
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

    public void msgSuccessDeleteProfil() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le profil à été supprimé", "succès"));
    }
    
        public void msgSuccessProfilCreation(String profilName) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le profil "+profilName+" à été crée", "  "));
    }

    public void msgErrorDeleteProfil() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Le profil n'a pas été supprimé", "existance d'utilisateur(s) avec ce profil"));
    }
    
       public void msgErrorAlreadyExistProfil() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Le profil ne peux être crée", "ce profil existe déjà"));
    }

    public void msgSuccesModif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profil à été modifié", "succès"));
    }
    
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                case STRING:
                    cell.setCellValue(cell.getStringCellValue().toUpperCase());
                    cell.setCellStyle(style);
                    break;
                }
            }
        }
    }

}
