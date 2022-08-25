/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Categorie;
import com.sbs.apiced_web.entities.Etablissement;
import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Usersnotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.CategorieMcManager;
import com.sbs.apiced_web.services.EtablissementManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.OperateurTelcoManager;
import com.sbs.apiced_web.services.TypeNotifManager;
import com.sbs.apiced_web.services.UsersNotifManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
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
@Named(value = "maitresAvaliderMBean")
@ViewScoped
public class MaitresAvaliderMBean implements Serializable {

    /**
     * Creates a new instance of MaitresAvaliderMBean
     */
    public MaitresAvaliderMBean() {
    }

    private String sortMc;
    private boolean value2;
    private Long nbreMcAvalider;
    private Maitrecommunautaire selectedMc;
    private List<Maitrecommunautaire> AllMc;
    private List<Maitrecommunautaire> listeMcEnAttenteDeValider;
    private List<Maitrecommunautaire> filteredMaitresCo;
    private List<String> listeMaitreCategories;
    private String newNomMc;
    private String newPrenomsMc;
    private String newMcGenre;
    private String newMcSitMatri;
    private Integer newMcNbrEnfants;
    private LocalDate newMcDateNaiss;
    private String newMcLieuNaiss;
    private String newMcDomicile;
    private String newMcNiveauSco;
    private String newMcDernierDiplome;
    private LocalDate newMcDatePriseFonction;
    private List<String> newMcClasses = new ArrayList<>();
    private Notifications notif = new Notifications();
    private Typenotifs typeNotification;
    private Utilisateur userCo;
    private String newMcContactdeux;
    private LocalDate dateprisedefonction;
    private String nouveauMatricule;
    private String nouvelEts;
    private List<String> listeNomsCate;
    private String libelleCate;
    private List<String> listeNomsEtablissements;
    private String newOperateurMc;
    private Boolean etatMoMo = Boolean.FALSE;
    private Boolean etatcomptemc;
    private Etablissement etsMc;
    private Categorie categorieMc;
    private String numeroPrincipal;
    private boolean statutwallet;
    private String motifModif;
    private List<Maitrecommunautaire> listeMcEnAttenteDeCorrection;
    private List<Utilisateur> listeUsers;

    @EJB
    private AuditlogManager audit;
    @EJB
    private TypeNotifManager typeNotifMgr;
    @EJB
    private UtilisateurManager utilisateurMgr;
    @EJB
    private NotifsManager notifMgr;
    @EJB
    private OperateurTelcoManager opTelcoMgr;
    @EJB
    private EtablissementManager etsMgr;
    @EJB
    private CategorieMcManager categMgr;
    @EJB
    private MaitreCoManager mcMgr;
    @EJB
    private UsersNotifManager userNotifMgr;

    public List<Maitrecommunautaire> getListeMcEnAttenteDeCorrection() {
        return listeMcEnAttenteDeCorrection;
    }

    public void setListeMcEnAttenteDeCorrection(List<Maitrecommunautaire> listeMcEnAttenteDeCorrection) {
        this.listeMcEnAttenteDeCorrection = listeMcEnAttenteDeCorrection;
    }

    public List<Utilisateur> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(List<Utilisateur> listeUsers) {
        this.listeUsers = listeUsers;
    }

    public String getSortMc() {
        return sortMc;
    }

    public void setSortMc(String sortMc) {
        this.sortMc = sortMc;
    }

    public Long getNbreMcAvalider() {
        return nbreMcAvalider;
    }

    public void setNbreMcAvalider(Long nbreMcAvalider) {
        this.nbreMcAvalider = nbreMcAvalider;
    }

    public boolean isValue2() {
        return value2;
    }

    public void setValue2(boolean value2) {
        this.value2 = value2;
    }

    public List<Maitrecommunautaire> getListeMcEnAttenteDeValider() {
        return listeMcEnAttenteDeValider;
    }

    public void setListeMcEnAttenteDeValider(List<Maitrecommunautaire> listeMcEnAttenteDeValider) {
        this.listeMcEnAttenteDeValider = listeMcEnAttenteDeValider;
    }

    public boolean isStatutwallet() {
        return statutwallet;
    }

    public void setStatutwallet(boolean statutwallet) {
        this.statutwallet = statutwallet;
    }

    public String getMotifModif() {
        return motifModif;
    }

