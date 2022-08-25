/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Categorie;
import com.sbs.apiced_web.entities.Etablissement;
import com.sbs.apiced_web.entities.Ipep;
import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Promotion;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Usersnotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.entities.Villes;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.CategorieMcManager;
import com.sbs.apiced_web.services.EtablissementManager;
import com.sbs.apiced_web.services.MaitreCoManager;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.PrimeFaces;

/**
 *
 * @author samuel
 */
@Named
@ViewScoped
public class DetailMaitreCoMBean implements Serializable {

    private Maitrecommunautaire mc;
    private BigDecimal idmc;
    private String nomEtablissement;
    private Etablissement mcEtablissement;
    private String nomCategorie;
    private Maitrecommunautaire selectedMc;
    private Maitrecommunautaire updatedMc;
    private List<Maitrecommunautaire> listeUnMc = new ArrayList<>();
    private List<Categorie> listeCategorieMc;
    private List<String> listeNomsEtablissements;
    private List<String> listeNomsOperateurs;
    private String nomOperateurSelectedMc;
    private String operateurMc;
    private Promotion promo;
    private String statutMaitre;
    private Boolean etatMoMo;
    private Villes lieudenaissance;
    private List<Villes> listeDesVilles;
    private String userCo;
    private String phoneMc;
    private Ipep localite;
    private LocalDate datedenaissance;
    private LocalDate datePriseFonction;
    private LocalDate datePriseRetraite;
    private Typenotifs typeNotification;
    private Notifications notif = new Notifications();
    private List<Utilisateur> listeUsers;

    @EJB
    private UsersNotifManager userNotifMgr;
    @EJB
    private MaitreCoManager mcManager;
    @EJB
    private CategorieMcManager categMgr;
    @EJB
    private EtablissementManager etsManager;
    @EJB
    private AuditlogManager audit;
    @EJB
    private VilleManager villeMgr;
    @EJB
    private OperateurTelcoManager opMgr;
    @EJB
    private MaitreCoManager mcMgr;
    @EJB
    private TypeNotifManager typeNotifMgr;
    @EJB
    private UtilisateurManager utilisateurMgr;
    @EJB
    private NotifsManager notifMgr;

    public Maitrecommunautaire getUpdatedMc() {
        return updatedMc;
    }

    public void setUpdatedMc(Maitrecommunautaire updatedMc) {
        this.updatedMc = updatedMc;
    }

    public Ipep getLocalite() {
        return localite;
    }

    public void setLocalite(Ipep localite) {
        this.localite = localite;
    }

    public Etablissement getMcEtablissement() {
        return mcEtablissement;
    }

    public void setMcEtablissement(Etablissement mcEtablissement) {
        this.mcEtablissement = mcEtablissement;
    }

    public Maitrecommunautaire getSelectedMc() {
        return selectedMc;
    }

    public void setSelectedMc(Maitrecommunautaire selectedMc) {
        this.selectedMc = selectedMc;
    }

    public List<Maitrecommunautaire> getListeUnMc() {
        return listeUnMc;
    }

    public void setListeUnMc(List<Maitrecommunautaire> listeUnMc) {
        this.listeUnMc = listeUnMc;
    }

    public Boolean getEtatMoMo() {
        return etatMoMo;
    }

    public void setEtatMoMo(Boolean etatMoMo) {
        this.etatMoMo = etatMoMo;
    }

    public List<Categorie> getListeCategorieMc() {
        return listeCategorieMc;
    }

    public void setListeCategorieMc(List<Categorie> listeCategorieMc) {
        this.listeCategorieMc = listeCategorieMc;
    }

    public List<String> getListeNomsEtablissements() {
        return listeNomsEtablissements;
    }

    public void setListeNomsEtablissements(List<String> listeNomsEtablissements) {
        this.listeNomsEtablissements = listeNomsEtablissements;
    }

    public String getNomOperateurSelectedMc() {
        return nomOperateurSelectedMc;
    }

