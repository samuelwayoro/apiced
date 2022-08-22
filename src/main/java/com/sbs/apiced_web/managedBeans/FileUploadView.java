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
import com.sbs.apiced_web.services.BailleurManager;
import com.sbs.apiced_web.services.CategorieMcManager;
import com.sbs.apiced_web.services.EtablissementManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.OperateurTelcoManager;
import com.sbs.apiced_web.services.ParamManager;
import com.sbs.apiced_web.services.TypeNotifManager;
import com.sbs.apiced_web.services.UsersNotifManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import com.sbs.apiced_web.services.VilleManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.shaded.commons.io.FilenameUtils;

/**
 *
 * @author samuel
 */
@Named(value = "fileUploadView")
@ViewScoped
public class FileUploadView implements Serializable {

    /**
     * Creates a new instance of FileUploadView
     */
    public FileUploadView() {
    }

    private String fileName;
    private UploadedFile file;
    private UploadedFiles files;
    //private static final String NAME = "D:\\nouveauMaitres\\fichiers\\";
    private String NAME;
    private String nomFichierAUploader;
    private Maitrecommunautaire leMaitre;
    private List<Maitrecommunautaire> listeMaitres;
    private List<String> listeNomsEtablissements;
    private Maitrecommunautaire selectedMc;
    private LocalDate newMcDatePriseFonction;
    private String genreMc;
    private String nomMc;
    private String prenomsMc;
    private String nniMc;
    private String lieuDeNaissanceMc;
    private String bailleurMc;
    private String ecoleMc;
    private String milieuResidenceMc;
    private String contactDeuxMc;
    private Etablissement etsMc;
    private List<Etablissement> etablissements;
    private Categorie newCateMc;
    private List<Categorie> listeCategories;
    private List<String> listeCategs;
    private Notifications notif = new Notifications();
    private List<Villes> listeDesVilles;
    private String userCo;
    private List<String> listeOperateurs;
    private String numeroUnMc;
    private String operateurTelcoMc;
    private LocalDate newMcDateNaiss;
    private String dateNaissanceMc;
    private String langueMc;
    private String statutcompteMc;
    private String nouvelleDateNaissance;
    private String nouvelleDatePriseFonction;
    private List<String> newMcClasses = new ArrayList<>();
    private Typenotifs typeNotification;
    private List<Utilisateur> listeUsers;
    private Villes lieudenaissance;
    private Villes domicileUtilisateur;
    private String newMcNiveauSco;
    private String newMcDernierDiplome;
    private LocalDate dateprisedefonction;
    private String motifModif;
    private List<String> listeNomsBailleurs;
    private String matricule;

    @EJB
    private EtablissementManager etsMgr;
    @EJB
    private CategorieMcManager categMgr;
    @EJB
    private VilleManager villeMgr;
    @EJB
    private OperateurTelcoManager opTelcoMgr;
    @EJB
    private UtilisateurManager utilisateurMgr;
    @EJB
    private MaitreCoManager mcMgr;
    @EJB
    private UsersNotifManager userNotifMgr;
    @EJB
    private AuditlogManager audit;
    @EJB
    private TypeNotifManager typeNotifMgr;
    @EJB
    private NotifsManager notifMgr;
    @EJB
    private ParamManager paramMgr;
    @EJB
    private BailleurManager bailleurMgr;

