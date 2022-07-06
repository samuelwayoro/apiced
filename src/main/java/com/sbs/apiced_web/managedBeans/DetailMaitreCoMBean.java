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
import com.sbs.apiced_web.entities.Promotion;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.entities.Villes;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.CategorieMcManager;
import com.sbs.apiced_web.services.EtablissementManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.OperateurTelcoManager;
import com.sbs.apiced_web.services.VilleManager;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
        log.setAction("modification des infos du maitre  : " + mc.getNom() + " de matricule :" + mc.getMatricule() + "avant validation du coordonnateur");
        log.setDateaction(dateModif);
        audit.persist(log);

        if (this.nomEtablissement == null) {
            nomEtablissement = mc.getEcole();
        }

        //rechercher un etablissement a partir du nom selectionné 
        //mc.setEtablissement(mcManager.getEtablissementByName(this.nomEtablissement));
        // mc.setEtablissement(mcManager.getEtablissementNameById(nomEtablissement));
        // mc.setEcole(mcManager.get);
        //changement de l'opérateur du maitre 
        if (this.operateurMc != null) {
            //mc.setOperatortelco(mcManager.getIdOpByName(this.operateurMc));
            mc.setOperatortelco(opMgr.oneOpByName(operateurMc));
        }

        //gestion du changement de la catégorie 
        //si changement de categorie : modification de la categorie du mc 
        if (this.nomCategorie != null) {
            //System.out.println("modification de la categorie du mc  de  " + mc.getIdcategoriepro().getLibelle() + " à " + nomCategorie);
            mc.setCategoriepro(nomCategorie);

        }
//
//        if (this.statutMaitre != null) {//modification du statut du mc : retraité - actif - suspendu (par l'apiced bloc momentannement son accès au paiement de subsides)
//            if (statutMaitre.equalsIgnoreCase("actif")) {
//                this.mc.setEtatcomptemc(Boolean.TRUE);
//            } else {
//                this.mc.setEtatcomptemc(Boolean.FALSE); //on ne permet pas de payer les mc qui sont a la retraite
//            }
//        }

        //remise a jour du statut de son wallet 
        System.out.println("le statu du mc est à présent : " + mc.getStatutwallet());
        //sinon on garde la mm categorie pour le mc
        //mise a jour
        //mcManager.updateProfil(mc);
        msgSuccesModif();
        //maj de la liste des profils
        // PrimeFaces.current().ajax().update("form:messages");

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
}
