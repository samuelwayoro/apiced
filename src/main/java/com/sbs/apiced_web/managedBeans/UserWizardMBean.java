/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Bailleur;
import com.sbs.apiced_web.entities.Categorie;
import com.sbs.apiced_web.entities.Etablissement;
import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Niveauscolaire;
import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Operateur;
import com.sbs.apiced_web.entities.Parametres;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Usersnotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.entities.Villes;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.BailleurManager;
import com.sbs.apiced_web.services.CategorieMcManager;
import com.sbs.apiced_web.services.ClasseAffecteeManager;
import com.sbs.apiced_web.services.DiplomesManager;
import com.sbs.apiced_web.services.EtablissementManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.NiveauScolaireManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.OperateurTelcoManager;
import com.sbs.apiced_web.services.ParamManager;
import com.sbs.apiced_web.services.TypeNotifManager;
import com.sbs.apiced_web.services.TypePiecesAdministrativeManager;
import com.sbs.apiced_web.services.UsersNotifManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import com.sbs.apiced_web.services.VilleManager;
import com.sbs.apiced_web.utility.Utility;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author samuel
 */
@Named
@ViewScoped
public class UserWizardMBean implements Serializable {

//proprietes de la date 
    private Date date;

    private Maitrecommunautaire mc = new Maitrecommunautaire();
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
    private List<String> listeCategories;
    private boolean skip;
    private List<String> listeNomsCate;
    private List<String> listeNomsEtablissements;
    private List<String> listeNomsOperateurTelco;
    private List<String> listeClassesAffectees;
    //private List<Operateur> listeOperateurs;
    private List<String> listeOperateurs;
    private String libelleCate;
    private String diplome;
    private String classesAffectee;
    private String nomEtablissement;
    private String operateurMc;
    private String sitmatrimonial;
    private String typePiece;
    private Categorie categorieMc;
    private Etablissement etsMc;
    private Boolean etatMoMo = Boolean.FALSE;
    private String loperateurDuMc;
    private Notifications notif = new Notifications();
    private Typenotifs typeNotification;
    private List<Villes> listeDesVilles;
    private List<String> listeTypePiece;
    private UploadedFile file;
    private Villes domicileUtilisateur;
    private Date dateDeNaissance;
    private Date datePriseDeFonction;
    private String userCo;
    private Villes lieudenaissance;
    private Niveauscolaire nivosco;
    private List<String> listeNiveauSco;
    private List<String> listeNomsBailleurs;
    private String niveauScoMaitre;
    private Bailleur nomBailleur;
    private LocalDate datedenaissance, dateprisedefonction;
    private String nouvelleDateNaissance;
    private String nouvelleDateDePriseDeFonction;
    private List<String> listeNomsDiplome;
    private List<String> classesAffectees = new ArrayList<>();
    private static int numInt = 0;
    private String matricule;
    private List<Utilisateur> listeUsers;

    @EJB
    private MaitreCoManager mcMgr;
    @EJB
    private CategorieMcManager categMgr;
    @EJB
    private EtablissementManager etsMgr;
    @EJB
    private UtilisateurManager utilisateurMgr;
    @EJB
    private AuditlogManager auditMgr;
    @EJB
    private OperateurTelcoManager opTelcoMgr;
    @EJB
    private ParamManager paramMgr;
    //mgr de typ noti et de notif 
    @EJB
    private TypeNotifManager typeNotifMgr;
    @EJB
    private NotifsManager notifMgr;
    @EJB
    private VilleManager villeMgr;
    @EJB
    private NiveauScolaireManager nivoScoMgr;
    @EJB
    private ClasseAffecteeManager classeAffecteeMgr;
    @EJB
    private DiplomesManager dernierDiplomeMgr;
    @EJB
    private UsersNotifManager userNotifMgr;
    @EJB
    private TypePiecesAdministrativeManager typePiecesMgr;
    @EJB
    private BailleurManager bailleurMgr;