    @PostConstruct
    public void init() {
        listeNomsBailleurs = bailleurMgr.listeNomsBailleurs();
        //repertoire de telechargement du fichier des news maitres 
        NAME = paramMgr.paramByLibelle("REPERTOIRE_LISTE_NOUVEAUX_MAITRES").getValeur();
        //nom du fichier a uploader 
        nomFichierAUploader = paramMgr.paramByLibelle("NOM_FICHIER_NOUVEAUX_MAITRE").getValeur();
        //listeClassesAffectees = classeAffecteeMgr.allClassesNames();
        listeDesVilles = villeMgr.getAllVilles();
        //listeNiveauSco = nivoScoMgr.allNiveauScolaireNames();
        //listeNomsDiplome = dernierDiplomeMgr.allDernierDiplomesNames();
        // recuperation du user en session 
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        userCo = session.getAttribute("utilisateur").toString();

        listeOperateurs = opTelcoMgr.getAllOperateurTelcoNames();
        etablissements = etsMgr.getEtablissements();
        listeCategories = categMgr.allMcCategorie();
        listeCategs = categMgr.listNomsCategories();

    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public List<String> getListeCategs() {
        return listeCategs;
    }

    public void setListeCategs(List<String> listeCategs) {
        this.listeCategs = listeCategs;
    }

    public String getDateNaissanceMc() {
        return dateNaissanceMc;
    }

    public void setDateNaissanceMc(String dateNaissanceMc) {
        this.dateNaissanceMc = dateNaissanceMc;
    }

    public String getEcoleMc() {
        return ecoleMc;
    }

    public void setEcoleMc(String ecoleMc) {
        this.ecoleMc = ecoleMc;
    }

    public void setListeNomsEtablissements(List<String> listeNomsEtablissements) {
        this.listeNomsEtablissements = listeNomsEtablissements;
    }

    //retourne la liste des noms de tous les établissements scolaire
    public List<String> getListeNomsEtablissements() {
        if (listeNomsEtablissements == null) {
            listeNomsEtablissements = mcMgr.getAllEtablissementsName();
        }
        return listeNomsEtablissements;
    }

    public String getMilieuResidenceMc() {
        return milieuResidenceMc;
    }

    public void setMilieuResidenceMc(String milieuResidenceMc) {
        this.milieuResidenceMc = milieuResidenceMc;
    }

    public String getOperateurTelcoMc() {
        return operateurTelcoMc;
    }

    public void setOperateurTelcoMc(String operateurTelcoMc) {
        this.operateurTelcoMc = operateurTelcoMc;
    }

    public String getStatutcompteMc() {
        return statutcompteMc;
    }

    public void setStatutcompteMc(String statutcompteMc) {
        this.statutcompteMc = statutcompteMc;
    }

    public String getLangueMc() {
        return langueMc;
    }

    public void setLangueMc(String langueMc) {
        this.langueMc = langueMc;
    }

    public String getBailleurMc() {
        return bailleurMc;
    }

    public void setBailleurMc(String bailleurMc) {
        this.bailleurMc = bailleurMc;
    }

    public String getPrenomsMc() {
        return prenomsMc;
    }

    public void setPrenomsMc(String prenomsMc) {
        this.prenomsMc = prenomsMc;
    }

    public String getNomMc() {
        return nomMc;
    }

    public void setNomMc(String nomMc) {
        this.nomMc = nomMc;
    }

    public String getNomFichierAUploader() {
        return nomFichierAUploader;
    }

    public void setNomFichierAUploader(String nomFichierAUploader) {
        this.nomFichierAUploader = nomFichierAUploader;
    }

    public List<String> getListeNomsBailleurs() {
        return listeNomsBailleurs;
    }

    public void setListeNomsBailleurs(List<String> listeNomsBailleurs) {
        this.listeNomsBailleurs = listeNomsBailleurs;
    }

    public BailleurManager getBailleurMgr() {
        return bailleurMgr;
    }

    public void setBailleurMgr(BailleurManager bailleurMgr) {
        this.bailleurMgr = bailleurMgr;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
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

    public LocalDate getDateprisedefonction() {
        return dateprisedefonction;
    }

    public void setDateprisedefonction(LocalDate dateprisedefonction) {
        this.dateprisedefonction = dateprisedefonction;
    }

    public String getMotifModif() {
        return motifModif;
    }

    public void setMotifModif(String motifModif) {
        this.motifModif = motifModif;
    }

    public ParamManager getParamMgr() {
        return paramMgr;
    }

    public void setParamMgr(ParamManager paramMgr) {
        this.paramMgr = paramMgr;
    }

    public List<Villes> getListeDesVilles() {
        return listeDesVilles;
    }

    public void setListeDesVilles(List<Villes> listeDesVilles) {
        this.listeDesVilles = listeDesVilles;
    }

    public String getUserCo() {
        return userCo;
    }

    public void setUserCo(String userCo) {
        this.userCo = userCo;
    }

    public List<String> getListeOperateurs() {
        return listeOperateurs;
    }

    public void setListeOperateurs(List<String> listeOperateurs) {
        this.listeOperateurs = listeOperateurs;
    }

    public String getNumeroUnMc() {
        return numeroUnMc;
    }

    public void setNumeroUnMc(String numeroUnMc) {
        this.numeroUnMc = numeroUnMc;
    }

    public LocalDate getNewMcDateNaiss() {
        return newMcDateNaiss;
    }

    public void setNewMcDateNaiss(LocalDate newMcDateNaiss) {
        this.newMcDateNaiss = newMcDateNaiss;
    }

    public String getNouvelleDateNaissance() {
        return nouvelleDateNaissance;
    }

    public void setNouvelleDateNaissance(String nouvelleDateNaissance) {
        this.nouvelleDateNaissance = nouvelleDateNaissance;
    }

    public String getNouvelleDatePriseFonction() {
        return nouvelleDatePriseFonction;
    }

    public void setNouvelleDatePriseFonction(String nouvelleDatePriseFonction) {
        this.nouvelleDatePriseFonction = nouvelleDatePriseFonction;
    }

    public List<String> getNewMcClasses() {
        return newMcClasses;
    }

    public void setNewMcClasses(List<String> newMcClasses) {
        this.newMcClasses = newMcClasses;
    }

    public Typenotifs getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(Typenotifs typeNotification) {
        this.typeNotification = typeNotification;
    }

    public List<Utilisateur> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(List<Utilisateur> listeUsers) {
        this.listeUsers = listeUsers;
    }

    public Villes getLieudenaissance() {
        return lieudenaissance;
    }

    public void setLieudenaissance(Villes lieudenaissance) {
        this.lieudenaissance = lieudenaissance;
    }

    public Villes getDomicileUtilisateur() {
        return domicileUtilisateur;
    }

    public void setDomicileUtilisateur(Villes domicileUtilisateur) {
        this.domicileUtilisateur = domicileUtilisateur;
    }

    public VilleManager getVilleMgr() {
        return villeMgr;
    }

    public void setVilleMgr(VilleManager villeMgr) {
        this.villeMgr = villeMgr;
    }

    public OperateurTelcoManager getOpTelcoMgr() {
        return opTelcoMgr;
    }

    public void setOpTelcoMgr(OperateurTelcoManager opTelcoMgr) {
        this.opTelcoMgr = opTelcoMgr;
    }

    public UtilisateurManager getUtilisateurMgr() {
        return utilisateurMgr;
    }

    public void setUtilisateurMgr(UtilisateurManager utilisateurMgr) {
        this.utilisateurMgr = utilisateurMgr;
    }

    public MaitreCoManager getMcMgr() {
        return mcMgr;
    }

    public void setMcMgr(MaitreCoManager mcMgr) {
        this.mcMgr = mcMgr;
    }

    public UsersNotifManager getUserNotifMgr() {
        return userNotifMgr;
    }

    public void setUserNotifMgr(UsersNotifManager userNotifMgr) {
        this.userNotifMgr = userNotifMgr;
    }

    public AuditlogManager getAudit() {
        return audit;
    }

    public void setAudit(AuditlogManager audit) {
        this.audit = audit;
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

    public Notifications getNotif() {
        return notif;
    }

    public void setNotif(Notifications notif) {
        this.notif = notif;
    }

    public List<Categorie> getListeCategories() {
        return listeCategories;
    }

    public void setListeCategories(List<Categorie> listeCategories) {
        this.listeCategories = listeCategories;
    }

    public CategorieMcManager getCategMgr() {
        return categMgr;
    }

    public void setCategMgr(CategorieMcManager categMgr) {
        this.categMgr = categMgr;
    }

    public Converter getCategoriesConverter() {
        return categoriesConverter;
    }

    public void setCategoriesConverter(Converter categoriesConverter) {
        this.categoriesConverter = categoriesConverter;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFiles getFiles() {
        return files;
    }

    public void setFiles(UploadedFiles files) {
        this.files = files;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Maitrecommunautaire getLeMaitre() {
        return leMaitre;
    }

    public void setLeMaitre(Maitrecommunautaire leMaitre) {
        this.leMaitre = leMaitre;
    }

    public List<Maitrecommunautaire> getListeMaitres() {
        return listeMaitres;
    }

    public void setListeMaitres(List<Maitrecommunautaire> listeMaitres) {
        this.listeMaitres = listeMaitres;
    }

    public Maitrecommunautaire getSelectedMc() {
        return selectedMc;
    }

    public void setSelectedMc(Maitrecommunautaire selectedMc) {
        this.selectedMc = selectedMc;
    }

    public LocalDate getNewMcDatePriseFonction() {
        return newMcDatePriseFonction;
    }

    public void setNewMcDatePriseFonction(LocalDate newMcDatePriseFonction) {
        this.newMcDatePriseFonction = newMcDatePriseFonction;
    }

    public String getGenreMc() {
        return genreMc;
    }

    public void setGenreMc(String genreMc) {
        this.genreMc = genreMc;
    }

    public String getDomicileMc() {
        return nniMc;
    }

    public void setDomicileMc(String nniMc) {
        this.nniMc = nniMc;
    }

    public String getLieuDeNaissanceMc() {
        return lieuDeNaissanceMc;
    }

    public void setLieuDeNaissanceMc(String lieuDeNaissanceMc) {
        this.lieuDeNaissanceMc = lieuDeNaissanceMc;
    }

    public String getContactDeuxMc() {
        return contactDeuxMc;
    }

    public void setContactDeuxMc(String contactDeuxMc) {
        this.contactDeuxMc = contactDeuxMc;
    }

    public Etablissement getEtsMc() {
        return etsMc;
    }

    public void setEtsMc(Etablissement etsMc) {
        this.etsMc = etsMc;
    }

    public EtablissementManager getEtsMgr() {
        return etsMgr;
    }

    public void setEtsMgr(EtablissementManager etsMgr) {
        this.etsMgr = etsMgr;
    }

    public Converter getEtablissementConverter() {
        return etablissementConverter;
    }

    public void setEtablissementConverter(Converter etablissementConverter) {
        this.etablissementConverter = etablissementConverter;
    }

    public Categorie getNewCateMc() {
        return newCateMc;
    }

    public void setNewCateMc(Categorie newCateMc) {
        this.newCateMc = newCateMc;
    }
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

    public List<Etablissement> getEtablissements() {
        return etablissements;
    }

    public void setEtablissements(List<Etablissement> etablissements) {
        this.etablissements = etablissements;
    }

    public void upload() throws FileNotFoundException, IOException {

        leMaitre = new Maitrecommunautaire();
        listeMaitres = new ArrayList<>();
        FileInputStream fis = null;
        String filePath = "D:\\temp\\kk\\";
        byte[] bytes;
        int n = 0;

        if (file != null && file.getSize() > 0L) {

            System.out.println("le nom du fichier  " + file.getFileName() + " son poids  " + file.getSize());
            //bytes = file.getContent();
            //fichier retenu pour le traitement 
            FileInputStream leFichier;
            String nomFichier = FilenameUtils.getName(file.getFileName());
            //
            try {//lancement du traitement du fichier telecharger
                File f = new File(NAME + nomFichier);
                //System.out.println("le absolute path du fichier uploader " + f.getAbsolutePath());
                //System.out.println("le bon absolute path doit etre " + NAME + nomFichier);

                if (!f.getAbsolutePath().equalsIgnoreCase("D:\\nouveaumaitres\\fichiers\\nouveauxmaitres.xlsx")) {//si le fichier nest pas dns le bon repertoire
                    System.out.println("mauvais repertoire ou mauvaise extension du fichier");
                    msgErreurRepertoire();

                } else {

                    System.out.println("bon repertoire");
                    msgSuccesFileUpload();
                    leFichier = new FileInputStream(f);
                    Workbook wb = null;
                    //wb = WorkbookFactory.create(leFichier);
                    wb = new XSSFWorkbook(leFichier);
                    //Sheet sheet = wb.getSheetAt(0);
                    DataFormatter dataFormatter = new DataFormatter();
                    //nouveau traitement 
                    Iterator<Sheet> sheets = wb.sheetIterator();

                    while (sheets.hasNext()) {
                        Sheet sh = sheets.next();
                        //System.out.println("nom du fichier  " + sh.getSheetName());
                        //System.out.println("-------------------");                
                        Iterator<Row> iterator = sh.iterator();
                        while (iterator.hasNext()) {
                            String[] dataTab = new String[13];
                            int i = 0;
                            //System.out.println("debut ligne");
                            Row row = iterator.next();
                            Iterator<Cell> celIterator = row.iterator();
                            while (celIterator.hasNext()) {
                                // System.out.println("debut celule ");
                                //System.out.println("la valeur de i "+i);
                                Cell cell = celIterator.next();
                                //recup de type date 
                                String cellValue = dataFormatter.formatCellValue(cell);
                                //System.out.println(cellValue + "\t");
                                // System.out.println("ligne  " + cell.getRow().getRowNum());
                                //remplir le tableau de donnees si il s'agit de donnee et non d'entete du fichier excel 
                                if (cell.getRow().getRowNum() > 0) {
                                    dataTab[i] = cellValue;
                                    switch (i) {
                                        case 0:
                                            System.out.println("ecole  " + cellValue);
                                            leMaitre.setEcole(cellValue);
                                            break;
                                        case 1:
                                            System.out.println("type ecole  " + cellValue);
                                            leMaitre.setType_ecole(cellValue);
                                            break;
                                        case 2:
                                            System.out.println("nom "+cellValue);
                                            leMaitre.setNom(cellValue);
                                            break;
                                        case 3:
                                            System.out.println("prenoms  "+cellValue);
                                            leMaitre.setPrenoms(cellValue);
                                            break;
                                        case 4:
                                            System.out.println("genre   "+cellValue);
                                            leMaitre.setGenre(cellValue);
                                            break;
                                        case 5:
                                            System.out.println("langue   "+cellValue);
                                            leMaitre.setLangue(cellValue);
                                            break;
                                        case 6:
                                            System.out.println("date de naissance  "+cellValue);
                                            leMaitre.setDatenaissance(cellValue);
                                            break;
                                        case 7:
                                            System.out.println("contact 1  "+cellValue);
                                            leMaitre.setContactun(cellValue);
                                            break;
                                        case 8:
                                            System.out.println("bailleur  "+cellValue);
                                            leMaitre.setBailleur(cellValue);
                                            break;
                                        case 9:
                                            System.out.println("categorie  "+cellValue);
                                            leMaitre.setCategoriepro(cellValue);
                                            break;
                                        case 10:
                                            System.out.println("salaire  "+cellValue);
                                            leMaitre.setMensuel(cellValue);
                                            break;
                                        case 11:
                                            System.out.println("statut "+cellValue);
                                            leMaitre.setStatutcompte(cellValue);
                                            break;
                                        default:
                                            break;
                                    }
                                    i++;
                                }
                                System.out.println("fin traitement cellule");
                            }
                            System.out.println("fin ligne ");
                            //System.out.println("----------");
                            if (dataTab[0] == null) {
                                //System.out.println("le tableau est vide on ne peux rien afficher");
                            } else {
                                //System.out.println("nom   " + leMaitre.getNom() + "  ecole   " + leMaitre.getEcole());
                                listeMaitres.add(leMaitre);
                                leMaitre = new Maitrecommunautaire();
                            }
                        }
                        //System.out.println("*********");
                        //System.out.println("affichage de la liste de maitres ");
//                        for (Maitrecommunautaire m : listeMaitres) {
//                            System.out.println("nom  " + m.getNom() + "   ecole  " + m.getEcole());
//                        }
                    }
                    wb.close();
                }
            } catch (IOException e) {//le fichier ne respect pas les bon critère de repertoire
                System.out.println("erreur lors du telechargement du fichier." + e.getMessage());
                msgErreurRepertoire();
            }
            //
        } else {
            //le user a cliquer sur le btn valider sans telecharger le fichier 
            msgErreurUpload();
        }
    }

    public void uploadMultiple() {
        if (files != null) {
            for (UploadedFile f : files.getFiles()) {
                FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    //supprimer le user de la liste des mc a insert a partir du fichier excel 
    public void deleteFromList() {
        System.out.println("delete le mc de la liste ....");
        listeMaitres.remove(selectedMc);
        //PrimeFaces.current().executeScript("PF('viewModifMcDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:tbl");
    }

    public void deleteUser() {
        listeMaitres.remove(this.selectedMc);
        this.selectedMc = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Maitre communautaire retiré"));
        PrimeFaces.current().ajax().update("form:messages", "form:tbl");

    }

    public void majInfosMc() {

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
        Utilisateur createur = utilisateurMgr.verif(userCo);

        //nom
        if (nomMc != null) {
            selectedMc.setNom(nomMc);
        } else {
            selectedMc.setNom(selectedMc.getNom());
            if (selectedMc.getNom() == null && selectedMc.getNom().isEmpty()) {
                System.out.println("veuillez renseigner le nom du mc");
            }
        }
        //GESTION DE PRENOMS ICI 
        if (prenomsMc != null) {
            selectedMc.setPrenoms(prenomsMc);
        } else {
            selectedMc.setPrenoms(selectedMc.getPrenoms());
            if (selectedMc.getPrenoms() == null && selectedMc.getPrenoms().isEmpty()) {
                System.out.println("veuillez renseigner le prenom du mc");
            }
        }

        //genre 
        if (genreMc != null) {
            selectedMc.setGenre(genreMc);
        } else {
            selectedMc.setGenre(selectedMc.getGenre());
        }
        //le contact
        if (numeroUnMc != null) {
            System.out.println("numero saisi  " + numeroUnMc);
            selectedMc.setContactun(numeroUnMc);
        } else {
            System.out.println("numero gardee  " + selectedMc.getContactun());
        }
        //operateur telecom
        if (operateurTelcoMc != null) {
            selectedMc.setOperatortelco(operateurTelcoMc);
        } else {
            msgErreurOperateurTelcoMc();
        }

        //domicile 
        if (nniMc != null) {
            selectedMc.setNni(nniMc);
        } else {
            msgErreurDomicile();
        }
        //lieu de naissance 
        selectedMc.setLieudenaissance(lieuDeNaissanceMc);
        //bailleur 
        if (bailleurMc != null) {
            System.out.println("bailleur renseigne  " + bailleurMc);
            selectedMc.setBailleur(bailleurMc);
        } else {
            System.out.println("bailleur retenu " + selectedMc.getBailleur());
        }
        //categorie
        System.out.println("categorie choisi " + selectedMc.getCategoriepro());

        if (selectedMc.getBailleur().equalsIgnoreCase("PREAT") && !selectedMc.getCategoriepro().equalsIgnoreCase("CAT1")) {
            msgErreurCategorie();
        } else if (selectedMc.getBailleur().equalsIgnoreCase("PARSET2") && (!selectedMc.getCategoriepro().equalsIgnoreCase("BOURSE") && !selectedMc.getCategoriepro().equalsIgnoreCase("CAT1") && !selectedMc.getCategoriepro().equalsIgnoreCase("CAT2") && !selectedMc.getCategoriepro().equalsIgnoreCase("CAT3"))) {
            msgErreurCategorie();
        }

        //milieu de résidence 
        if (milieuResidenceMc != null) {
            selectedMc.setMilieuResidence(milieuResidenceMc);
        } else {
            msgErreurMilieuResidencetMc();
        }
        //etablissement
        if (selectedMc.getEcole() != null) {
            //debogage de l'ecole 
            System.out.println("nom de lecole  " + selectedMc.getEcole());
            //recup de toutes les infos regionales 
            Etablissement ecole = etsMgr.getEtsByNom(selectedMc.getEcole());
            System.out.println("ecole recuperee   " + ecole.getNom());
            System.out.println("nom ipep  :" + ecole.getNomipep() + " nom iden :" + ecole.getNomiden() + " nom drej " + ecole.getNomdrej());
            selectedMc.setIpep(ecole.getNomipep());
            selectedMc.setIden(ecole.getNomiden());
            selectedMc.setDrej(ecole.getNomdrej());

        }
        //date de naissance 
        if (dateNaissanceMc != null) {
            selectedMc.setDatenaissance(dateNaissanceMc);
        } else {
            System.out.println("ON GARDE LA DATE DE NAISSANCE PRISE DANS LE FICHIER EXCEL ....." + selectedMc.getDatenaissance());
            selectedMc.setDatenaissance(selectedMc.getDatenaissance());
        }

        //langue
        System.out.println("la langue retenue " + selectedMc.getLangue());

        //statut compte mc
        System.out.println("statut du compte selectionne " + selectedMc.getStatutcompte());

        //statut wallet et etat compte reste inchangé 
        selectedMc.setEtatcomptemc(Boolean.TRUE);
        selectedMc.setStatutwallet(Boolean.TRUE);

        //date de creation du maitre dans la plateforme : date d'ajout 
        selectedMc.setDatecreationcompte(DateOfDay());
        //etat validation du coordonnateur 
        selectedMc.setValidationcoordonnateur(Boolean.TRUE);

        //generation du matricule
        generationMatricule(selectedMc);

        System.out.println("informations retenues  nom->" + selectedMc.getNom() + " \n genre->" + selectedMc.getGenre() + "  numero->" + selectedMc.getContactun()
                + "  operateur telecom " + selectedMc.getOperatortelco() + " domicile  " + selectedMc.getNni() + " lieu de naissance " + selectedMc.getLieudenaissance() + "  bailleur " + selectedMc.getBailleur()
                + "  milieu de residence " + selectedMc.getMilieuResidence() + " etablissement " + selectedMc.getEcole() + "  date de naissance " + selectedMc.getDatenaissance() + " langue " + selectedMc.getLangue()
                + " categorie pro " + selectedMc.getCategoriepro() + " observation " + selectedMc.getStatutcompte());

        //si persiste ok : save le log - sinon msg d'erreur
        if (mcMgr.persistByExcelFile(selectedMc)) {
            mcMgr.persist(selectedMc);
            //auditlog
            saveLog("insertion du nouveau maitre  " + selectedMc.getNom(), DateOfDay());
            //enregistrement de la notif
            BigDecimal typn = BigDecimal.valueOf(6);
            typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
            String details = "insertion dans la base de donnees du maitre : " + selectedMc.getNom() + "  " + selectedMc.getPrenoms() + "  par le coordonnateur :" + userCo + "par fichier excel";
            String libelleNotif = "insertion dans la base de donnees du maitre     " + selectedMc.getNom() + "  " + selectedMc.getPrenoms();
            notif.setLibelle(libelleNotif);
            notif.setDetails(details);
            notif.setDatecreation(DateOfDay());
            notif.setDateresolution(DateOfDay());
            notif.setEtat(BigInteger.ZERO);
            notif.setTypenotif(typeNotification);
            notif.setCreateur(createur);
            //recup du last id de la table mc pour insert du nouveau 
            int newId = mcMgr.nbreTotalDesMcs().intValue() + 1;
            //notif.setIdinfo(selectedMc.getId().toString());
            notif.setIdinfo(String.valueOf(newId));
            notifMgr.persist(notif);

            //persistance de toutes les notifications pour tous les utilisateurs ...
            //je recupère la liste de tous les users
            listeUsers = utilisateurMgr.getAllActivedUsers();
            //je recupère la dernière notif créée pour le setting a venir 
            Integer lastNotifId = notifMgr.lastNotif();

            for (Utilisateur u : listeUsers) {
                //System.out.println("le user en cours ... : " + u.getNOM());
                Usersnotifs userNotif = new Usersnotifs();
                userNotif.setDateinsert(DateOfDay());
                userNotif.setEtat(BigInteger.ZERO);
                userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
                userNotif.setTitre(libelleNotif);
                userNotif.setInformation(details);
                userNotif.setCreateur(userCo);
                userNotif.setTypeusernotif("CREA_MC");

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
            //maj du tableau des mc et raffraichissement de la vue 
            listeMaitres.remove(selectedMc);
            PrimeFaces.current().executeScript("PF('viewModifMcDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:tbl");
            clearChampsAfterMajMineure();
            notif = null;
        } else {
            System.out.println("sauvegarde impossible ....");
            msgErreurInsertDeLaBd(selectedMc.getContactun());
            PrimeFaces.current().executeScript("PF('viewModifMcDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:tbl");
            clearChampsAfterMajMineure();
            notif = null;
        }
        PrimeFaces.current().executeInitScript("PF('viewModifMcDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:tbl");
        clearChampsAfterMajMineure();
    }

    public void generationMatricule(Maitrecommunautaire mc) {

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
        mc.setMatricule(matricule);
    }

    public void msgSuccesModif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "succès", "le maitre a été ajouté dans la base de données , prière valider son compte"));
    }

    public void msgSuccesFileUpload() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "succès", "fichier téléchargé , prière valider les maitres dans le tableau ci-dessous"));
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void handleFilesUpload(FilesUploadEvent event) {
        for (UploadedFile f : event.getFiles().getFiles()) {
            FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    //msg d'erreur sur la saisie d'une categorie 
    public void msgErreurCategorie() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "cette catégorie n'existe pas pour pour ce Bailleur"));
    }

    //msg d'erreur sur le repertoir du fichier a télécharger 
    public void msgErreurRepertoire() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "attention le fichier doit obligatoirement se trouver ici : " + NAME + "  ou son nom doit être  :   " + nomFichierAUploader));
    }