    public void setMotifModif(String motifModif) {
        this.motifModif = motifModif;
    }

    public Boolean getStatutwallet() {
        return statutwallet;
    }

    public void setStatutwallet(Boolean statutwallet) {
        this.statutwallet = statutwallet;
    }

    public String getNumeroPrincipal() {
        return numeroPrincipal;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numeroPrincipal = numeroPrincipal;
    }

    public CategorieMcManager getCategMgr() {
        return categMgr;
    }

    public void setCategMgr(CategorieMcManager categMgr) {
        this.categMgr = categMgr;
    }

    public Etablissement getEtsMc() {
        return etsMc;
    }

    public void setEtsMc(Etablissement etsMc) {
        this.etsMc = etsMc;
    }

    public Boolean getEtatcomptemc() {
        return etatcomptemc;
    }

    public void setEtatcomptemc(Boolean etatcomptemc) {
        this.etatcomptemc = etatcomptemc;
    }

    public Boolean getEtatMoMo() {
        return etatMoMo;
    }

    public void setEtatMoMo(Boolean etatMoMo) {
        this.etatMoMo = etatMoMo;
    }

    public String getNewOperateurMc() {
        return newOperateurMc;
    }

    public void setNewOperateurMc(String newOperateurMc) {
        this.newOperateurMc = newOperateurMc;
    }

    public OperateurTelcoManager getOpTelcoMgr() {
        return opTelcoMgr;
    }

    public void setOpTelcoMgr(OperateurTelcoManager opTelcoMgr) {
        this.opTelcoMgr = opTelcoMgr;
    }

    public List<String> getListeNomsCate() {
        return listeNomsCate;
    }

    public void setListeNomsCate(List<String> listeNomsCate) {
        this.listeNomsCate = listeNomsCate;
    }

    public List<String> getListeNomsEtablissements() {
        return listeNomsEtablissements;
    }

    public void setListeNomsEtablissements(List<String> listeNomsEtablissements) {
        this.listeNomsEtablissements = listeNomsEtablissements;
    }

    public Categorie getCategorieMc() {
        return categorieMc;
    }

    public void setCategorieMc(Categorie categorieMc) {
        this.categorieMc = categorieMc;
    }

    public EtablissementManager getEtsMgr() {
        return etsMgr;
    }

    public void setEtsMgr(EtablissementManager etsMgr) {
        this.etsMgr = etsMgr;
    }

    //retourne la liste de tous les noms de catégorie professionelle
    public List<String> getListNomsCategorieMc() {
        if (listeNomsCate == null) {
            listeNomsCate = mcMgr.getAllCategorieName();
        }
        return listeNomsCate;
    }

    public Notifications getNotif() {
        return notif;
    }

    public void setNotif(Notifications notif) {
        this.notif = notif;
    }

    public Typenotifs getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(Typenotifs typeNotification) {
        this.typeNotification = typeNotification;
    }

    public Utilisateur getUserCo() {
        return userCo;
    }

    public void setUserCo(Utilisateur userCo) {
        this.userCo = userCo;
    }

    public String getNouveauMatricule() {
        return nouveauMatricule;
    }

    public void setNouveauMatricule(String nouveauMatricule) {
        this.nouveauMatricule = nouveauMatricule;
    }

    public String getNouvelEts() {
        return nouvelEts;
    }

    public void setNouvelEts(String nouvelEts) {
        this.nouvelEts = nouvelEts;
    }

    public TypeNotifManager getTypeNotifMgr() {
        return typeNotifMgr;
    }

    public void setTypeNotifMgr(TypeNotifManager typeNotifMgr) {
        this.typeNotifMgr = typeNotifMgr;
    }

    public UtilisateurManager getUtilisateurMgr() {
        return utilisateurMgr;
    }

    public void setUtilisateurMgr(UtilisateurManager utilisateurMgr) {
        this.utilisateurMgr = utilisateurMgr;
    }

    public NotifsManager getNotifMgr() {
        return notifMgr;
    }

    public void setNotifMgr(NotifsManager notifMgr) {
        this.notifMgr = notifMgr;
    }

    public String getLibelleCate() {
        return libelleCate;
    }

    public void setLibelleCate(String libelleCate) {
        this.libelleCate = libelleCate;
    }

