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
import com.sbs.apiced_web.entities.Villes;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.CategorieMcManager;
import com.sbs.apiced_web.services.ClasseAffecteeManager;
import com.sbs.apiced_web.services.DiplomesManager;
import com.sbs.apiced_web.services.EtablissementManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.NiveauScolaireManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.OperateurTelcoManager;
import com.sbs.apiced_web.services.TypeNotifManager;
import com.sbs.apiced_web.services.UsersNotifManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import com.sbs.apiced_web.services.VilleManager;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author samuel
 */
@Named(value = "annuaireMBean")
@ViewScoped
public class AnnuaireMBean implements Serializable {

    private boolean value2;
    private String nomMc;
    private String prenoms;
    private Categorie newCateMc;
    private List<Categorie> listeCategories;
    private Maitrecommunautaire selectedMc;
    private  List<Maitrecommunautaire> listeUnMc = new ArrayList<>() ;
    private List<Maitrecommunautaire> AllMc = new ArrayList<>() ;
    private LazyDataModel<Maitrecommunautaire> lazyModel;
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
    private String userCo;
    private String newMcContactdeux;
    private List<String> listeClassesAffectees;
    private List<Villes> listeDesVilles;
    private List<String> listeNiveauSco;
    private List<String> listeNomsDiplome;
    private String nouvelleDateNaissance;
    private String nouvelleDatePriseFonction;
    private Villes lieudenaissance;
    private Villes domicileUtilisateur;
    private String lieuDeNaissanceMc;
    private String nniMc;
    private LocalDate dateprisedefonction;
    private String nouveauMatricule;
    private String nouvelEts;
    private List<String> listeNomsCate;
    private String libelleCate;
    private List<String> listeNomsEtablissements;
    private List<Etablissement> etablissements;
    private List<String> listeOperateurs;
    private String newOperateurMc;
    private Boolean etatMoMo = Boolean.FALSE;
    private Boolean etatcomptemc;
    private Etablissement etsMc;
    private Categorie categorieMc;
    private String numeroPrincipal;
    private boolean statutwallet;
    private String motifModif;
    private List<Utilisateur> listeUsers;
    private List<Maitrecommunautaire> listeAnnuaire;
    private String genreMc;
    private String numeroUnMc;
    private String contactDeuxMc;
     private Long totalMaitreCommunautaire ;

    @EJB
    private CategorieMcManager categManager;
    @EJB
    private ClasseAffecteeManager classeAffecteeMgr;
    @EJB
    private VilleManager villeMgr;
    @EJB
    private NiveauScolaireManager nivoScoMgr;
    @EJB
    private DiplomesManager dernierDiplomeMgr;
    @EJB
    private MaitreCoManager mcMgr;
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
    private UsersNotifManager userNotifMgr;

    @PostConstruct
    public void init() {

        //lazyModel = new LazyMaitreCommunautaireDataModel(mcMgr.allMc()); // mcMgr.allMc() = liste de donnees

        AllMc = mcMgr.allMaitreCommunautaires();
        //listeAnnuaire = mcMgr.listeAnnuaire();
        listeClassesAffectees = classeAffecteeMgr.allClassesNames();
        listeDesVilles = villeMgr.getAllVilles();
        listeNiveauSco = nivoScoMgr.allNiveauScolaireNames();
        listeNomsDiplome = dernierDiplomeMgr.allDernierDiplomesNames();
        // recuperation du user en session 
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        userCo = session.getAttribute("utilisateur").toString();
                //rnbre total de mc en base 
        totalMaitreCommunautaire =  mcMgr.nbreTotalDesMcs();

        listeOperateurs = opTelcoMgr.getAllOperateurTelcoNames();
        etablissements = etsMgr.getEtablissements();
        listeCategories = categMgr.allMcCategorie();

    }

    public List<Maitrecommunautaire> getListeUnMc() {
        return listeUnMc;
    }

    public void setListeUnMc(List<Maitrecommunautaire> listeUnMc) {
        this.listeUnMc = listeUnMc;
    }
    
    
       public Long getTotalMaitreCommunautaire() {
        return totalMaitreCommunautaire;
    }

    public void setTotalMaitreCommunautaire(Long totalMaitreCommunautaire) {
        this.totalMaitreCommunautaire = totalMaitreCommunautaire;
    }


    public String getNumeroUnMc() {
        return numeroUnMc;
    }

    public void setNumeroUnMc(String numeroUnMc) {
        this.numeroUnMc = numeroUnMc;
    }

    public Categorie getNewCateMc() {
        return newCateMc;
    }

    public void setNewCateMc(Categorie newCateMc) {
        this.newCateMc = newCateMc;
    }

    public String getLieuDeNaissanceMc() {
        return lieuDeNaissanceMc;
    }

    public List<Categorie> getListeCategories() {
        return listeCategories;
    }

    public void setListeCategories(List<Categorie> listeCategories) {
        this.listeCategories = listeCategories;
    }

    public void setLieuDeNaissanceMc(String lieuDeNaissanceMc) {
        this.lieuDeNaissanceMc = lieuDeNaissanceMc;
    }

    public String getNniMc() {
        return nniMc;
    }

    public void setNniMc(String nniMc) {
        this.nniMc = nniMc;
    }

    public String getContactDeuxMc() {
        return contactDeuxMc;
    }

    public void setContactDeuxMc(String contactDeuxMc) {
        this.contactDeuxMc = contactDeuxMc;
    }

    public String getNOMMc() {
        return nomMc;
    }