    public void setNomOperateurSelectedMc(String nomOperateurSelectedMc) {
        this.nomOperateurSelectedMc = nomOperateurSelectedMc;
    }

    public String getPhoneMc() {
        return phoneMc;
    }

    public void setPhoneMc(String phoneMc) {
        this.phoneMc = phoneMc;
    }

    public MaitreCoManager getMcManager() {
        return mcManager;
    }

    public void setMcManager(MaitreCoManager mcManager) {
        this.mcManager = mcManager;
    }

    public CategorieMcManager getCategMgr() {
        return categMgr;
    }

    public void setCategMgr(CategorieMcManager categMgr) {
        this.categMgr = categMgr;
    }

    public EtablissementManager getEtsManager() {
        return etsManager;
    }

    public void setEtsManager(EtablissementManager etsManager) {
        this.etsManager = etsManager;
    }

    public AuditlogManager getAudit() {
        return audit;
    }

    public void setAudit(AuditlogManager audit) {
        this.audit = audit;
    }

    public VilleManager getVilleMgr() {
        return villeMgr;
    }

    public void setVilleMgr(VilleManager villeMgr) {
        this.villeMgr = villeMgr;
    }

    public LocalDate getDatedenaissance() {
        return datedenaissance;
    }

    public void setDatedenaissance(LocalDate datedenaissance) {
        this.datedenaissance = datedenaissance;
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

    private Converter categorieConverter = new Converter() {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            System.out.println("value " + value);
            return categMgr.findCategorieById(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return ((Categorie) value).getIdcategorie().toString();
        }
    };

    @PostConstruct
    public void init() {
        // recuperation du user en session 
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        userCo = session.getAttribute("utilisateur").toString();
        listeDesVilles = villeMgr.getAllVilles();
        updatedMc = new Maitrecommunautaire();
    }

    public String getUserCo() {
        return userCo;
    }

    public void setUserCo(String userCo) {
        this.userCo = userCo;
    }

    public String getStatutMaitre() {
        return statutMaitre;
    }

    public void setStatutMaitre(String statutMaitre) {
        this.statutMaitre = statutMaitre;
    }

    public Promotion getPromo() {
        return promo;
    }

    public void setPromo(Promotion promo) {
        this.promo = promo;
    }

    public CategorieMcManager getCategManager() {
        return categMgr;
    }

    public void setCategManager(CategorieMcManager categMgr) {
        this.categMgr = categMgr;
    }

    public String getNOMCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public String getOperateurMc() {
        return operateurMc;
    }

    public void setOperateurMc(String operateurMc) {
        this.operateurMc = operateurMc;
    }

    public String getNOMEtablissement() {
        return nomEtablissement;
    }

    public void setNomEtablissement(String nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public String getNOMOperateurSelectedMc() {
        return nomOperateurSelectedMc;
    }

    public BigDecimal getIdmc() {
        return idmc;
    }

    public void setIdmc(BigDecimal idmc) {
        this.idmc = idmc;
    }

    public Maitrecommunautaire getMc() {
        return mc;
    }

    public void setMc(Maitrecommunautaire mc) {
        this.mc = mc;
    }

    public Maitrecommunautaire getDetails() {
        return mc;
    }

    public Villes getLieudenaissance() {
        return lieudenaissance;
    }

    public void setLieudenaissance(Villes lieudenaissance) {
        this.lieudenaissance = lieudenaissance;
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

    public Converter getCategorieConverter() {
        return categorieConverter;
    }

    public void setCategorieConverter(Converter categorieConverter) {
        this.categorieConverter = categorieConverter;
    }

    public OperateurTelcoManager getOpMgr() {
        return opMgr;
    }

    public void setOpMgr(OperateurTelcoManager opMgr) {
        this.opMgr = opMgr;
    }

    public LocalDate getDatePriseFonction() {
        return datePriseFonction;
    }

    public void setDatePriseFonction(LocalDate datePriseFonction) {
        this.datePriseFonction = datePriseFonction;
    }

    public LocalDate getDatePriseRetraite() {
        return datePriseRetraite;
    }

    public void setDatePriseRetraite(LocalDate datePriseRetraite) {
        this.datePriseRetraite = datePriseRetraite;
    }

    public void loadMaitreCoDetails() {
        try {
            //this.mc = mcManager.getMaitreCoInfo(idmc);
            this.mc = mcManager.loadMcByPhoneNumber(phoneMc);

            mcEtablissement = etsManager.getEtsByNom(this.mc.getEcole());
            // System.out.println("l'etablissement recuperee est   " + mcEtablissement);
            // localite = etsManager.EtablissementIpep(mcEtablissement.getIpep());

        } catch (Exception e) {
            this.mc = null;
            System.out.println("il n'existe pas de maitre communautaire avec ce numéro" + e.getMessage());
        }
    }

    public void loadMcForUpdate() {
        mc = mcManager.loadMcByMatricule(mc.getMatricule());
        //System.out.println("le mc selectionné est "+mc.getNOM());
    }

    //recuperer le nom opérateur du mc selectionné   
//    public String getMcNomOp() {
//        return nomOperateurSelectedMc = mcManager.getOperateurName(mc.getOperatortelco());
//    }
    //methode retournant la liste de tous les établissement sauf l'actuel du mc selectionné
//    public List<String> getListeNomsEtablissement() {
//        if (listeNomsEtablissements == null) {
//            listeNomsEtablissements = mcManager.getAllEtablissementsNameWithoutMcEts(mc.getEcole());
//        }
//        return listeNomsEtablissements;
//    }
    //methode retournant la liste de toutes les catégorie des mc 
    public List<Categorie> getListeNomsCategorie() {
        if (listeCategorieMc == null) {
            //listeCategorieMc = mcManager.getAllCategorieNameWithoutMcCategorie(mc.getIdcategoriepro().getLibelle());
            listeCategorieMc = categMgr.allMcCategorie();
        }
        return listeCategorieMc;
    }

    //retournant la liste de tous les nom des opérateurs sans le nom de l'operateur du mc selectionné
    public List<String> getListeNomsOperateurs() {
        if (listeNomsOperateurs == null) {
            listeNomsOperateurs = mcManager.getAllOperateurNameWithoutMcOp(mc.getOperatortelco());
        }
        return listeNomsOperateurs;
    }

    //suppression de la demande de creation d'un mc 
    public void deleteMaitre() {
        System.out.println("suppression du mc d'id " + mc.getId());
        msgSuccesSuppressionMc();
        mcManager.deleteProfil(mc.getId());
    }

    //M.A.J de maitre
    /**
     * methode de maj des infos d'un maitre
     */
    public void updateMaitre() {
        System.out.println("mise a jours des infos du maitre ");
        Utilisateur createur = utilisateurMgr.verif(userCo);
        String nouvelleDateNaissance = null, dateDePriseDeFonction = null, dateDeDepartRetraite = null;
        int dateCompare;
        //System.out.println("date naissance "+datedenaissance+"  date de depart a la retraite  "+datePriseRetraite+"  date de prise de fonction  "+datePriseFonction);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
        try {
            //comparaison de la date de depart a la retraite et la date de prise de fonction
            dateCompare = datePriseRetraite.compareTo(datePriseFonction);
            System.out.println("resultat de comparaison des dates    " + dateCompare);

            if (dateCompare > 0) {//date de prise de fonction est bien antérieur a la date de depart a la retraite 
                System.out.println("date de prise de fonction est bien antérieur a la date de depart a la retraite ");
            } else if (dateCompare < 0) {//la date de prise retraite est antérieur a la date de prise de fonction (pas bon)
                System.out.println("date de prise de fonction est bien antérieur a la date de depart a la retraite ");
                erreurMsgWorkDateOne();

            } else {//les deux dates sont egales ...(pas bon)
                System.out.println("erreur , date de prise fonction et date retraite sont identique  ");
                erreurMsgWorkDateTwo();
            }

            nouvelleDateNaissance = f.format(datedenaissance);
            dateDeDepartRetraite = f.format(datePriseRetraite);
            dateDePriseDeFonction = f.format(datePriseFonction);

            System.out.println("date de naissance   " + nouvelleDateNaissance + " date debut fonction " + dateDePriseDeFonction + " date prise retraite  " + dateDeDepartRetraite);

        } catch (NullPointerException e) {
            System.out.println("les champs date ne sont pas tous rempli ...");
        }

        try {
            System.out.println("nom " + updatedMc.getNom() + " etat retraite" + updatedMc.getEtatretraite());
        } catch (Exception e) {
            System.out.println("certain champs du forumulaire n'ont pas ete remplis ");
        }

        //coder le update des infos du maitre en fonction du 
        //statut wallet et etat compte reste inchangé 
        // selectedMc.setEtatcomptemc(selectedMc.getEtatcomptemc());
        // selectedMc.setStatutwallet(selectedMc.getStatutwallet());
        if (!updatedMc.getNom().isEmpty()) {
            this.mc.setNom(updatedMc.getNom());
        }
        if (!updatedMc.getPrenoms().isEmpty()) {
            this.mc.setPrenoms(updatedMc.getPrenoms());
        }
        if (!updatedMc.getGenre().isEmpty()) {
            this.mc.setGenre(updatedMc.getGenre());
        }
        try {
            if (!nouvelleDateNaissance.isEmpty()) {
                this.mc.setDatenaissance(nouvelleDateNaissance);
            }
        } catch (Exception e) {
            System.out.println("le champ date de naissance est vide");
        }

        if (!updatedMc.getLieudenaissance().isEmpty()) {
            this.mc.setLieudenaissance(updatedMc.getLieudenaissance());
        }
        if (!updatedMc.getSitmatrimonial().isEmpty()) {
            this.mc.setSitmatrimonial(updatedMc.getSitmatrimonial());
        }
        if (!updatedMc.getLangue().isEmpty()) {
            this.mc.setLangue(updatedMc.getLangue());
        }
        if (!updatedMc.getContactun().isEmpty()) {
            this.mc.setContactun(updatedMc.getContactun());
        }
        if (!updatedMc.getContactdeux().isEmpty()) {
            this.mc.setContactdeux(updatedMc.getContactdeux());
        }
        if (!updatedMc.getOperatortelco().isEmpty()) {
            this.mc.setOperatortelco(updatedMc.getOperatortelco());
        }

        //gestion du statut wallet 
        System.out.println("statut wallet selectionné   " + mc.getStatutwallet());

        if (!updatedMc.getBailleur().isEmpty()) {
            this.mc.setBailleur(updatedMc.getBailleur());
        }

        if (!updatedMc.getCategoriepro().isEmpty()) {
            this.mc.setCategoriepro(updatedMc.getCategoriepro());
        }

        if (!updatedMc.getMensuel().isEmpty()) {
            this.mc.setMensuel(updatedMc.getMensuel());
        }

        if (!updatedMc.getDrej().isEmpty()) {
            this.mc.setDrej(updatedMc.getDrej());
        }

        if (!updatedMc.getIden().isEmpty()) {
            this.mc.setIden(updatedMc.getIden());
        }

        if (!updatedMc.getIpep().isEmpty()) {
            this.mc.setIpep(updatedMc.getIpep());
        }

        if (!updatedMc.getType_ecole().isEmpty()) {
            this.mc.setType_ecole(updatedMc.getType_ecole());
        }

        if (!updatedMc.getEcole().isEmpty()) {
            this.mc.setEcole(updatedMc.getEcole());
        }

        try {
            if (!dateDePriseDeFonction.isEmpty()) {
                this.mc.setDateprisefonction(dateDePriseDeFonction);
            }
        } catch (Exception e) {
            System.out.println("pris de fonction vide");
        }

        try {
            if (!dateDeDepartRetraite.isEmpty()) {
                this.mc.setDateretraite(dateDeDepartRetraite);
                this.mc.setEtatretraite(Boolean.TRUE);
            }
        } catch (Exception e) {
            System.out.println("date de retraite vide ");
        }

        if (!updatedMc.getNni().isEmpty()) {
            this.mc.setNni(updatedMc.getNni());
        }

        try {
            if (!updatedMc.getStatutcompte().isEmpty()) {
                this.mc.setStatutcompte(updatedMc.getStatutcompte());
            }
        } catch (Exception e) {
            System.out.println("champ observation resté intact");
        }

        mcMgr.updateMaitre(mc);

//auditlog
        saveLog("mise a jour des informations du maitre  " + updatedMc.getNom(), DateOfDay());
        //enregistrement de la notif
        BigDecimal typn = BigDecimal.valueOf(6);
        typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
        String details = "mise a jour des informations du maitre communautaire : " + updatedMc.getNom() + "  " + updatedMc.getPrenoms() + "  par le coordonnateur :" + userCo;
        String libelleNotif = "Mise à jour des informations du maitre communautaire     " + updatedMc.getNom() + "  " + updatedMc.getPrenoms();
        notif.setLibelle(libelleNotif);
        notif.setDetails(details);
        notif.setDatecreation(DateOfDay());
        notif.setDateresolution(DateOfDay());
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

    public void pdfExport() {
        System.out.println("impression du pdf du mc : " + mc.getNom());
        //aller chercher un mc en fonction de son gsm
        listeUnMc = mcMgr.McByPhone(mc.getContactun());
        //System.out.println("la taille de listeUnMc  " + listeUnMc.size());
        String fileName = "MaitreCommunautaire_" + mc.getNom() + ".pdf";
        System.out.println("le nom du fichier : " + fileName);
        //String jasperPath = "/resources/mcReport.jasper";
        String jasperPath = "/resources/detailsMaitre.jasper";

        try {
            this.PDFOne(null, jasperPath, mc, fileName);
        } catch (JRException | IOException ex) {
            Logger.getLogger(DetailMaitreCoMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void PDFOne(Map<String, Object> params, String jasperPath, Maitrecommunautaire datasource, String fileName) throws JRException, IOException {
        String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
        File file = new File(relativePath);
        JRAbstractBeanDataSource data = new JRBeanCollectionDataSource(listeUnMc);
        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params, data);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachement;filename=" + fileName);
        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);
        FacesContext.getCurrentInstance().responseComplete();
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

    //fonction de verification de l'etat du compte mobile money du user 
    public void verifMobileMoney() {
        System.out.println("verification du compte chez l'opérateur en cours....");
        System.out.println("l'état actuel est :" + mc.getStatutwallet());
        //mettre a true si c a false
        if (mc.getStatutwallet().equals(Boolean.FALSE)) {
            etatMoMo = Boolean.TRUE;
        }
        System.out.println("l'etat après :" + etatMoMo);
        mc.setStatutwallet(etatMoMo);
        PrimeFaces.current().ajax().update("form:messages");
        msgMomoVerif();
    }

    public void msgSuccesModif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informations enregistrées", "le maitre a été modifié"));
    }

    public void msgNotifDesactivationMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention :", "le maitre a désactivé"));

    }

    private void msgMomoVerif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès ", "Le portfeuille éléctronique est actif"));
    }

    public void msgSuccesSuppressionMc() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "le maître communautaire est supprimé"));
    }

    public String DateOfDay() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String date = dt.format(dtFormat);
        return date;
    }

    public void erreurMsgWorkDateOne() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "La date de depart retraite  est antérieure à la date de prise fonction , veuillez corriger"));
    }

    public void erreurMsgWorkDateTwo() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Les dates de depart retraite et date prise fonction sont égales , veuillez corriger"));
    }

}