    //retourne la liste des noms de tous les établissements scolaire
    public List<String> getListeNomsEtablissement() {
        if (listeNomsEtablissements == null) {
            listeNomsEtablissements = mcMgr.getAllEtablissementsName();
        }
        return listeNomsEtablissements;
    }

    public LocalDate getDateprisedefonction() {
        return dateprisedefonction;
    }

    public void setDateprisedefonction(LocalDate dateprisedefonction) {
        this.dateprisedefonction = dateprisedefonction;
    }

    public MaitreCoManager getMcMgr() {
        return mcMgr;
    }

    public void setMcMgr(MaitreCoManager mcMgr) {
        this.mcMgr = mcMgr;
    }

    public AuditlogManager getAudit() {
        return audit;
    }

    public void setAudit(AuditlogManager audit) {
        this.audit = audit;
    }

    public List<String> getNewMcClasses() {
        return newMcClasses;
    }

    public void setNewMcClasses(List<String> newMcClasses) {
        this.newMcClasses = newMcClasses;
    }

    public String getNewNomMc() {
        return newNomMc;
    }

    public void setNewNomMc(String newNomMc) {
        this.newNomMc = newNomMc;
    }

    public String getNewPrenomsMc() {
        return newPrenomsMc;
    }

    public void setNewPrenomsMc(String newPrenomsMc) {
        this.newPrenomsMc = newPrenomsMc;
    }

    public Maitrecommunautaire getSelectedMc() {
        return selectedMc;
    }

    public void setSelectedMc(Maitrecommunautaire selectedMc) {
        this.selectedMc = selectedMc;
    }

    public List<Maitrecommunautaire> getAllMc() {
        return AllMc;
    }

    public void setAllMc(List<Maitrecommunautaire> AllMc) {
        this.AllMc = AllMc;
    }

    public List<String> getListeMaitreCategories() {
        return listeMaitreCategories;
    }

    public void setListeMaitreCategories(List<String> listeMaitreCategories) {
        this.listeMaitreCategories = listeMaitreCategories;
    }

    public String getNewMcGenre() {
        return newMcGenre;
    }

    public void setNewMcGenre(String newMcGenre) {
        this.newMcGenre = newMcGenre;
    }

    public String getNewMcSitMatri() {
        return newMcSitMatri;
    }

    public void setNewMcSitMatri(String newMcSitMatri) {
        this.newMcSitMatri = newMcSitMatri;
    }

    public Integer getNewMcNbrEnfants() {
        return newMcNbrEnfants;
    }

    public void setNewMcNbrEnfants(Integer newMcNbrEnfants) {
        this.newMcNbrEnfants = newMcNbrEnfants;
    }

    public LocalDate getNewMcDateNaiss() {
        return newMcDateNaiss;
    }

    public void setNewMcDateNaiss(LocalDate newMcDateNaiss) {
        this.newMcDateNaiss = newMcDateNaiss;
    }

    public String getNewMcLieuNaiss() {
        return newMcLieuNaiss;
    }

    public void setNewMcLieuNaiss(String newMcLieuNaiss) {
        this.newMcLieuNaiss = newMcLieuNaiss;
    }

    public String getNewMcDomicile() {
        return newMcDomicile;
    }

    public void setNewMcDomicile(String newMcDomicile) {
        this.newMcDomicile = newMcDomicile;
    }

    public String getNewMcNiveauSco() {
        return newMcNiveauSco;
    }

    public void setNewMcNiveauSco(String newMcNiveauSco) {
        this.newMcNiveauSco = newMcNiveauSco;
    }

    public String getNewMcDernierDiplome() {
        return newMcDernierDiplome;
    }

    public void setNewMcDernierDiplome(String newMcDernierDiplome) {
        this.newMcDernierDiplome = newMcDernierDiplome;
    }

    public LocalDate getNewMcDatePriseFonction() {
        return newMcDatePriseFonction;
    }

    public void setNewMcDatePriseFonction(LocalDate newMcDatePriseFonction) {
        this.newMcDatePriseFonction = newMcDatePriseFonction;
    }

    public String getNewMcContactdeux() {
        return newMcContactdeux;
    }

    public void setNewMcContactdeux(String newMcContactdeux) {
        this.newMcContactdeux = newMcContactdeux;
    }

    @PostConstruct
    public void init() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        userCo = (Utilisateur) session.getAttribute("utilisateurConnecte");