    public void setNomMc(String nomMc) {
        this.nomMc = nomMc;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getGenreMc() {
        return genreMc;
    }

    public void setGenreMc(String genreMc) {
        this.genreMc = genreMc;
    }

    public List<Maitrecommunautaire> getListeAnnuaire() {
        return listeAnnuaire;
    }

    public void setListeAnnuaire(List<Maitrecommunautaire> listeAnnuaire) {
        this.listeAnnuaire = listeAnnuaire;
    }

    public UsersNotifManager getUserNotifMgr() {
        return userNotifMgr;
    }

    public void setUserNotifMgr(UsersNotifManager userNotifMgr) {
        this.userNotifMgr = userNotifMgr;
    }

    //converter des villes a afficher 
    private Converter villesConverter = new Converter() {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            return villeMgr.findVilleById(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return ((Villes) value).getIdville().toString();
        }
    };

    //converter des categories
    private Converter categoriesConverter = new Converter() {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            return categMgr.findCategorieById(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return ((Categorie) value).getIdcategorie().toString();
        }
    };
    //converter des etablissements
    private Converter etablissementConverter = new Converter() {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            Double d = new Double(value);
            return etsMgr.findEtsById(BigDecimal.valueOf(d));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return ((Etablissement) value).getIdetablissement().toString();
        }
    };

    public Converter getCategoriesConverter() {
        return categoriesConverter;
    }

    public void setCategoriesConverter(Converter categoriesConverter) {
        this.categoriesConverter = categoriesConverter;
    }

    public List<Etablissement> getEtablissements() {
        return etablissements;
    }

    public void setEtablissements(List<Etablissement> etablissements) {
        this.etablissements = etablissements;
    }

    public boolean isStatutwallet() {
        return statutwallet;
    }

    public void setStatutwallet(boolean statutwallet) {
        this.statutwallet = statutwallet;
    }

    public List<Utilisateur> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(List<Utilisateur> listeUsers) {
        this.listeUsers = listeUsers;
    }

    public boolean isValue2() {
        return value2;
    }

