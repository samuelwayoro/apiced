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
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.PrimeFaces;
import com.sbs.apiced_web.entities.Person;

/**
 *
 * @author samuel
 */
@Named(value = "rechercherMBean")
@ViewScoped
public class RechercherMBean implements Serializable {

    private String nomMc;
    private String prenomsMc;
    private String matricule;
    private List<Maitrecommunautaire> mcs = new ArrayList<>();
    private Maitrecommunautaire selectedMc;
    private Notifications notif = new Notifications();
    private Categorie newCateMc;
    private String userCo;
    private String genreMc;
    private String numeroUnMc;
    private String contactDeuxMc;
    private LocalDate newMcDateNaiss;
    private String nouvelleDateNaissance;
    private String lieuDeNaissanceMc;
    private String domicileMc;
    private Etablissement etsMc;
    private LocalDate newMcDatePriseFonction;
    private String nouvelleDatePriseFonction;
    private List<String> newMcClasses = new ArrayList<>();
    private Typenotifs typeNotification;
    private List<Utilisateur> listeUsers;
    private List<Etablissement> etablissements;
    private List<Categorie> listeCategories;
    private List<String> listeOperateurs;

    @EJB
    private UtilisateurManager utilisateurMgr;
    @EJB
    private MaitreCoManager mcManager;
    @EJB
    private MaitreCoManager mcMgr;
    @EJB
    private TypeNotifManager typeNotifMgr;
    @EJB
    private NotifsManager notifMgr;
    @EJB
    private UsersNotifManager userNotifMgr;
    @EJB
    private AuditlogManager audit;
    @EJB
    private EtablissementManager etsMgr;
    @EJB
    private CategorieMcManager categMgr;
    @EJB
    private OperateurTelcoManager opTelcoMgr;

    @PostConstruct
    public void init() {
        // recuperation du user en session 
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        userCo = session.getAttribute("utilisateur").toString();
        System.out.println("USER MODIFICATEUR   " + userCo);
        listeOperateurs = opTelcoMgr.getAllOperateurTelcoNames();
        etablissements = etsMgr.getEtablissements();
        //System.out.println("taille de la liste des etablissements  " + etablissements.size());
        listeCategories = categMgr.allMcCategorie();
        // mcs = mcMgr.allMaitreCommunautaires();
    }

//     public void printPDF() throws JRException, IOException {
//        System.out.println("la taille de l'annuaire " + listeAnnuaire.size());
//        System.out.println("le contenu de listeAnnuaire : ");
//        for (Maitrecommunautaire m : listeAnnuaire) {
//            System.out.println("-->" + m.getNom());
//        }
//        String fileName = "Liste_Maitres_Communautaire_" + DateOfDay() + ".pdf";
//        System.out.println("le nom du fichier : " + fileName);
//        String jasperPath = "/resources/reportAnnuaire.jasper";
//
//        this.PDF(null, jasperPath, listeAnnuaire, fileName);
//    }
//
//    public void PDF(Map<String, Object> params, String jasperPath, List<Maitrecommunautaire> datasource, String fileName) throws JRException, IOException {
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
    public void annuairRapportPDF() throws JRException, IOException {
        System.out.println("lancement de l'impression ...");
//        List<Person> dataSource = new ArrayList<>();
//        dataSource.add(new Person("Leonel", "Messi"));
//        dataSource.add(new Person("Christiano", "Ronaldo"));
//        dataSource.add(new Person("Kylian", "M'bappé"));
        System.out.println("la taille de la liste " + mcs.size());

//        mcs.add(new Maitrecommunautaire("toto", "tata"));
//        mcs.add(new Maitrecommunautaire("titi", "tutu"));
        String fileName = "maitres.pdf";
        String jasperPath = "/resources/maitres_communautaires.jasper";
        PDF(null, jasperPath, mcs, fileName);
    }