        //listeMcEnAttenteDeCorrection = mcMgr.listeMcAcorriger();
        listeMcEnAttenteDeValider = mcMgr.listeMcAvalider();

        nbreMcAvalider = mcMgr.nbreMcAvalider();

    }

    public List<Maitrecommunautaire> getFilteredMaitresCo() {
        return filteredMaitresCo;
    }

    public void setFilteredMaitresCo(List<Maitrecommunautaire> filteredMaitresCo) {
        this.filteredMaitresCo = filteredMaitresCo;
    }

    //afficher la liste de tous les maitres communautaire pour l'annuaire
    public List<Maitrecommunautaire> listeAnnuaire() {
        return mcMgr.getAnnuaire();
    }

    /**
     * methode de validation du compte d'un nouveau mc
     */
    public void validerMc() {
        //System.out.println("la valeur recup  " + sortMc);
        if (sortMc.equalsIgnoreCase("valider")) {
            selectedMc.setValidationcoordonnateur(Boolean.TRUE);
           // selectedMc.setRejetvalidation(Boolean.FALSE);
            selectedMc.setDatemodifcompte(DateOfDay());
            selectedMc.setEtatcomptemc(Boolean.TRUE);//validation du compte
            selectedMc.setStatutcompte("actif");
            selectedMc.setIdvalideurcompte(userCo.getIdutilisateur().longValue());
            selectedMc.setDateactivationcompte(DateOfDay());
            selectedMc.setEtatretraite(Boolean.FALSE);
           // selectedMc.setMotifrejetvalidation(null);
            selectedMc.setMotifsuspension(motifModif);
            mcMgr.updateMaitre(selectedMc);
            msgSuccesValidationMc();
            listeMcEnAttenteDeValider.remove(selectedMc);
            nbreMcAvalider = nbreMcAvalider - 1;

            notif.setLibelle("Compte d'un nouvau maitre communautaire validé ");
            String details = "Validation du compte du nouveau maitre communautaire : " + selectedMc.getNom() + "  " + selectedMc.getPrenoms() + "  par le coordonnateur :" + userCo.getLogin();
            notif.setDetails(details);
            notif.setDatecreation(DateOfDay());
            BigDecimal typn = BigDecimal.valueOf(1);
            typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
            notif.setEtat(BigInteger.ZERO);
            notif.setTypenotif(typeNotification);
            notif.setCreateur(userCo);
            notif.setIdinfo(selectedMc.getId().toString());
            //save de la notif
            notifMgr.persist(notif);

            //persistance de toutes les notifications pour tous les utilisateurs ...
            //je recupère la liste de tous les users
            listeUsers = utilisateurMgr.getAllActivedUsers();
            //je recupère la dernière notif créée pour le setting a venir 
            Integer lastNotifId = notifMgr.lastNotif();
            //je le parcours et je cree un notifsuser avec un etat a zero...
            for (Utilisateur u : listeUsers) {
                System.out.println("le user en cours ... : " + u.getNom());
                Usersnotifs userNotif = new Usersnotifs();
                userNotif.setDateinsert(DateOfDay());
                userNotif.setEtat(BigInteger.ZERO);
                userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
                userNotif.setTitre("Validation du compte d'un nouveau maitre communautaire");
                userNotif.setInformation(details);
                userNotif.setCreateur(userCo.getLogin());
                userNotif.setTypeusernotif("MCVALIDE");
                //construction des btn en fonction des profils
                if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                    userNotif.setBtncorrigemc("false");
                } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                    userNotif.setBtncorrigemc("false");
                } else {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                    userNotif.setBtncorrigemc("false");
                }
                //setter l'id du notif
                userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
                //on persist la notifUser pr finir
                userNotifMgr.persist(userNotif);
            }

            Auditlog log = new Auditlog();
            log.setAuteurIdutilisateur(userCo);
            log.setLogin(userCo.getLogin());
            log.setAction("validation du maitre communautaire  : " + selectedMc.getNom() + " " + selectedMc.getPrenoms() + " par le coordonnateur  : " + userCo.getLogin());
            log.setDateaction(DateOfDay());
            audit.persist(log);
            //maj du tableau des mc et raffraichissement de la vue
            PrimeFaces.current().executeScript("PF('viewModifMcDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:tbl", "panelInfo");
            this.sortMc = null;
            this.motifModif = null;