    public void setValue2(boolean value2) {
        this.value2 = value2;
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

    public List<String> getListeOperateurs() {
        return listeOperateurs;
    }

    public void setListeOperateurs(List<String> listeOperateurs) {
        this.listeOperateurs = listeOperateurs;
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

    public String getUserCo() {
        return userCo;
    }

    public void setUserCo(String userCo) {
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

    public Villes getLieudenaissance() {
        return lieudenaissance;
    }

    public void setLieudenaissance(Villes lieudenaissance) {
        this.lieudenaissance = lieudenaissance;
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

    public Villes getDomicileUtilisateur() {
        return domicileUtilisateur;
    }

    public void setDomicileUtilisateur(Villes domicileUtilisateur) {
        this.domicileUtilisateur = domicileUtilisateur;
    }

    public String getNouvelleDatePriseFonction() {
        return nouvelleDatePriseFonction;
    }

    public void setNouvelleDatePriseFonction(String nouvelleDatePriseFonction) {
        this.nouvelleDatePriseFonction = nouvelleDatePriseFonction;
    }

    public String getNouvelleDateNaissance() {
        return nouvelleDateNaissance;
    }

    public void setNouvelleDateNaissance(String nouvelleDateNaissance) {
        this.nouvelleDateNaissance = nouvelleDateNaissance;
    }

    public List<String> getNewMcClasses() {
        return newMcClasses;
    }

    public void setNewMcClasses(List<String> newMcClasses) {
        this.newMcClasses = newMcClasses;
    }

    public DiplomesManager getDernierDiplomeMgr() {
        return dernierDiplomeMgr;
    }

    public void setDernierDiplomeMgr(DiplomesManager dernierDiplomeMgr) {
        this.dernierDiplomeMgr = dernierDiplomeMgr;
    }

    public List<String> getListeNomsDiplome() {
        return listeNomsDiplome;
    }

    public void setListeNomsDiplome(List<String> listeNomsDiplome) {
        this.listeNomsDiplome = listeNomsDiplome;
    }

    public NiveauScolaireManager getNivoScoMgr() {
        return nivoScoMgr;
    }

    public void setNivoScoMgr(NiveauScolaireManager nivoScoMgr) {
        this.nivoScoMgr = nivoScoMgr;
    }

    public List<String> getListeClassesAffectees() {
        return listeClassesAffectees;
    }

    public void setListeClassesAffectees(List<String> listeClassesAffectees) {
        this.listeClassesAffectees = listeClassesAffectees;
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

    public String getNomMc() {
        return nomMc;
    }

    public LazyDataModel<Maitrecommunautaire> getLazyModel() {
        System.out.println("--->" + lazyModel);
        return lazyModel;
    }

    public List<String> getListeMaitreCategories() {
        return listeMaitreCategories;
    }

    public void setListeMaitreCategories(List<String> listeMaitreCategories) {
        this.listeMaitreCategories = listeMaitreCategories;
    }

    public CategorieMcManager getCategManager() {
        return categManager;
    }

    public void setCategManager(CategorieMcManager categManager) {
        this.categManager = categManager;
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

    public ClasseAffecteeManager getClasseAffecteeMgr() {
        return classeAffecteeMgr;
    }

    public void setClasseAffecteeMgr(ClasseAffecteeManager classeAffecteeMgr) {
        this.classeAffecteeMgr = classeAffecteeMgr;
    }

    public VilleManager getVilleMgr() {
        return villeMgr;
    }

    public void setVilleMgr(VilleManager villeMgr) {
        this.villeMgr = villeMgr;
    }

    public Converter getVillesConverter() {
        return villesConverter;
    }

    public void setVillesConverter(Converter villesConverter) {
        this.villesConverter = villesConverter;
    }

    public List<Villes> getListeDesVilles() {
        return listeDesVilles;
    }

    public void setListeDesVilles(List<Villes> listeDesVilles) {
        this.listeDesVilles = listeDesVilles;
    }

    public List<String> getListeNiveauSco() {
        return listeNiveauSco;
    }

    public void setListeNiveauSco(List<String> listeNiveauSco) {
        this.listeNiveauSco = listeNiveauSco;
    }

    public Converter getEtablissementConverter() {
        return etablissementConverter;
    }

    public void setEtablissementConverter(Converter etablissementConverter) {
        this.etablissementConverter = etablissementConverter;
    }

    public List<Maitrecommunautaire> getFilteredMaitresCo() {
        return filteredMaitresCo;
    }

    public void setFilteredMaitresCo(List<Maitrecommunautaire> filteredMaitresCo) {
        this.filteredMaitresCo = filteredMaitresCo;
    }

    //methode retournant la liste de toutes les catégorie sans celle du mc sélectionné
    public List<String> getListeNomsCategorie() {
        if (listeMaitreCategories == null) {
            listeMaitreCategories = categManager.getAllCategoriesNames();
        }
        return listeMaitreCategories;
    }

    public List<Maitrecommunautaire> getAllMc() {
        return AllMc;
    }

    public void setAllMc(List<Maitrecommunautaire> AllMc) {
        this.AllMc = AllMc;
    }
    
    

    /**
     * modif mineure infos MC
     */
    public void modifMineurInfos() {
        //recup de la categorie 
        //System.out.println("la categorie et le bailleur du mc sont : "+selectedMc.getIdcategoriepro().getLibelle()+"  "+selectedMc.getBailleur().getNOM());
        notif = new Notifications();
//        if (selectedMc.getBailleur().getNOM().equalsIgnoreCase("PREAT")) {
        if (selectedMc.getBailleur().equalsIgnoreCase("PREAT")) {
            System.out.println("attention il est du preat");
            if (!newCateMc.getLibelle().equalsIgnoreCase("CATEGORIE 1")) {
                //System.out.println("oups erreur");
                System.out.println("LA VALEUR EST : " + newCateMc.getLibelle());

                msgErreurCategorie();
            } else {
                DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
                Utilisateur createur = utilisateurMgr.verif(userCo);
//                //nom
//                if (nomMc != null) {
//                    selectedMc.setNom(nomMc);
//                } else {
//                    selectedMc.setNom(selectedMc.getNom());
//                }
//                //prenom
//                if (prenoms != null) {
//                    selectedMc.setPrenoms(prenoms);
//                } else {
//                    selectedMc.setPrenoms(selectedMc.getPrenoms());
//                }

                //categorie
                selectedMc.setCategoriepro(newCateMc.getLibelle());
                //genre 
                if (genreMc != null) {
                    selectedMc.setGenre(genreMc);
                }
                //contact n°1
                if (numeroUnMc != null) {
                    selectedMc.setContactun(numeroUnMc);
                } else {
                    selectedMc.setContactun(selectedMc.getContactun());
                }
                //contactDeux
                if (contactDeuxMc != null) {
                    selectedMc.setContactdeux(contactDeuxMc);
                } else {
                    selectedMc.setContactdeux(selectedMc.getContactdeux());
                }

                //date de naissance 
                if (newMcDateNaiss != null) {
                    //formattage des date a enregistrer
                    nouvelleDateNaissance = f.format(newMcDateNaiss);
                    selectedMc.setDatenaissance(nouvelleDateNaissance);
                }
                //lieu de naissance 
                if (lieuDeNaissanceMc != null) {
                    selectedMc.setLieudenaissance(lieuDeNaissanceMc);
                } else {
                    selectedMc.setLieudenaissance(selectedMc.getLieudenaissance());
                }

                //domicile 
                if (nniMc != null) {
                    selectedMc.setNni(nniMc);
                } else {
                    selectedMc.setNni(selectedMc.getNni());
                }

                //ecole
                if (etsMc != null) {
                    selectedMc.setEcole(etsMc.getNom());
                }
                //date de prise de fonction 
                if (newMcDatePriseFonction != null) {
                    nouvelleDatePriseFonction = f.format(newMcDatePriseFonction);
                    selectedMc.setDateprisefonction(nouvelleDatePriseFonction);
                }
                //classes affectees 
                if (newMcClasses.size() > 0) {
                    //on parcours la liste remplis , pour remplire l'ancienne en l'ayant d'abord vidée
                    StringBuilder str = new StringBuilder();
                    for (String classe : newMcClasses) {
                        str.append(classe);
                        str.append(" ");
                    }
                    selectedMc.setClasseaffectee(str.toString());
                }

                //statut wallet et etat compte reste inchangé 
                selectedMc.setEtatcomptemc(selectedMc.getEtatcomptemc());
                selectedMc.setStatutwallet(selectedMc.getStatutwallet());
                mcMgr.updateMaitre(selectedMc);
                //auditlog
                saveLog("mise a jour des informations du maitre  " + selectedMc.getNom(), DateOfDay());
                //enregistrement de la notif
                BigDecimal typn = BigDecimal.valueOf(6);
                typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
                String details = "mise a jour des informations du maitre communautaire : " + selectedMc.getNom() + "  " + selectedMc.getPrenoms() + "  par le coordonnateur :" + userCo;
                String libelleNotif = "Mise à jour des informations du maitre communautaire     " + selectedMc.getNom() + "  " + selectedMc.getPrenoms();
                notif.setLibelle(libelleNotif);
                notif.setDetails(details);
                notif.setDatecreation(DateOfDay());
                notif.setDateresolution(DateOfDay());
                notif.setEtat(BigInteger.ZERO);
                notif.setTypenotif(typeNotification);
                notif.setCreateur(createur);
                notif.setIdinfo(selectedMc.getId().toString());
                //save de la notif
                notifMgr.persist(notif);

                //persistance de toutes les notifications pour tous les utilisateurs ...
                //je recupère la liste de tous les users
                listeUsers = utilisateurMgr.getAllActivedUsers();
                //je recupère la dernière notif créée pour le setting a venir 
                Integer lastNotifId = notifMgr.lastNotif();

                //creation des btns 
                //je le parcours et je cree un notifsuser avec un etat a zero...
                for (Utilisateur u : listeUsers) {
                    //System.out.println("le user en cours ... : " + u.getNOM());
                    Usersnotifs userNotif = new Usersnotifs();
                    userNotif.setDateinsert(DateOfDay());
                    userNotif.setEtat(BigInteger.ZERO);
                    userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
                    userNotif.setTitre(libelleNotif);
                    userNotif.setInformation(details);
                    userNotif.setCreateur(userCo);
                    userNotif.setTypeusernotif("MODIF_MC");

                    //construction des btn en fonction des profils
                    if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                        userNotif.setBtnvalidemc("false");
                        userNotif.setBtnvalidepaie("false");
                        userNotif.setBtndetail("true");
                    } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                        userNotif.setBtnvalidemc("false");
                        userNotif.setBtnvalidepaie("false");
                        userNotif.setBtndetail("true");
                    } else {
                        userNotif.setBtnvalidemc("false");
                        userNotif.setBtnvalidepaie("false");
                        userNotif.setBtndetail("true");
                    }

                    //setter l'id du notif
                    userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
                    //on persist la notifUser pr finir
                    userNotifMgr.persist(userNotif);
                }
                msgSuccesModif();
            }

        } else {//alors le parset 2
            System.out.println("il est du parset 2");

            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
            Utilisateur createur = utilisateurMgr.verif(userCo);
//                //nom
//                if (nomMc != null) {
//                    selectedMc.setNom(nomMc);
//                } else {
//                    selectedMc.setNom(selectedMc.getNom());
//                }
//
//                //prenom
//                if (prenoms != null) {
//                    selectedMc.setPrenoms(prenoms);
//                } else {
//                    selectedMc.setPrenoms(selectedMc.getPrenoms());
//                }
            //genre 
            if (genreMc != null) {
                selectedMc.setGenre(genreMc);
            }

            //categorie
            selectedMc.setCategoriepro(newCateMc.getLibelle());

            //contact n°1
            if (numeroUnMc != null) {
                selectedMc.setContactun(numeroUnMc);
            } else {
                selectedMc.setContactun(selectedMc.getContactun());
            }
            //contactDeux
            if (contactDeuxMc != null) {
                selectedMc.setContactdeux(contactDeuxMc);
            } else {
                selectedMc.setContactdeux(selectedMc.getContactdeux());
            }

            //date de naissance 
            if (newMcDateNaiss != null) {
                //formattage des date a enregistrer
                nouvelleDateNaissance = f.format(newMcDateNaiss);
                selectedMc.setDatenaissance(nouvelleDateNaissance);
            }
            //lieu de naissance 
            if (lieuDeNaissanceMc != null) {
                selectedMc.setLieudenaissance(lieuDeNaissanceMc);
            } else {
                selectedMc.setLieudenaissance(selectedMc.getLieudenaissance());
            }

            //domicile 
            if (nniMc != null) {
                selectedMc.setNni(nniMc);
            } else {
                selectedMc.setNni(selectedMc.getNni());
            }

            //ecole
            if (etsMc != null) {
                selectedMc.setEcole(etsMc.getNom());
            }

//        //niveau scolaire 
//        if (newMcNiveauSco != null) {
//            selectedMc.setNiveauscolaire(newMcNiveauSco);
//        }
//        //dernier diplôme 
//        if (newMcDernierDiplome != null) {
//            selectedMc.setDernierdiplome(newMcDernierDiplome);
//        }
            //date de prise de fonction 
            //date de prise de fonction 
            if (newMcDatePriseFonction != null) {
                nouvelleDatePriseFonction = f.format(newMcDatePriseFonction);
                selectedMc.setDateprisefonction(nouvelleDatePriseFonction);
            }
            //classes affectees 
            if (newMcClasses.size() > 0) {
                //on parcours la liste remplis , pour remplire l'ancienne en l'ayant d'abord vidée
                StringBuilder str = new StringBuilder();
                for (String classe : newMcClasses) {
                    str.append(classe);
                    str.append(" ");
                }
                selectedMc.setClasseaffectee(str.toString());
            }

            //statut wallet et etat compte reste inchangé 
            selectedMc.setEtatcomptemc(selectedMc.getEtatcomptemc());
            selectedMc.setStatutwallet(selectedMc.getStatutwallet());
            mcMgr.updateMaitre(selectedMc);
            //auditlog
            saveLog("mise a jour des informations du maitre  " + selectedMc.getNom(), DateOfDay());
            //enregistrement de la notif
            BigDecimal typn = BigDecimal.valueOf(6);
            typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
            String details = "mise a jour des informations du maitre communautaire : " + selectedMc.getNom() + "  " + selectedMc.getPrenoms() + "  par le coordonnateur :" + userCo;
            String libelleNotif = "Mise à jour des informations du maitre communautaire     " + selectedMc.getNom() + "  " + selectedMc.getPrenoms();
            notif.setLibelle(libelleNotif);
            notif.setDetails(details);
            notif.setDatecreation(DateOfDay());
            notif.setDateresolution(DateOfDay());
            notif.setEtat(BigInteger.ZERO);
            notif.setTypenotif(typeNotification);
            notif.setCreateur(createur);
            notif.setIdinfo(selectedMc.getId().toString());
            //save de la notif
            notifMgr.persist(notif);

            //persistance de toutes les notifications pour tous les utilisateurs ...
            //je recupère la liste de tous les users
            listeUsers = utilisateurMgr.getAllActivedUsers();
            //je recupère la dernière notif créée pour le setting a venir 
            Integer lastNotifId = notifMgr.lastNotif();

            //creation des btns 
            //je le parcours et je cree un notifsuser avec un etat a zero...
            for (Utilisateur u : listeUsers) {
                //System.out.println("le user en cours ... : " + u.getNOM());
                Usersnotifs userNotif = new Usersnotifs();
                userNotif.setDateinsert(DateOfDay());
                userNotif.setEtat(BigInteger.ZERO);
                userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
                userNotif.setTitre(libelleNotif);
                userNotif.setInformation(details);
                userNotif.setCreateur(userCo);
                userNotif.setTypeusernotif("MODIF_MC");
                //construction des btn en fonction des profils
                if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                } else {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                }
                //setter l'id du notif
                userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
                //on persist la notifUser pr finir
                userNotifMgr.persist(userNotif);
            }
            msgSuccesModif();

        }
        //maj du tableau des mc et raffraichissement de la vue 
        PrimeFaces.current().executeScript("PF('viewModifMcDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:tbl");
        clearChampsAfterMajMineure();
        notif = null;
    }

    //modification du matricul du mc par le coordo
    public void modifMatriculeWithMotif() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
        Utilisateur createur = utilisateurMgr.verif(userCo);
        selectedMc.setDatemodifcompte(DateOfDay());
        //le matricule est deja changé
        System.out.println("nouvelle valeures " + selectedMc.getMatricule() + "  " + this.motifModif + " " + selectedMc.getDatemodifcompte());
        msgSuccesModifMatricule();
        //auditlog
        saveLog("modification du matricule du maitre  " + selectedMc.getNom(), DateOfDay());
        BigDecimal typn = BigDecimal.valueOf(6);
        typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
        String details = "Mise à jour du  matricule du maitre  " + selectedMc.getNom() + "  " + selectedMc.getPrenoms() + "  par le coordonnateur :" + userCo;
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
        mcMgr.updateMaitre(selectedMc);
        PrimeFaces.current().executeScript("PF('modifMatriculeDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        clearChampsAfterMajMineure();
        //je recupère la liste de tous les users
        listeUsers = utilisateurMgr.getAllActivedUsers();
//        //je recupère la dernière notif créée pour le setting a venir 
        Integer lastNotifId = notifMgr.lastNotif();
//
        //creation des btns 
        //je le parcours et je cree un notifsuser avec un etat a zero...
        for (Utilisateur u : listeUsers) {
            //System.out.println("le user en cours ... : " + u.getNOM());
            Usersnotifs userNotif = new Usersnotifs();
            userNotif.setDateinsert(DateOfDay());
            userNotif.setEtat(BigInteger.ZERO);
            userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
            userNotif.setTitre("Modification du matricule d'un maitre communautaire");
            userNotif.setInformation(details);
            userNotif.setCreateur(userCo);
            userNotif.setTypeusernotif("MODIF_MC");

            //construction des btn en fonction des profils
            if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                userNotif.setBtnvalidemc("false");
                userNotif.setBtnvalidepaie("false");
                userNotif.setBtndetail("true");
            } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                userNotif.setBtnvalidemc("false");
                userNotif.setBtnvalidepaie("false");
                userNotif.setBtndetail("true");
            } else {
                userNotif.setBtnvalidemc("false");
                userNotif.setBtnvalidepaie("false");
                userNotif.setBtndetail("true");
            }

            //setter l'id du notif
            userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
            //on persist la notifUser pr finir
            userNotifMgr.persist(userNotif);
            notif = null;
        }
        motifModif = null;
    }

    //modif de la catégorie pro par le coordo
    public void modifCategProWithMotif() {
//categ pro : promotion d'un maitre
        if (libelleCate != null) {
            //recuperation de la categorie du mc a partir du nom selectionné        
            categorieMc = categMgr.getCategorieByName(libelleCate);
            selectedMc.setCategoriepro(newCateMc.getLibelle());

        }
        //maj du tableau des mc et raffraichissement de la vue 
        PrimeFaces.current().executeScript("PF('modifCategorieProDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");

        msgSuccesModifCategPro();

        Utilisateur createur = utilisateurMgr.verif(userCo);
        selectedMc.setDatemodifcompte(DateOfDay());
        mcMgr.updateMaitre(selectedMc);
        //System.out.println("catégorie choisie : " + libelleCate + " le motif " + motifModif + "  a la date :" + DateOfDay());
        //auditlog
        saveLog("modification de la catéogorie professionnelle du maitre  " + selectedMc.getNom(), DateOfDay());
        BigDecimal typn = BigDecimal.valueOf(7);
        typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
        String details = "Mise à jour de la catégorie proféssionnelle du maitre  " + selectedMc.getNom() + "  " + selectedMc.getPrenoms() + "  par le coordonnateur " + userCo;
        notif.setLibelle("modification de la catégorie de la catégorie professionnelle du maitre");
        notif.setDetails(details);
        notif.setDatecreation(DateOfDay());
        notif.setDateresolution(DateOfDay());
        notif.setTypenotif(typeNotification);
        notif.setCreateur(createur);
        notif.setIdinfo(selectedMc.getId().toString());
        //save de la notif
        notifMgr.persist(notif);
        //persistance de toutes les notifications pour tous les utilisateurs ...
        //je recupère la liste de tous les users
        listeUsers = utilisateurMgr.getAllActivedUsers();
        //je recupère la dernière notif créée pour le setting a venir 
        Integer lastNotifId = notifMgr.lastNotif();
        //creation des btns 
        //je le parcours et je cree un notifsuser avec un etat a zero...
        for (Utilisateur u : listeUsers) {
            //System.out.println("le user en cours ... : " + u.getNOM());
            Usersnotifs userNotif = new Usersnotifs();
            userNotif.setDateinsert(DateOfDay());
            userNotif.setEtat(BigInteger.ZERO);
            userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
            userNotif.setTitre("Modification de la catégorie d'un maitre communautaire");
            userNotif.setInformation(details);
            userNotif.setCreateur(userCo);
            userNotif.setTypeusernotif("MODIF_MC");
            //construction des btn en fonction des profils
            if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                userNotif.setBtnvalidemc("false");
                userNotif.setBtnvalidepaie("false");
                userNotif.setBtndetail("true");
            } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                userNotif.setBtnvalidemc("false");
                userNotif.setBtnvalidepaie("false");
                userNotif.setBtndetail("true");
            } else {
                userNotif.setBtnvalidemc("false");
                userNotif.setBtnvalidepaie("false");
                userNotif.setBtndetail("true");
            }
            //setter l'id du notif
            userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
            //on persist la notifUser pr finir
            userNotifMgr.persist(userNotif);
        }
        libelleCate = null;
        selectedMc = null;
        notif = null;
        motifModif = null;
    }

    public void modifEtsWithMotif() {
        //System.out.println("établissement choisi : "+nouvelEts+" le motif "+motifModif);
        // etablissement
        if (nouvelEts != null) {
            //recup de l'établissement a partir du nom choisi
            etsMc = etsMgr.getEtsByNom(nouvelEts);
            selectedMc.setEcole(etsMc.getNom());
        }
        Utilisateur createur = utilisateurMgr.verif(userCo);
        selectedMc.setDatemodifcompte(DateOfDay());
        //on persite le motif dans la table des notifs
        msgSuccesModifEtablissement();
        mcMgr.updateMaitre(selectedMc);
        //auditlog
        saveLog("modification de l'établissement d'enseignement du maitre  " + selectedMc.getNom(), DateOfDay());
        BigDecimal typn = BigDecimal.valueOf(8);
        typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
        String details = "Mise à jour du  matricule du maitre  " + selectedMc.getNom() + "  " + selectedMc.getPrenoms();
        notif.setLibelle(motifModif);
        notif.setDetails(details);
        notif.setDatecreation(DateOfDay());
        notif.setDateresolution(DateOfDay());
        notif.setTypenotif(typeNotification);
        notif.setCreateur(createur);
        notif.setIdinfo(selectedMc.getId().toString());
        //save de la notif
        notifMgr.persist(notif);
        //maj du tableau des mc et raffraichissement de la vue 
        PrimeFaces.current().executeScript("PF('modifEtablissementDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        nouvelEts = null;
        selectedMc = null;
        motifModif = null;
        notif = null;

    }

    public void infosPaieWithMotif() { //modification des informations de paiement par le coordo
        notif = new Notifications();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
        Utilisateur createur = utilisateurMgr.verif(userCo);

        //operateur
        System.out.println("la valeur renvoyée : " + newOperateurMc);
        if (newOperateurMc.equalsIgnoreCase(selectedMc.getOperatortelco())) {
            selectedMc.setOperatortelco(selectedMc.getOperatortelco());
        } else {
            selectedMc.setOperatortelco(newOperateurMc);
        }

        //num principal
        if (numeroPrincipal.length() > 0) {
            selectedMc.setContactun(numeroPrincipal);
        } else {
            selectedMc.setContactun(selectedMc.getContactun());
        }
        //System.out.println("numéro qui va etre stocke " + selectedMc.getContactun());
        //compte momo
        //selectedMc.setStatutwallet(value2);
        if (selectedMc.getStatutwallet()) {//si c a true
            System.out.println("setting de la valeur de statut wallet a actif " + selectedMc.getStatutwallet());
            selectedMc.setValeurestatutwallet("actif");
            selectedMc.setStatutwallet(selectedMc.getStatutwallet());
        } else {
            System.out.println("setting de la valeur de statut wallet a non actif  " + selectedMc.getStatutwallet());
            selectedMc.setValeurestatutwallet("non actif");
            selectedMc.setStatutwallet(selectedMc.getStatutwallet());
        }
        selectedMc.setMotifsuspension(motifModif);
        msgSuccesModifInfosPaie();
        mcMgr.updateMaitre(selectedMc);
        //auditlog
        saveLog("modification des informations de paiement du maitre :" + selectedMc.getNom(), DateOfDay());
        //enregistrement de la notif
        BigDecimal typn = BigDecimal.valueOf(9);
        typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
        String libelleNotif = "modification des informations de paiement du maitre " + selectedMc.getNom() + " " + selectedMc.getPrenoms();
        notif.setLibelle(libelleNotif);
        String details = "Mise à jour des informations de paiement du maitre  " + selectedMc.getNom() + "  " + selectedMc.getPrenoms() + "  par le coordonnateur" + userCo;
        notif.setDetails(details);
        notif.setDatecreation(DateOfDay());
        notif.setDateresolution(DateOfDay());
        notif.setEtat(BigInteger.ZERO);
        notif.setTypenotif(typeNotification);
        notif.setCreateur(createur);
        notif.setIdinfo(selectedMc.getId().toString());
        //save de la notif
        notifMgr.persist(notif);
        //persistance de toutes les notifications pour tous les utilisateurs ...
        //je recupère la liste de tous les users
        listeUsers = utilisateurMgr.getAllActivedUsers();
        //je recupère la dernière notif créée pour le setting a venir 
        Integer lastNotifId = notifMgr.lastNotif();
        //creation des btns 
        //je le parcours et je cree un notifsuser avec un etat a zero...
        for (Utilisateur u : listeUsers) {
            //System.out.println("le user en cours ... : " + u.getNOM());
            Usersnotifs userNotif = new Usersnotifs();
            userNotif.setDateinsert(DateOfDay());
            userNotif.setEtat(BigInteger.ZERO);
            userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
            userNotif.setTitre(libelleNotif);
            userNotif.setInformation(details);
            userNotif.setCreateur(userCo);
            userNotif.setTypeusernotif("MODIF_MC");

            //construction des btn en fonction des profils
            if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                userNotif.setBtnvalidemc("false");
                userNotif.setBtnvalidepaie("false");
                userNotif.setBtndetail("true");
            } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                userNotif.setBtnvalidemc("false");
                userNotif.setBtnvalidepaie("false");
                userNotif.setBtndetail("true");
            } else {
                userNotif.setBtnvalidemc("false");
                userNotif.setBtnvalidepaie("false");
                userNotif.setBtndetail("true");
            }
            //setter l'id du notif
            userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
            //on persist la notifUser pr finir
            userNotifMgr.persist(userNotif);
        }

        //maj du tableau des mc et raffraichissement de la vue 
        PrimeFaces.current().executeScript("PF('modifInfosPaieDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
//  clear les champs   
        newOperateurMc = null;
        numeroPrincipal = null;
        motifModif = null;
        selectedMc = null;
        notif = null;
    }

    /**
     * mise a la retraite du maitre communautaire
     */
    public void miseEnRetraiteWithMotif() {
        notif = new Notifications();
        //changement des valeur du maitre 
        Utilisateur createur = utilisateurMgr.verif(userCo);
        System.out.println("mise en retraite du maitre ..." + selectedMc.getEtatretraite());
        if (selectedMc.getEtatretraite()) {

            System.out.println("il es a la retraite");
            selectedMc.setDatemodifcompte(DateOfDay());
            //desactivation du compte du maitre dans la plateforme
            selectedMc.setEtatcomptemc(Boolean.FALSE);
            selectedMc.setEtatretraite(Boolean.TRUE);
            selectedMc.setValeureetatretraite("a la retraite");
            selectedMc.setDateretraite(DateOfDay());
            selectedMc.setStatutwallet(Boolean.FALSE);
            selectedMc.setValeurestatutwallet("non actif");
            msgSuccesRetraite();
            mcMgr.updateMaitre(selectedMc);
            //auditlog
            saveLog("mise a la retraitre du maitre : " + selectedMc.getNom(), DateOfDay());
            //on persite le motif dans la table des notifs
            BigDecimal typn = BigDecimal.valueOf(10);
            typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
            String libelleNotif = "Mise a la rétraite du maitre " + selectedMc.getNom() + " " + selectedMc.getPrenoms();
            String details = "Mise à la retraite du maitre : " + selectedMc.getNom() + "  " + selectedMc.getPrenoms() + "  par le coordonnateur : " + userCo;
            notif.setLibelle(libelleNotif);
            notif.setDetails(details);
            notif.setDatecreation(DateOfDay());
            notif.setDateresolution(DateOfDay());
            notif.setTypenotif(typeNotification);
            notif.setCreateur(createur);
            notif.setIdinfo(selectedMc.getId().toString());
            //save de la notif
            notifMgr.persist(notif);
            //persistance de toutes les notifications pour tous les utilisateurs ...
            //je recupère la liste de tous les users
            listeUsers = utilisateurMgr.getAllActivedUsers();
            //je recupère la dernière notif créée pour le setting a venir 
            Integer lastNotifId = notifMgr.lastNotif();
            //creation des btns 
            //je le parcours et je cree un notifsuser avec un etat a zero...
            for (Utilisateur u : listeUsers) {
                //System.out.println("le user en cours ... : " + u.getNOM());
                Usersnotifs userNotif = new Usersnotifs();
                userNotif.setDateinsert(DateOfDay());
                userNotif.setEtat(BigInteger.ZERO);
                userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
                userNotif.setTitre(libelleNotif);
                userNotif.setInformation(details);
                userNotif.setCreateur(userCo);
                userNotif.setTypeusernotif("MODIF_MC");
                //construction des btn en fonction des profils
                if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                } else {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                }
                //setter l'id du notif
                userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
                //on persist la notifUser pr finir
                userNotifMgr.persist(userNotif);
            }
            //maj du tableau des mc et raffraichissement de la vue 
            PrimeFaces.current().executeScript("PF('retraiteDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
            selectedMc = null;
            motifModif = null;
            notif = null;
        } else { //si il est remis en activité
            System.out.println("il est en activité");
            selectedMc.setDatemodifcompte(DateOfDay());
            //desactivation du compte du maitre dans la plateforme
            selectedMc.setEtatcomptemc(Boolean.TRUE);
            selectedMc.setEtatretraite(Boolean.FALSE);
            selectedMc.setValeureetatretraite("actif");
            selectedMc.setDateretraite(null);
            selectedMc.setStatutwallet(Boolean.TRUE);
            selectedMc.setValeurestatutwallet("actif");
            msgSuccesRemiseEnFonction();
            mcMgr.updateMaitre(selectedMc);
            //auditlog
            saveLog("remis en fonction du maitre : " + selectedMc.getNom(), DateOfDay() + " par le coordonnateur " + userCo);
            //on persite le motif dans la table des notifs
            BigDecimal typn = BigDecimal.valueOf(12);
            typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
            String libelleNotif = "Remis en fonction du maitre " + selectedMc.getNom() + " " + selectedMc.getPrenoms();
            String details = "Remise en fonction du maitre : " + selectedMc.getNom() + "  " + selectedMc.getPrenoms() + "  par le coordonnateur : " + userCo;
            notif.setLibelle(libelleNotif);
            notif.setDetails(details);
            notif.setDatecreation(DateOfDay());
            notif.setDateresolution(DateOfDay());
            notif.setTypenotif(typeNotification);
            notif.setCreateur(createur);
            notif.setIdinfo(selectedMc.getId().toString());
            //save de la notif
            notifMgr.persist(notif);
            //persistance de toutes les notifications pour tous les utilisateurs ...
            //je recupère la liste de tous les users
            listeUsers = utilisateurMgr.getAllActivedUsers();
            //je recupère la dernière notif créée pour le setting a venir 
            Integer lastNotifId = notifMgr.lastNotif();
            //creation des btns 
            //je le parcours et je cree un notifsuser avec un etat a zero...
            for (Utilisateur u : listeUsers) {
                //System.out.println("le user en cours ... : " + u.getNOM());
                Usersnotifs userNotif = new Usersnotifs();
                userNotif.setDateinsert(DateOfDay());
                userNotif.setEtat(BigInteger.ZERO);
                userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
                userNotif.setTitre(libelleNotif);
                userNotif.setInformation(details);
                userNotif.setCreateur(userCo);
                userNotif.setTypeusernotif("MODIF_MC");
                //construction des btn en fonction des profils
                if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                } else {
                    userNotif.setBtnvalidemc("false");
                    userNotif.setBtnvalidepaie("false");
                    userNotif.setBtndetail("true");
                }
                //setter l'id du notif
                userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
                //on persist la notifUser pr finir
                userNotifMgr.persist(userNotif);
            }
            //maj du tableau des mc et raffraichissement de la vue 
            PrimeFaces.current().executeScript("PF('retraiteDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
            selectedMc = null;
            motifModif = null;
            notif = null;
        }

    }
    
    /**
     * Impression de tout l'annuaire
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.io.IOException
    */
    public void printPDF() throws JRException, IOException {
        System.out.println("le nombre de maitre communautaire dans  l'annuaire " + AllMc.size());
//        System.out.println("le contenu de listeAnnuaire : ");
//        for (Maitrecommunautaire m : AllMc) {
//            System.out.println("-->" + m.getNom());
//        }
        String fileName = "Liste_Maitres_Communautaire_" + DateOfDay() + ".pdf";
        System.out.println("le nom du fichier : " + fileName);
        String jasperPath = "/resources/maitres_communautaires.jasper";
        this.PDF(null, jasperPath, AllMc, fileName);
    }

    public void PDF(Map<String, Object> params, String jasperPath, List<Maitrecommunautaire> datasource, String fileName) throws JRException, IOException {
        String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
        File file = new File(relativePath);
        JRBeanCollectionDataSource src = new JRBeanCollectionDataSource(datasource, false);
        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params, src);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachement;filename=" + fileName);
        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    
    


