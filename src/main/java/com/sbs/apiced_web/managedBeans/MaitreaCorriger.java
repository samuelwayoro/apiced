/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
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
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

/**
 *
 * @author samuel
 */
@Named(value = "maitreaCorriger")
@ViewScoped
public class MaitreaCorriger implements Serializable {

    private List<Maitrecommunautaire> listeMcEnAttenteDeCorrection;
    private Long nbreMcAcorriger;
    private String userCo;
    private String sitmatrimonial;
    private Maitrecommunautaire selectedMc;
    private LocalDate newMcDateNaiss;
    private Villes lieudenaissance;
    private List<Villes> listeDesVilles;
    private Villes domicileUtilisateur;
    private String newMcNiveauSco;
    private List<String> listeNiveauSco;
    private String newMcDernierDiplome;
    private List<String> listeNomsDiplome;
    private LocalDate newMcDatePriseFonction;
    private List<String> newMcClasses = new ArrayList<>();
    private List<String> listeClassesAffectees;
    private Notifications notif = new Notifications();
    private String nouvelleDateNaissance;
    private String nouvelleDatePriseFonction;
    private Typenotifs typeNotification;
    private LocalDate dateprisedefonction;
    private String motifModif;
    private Utilisateur leUser;
        private List<Utilisateur> listeUsers;


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

    /**
     * Creates a new instance of MaitreaCorriger
     */
    public MaitreaCorriger() {
    }