//            selectedMc.setMotifrejetvalidation(null);
        } else {
            selectedMc.setValidationcoordonnateur(null);
         //   selectedMc.setRejetvalidation(Boolean.TRUE);
         //   selectedMc.setDaterejetvalidation(DateOfDay());
            selectedMc.setIdvalideurcompte(userCo.getIdutilisateur().longValue());
            selectedMc.setEtatretraite(Boolean.FALSE);
            selectedMc.setStatutcompte("non actif");
//            if (selectedMc.getMotifrejetvalidation() == null) {
//                selectedMc.setMotifrejetvalidation("corriger toutes les informations du maitre ....");
//            }
            selectedMc.setEtatcomptemc(Boolean.FALSE);
            selectedMc.setDatemodifcompte(DateOfDay());
            mcMgr.updateMaitre(selectedMc);
            msgRejetValidationMc();
            listeMcEnAttenteDeValider.remove(selectedMc);
            nbreMcAvalider = nbreMcAvalider - 1;

            notif.setLibelle("Compte du nouvau maitre communautaire " + selectedMc.getNom() + "   " + selectedMc.getPrenoms() + " rejeté pour correction ");
//            String details = "Validation du compte du nouveau maitre communautaire : " + selectedMc.getNom() + "  " + selectedMc.getPrenoms() + " rejeté  par le coordonnateur :" + userCo.getLogin() + "  motif : " + selectedMc.getMotifrejetvalidation();
  //          notif.setDetails(details);
            notif.setDatecreation(DateOfDay());
            BigDecimal typn = BigDecimal.valueOf(1);
            typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
            notif.setEtat(BigInteger.ZERO);
            notif.setTypenotif(typeNotification);
            notif.setCreateur(userCo);
            notif.setIdinfo(selectedMc.getId().toString());
            //save de la notif
            notifMgr.persist(notif);

            //persistance de toutes les notifications pour tous les utilisateurs ...
            //je recupère la liste de tous les users
            listeUsers = utilisateurMgr.getAllActivedUsers();
            //je recupère la dernière notif créée pour le setting a venir 
            Integer lastNotifId = notifMgr.lastNotif();
            //je le parcours et je cree un notifsuser avec un etat a zero...
            for (Utilisateur u : listeUsers) {
                System.out.println("le user en cours ... : " + u.getNom());
                Usersnotifs userNotif = new Usersnotifs();
                userNotif.setDateinsert(DateOfDay());
                userNotif.setEtat(BigInteger.ZERO);
                userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
                userNotif.setTitre("Validation rejetée du compte d'un nouveau maitre communautaire");
//                userNotif.setInformation(details);
                userNotif.setCreateur(userCo.getLogin());
                userNotif.setTypeusernotif("MCREJETE");

                //construction des btn en fonction des profils
                if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("false");
                    userNotif.setBtncorrigemc("true");
                } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                    userNotif.setBtncorrigemc("false");
                } else {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                    userNotif.setBtncorrigemc("false");
                }
                //setter l'id du notif
                userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
                //on persist la notifUser pr finir
                userNotifMgr.persist(userNotif);
            }

            Auditlog log = new Auditlog();
            log.setAuteurIdutilisateur(userCo);
            log.setLogin(userCo.getLogin());
            log.setAction("validation rejeté du maitre communautaire  : " + selectedMc.getNom() + " " + selectedMc.getPrenoms() + " par le coordonnateur  : " + userCo.getLogin());
            log.setDateaction(DateOfDay());
            audit.persist(log);
            //System.out.println("le mc sera rejeté... ");
            PrimeFaces.current().executeScript("PF('viewModifMcDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:tbl");
            this.sortMc = null;
//            selectedMc.setMotifrejetvalidation(null);

        }

    }

    //msg de succes de modification opérateur
    public void msgSuccesValidationMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "succès", "Le maître communautaire à été validé"));
    }

    //msg de succes de modification opérateur
    public void msgRejetValidationMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Rejet du Maitre", "Le maître communautaire à été réjété pour correction"));
    }

    public void addMessage() {
        String summary = selectedMc.getStatutwallet() ? "paiement activé" : "paiement désactivé";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null));
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

    public String DateOfDay() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String date = dt.format(dtFormat);
        return date;
    }

}