//    public void annuairRapportPDF() throws JRException, IOException {
//        List<Person> dataSource = new ArrayList<>();
//        dataSource.add(new Person("Leonel", "Messi"));
//        dataSource.add(new Person("Christiano", "Ronaldo"));
//        dataSource.add(new Person("Kylian", "M'bappé"));
//        System.out.println("la taille de la liste " + dataSource.size());
//        String fileName = "name.pdf";
//        String jasperPath = "/resources/reportAnnuaire.jasper";
//        PDF(null, jasperPath, dataSource, fileName);
//    }
//
//    public void PDF(Map<String, Object> params, String jasperPath, List<?> datasource, String fileName) throws JRException, IOException {
//        String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
//        File file = new File(relativePath);
//        JRBeanCollectionDataSource src = new JRBeanCollectionDataSource(datasource, false);
//        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params, src);
//        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//        response.addHeader("Content-disposition", "attachement;filename=" + fileName);
//        ServletOutputStream stream = response.getOutputStream();
//        JasperExportManager.exportReportToPdfStream(print, stream);
//        FacesContext.getCurrentInstance().responseComplete();
//    }
    
    
    //msg de succes de modification opérateur
    public void msgSuccesModif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "succès", "les informations du maitre ont été modifiées"));
    }

    //msg d'erreur sur la saisie d'une categorie 
    public void msgErreurCategorie() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "cette catégorie n'existe pas pour pour ce Bailleur"));
    }

    public void msgSuccesModifMatricule() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "succès", "le matricule du maitre a été modifié"));
    }

    public void msgSuccesModifCategPro() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "le maître a été promu", "la catégorie professionnelle à été modifiée"));
    }

    public void msgSuccesModifEtablissement() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Etablissement d'enseignement modifié", "succès "));
    }

    public void msgSuccesRetraite() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "le Maitre à été mis à la retraite", "succès "));
    }

    public void msgSuccesRemiseEnFonction() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "le Maitre à été remis en fonction", "succès "));
    }

    //msg de succes de demande d'emission de modification du mc 
    public void msgSuccesModifInfosPaie() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "modification des informations de paiement", "les informations de paiement du maitre ont été modifiées"));
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

    //vider les champs après la modification mineure des mc
    private void clearChampsAfterMajMineure() {
        selectedMc = null;
        newMcDateNaiss = null;
        nouvelleDateNaissance = null;
        lieudenaissance = null;
        nniMc = null;
        domicileUtilisateur = null;
        newMcNiveauSco = null;
        newMcDernierDiplome = null;
        dateprisedefonction = null;
        nouvelleDatePriseFonction = null;
        newMcClasses.clear();
        motifModif = null;
    }


}