    @PostConstruct
    public void init() {
       // recuperation du user en session 
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         leUser = (Utilisateur) session.getAttribute("utilisateurConnecte");
        userCo = session.getAttribute("utilisateur").toString();
        listeMcEnAttenteDeCorrection = mcMgr.listeMcAcorriger();
        nbreMcAcorriger = mcMgr.nbreMcAcorriger();
        listeDesVilles = villeMgr.getAllVilles();
        listeNiveauSco = nivoScoMgr.allNiveauScolaireNames();
        listeNomsDiplome = dernierDiplomeMgr.allDernierDiplomesNames();
        listeClassesAffectees = classeAffecteeMgr.allClassesNames();
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

    public List<Utilisateur> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(List<Utilisateur> listeUsers) {
        this.listeUsers = listeUsers;
    }
    
    public Utilisateur getLeUser() {
        return leUser;
    }

    public void setLeUser(Utilisateur leUser) {
        this.leUser = leUser;
    }

    public String getMotifModif() {
        return motifModif;
    }

    public void setMotifModif(String motifModif) {
        this.motifModif = motifModif;
    }

    public LocalDate getDateprisedefonction() {
        return dateprisedefonction;
    }

    public void setDateprisedefonction(LocalDate dateprisedefonction) {
        this.dateprisedefonction = dateprisedefonction;
    }

    public Typenotifs getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(Typenotifs typeNotification) {
        this.typeNotification = typeNotification;
    }

    public String getNouvelleDatePriseFonction() {
        return nouvelleDatePriseFonction;
    }

    public void setNouvelleDatePriseFonction(String nouvelleDatePriseFonction) {
        this.nouvelleDatePriseFonction = nouvelleDatePriseFonction;
    }

    public List<String> getListeClassesAffectees() {
        return listeClassesAffectees;
    }

    public void setListeClassesAffectees(List<String> listeClassesAffectees) {
        this.listeClassesAffectees = listeClassesAffectees;
    }

    public String getNouvelleDateNaissance() {
        return nouvelleDateNaissance;
    }

    public void setNouvelleDateNaissance(String nouvelleDateNaissance) {
        this.nouvelleDateNaissance = nouvelleDateNaissance;
    }

    public LocalDate getNewMcDatePriseFonction() {
        return newMcDatePriseFonction;
    }

    public void setNewMcDatePriseFonction(LocalDate newMcDatePriseFonction) {
        this.newMcDatePriseFonction = newMcDatePriseFonction;
    }

    public List<String> getNewMcClasses() {
        return newMcClasses;
    }

    public void setNewMcClasses(List<String> newMcClasses) {
        this.newMcClasses = newMcClasses;
    }

    public List<String> getListeNomsDiplome() {
        return listeNomsDiplome;
    }

    public void setListeNomsDiplome(List<String> listeNomsDiplome) {
        this.listeNomsDiplome = listeNomsDiplome;
    }

    public String getNewMcDernierDiplome() {
        return newMcDernierDiplome;
    }

    public void setNewMcDernierDiplome(String newMcDernierDiplome) {
        this.newMcDernierDiplome = newMcDernierDiplome;
    }

    public List<String> getListeNiveauSco() {
        return listeNiveauSco;
    }

    public void setListeNiveauSco(List<String> listeNiveauSco) {
        this.listeNiveauSco = listeNiveauSco;
    }

    public String getNewMcNiveauSco() {
        return newMcNiveauSco;
    }

    public void setNewMcNiveauSco(String newMcNiveauSco) {
        this.newMcNiveauSco = newMcNiveauSco;
    }

    public Villes getDomicileUtilisateur() {
        return domicileUtilisateur;
    }

    public void setDomicileUtilisateur(Villes domicileUtilisateur) {
        this.domicileUtilisateur = domicileUtilisateur;
    }

    public Maitrecommunautaire getSelectedMc() {
        return selectedMc;
    }

    public void setSelectedMc(Maitrecommunautaire selectedMc) {
        this.selectedMc = selectedMc;
    }

    public LocalDate getNewMcDateNaiss() {
        return newMcDateNaiss;
    }

    public void setNewMcDateNaiss(LocalDate newMcDateNaiss) {
        this.newMcDateNaiss = newMcDateNaiss;
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

    public String getSitmatrimonial() {
        return sitmatrimonial;
    }

    public void setSitmatrimonial(String sitmatrimonial) {
        this.sitmatrimonial = sitmatrimonial;
    }

    public List<Maitrecommunautaire> getListeMcEnAttenteDeCorrection() {
        return listeMcEnAttenteDeCorrection;
    }

    public void setListeMcEnAttenteDeCorrection(List<Maitrecommunautaire> listeMcEnAttenteDeCorrection) {
        this.listeMcEnAttenteDeCorrection = listeMcEnAttenteDeCorrection;
    }

    public Long getNbreMcAcorriger() {
        return nbreMcAcorriger;
    }

    public void setNbreMcAcorriger(Long nbreMcAcorriger) {
        this.nbreMcAcorriger = nbreMcAcorriger;
    }

    public String getUserCo() {
        return userCo;
    }

    public void setUserCo(String userCo) {
        this.userCo = userCo;
    }

    public Notifications getNotif() {
        return notif;
    }

    public void setNotif(Notifications notif) {
        this.notif = notif;
    }

    public CategorieMcManager getCategManager() {
        return categManager;
    }

    public void setCategManager(CategorieMcManager categManager) {
        this.categManager = categManager;
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

    public NiveauScolaireManager getNivoScoMgr() {
        return nivoScoMgr;
    }

    public void setNivoScoMgr(NiveauScolaireManager nivoScoMgr) {
        this.nivoScoMgr = nivoScoMgr;
    }

    public DiplomesManager getDernierDiplomeMgr() {
        return dernierDiplomeMgr;
    }

    public void setDernierDiplomeMgr(DiplomesManager dernierDiplomeMgr) {
        this.dernierDiplomeMgr = dernierDiplomeMgr;
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

    public OperateurTelcoManager getOpTelcoMgr() {
        return opTelcoMgr;
    }

    public void setOpTelcoMgr(OperateurTelcoManager opTelcoMgr) {
        this.opTelcoMgr = opTelcoMgr;
    }

    public EtablissementManager getEtsMgr() {
        return etsMgr;
    }

    public void setEtsMgr(EtablissementManager etsMgr) {
        this.etsMgr = etsMgr;
    }

    public CategorieMcManager getCategMgr() {
        return categMgr;
    }

    public void setCategMgr(CategorieMcManager categMgr) {
        this.categMgr = categMgr;
    }

    public List<Villes> getListeDesVilles() {
        return listeDesVilles;
    }

    public void setListeDesVilles(List<Villes> listeDesVilles) {
        this.listeDesVilles = listeDesVilles;
    }

    /**
     * modif mineure infos MC
     */
    public void modifMineurInfos() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH);
        Utilisateur createur = utilisateurMgr.verif(userCo);
        //puis maj du maitre co modifier
        //date de naissance 
        if (newMcDateNaiss != null) {
            //formattage des date a enregistrer
            nouvelleDateNaissance = f.format(newMcDateNaiss);
            selectedMc.setDatenaissance(nouvelleDateNaissance);
        }
        //lieu de naissance 
        if (lieudenaissance != null) {
            selectedMc.setLieudenaissance(lieudenaissance.getNomville());
        }
        //domicile 
        if (domicileUtilisateur != null) {
            selectedMc.setNni(domicileUtilisateur.getNomville());
        }
        //niveau scolaire 
        if (newMcNiveauSco != null) {
            selectedMc.setNiveauscolaire(newMcNiveauSco);
        }
        //dernier diplôme 
        if (newMcDernierDiplome != null) {
            selectedMc.setDernierdiplome(newMcDernierDiplome);
        }
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
        selectedMc.setValidationcoordonnateur(Boolean.FALSE);
        selectedMc.setRejetvalidation(Boolean.FALSE);
        selectedMc.setDatemodifcompte(DateOfDay());
        selectedMc.setIdvalideurcompte(null);
        selectedMc.setMotifrejetvalidation(null);
        selectedMc.setDaterejetvalidation(null);
        selectedMc.setDatemodifcompte(DateOfDay());

        //System.out.println("modif  : "+selectedMc.getSitmatrimonial()+"  "+selectedMc.getNbreenfants());
        msgSuccesModif();
        mcMgr.updateMaitre(selectedMc);
        //auditlog
        saveLog(" correction des informations du maitre " + selectedMc.getNom() + "   pour validation ", DateOfDay());
        //enregistrement de la notif
        BigDecimal typn = BigDecimal.valueOf(6);
        typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
        listeMcEnAttenteDeCorrection.remove(selectedMc);
        //String libelleNotif = "Les informations du maitre communautaire   " + selectedMc.getNOM() + "  " + selectedMc.getPrenoms() + " ont été corrigées pour validation";
        notif.setLibelle("Les informations du maitre communautaire   "+ selectedMc.getNom() + "  " + selectedMc.getPrenoms() + " ont été corrigées pour validation");
        String details ="le compte d'un maitre communautaire est soummis a validation après correction ";
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
                //je le parcours et je cree un notifsuser avec un etat a zero...
                for (Utilisateur u : listeUsers) {
                    //System.out.println("le user en cours ... : " + u.getNOM());
                    Usersnotifs userNotif = new Usersnotifs();
                    userNotif.setDateinsert(DateOfDay());
                    userNotif.setEtat(BigInteger.ZERO);
                    userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
                    userNotif.setTitre("Soummission du compte de maitre communautaire pour validation après correction ");
                    userNotif.setInformation(details);
                    userNotif.setCreateur(userCo);
                    userNotif.setTypeusernotif("MCCORRIGE");
                      //construction des btn en fonction des profils
                    if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                        userNotif.setBtnvalidemc("false");
                        userNotif.setBtnvalidepaie("false");
                        userNotif.setBtndetail("true");
                        userNotif.setBtncorrigemc("false");
                    } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                        userNotif.setBtnvalidemc("true");
                        userNotif.setBtnvalidepaie("false");
                        userNotif.setBtndetail("false");
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
        
        //maj du tableau des mc et raffraichissement de la vue 
        PrimeFaces.current().executeScript("PF('viewModifMcDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:tbl");
        clearChampsAfterMajMineure();
        notif = null;
    }

    //msg de succes de modification opérateur
    public void msgSuccesModif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "succès", "les informations du maitre ont été corrigés "));
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
        domicileUtilisateur = null;
        newMcNiveauSco = null;
        newMcDernierDiplome = null;
        dateprisedefonction = null;
        nouvelleDatePriseFonction = null;
        newMcClasses.clear();
        motifModif = null;
    }

}