    //msg d'erreur lors de l'insert en bd
    public void msgErreurInsertDeLaBd(String nomMc) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "Un maitre communautaire existe deja dans la base de donnees avec ce contact   " + nomMc));
    }

    public void msgErreurUploadFichier() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "le fichier n'a pu etre téléchargé  :"));
    }

    //msg erreur si le fuser na pas telecharger le fichier et a valider sur le btn 
    public void msgErreurUpload() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "veuillez télécharger le fichier avant de valider  "));
    }

    private void msgErreurNomMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "veuillez renseigner le nom du maitre svp "));
    }

    private void msgErreurOperateurTelcoMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "veuillez renseigner l'opérateur télécom  du maitre svp "));
    }

    private void msgErreurGenre() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "veuillez renseigner le sexe du maitre svp"));
    }

    private void msgErreurDomicile() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "veuillez renseigner le domicile du maitre svp"));
    }

    private void msgErreurStatutCompteMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "veuillez renseigner le statut du compte du maitre svp"));
    }

    private void msgErreurEtablissementMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "veuillez renseigner l'etablissement du maitre svp"));
    }

    private void msgErreurMilieuResidencetMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "veuillez renseigner le milieu de residence du maitre svp"));
    }

    private void msgErreurDateNaissanceMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "veuillez renseigner la date de naissance du maitre svp"));
    }

    private void msgErreurSaisieLangueMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "veuillez renseigner la langue du maitre svp"));
    }

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
        log.setAction(msg + " par l'utilisateur " + userLogin);
        log.setDateaction(date);
        audit.persist(log);
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