    public void PDF(Map<String, Object> params, String jasperPath, List<?> datasource, String fileName) throws JRException, IOException {
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

    public OperateurTelcoManager getOpTelcoMgr() {
        return opTelcoMgr;
    }

    public void setOpTelcoMgr(OperateurTelcoManager opTelcoMgr) {
        this.opTelcoMgr = opTelcoMgr;
    }

    public List<Categorie> getListeCategories() {
        return listeCategories;
    }

    public void setListeCategories(List<Categorie> listeCategories) {
        this.listeCategories = listeCategories;
    }

    public List<String> getListeOperateurs() {
        return listeOperateurs;
    }

    public void setListeOperateurs(List<String> listeOperateurs) {
        this.listeOperateurs = listeOperateurs;
    }

    public List<Etablissement> getEtablissements() {
        return etablissements;
    }

    public void setEtablissements(List<Etablissement> etablissements) {
        this.etablissements = etablissements;
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

    public String getNomMc() {
        return nomMc;
    }

    public void setNomMc(String nomMc) {
        this.nomMc = nomMc;
    }

    public String getPrenomsMc() {
        return prenomsMc;
    }

    public void setPrenomsMc(String prenomsMc) {
        this.prenomsMc = prenomsMc;
    }

    public List<Maitrecommunautaire> getMcs() {
        return mcs;
    }

    public void setMcs(List<Maitrecommunautaire> mcs) {
        this.mcs = mcs;
    }

    public MaitreCoManager getMcManager() {
        return mcManager;
    }

    public void setMcManager(MaitreCoManager mcManager) {
        this.mcManager = mcManager;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Maitrecommunautaire getSelectedMc() {
        return selectedMc;
    }

    public void setSelectedMc(Maitrecommunautaire selectedMc) {
        this.selectedMc = selectedMc;
    }

    public Notifications getNotif() {
        return notif;
    }

    public void setNotif(Notifications notif) {
        this.notif = notif;
    }

    public Categorie getNewCateMc() {
        return newCateMc;
    }

    public void setNewCateMc(Categorie newCateMc) {
        this.newCateMc = newCateMc;
    }

    public String getUserCo() {
        return userCo;
    }

    public void setUserCo(String userCo) {
        this.userCo = userCo;
    }

    public String getGenreMc() {
        return genreMc;
    }

    public void setGenreMc(String genreMc) {
        this.genreMc = genreMc;
    }

    public String getNumeroUnMc() {
        return numeroUnMc;
    }

    public void setNumeroUnMc(String numeroUnMc) {
        this.numeroUnMc = numeroUnMc;
    }

    public String getContactDeuxMc() {
        return contactDeuxMc;
    }

    public void setContactDeuxMc(String contactDeuxMc) {
        this.contactDeuxMc = contactDeuxMc;
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

    public String getLieuDeNaissanceMc() {
        return lieuDeNaissanceMc;
    }

    public void setLieuDeNaissanceMc(String lieuDeNaissanceMc) {
        this.lieuDeNaissanceMc = lieuDeNaissanceMc;
    }

    public String getDomicileMc() {
        return domicileMc;
    }

    public void setDomicileMc(String domicileMc) {
        this.domicileMc = domicileMc;
    }

    public Etablissement getEtsMc() {
        return etsMc;
    }

    public void setEtsMc(Etablissement etsMc) {
        this.etsMc = etsMc;
    }

    public LocalDate getNewMcDatePriseFonction() {
        return newMcDatePriseFonction;
    }

    public void setNewMcDatePriseFonction(LocalDate newMcDatePriseFonction) {
        this.newMcDatePriseFonction = newMcDatePriseFonction;
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

    public RechercherMBean() {
    }

    public void searchByfirstnameAndLastname() {
        if ((!nomMc.isEmpty() && !prenomsMc.isEmpty()) && (nomMc != null && prenomsMc != null)) {

            System.out.println("rechercher avec noms et prenoms..........");
            System.out.println("nom prenoms saisis :" + nomMc + "   " + prenomsMc);
            mcs = mcManager.rechercherParNomPrenoms(nomMc, prenomsMc);
            System.out.println("nombre de maitre trouve  " + mcs.size());

            for (Maitrecommunautaire mc : mcs) {
                System.out.println("---->" + mc.getNom());
            }

        } else if ((!nomMc.isEmpty() && prenomsMc.isEmpty())) {

            System.out.println("rechercher avec le nom...");
            System.out.println("nom saisi  " + nomMc);
            mcs = mcManager.rechercherParNom(nomMc);

        } else if (nomMc.isEmpty() && !prenomsMc.isEmpty()) {
            System.out.println("rechercher avec le prenoms ...");
            System.out.println("prenom saisi  " + prenomsMc);
            mcs = mcManager.rechercherParPrenoms(prenomsMc);
        }
    }

    public void searchByMatricule() {

        if (!matricule.isEmpty() && matricule != null) {
            System.out.println("matricule saisi : " + matricule);
            mcs = mcManager.rechercherParMatricule(matricule);
            System.out.println("le mc retrouve");
            for (Maitrecommunautaire mc : mcs) {
                System.out.println(" nom  " + mc.getNom() + "  prenoms" + mc.getPrenoms() + "  matricule " + mc.getMatricule());

            }
        }

    }

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

                //categorie
                if (!selectedMc.getCategoriepro().equals(newCateMc.getLibelle())) {
                    System.out.println("val de categorie en cours ->" + selectedMc.getCategoriepro() + " val du choix  " + newCateMc.getLibelle());
                    selectedMc.setCategoriepro(newCateMc.getLibelle());
                } else {
                    System.out.println("val de categorie en cours ->" + selectedMc.getCategoriepro() + " val du choix  " + newCateMc.getLibelle());

                    selectedMc.setCategoriepro(selectedMc.getCategoriepro());
                }

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
                selectedMc.setLieudenaissance(selectedMc.getLieudenaissance());

                //domicile 
                if (domicileMc != null) {
                    System.out.println("le domicile saisi  " + domicileMc);
                    selectedMc.setNni(domicileMc);
                } else {
                    System.out.println("le domicile finale  " + domicileMc);
                    selectedMc.setNni(selectedMc.getNni());
                }

                //ecole
                if (etsMc.getNom() != selectedMc.getEcole()) {
                    System.out.println("val de lecol actu  " + selectedMc.getEcole() + "  val de la saisie " + etsMc.getNom());
                    selectedMc.setEcole(etsMc.getNom());
                } else {
                    System.out.println("val de lecol actu  " + selectedMc.getEcole() + "  val de la saisie " + etsMc.getNom());

                    selectedMc.setEcole(selectedMc.getEcole());
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
            System.out.println("utilisateur modificateur " + createur);
            //genre 
            if (genreMc != null) {
                selectedMc.setGenre(genreMc);
            }
            //categorie
            if (!selectedMc.getCategoriepro().equals(selectedMc.getCategoriepro())) {
                //System.out.println("val de categorie en cours ->"+selectedMc.getCategoriepro()+" val du choix  "+newCateMc.getLibelle());
                selectedMc.setCategoriepro(newCateMc.getLibelle());
            } else {
                //  System.out.println("val de categorie en cours ->"+selectedMc.getCategoriepro()+" val du choix  "+newCateMc.getLibelle());
                selectedMc.setCategoriepro(selectedMc.getCategoriepro());
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

            selectedMc.setLieudenaissance(selectedMc.getLieudenaissance());

            //domicile 
            if (domicileMc != null) {
                System.out.println("domicile non changé " + domicileMc);
                selectedMc.setNni(domicileMc);
            } else {
                System.out.println("domicile changé " + selectedMc.getNni());
                selectedMc.setNni(selectedMc.getNni());
            }

            //ecole
            if (etsMc.getNom() != selectedMc.getEcole()) {
                selectedMc.setEcole(etsMc.getNom());
            } else {
                selectedMc.setEcole(selectedMc.getEcole());
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
        //maj du tableau des mc et raffraichissement de la vue 
        PrimeFaces.current().executeScript("PF('viewModifMcDialog').hide()");
        PrimeFaces.current().ajax().update("listMcForm:messages", "listMcForm:maitresTrouves");
        clearChampsAfterMajMineure();
        notif = null;
    }

    //msg d'erreur sur la saisie d'une categorie 
    public void msgErreurCategorie() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur", "cette catégorie n'existe pas pour pour ce Bailleur"));
    }

    //msg de succes de modification opérateur
    public void msgSuccesModif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "succès", "les informations du maitre ont été modifiées"));
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
        domicileMc = null;
        nouvelleDatePriseFonction = null;
        newMcClasses.clear();
    }

}