    //converter des operateur mobile
    private Converter operateurConverter = new Converter() {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            //return opTelcoMgr.OneOpById(Integer.parseInt(value));
            return opTelcoMgr.oneOpByName(value);
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            //return ((Operateur) value).getIdoperateur().toString();
            //return ((Operateur) value).getNom();
            return ((Operateur) value).toString();
        }
    };

    private Converter bailleurConverter = new Converter() {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            System.out.println("la valeur ->" + value);
            return bailleurMgr.oneBailleurById(BigDecimal.valueOf(Double.valueOf(value)));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return ((Bailleur) value).toString();
        }
    };

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

    //converter des niveau scolaire 
    private Converter niveauScolaireConverter = new Converter() {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            return nivoScoMgr.findNiveauAcademiq(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return ((Niveauscolaire) value).getIdniveau().toString();
        }
    };

    public Converter getCategoriesConverter() {
        return categoriesConverter;
    }

    public void setCategoriesConverter(Converter categoriesConverter) {
        this.categoriesConverter = categoriesConverter;
    }

    public List<String> getListeCategories() {
        return listeCategories;
    }

    public void setListeCategories(List<String> listeCategories) {
        this.listeCategories = listeCategories;
    }

    public BailleurManager getBailleurMgr() {
        return bailleurMgr;
    }

    public void setBailleurMgr(BailleurManager bailleurMgr) {
        this.bailleurMgr = bailleurMgr;
    }

    public List<String> getListeNomsBailleurs() {
        return listeNomsBailleurs;
    }

    public void setListeNomsBailleurs(List<String> listeNomsBailleurs) {
        this.listeNomsBailleurs = listeNomsBailleurs;
    }

    public Bailleur getNomBailleur() {
        return nomBailleur;
    }

    public void setNomBailleur(Bailleur nomBailleur) {
        this.nomBailleur = nomBailleur;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public TypePiecesAdministrativeManager getTypePiecesMgr() {
        return typePiecesMgr;
    }

    public void setTypePiecesMgr(TypePiecesAdministrativeManager typePiecesMgr) {
        this.typePiecesMgr = typePiecesMgr;
    }

    public String getTypePiece() {
        return typePiece;
    }

    public void setTypePiece(String typePiece) {
        this.typePiece = typePiece;
    }

    public List<String> getListeTypePiece() {
        return listeTypePiece;
    }

    public void setListeTypePiece(List<String> listeTypePiece) {
        this.listeTypePiece = listeTypePiece;
    }

    public UsersNotifManager getUserNotifMgr() {
        return userNotifMgr;
    }

    public void setUserNotifMgr(UsersNotifManager userNotifMgr) {
        this.userNotifMgr = userNotifMgr;
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

    public List<String> getListeNomsOperateurTelco() {
        return listeNomsOperateurTelco;
    }

    public void setListeNomsOperateurTelco(List<String> listeNomsOperateurTelco) {
        this.listeNomsOperateurTelco = listeNomsOperateurTelco;
    }

    public Categorie getCategorieMc() {
        return categorieMc;
    }

    public void setCategorieMc(Categorie categorieMc) {
        this.categorieMc = categorieMc;
    }

    public Etablissement getEtsMc() {
        return etsMc;
    }

    public void setEtsMc(Etablissement etsMc) {
        this.etsMc = etsMc;
    }

    public String getUserCo() {
        return userCo;
    }

    public void setUserCo(String userCo) {
        this.userCo = userCo;
    }

    public List<Utilisateur> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(List<Utilisateur> listeUsers) {
        this.listeUsers = listeUsers;
    }

    public MaitreCoManager getMcMgr() {
        return mcMgr;
    }

    public void setMcMgr(MaitreCoManager mcMgr) {
        this.mcMgr = mcMgr;
    }

    public CategorieMcManager getCategMgr() {
        return categMgr;
    }

    public void setCategMgr(CategorieMcManager categMgr) {
        this.categMgr = categMgr;
    }

    public EtablissementManager getEtsMgr() {
        return etsMgr;
    }

    public void setEtsMgr(EtablissementManager etsMgr) {
        this.etsMgr = etsMgr;
    }

    public UtilisateurManager getUtilisateurMgr() {
        return utilisateurMgr;
    }

    public void setUtilisateurMgr(UtilisateurManager utilisateurMgr) {
        this.utilisateurMgr = utilisateurMgr;
    }

    public AuditlogManager getAuditMgr() {
        return auditMgr;
    }

    public void setAuditMgr(AuditlogManager auditMgr) {
        this.auditMgr = auditMgr;
    }

    public OperateurTelcoManager getOpTelcoMgr() {
        return opTelcoMgr;
    }

    public void setOpTelcoMgr(OperateurTelcoManager opTelcoMgr) {
        this.opTelcoMgr = opTelcoMgr;
    }

    public ParamManager getParamMgr() {
        return paramMgr;
    }

    public void setParamMgr(ParamManager paramMgr) {
        this.paramMgr = paramMgr;
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

    public VilleManager getVilleMgr() {
        return villeMgr;
    }

    public void setVilleMgr(VilleManager villeMgr) {
        this.villeMgr = villeMgr;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public List<String> getListeNomsDiplome() {
        return listeNomsDiplome;
    }

    public void setListeNomsDiplome(List<String> listeNomsDiplome) {
        this.listeNomsDiplome = listeNomsDiplome;
    }

    public DiplomesManager getDernierDiplomeMgr() {
        return dernierDiplomeMgr;
    }

    public void setDernierDiplomeMgr(DiplomesManager dernierDiplomeMgr) {
        this.dernierDiplomeMgr = dernierDiplomeMgr;
    }

    public Niveauscolaire getNivosco() {
        return nivosco;
    }

    public void setNivosco(Niveauscolaire nivosco) {
        this.nivosco = nivosco;
    }

    public NiveauScolaireManager getNivoScoMgr() {
        return nivoScoMgr;
    }

    public void setNivoScoMgr(NiveauScolaireManager nivoScoMgr) {
        this.nivoScoMgr = nivoScoMgr;
    }

    public ClasseAffecteeManager getClasseAffecteeMgr() {
        return classeAffecteeMgr;
    }

    public void setClasseAffecteeMgr(ClasseAffecteeManager classeAffecteeMgr) {
        this.classeAffecteeMgr = classeAffecteeMgr;
    }

    public List<String> getListeNiveauSco() {
        return listeNiveauSco;
    }

    public void setListeNiveauSco(List<String> listeNiveauSco) {
        this.listeNiveauSco = listeNiveauSco;
    }

    public List<Villes> getListeDesVilles() {
        return listeDesVilles;
    }

    public void setListeDesVilles(List<Villes> listeDesVilles) {
        this.listeDesVilles = listeDesVilles;
    }

    public Boolean getEtatMoMo() {
        return etatMoMo;
    }

    public void setEtatMoMo(Boolean etatMoMo) {
        this.etatMoMo = etatMoMo;
    }

    public String getOperateurMc() {
        return operateurMc;
    }

    public void setOperateurMc(String operateurMc) {
        this.operateurMc = operateurMc;
    }

    public Maitrecommunautaire getMc() {
        return mc;
    }

    public void setMc(Maitrecommunautaire mc) {
        this.mc = mc;
    }

    public String getLibelleCate() {
        return libelleCate;
    }

    public void setLibelleCate(String libelleCate) {
        this.libelleCate = libelleCate;
    }

    public String getNomEtablissement() {
        return nomEtablissement;
    }

    public void setNomEtablissement(String nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public MaitreCoManager getMcManager() {
        return mcMgr;
    }

    public void setMcManager(MaitreCoManager mcMgr) {
        this.mcMgr = mcMgr;
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

    public String getLoperateurDuMc() {
        return loperateurDuMc;
    }

    public void setLoperateurDuMc(String loperateurDuMc) {
        this.loperateurDuMc = loperateurDuMc;
    }

    public Converter getVillesConverter() {
        return villesConverter;
    }

    public void setVillesConverter(Converter villesConverter) {
        this.villesConverter = villesConverter;
    }

    public Converter getNiveauScolaireConverter() {
        return niveauScolaireConverter;
    }

    public void setNiveauScolaireConverter(Converter niveauScolaireConverter) {
        this.niveauScolaireConverter = niveauScolaireConverter;
    }

    public Converter getOperateurConverter() {
        return operateurConverter;
    }

    public void setOperateurConverter(Converter operateurConverter) {
        this.operateurConverter = operateurConverter;
    }

    public Converter getBailleurConverter() {
        return bailleurConverter;
    }

    public void setBailleurConverter(Converter bailleurConverter) {
        this.bailleurConverter = bailleurConverter;
    }

    public Date getDatePriseDeFonction() {
        return datePriseDeFonction;
    }

    public void setDatePriseDeFonction(Date datePriseDeFonction) {
        this.datePriseDeFonction = datePriseDeFonction;
    }

    public Villes getDomicileUtilisateur() {
        return domicileUtilisateur;
    }

    public void setDomicileUtilisateur(Villes domicileUtilisateur) {
        this.domicileUtilisateur = domicileUtilisateur;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Villes getLieudenaissance() {
        return lieudenaissance;
    }

    public void setLieudenaissance(Villes lieudenaissance) {
        this.lieudenaissance = lieudenaissance;
    }

    public String getNiveauScoMaitre() {
        return niveauScoMaitre;
    }

    public void setNiveauScoMaitre(String niveauScoMaitre) {
        this.niveauScoMaitre = niveauScoMaitre;
    }

    public List<String> getListeOperateurs() {
        return listeOperateurs;
    }

//    public List<Operateur> getListeOperateurs() {
//        return listeOperateurs;
//    }
//
//    public void setListeOperateurs(List<Operateur> listeOperateurs) {
//        this.listeOperateurs = listeOperateurs;
//    }
    public void setListeOperateurs(List<String> listeOperateurs) {
        this.listeOperateurs = listeOperateurs;
    }

    public LocalDate getDatedenaissance() {
        return datedenaissance;
    }

    public void setDatedenaissance(LocalDate datedenaissance) {
        this.datedenaissance = datedenaissance;
    }

    public LocalDate getDateprisedefonction() {
        return dateprisedefonction;
    }

    public void setDateprisedefonction(LocalDate dateprisedefonction) {
        this.dateprisedefonction = dateprisedefonction;
    }

    public String getNouvelleDateNaissance() {
        return nouvelleDateNaissance;
    }

    public void setNouvelleDateNaissance(String nouvelleDateNaissance) {
        this.nouvelleDateNaissance = nouvelleDateNaissance;
    }

    public String getNouvelleDateDePriseDeFonction() {
        return nouvelleDateDePriseDeFonction;
    }

    public void setNouvelleDateDePriseDeFonction(String nouvelleDateDePriseDeFonction) {
        this.nouvelleDateDePriseDeFonction = nouvelleDateDePriseDeFonction;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getClassesAffectee() {
        return classesAffectee;
    }

    public void setClassesAffectee(String classesAffectee) {
        this.classesAffectee = classesAffectee;
    }

    public List<String> getListeClassesAffectees() {
        return listeClassesAffectees;
    }

    public void setListeClassesAffectees(List<String> listeClassesAffectees) {
        this.listeClassesAffectees = listeClassesAffectees;
    }

    public String getSitmatrimonial() {
        return sitmatrimonial;
    }

    public void setSitmatrimonial(String sitmatrimonial) {
        this.sitmatrimonial = sitmatrimonial;
    }

    public List<String> getClassesAffectees() {
        return classesAffectees;
    }

    public void setClassesAffectees(List<String> classesAffectees) {
        this.classesAffectees = classesAffectees;
    }

    public static int getNumInt() {
        return numInt;
    }

    public static void setNumInt(int numInt) {
        UserWizardMBean.numInt = numInt;
    }

    @PostConstruct
    public void init() {
        // recuperation du user en session 
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        userCo = session.getAttribute("utilisateur").toString();
        listeDesVilles = villeMgr.getAllVilles();
        listeNiveauSco = nivoScoMgr.allNiveauScolaireNames();
        listeNomsBailleurs = bailleurMgr.listeNomsBailleurs();
        //listeOperateurs = opTelcoMgr.getAllOperateur();
        listeClassesAffectees = classeAffecteeMgr.allClassesNames();
        listeOperateurs = opTelcoMgr.getAllOperateurTelcoNames();
        listeNomsDiplome = dernierDiplomeMgr.allDernierDiplomesNames();
        listeTypePiece = typePiecesMgr.allTypesPieces();
        listeCategories = categMgr.listNomsCategories();
    }

    //system de validation de la catégorie en fonction du bailleur selectionné
    public void validateCategoriePro(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent component = event.getComponent();
        //recup du bailleur selectionné
        UIInput uiInputBailleur = (UIInput) component.findComponent("nomBailleur");
        String Bailleur = uiInputBailleur.getLocalValue() == null ? "" : uiInputBailleur.getLocalValue().toString();
        String nomBailleurId = uiInputBailleur.getClientId();

        //recup de la categ choisi
        UIInput uiInputCategoriePro = (UIInput) component.findComponent("idcategoriepro");
        String idCategoriPro = uiInputCategoriePro.getLocalValue() == null ? "" : uiInputCategoriePro.getLocalValue().toString();
        System.out.println("LA VALLEUR DE LA CATEGORIE SELECTIONNEE " + idCategoriPro + "  POUR VALIDATION");

        if (!idCategoriPro.equalsIgnoreCase("1")) {
            System.out.println("JUSTE VERFI LA VALEUR DE CATEGORIE PRO =>" + idCategoriPro);
        }

        System.out.println("VALEUR DU BAILLEUR CHOISI : " + Bailleur);

        if ((Bailleur.equalsIgnoreCase("1")) && (!idCategoriPro.equalsIgnoreCase("1"))) {
            System.out.println("******************DECLENCHEMENT DU MSG D'ERREUR************************");
            FacesMessage msg = new FacesMessage("cette catégorie n'appartient pas à ce bailleur ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(nomBailleurId, msg);
            fc.renderResponse();
        }
    }

    public void save() {
//        String prefixMC = "MC";
//        //dernier ID 
//        BigDecimal x =  (BigDecimal) mcMgr.getLastMcId();
//        Integer i = x.intValue();
//        System.out.println("la valeur de son id   "+  ++i);
//        String lastMcId = i.toString();
//        //initiaux Bailleur en fonction du choix 
//        String initalBailleur;
//        if (mc.getBailleur().equalsIgnoreCase("PARSET2")) {
//            initalBailleur = "PRST";
//        } else {
//            initalBailleur = "PREA";
//        }
//
//        //recup du genre choisi
//        String prefixGenre = mc.getGenre();
//        String genre;
//        if (prefixGenre.equalsIgnoreCase("M")) {
//            genre = "M";
//        } else {
//            genre = "F";
//        }
//        //System.out.println("le genre du mc  :" + prefixGenre);
//        matricule = prefixMC + initalBailleur + lastMcId + genre;
        generationMatricule();

        // System.out.println("*********MATRICULE GENERE : ---->" + matricule);
        //formattage des date a enregistrer
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
        nouvelleDateNaissance = f.format(datedenaissance);
        nouvelleDateDePriseDeFonction = f.format(dateprisedefonction);
        Integer verifDateNaissance = Integer.parseInt(nouvelleDateNaissance.substring(6));
        Integer verifDatePriseFonction = Integer.parseInt(nouvelleDateDePriseDeFonction.substring(6));
        //System.out.println("date de naissance : "+verifDateNaissance+"   date de prise de fonction : "+verifDatePriseFonction );
        if (verifDateNaissance < 1900) {
            msgDateNaissanceIncorrecte();
        } else if (verifDatePriseFonction < 1900) {
            msgDatePriseFonctionIncorrecte();
        } else if (verifDatePriseFonction <= verifDateNaissance) {//la date de prise de fonction ne doit pas êtres inferieur a la date de naissance 
            msgDateNaissanceInfDatePriseFonction();
            //System.out.println("attention le mc ne pas commence a travaille avant detre ne !!! ");
        } else {
            //System.out.println("la catégorie du mc est    " + mc.getCategoriepro());
            mc.setMatricule(matricule);
            mc.setDatecreationcompte(DateOfDay());
            mc.setEtatcomptemc(Boolean.FALSE);//pas encore activé par le valideur
            mc.setMatricule(matricule);
            //System.out.println("valeur de momo   " + etatMoMo);
            if (etatMoMo) {//si le wallet est activé...
                // System.out.println("valeur de momo dns le if "+etatMoMo);
                mc.setStatutwallet(etatMoMo);
                mc.setValeurestatutwallet("actif");
            } else {//sinon
                //System.out.println("valeur de momo dns le else "+etatMoMo);
                mc.setStatutwallet(etatMoMo);//sinon non actif...
                mc.setValeurestatutwallet("non actif");
            }
            mc.setValidationcoordonnateur(Boolean.FALSE);
            //System.out.println("valeur de compte mc :" + mc.getEtatcomptemc() + " valeur validation coordo " + mc.getValidationcoordonnateur());
            mc.setDomicile(domicileUtilisateur.getNomville());
            mc.setLieudenaissance(lieudenaissance.getNomville());
            mc.setNiveauscolaire(niveauScoMaitre);
            mc.setDatenaissance(nouvelleDateNaissance);
            mc.setDateprisefonction(nouvelleDateDePriseDeFonction);
            mc.setSitmatrimonial(sitmatrimonial);
            mc.setDernierdiplome(diplome);
            mc.setEtatretraite(Boolean.FALSE);//
            mc.setValeureetatretraite("actif");

            //recuperation des classes affectees 
            StringBuilder str = new StringBuilder();
            for (String classe : classesAffectees) {
                str.append(classe);
                str.append(" ");
            }

            //verification categorie - bailleur 
            System.out.println("resultat verification du bailleur et de la categorie du mc    " + verifCategorieBailleur(mc.getBailleur(), mc.getCategoriepro()));
            if (!verifCategorieBailleur(mc.getBailleur(), mc.getCategoriepro())) {
                msgErreurBailleurCategorie();
            } else {

                //System.out.println("VERIFICATION DU BAILLEUR / CATEGORIE  "+result);
                //System.out.println("la liste retournée : "+str+"    en mode toString()"+str.toString());
                mc.setClasseaffectee(str.toString());
                Utilisateur createur = utilisateurMgr.verif(userCo);
                Parametres motifSuspension = paramMgr.paramByVal("AUCUN");
                mc.setMotifsuspension(motifSuspension.getValeur());
                //setting de l'opérateur du maitre 
                mc.setOperatortelco(operateurMc);
                //verif de l'unicité du mc
                //System.out.println("le matricule a verifier est  " + mc.getMatricule());
                Long maitreCo = mcMgr.verifUniciteMatricule(mc.getMatricule());
                //System.out.println("la valeur du retour  : " + maitreCo);
                if (maitreCo == 0) {
                    //save dans la table mc
                    mcMgr.persist(mc);
                    //System.out.println("id du mc "+mc.getId());
                    //System.out.println("******** enregistrement de la notif ");
                    notif.setLibelle("Demande de validation du compte d'un nouveau maitre communautaire ");
                    String details = "Un nouveau maitre communautaire du nom de : " + mc.getNom() + "  " + mc.getPrenoms() + "   émis par l'utilisateur  :   " + userCo + " est en attente de validation";
                    notif.setDetails(details);
                    notif.setDatecreation(DateOfDay());
                    BigDecimal typn = BigDecimal.valueOf(1);
                    typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
                    notif.setEtat(BigInteger.ZERO);
                    notif.setTypenotif(typeNotification);
                    notif.setCreateur(createur);
                    notif.setIdinfo(mc.getId().toString());
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
                        //System.out.println("le user en cours ... : " + u.getNom());
                        Usersnotifs userNotif = new Usersnotifs();
                        userNotif.setDateinsert(DateOfDay());
                        userNotif.setEtat(BigInteger.ZERO);
                        userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
                        userNotif.setTitre("Création d'un nouveau maitre communautaire");
                        userNotif.setInformation(details);
                        userNotif.setCreateur(userCo);
                        userNotif.setTypeusernotif("MC");
                        userNotif.setBtnvalidemc("false");
                        userNotif.setBtnvalidepaie("false");
                        userNotif.setBtndetail("true");
                        //CONSTRUCTION DES  BTNS EN FONCTION DES PROFILS
                        /*
                        if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                            userNotif.setBtnvalidemc("false");
                            userNotif.setBtnvalidepaie("false");
                            userNotif.setBtndetail("true");
                        } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                            userNotif.setBtnvalidemc("true");
                            userNotif.setBtnvalidepaie("false");
                            userNotif.setBtndetail("false");
                        } else {
                            userNotif.setBtnvalidemc("false");
                            userNotif.setBtnvalidepaie("false");
                            userNotif.setBtndetail("true");
                        }
                         */
                        //setter l'id du notif
                        userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
                        //on persist la notifUser pr finir
                        userNotifMgr.persist(userNotif);
                    }
                    //affichage msg
                    msgSuccessNewMc();
                    saveLog("Création de nouveau maitre communautaire " + mc.getNom() + "  " + mc.getPrenoms() + " par l'utilisateur :" + userCo, userCo);
                    clearChamps();
                    //maj liste des mc
                    PrimeFaces.current().ajax().update("form:messages");
                } else {
                    //msg erreur
                    System.out.println("il existe deja un mc qui a ce matricule ");
                    msgExistanceMcEnBd();
                }
            }

        }

    }

    public void generationMatricule() {

        String prefixMC = "MC";
        //dernier ID 
        BigDecimal x = (BigDecimal) mcMgr.getLastMcId();
        Integer i = x.intValue();
        System.out.println("la valeur de son id   " + ++i);
        String lastMcId = i.toString();
        //initiaux Bailleur en fonction du choix 
        String initalBailleur;
        if (mc.getBailleur().equalsIgnoreCase("PARSET2")) {
            initalBailleur = "PRST";
        } else {
            initalBailleur = "PREA";
        }

        //recup du genre choisi
        String prefixGenre = mc.getGenre();
        String genre;
        if (prefixGenre.equalsIgnoreCase("M")) {
            genre = "M";
        } else {
            genre = "F";
        }
        //System.out.println("le genre du mc  :" + prefixGenre);
        matricule = prefixMC + initalBailleur + lastMcId + genre;
    }

    //fonction de verification de l'etat du compte mobile money du user 
    public void verifMobileMoney() {
        //System.out.println("verification du compte chez l'opérateur en cours....");
        //System.out.println("l'état actuel est :" + this.etatMoMo);
        etatMoMo = Boolean.TRUE;
        mc.setValeurestatutwallet("actif");
        //System.out.println("l'etat après :" + etatMoMo);
        //PrimeFaces.current().ajax().update("form:messages");
        //msgMomoVerif();
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;	//reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    //retourne la liste de tous les noms de catégorie professionelle
    public List<String> getListNomsCategorieMc() {
        if (listeNomsCate == null) {
            listeNomsCate = mcMgr.getAllCategorieName();
        }
        return listeNomsCate;
    }

    //retourne la liste des noms de tous les établissements scolaire
    public List<String> getListeNomsEtablissement() {
        if (listeNomsEtablissements == null) {
            listeNomsEtablissements = mcMgr.getAllEtablissementsName();
        }
        return listeNomsEtablissements;
    }

//liste de tous les opérateurs telco
    public List<String> getListeNomsOp() {
        if (listeNomsOperateurTelco == null) {
            listeNomsOperateurTelco = mcMgr.getOpNames();
        }
        return listeNomsOperateurTelco;
    }

    public String DateOfDay() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String dayDate = dt.format(dtFormat);
        return dayDate;
    }

    public void msgExistanceMcEnBd() {
        //  addMessage(FacesMessage.SEVERITY_ERROR, "erreur", "un utilisateur existe déjà avec ce login");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur dans la saisie du matricule ", "le matricule saisi appartient a un autre maitre "));
    }

    public void msgErreurChoixDiplomeNiveau() {
        //  addMessage(FacesMessage.SEVERITY_ERROR, "erreur", "un utilisateur existe déjà avec ce login");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur du choix du niveau scolaire et le diplôme ", "veuillez verifier le diplôme ou le niveau scolaire "));
    }

    public void msgSuccessNewMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès ", "Le Maître Commuanautaire a été crée , cliquer sur nouveau pour reprendre"));
    }

    public void msgDateNaissanceIncorrecte() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention ", "La date de naissance saisie est incorrecte"));
    }

    public void msgDatePriseFonctionIncorrecte() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "La date de prise de fonction est incorrecte"));
    }

    public void msgDateNaissanceInfDatePriseFonction() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "La date de prise de fonction saisie est inférieur à la date de naissance , veuillez corriger"));
    }

    public void msgErreurBailleurCategorie() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur", "La catégorie choisie ne fait pas partie des catégorie de ce Bailleur ..."));
    }

    public void msgNbreEnfantIncorrect() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attention", "Le nombre d'enfant entré est incorrecte , veuillez le corriger"));
    }

    private void clearChamps() {
        mc = null;
        this.nomEtablissement = null;
        this.libelleCate = null;
        this.operateurMc = null;
    }

    public void saveLog(String msg, String date) {
        //trace dans la table auditMgrlOg
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Utilisateur userConnected = (Utilisateur) session.getAttribute("utilisateurConnecte");
        String userLogin = userConnected.getLogin();
        Auditlog log = new Auditlog();
        log.setAuteurIdutilisateur(userConnected);
        log.setLogin(userLogin);
        log.setAction(msg);
        log.setDateaction(date);
        auditMgr.persist(log);
    }

    /*
     * Methode de Bourrage à Gauche de Zero par defaut : provient de webclring
     */
    public String bourrageGZero(String chaine, int longueur) {
        if (chaine == null) {
            chaine = "";
        }
        chaine = chaine.trim();
        while (chaine.length() < longueur) {
            chaine = "0" + chaine;
        }
        return chaine;
    }

    public void onDateSelect(SelectEvent<Date> event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void click() {
        PrimeFaces.current().ajax().update("form:display");
        PrimeFaces.current().executeScript("PF('dlg').show()");
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private void msgMomoVerif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès ", "Le portfeuille éléctronique est actif"));
    }

    private int BigDecimal(String lastMcId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * verification du couple categoriePro / Bailleur choisi
     *
     * @param bailleur
     * @param categoriepro
     * @return
     */
    private Boolean verifCategorieBailleur(String bailleur, String categoriepro) {
        Boolean response = null;
        String listeCategorieOfBailleur = bailleurMgr.listeCate(bailleur);
        //System.out.println("le contenu de la liste des categories : "+listeCategorieOfBailleur);
        String[] cates = listeCategorieOfBailleur.split("\\-");
        System.out.println("liste des categories du mc en creation   ");
        List<String> listeDesCate = new ArrayList<>();
        for (String cate : cates) {
            System.out.println("-->   " + cate);
            listeDesCate.add(cate);
//            if (cate.equalsIgnoreCase(categoriepro)) {
//                response = true;
//            } else {
//                response = false;
//            }
        }
        response = listeDesCate.contains(categoriepro);
        return response;
    }

}
