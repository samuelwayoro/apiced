/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Etatpaiement;
import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Paiement;
import com.sbs.apiced_web.entities.Parametres;
import com.sbs.apiced_web.entities.Transactions;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.EtatPaiementManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.PaiementMensuelManager;
import com.sbs.apiced_web.services.ParamManager;
import com.sbs.apiced_web.services.TransactionsManager;
import com.sbs.apiced_web.services.TypeNotifManager;
import com.sbs.apiced_web.services.UsersNotifManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
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
import javax.validation.constraints.Future;
import org.primefaces.PrimeFaces;
import java.io.FileWriter;
import java.io.Serializable;
import javax.faces.annotation.ManagedProperty;

/**
 *
 * @author samuel
 */
@Named(value = "paiementEnAttenteMBean")
@ViewScoped
public class PaiementEnAttenteMBean implements Serializable {

    private Paiement selectedPaiement;
    private Etatpaiement etatpaiement; 
    private Long nbrePaiementEnAttente;
    private List<Transactions> allTransactions;
    private Utilisateur utilisateur = new Utilisateur();
    private List<Paiement> listePaiementEnAttente;
    private List<Maitrecommunautaire> listeMaitreAirtelpayable;
    private BigInteger montantTotalSubsidesMcPayableAirtel;
    private Utilisateur userCo;
    private String newLibellePaiement;
    private BigInteger newMontanttotalPaiement;
    private List<Transactions> listeTransactionByOp;
    private String newDetailsPaiement;
    private List<Paiement> listeDesMoisDejaPayes;
    private Parametres oneUtilParam;
    private BigInteger montantRestantSubsides;
    private Typenotifs typeNotification;
    private FileWriter file;
    private Notifications notif;
    private String libelleNotif;
    private String detailNotif;
    private String motifModif;
    private List<Utilisateur> listeUsers;
    private List<Transactions> transactionsAsupprimer;
    private List<Transactions> transactionsAenvoyer;
    private static final String DELIMITER = ",";
    private static final String SEPARATOR = "\n";
    //En-tête de fichier
    //private static final String HEADER = "idtransaction ,numerointerne ,statutwallet ,typecompte ,montantsubside ,contactmaitre ,libellepaie ,responsecode ,responsemessage ,dateenvoi ,datepaiement ,moisanneepaie ,operateurs ,nommaitre ,prenomsmaitre ,dateenvoiaoperateur ,datepaiementdemandee ,datereceptionclient ,datelimitepaiement ,etattransaction ,paiementid";
    private static final String HEADER = "idtransaction ,numerointerne ,statutwallet ,typecompte ,montantsubside ,contactmaitre ,libellepaie ,responsecode ,responsemessage ,dateenvoi ,datepaiement ,moisanneepaie ,operateurs ,nommaitre ,prenomsmaitre ,dateenvoiaoperateur ,datepaiementdemandee ,datereceptionclient ,datelimitepaiement ,etattransaction ,paiementid";

    private static final String HEADERMOOV = "DESTINATION,AMOUNT,WALLETID,REFERENCE1,REFERENCE2";
    private static final String HEADERAIRTEL = "provider id ,Receiver Msisdn ,Amount ,Remarks ";

    @Future
    private LocalDate newDatepaiementPaiement;
    private LocalDate newMoisPaiement;

    @EJB
    private AuditlogManager auditMgr;
    @EJB
    private UtilisateurManager userMgr;
    @EJB
    private NotifsManager notifsMgr;
    @EJB
    private PaiementMensuelManager paieMgr;
    @EJB
    private MaitreCoManager mcManager;
    @EJB
    private TransactionsManager transacMgr;
    @EJB
    private UtilisateurManager utilisateurMgr;
    @EJB
    private AuditlogManager audit;
    @EJB
    private NotifsManager notifMgr;
    @EJB
    private UsersNotifManager userNotifMgr;
    @EJB
    private TypeNotifManager typeNotifMgr;
    @EJB
    private EtatPaiementManager etatPaieMgr;
    @EJB
    private ParamManager paramMgr;

    @ManagedProperty(value = "#{homeCoordoMBean}")
    private HomeCoordoMBean homeCoordoMBean;

    @ManagedProperty(value = "#{monProfilMBean}")
    private monProfilMBean monProfilMBean;

    /**
     * Creates a new instance of PaiementEnAttenteMBean
     */
    public PaiementEnAttenteMBean() {
    }

    public Etatpaiement getEtatpaiement() {
        return etatpaiement;
    }

    public void setEtatpaiement(Etatpaiement etatpaiement) {
        this.etatpaiement = etatpaiement;
    }

    public FileWriter getFile() {
        return file;
    }

    public void setFile(FileWriter file) {
        this.file = file;
    }

    
    
    public Parametres getOneUtilParam() {
        return oneUtilParam;
    }

    public void setOneUtilParam(Parametres oneUtilParam) {
        this.oneUtilParam = oneUtilParam;
    }

    public monProfilMBean getMonProfilMBean() {
        return monProfilMBean;
    }

    public void setMonProfilMBean(monProfilMBean monProfilMBean) {
        this.monProfilMBean = monProfilMBean;
    }

    public String getLibelleNotif() {
        return libelleNotif;
    }

    public void setLibelleNotif(String libelleNotif) {
        this.libelleNotif = libelleNotif;
    }

    public String getDetailNotif() {
        return detailNotif;
    }

    public void setDetailNotif(String detailNotif) {
        this.detailNotif = detailNotif;
    }

    public ParamManager getParamMgr() {
        return paramMgr;
    }

    public void setParamMgr(ParamManager paramMgr) {
        this.paramMgr = paramMgr;
    }

    public List<Transactions> getTransactionsAenvoyer() {
        return transactionsAenvoyer;
    }

    public void setTransactionsAenvoyer(List<Transactions> transactionsAenvoyer) {
        this.transactionsAenvoyer = transactionsAenvoyer;
    }

    public List<Transactions> getTransactionsAsupprimer() {
        return transactionsAsupprimer;
    }

    public void setTransactionsAsupprimer(List<Transactions> transactionsAsupprimer) {
        this.transactionsAsupprimer = transactionsAsupprimer;
    }

    public UsersNotifManager getUserNotifMgr() {
        return userNotifMgr;
    }

    public void setUserNotifMgr(UsersNotifManager userNotifMgr) {
        this.userNotifMgr = userNotifMgr;
    }

    public EtatPaiementManager getEtatPaieMgr() {
        return etatPaieMgr;
    }

    public void setEtatPaieMgr(EtatPaiementManager etatPaieMgr) {
        this.etatPaieMgr = etatPaieMgr;
    }

    public List<Utilisateur> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(List<Utilisateur> listeUsers) {
        this.listeUsers = listeUsers;
    }

    public List<Transactions> getListeTransactionByOp() {
        return listeTransactionByOp;
    }

    public void setListeTransactionByOp(List<Transactions> listeTransactionByOp) {
        this.listeTransactionByOp = listeTransactionByOp;
    }

    public Typenotifs getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(Typenotifs typeNotification) {
        this.typeNotification = typeNotification;
    }

    public Notifications getNotif() {
        return notif;
    }

    public void setNotif(Notifications notif) {
        this.notif = notif;
    }

    public String getMotifModif() {
        return motifModif;
    }

    public void setMotifModif(String motifModif) {
        this.motifModif = motifModif;
    }

    public UtilisateurManager getUtilisateurMgr() {
        return utilisateurMgr;
    }

    public void setUtilisateurMgr(UtilisateurManager utilisateurMgr) {
        this.utilisateurMgr = utilisateurMgr;
    }

    public AuditlogManager getAudit() {
        return audit;
    }

    public void setAudit(AuditlogManager audit) {
        this.audit = audit;
    }

    public NotifsManager getNotifMgr() {
        return notifMgr;
    }

    public void setNotifMgr(NotifsManager notifMgr) {
        this.notifMgr = notifMgr;
    }

    public List<Transactions> getAllTransactions() {
        return allTransactions;
    }

    public void setAllTransactions(List<Transactions> allTransactions) {
        this.allTransactions = allTransactions;
    }

    public TransactionsManager getTransacMgr() {
        return transacMgr;
    }

    public void setTransacMgr(TransactionsManager transacMgr) {
        this.transacMgr = transacMgr;
    }

    public Long getNbrePaiementEnAttente() {
        return nbrePaiementEnAttente;
    }

    public void setNbrePaiementEnAttente(Long nbrePaiementEnAttente) {
        this.nbrePaiementEnAttente = nbrePaiementEnAttente;
    }

    public Utilisateur getUserCo() {
        return userCo;
    }

    public void setUserCo(Utilisateur userCo) {
        this.userCo = userCo;
    }

    public BigInteger getMontantRestantSubsides() {
        return montantRestantSubsides;
    }

    public void setMontantRestantSubsides(BigInteger montantRestantSubsides) {
        this.montantRestantSubsides = montantRestantSubsides;
    }

    public String getNewLibellePaiement() {
        return newLibellePaiement;
    }

    public void setNewLibellePaiement(String newLibellePaiement) {
        this.newLibellePaiement = newLibellePaiement;
    }

    public BigInteger getNewMontanttotalPaiement() {
        return newMontanttotalPaiement;
    }

    public void setNewMontanttotalPaiement(BigInteger newMontanttotalPaiement) {
        this.newMontanttotalPaiement = newMontanttotalPaiement;
    }

    public LocalDate getNewMoisPaiement() {
        return newMoisPaiement;
    }

    public void setNewMoisPaiement(LocalDate newMoisPaiement) {
        this.newMoisPaiement = newMoisPaiement;
    }

    public LocalDate getNewDatepaiementPaiement() {
        return newDatepaiementPaiement;
    }

    public void setNewDatepaiementPaiement(LocalDate newDatepaiementPaiement) {
        this.newDatepaiementPaiement = newDatepaiementPaiement;
    }

    public String getNewDetailsPaiement() {
        return newDetailsPaiement;
    }

    public void setNewDetailsPaiement(String newDetailsPaiement) {
        this.newDetailsPaiement = newDetailsPaiement;
    }

    public List<Paiement> getListeDesMoisDejaPayes() {
        return listeDesMoisDejaPayes;
    }

    public void setListeDesMoisDejaPayes(List<Paiement> listeDesMoisDejaPayes) {
        this.listeDesMoisDejaPayes = listeDesMoisDejaPayes;
    }

    public List<Maitrecommunautaire> getListeMaitreAirtelpayable() {
        return listeMaitreAirtelpayable;
    }

    public void setListeMaitreAirtelpayable(List<Maitrecommunautaire> listeMaitreAirtelpayable) {
        this.listeMaitreAirtelpayable = listeMaitreAirtelpayable;
    }

    public BigInteger getMontantTotalSubsidesMcPayableAirtel() {
        return montantTotalSubsidesMcPayableAirtel;
    }

    public void setMontantTotalSubsidesMcPayableAirtel(BigInteger montantTotalSubsidesMcPayableAirtel) {
        this.montantTotalSubsidesMcPayableAirtel = montantTotalSubsidesMcPayableAirtel;
    }

    public MaitreCoManager getMcManager() {
        return mcManager;
    }

    public void setMcManager(MaitreCoManager mcManager) {
        this.mcManager = mcManager;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Paiement> getListePaiementEnAttente() {
        return listePaiementEnAttente;
    }

    public void setListePaiementEnAttente(List<Paiement> listePaiementEnAttente) {
        this.listePaiementEnAttente = listePaiementEnAttente;
    }

    public Paiement getSelectedPaiement() {
        return selectedPaiement;
    }

    public void setSelectedPaiement(Paiement selectedPaiement) {
        this.selectedPaiement = selectedPaiement;
    }

    public AuditlogManager getAuditMgr() {
        return auditMgr;
    }

    public void setAuditMgr(AuditlogManager auditMgr) {
        this.auditMgr = auditMgr;
    }

    public UtilisateurManager getUserMgr() {
        return userMgr;
    }

    public void setUserMgr(UtilisateurManager userMgr) {
        this.userMgr = userMgr;
    }

    public NotifsManager getNotifsMgr() {
        return notifsMgr;
    }

    public void setNotifsMgr(NotifsManager notifsMgr) {
        this.notifsMgr = notifsMgr;
    }

    public TypeNotifManager getTypeNotifMgr() {
        return typeNotifMgr;
    }

    public void setTypeNotifMgr(TypeNotifManager typeNotifMgr) {
        this.typeNotifMgr = typeNotifMgr;
    }

    public PaiementMensuelManager getPaieMgr() {
        return paieMgr;
    }

    public void setPaieMgr(PaiementMensuelManager paieMgr) {
        this.paieMgr = paieMgr;
    }

    public HomeCoordoMBean getHomeCoordoMBean() {
        return homeCoordoMBean;
    }

    public void setHomeCoordoMBean(HomeCoordoMBean homeCoordoMBean) {
        this.homeCoordoMBean = homeCoordoMBean;
    }

    @PostConstruct
    public void init() {

        notif = new Notifications();
        file = null;
        //liste des demandes de  paiement en attente de validation par le coordo
        listePaiementEnAttente = paieMgr.listePaiementEnAttente();
        //liste des mois deja payés pour controle lors de la modif
        listeDesMoisDejaPayes = paieMgr.listePaiementDejaEmis();

        nbrePaiementEnAttente = notifsMgr.qtesPaiementEnAttenteDeValidation();
        // recuperation du user en session 
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        System.out.println("loggin de l'action de l'utilisateur : " + session.getAttribute("utilisateur"));
        userCo = (Utilisateur) session.getAttribute("utilisateurConnecte");

        allTransactions = transacMgr.allTransactions();

    }

    /**
     * suppresion d'un profil dans la base
     */
    public void deletePaiement() {
        //System.out.println("suppresion du paiement : "+selectedPaiement.getLibelle());

        //recup de toutes les transactions du paiement selectionneé
        transactionsAsupprimer = transacMgr.selectedPaieTransactions(selectedPaiement.getIdpaiement());
        //boucle pr supprimer
        System.out.println("debut de suppression");
        for (Transactions t : transactionsAsupprimer) {
            transacMgr.deleteTransactions(t);
        }
        System.out.println("fin de suppression");

        //suppresion du paiement
        paieMgr.deletePaiement(selectedPaiement);
        listePaiementEnAttente.remove(selectedPaiement);

        //trace log
        Auditlog log = new Auditlog();
        log.setAuteurIdutilisateur(userCo);
        log.setLogin(userCo.getLogin());
        log.setAction("suppression du paiement  : " + selectedPaiement.getLibelle() + " par l'utilisateur : " + userCo.getLogin());
        log.setDateaction(DateOfDay());
        auditMgr.persist(log);

        msgSuccesDeletePaiement();
        PrimeFaces.current().executeScript("PF('deletePaiementDialog').hide()");
        PrimeFaces.current().ajax().update(":form:messages", ":form:paiementsEnAttente");

    }

    public void validerPaiementSubsides() {
        if (selectedPaiement.getOperateurmobile().equalsIgnoreCase("MOOV")) {
            System.out.println("c moov africa");
            msgSuccesValidePaiement();
            BigDecimal typn = BigDecimal.valueOf(4);
            typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
            libelleNotif = "fichier de Paiement de subside  :" + selectedPaiement.getLibelle() + " genere par le coordonnateur  : " + userCo.getLogin();
            detailNotif = "validation et generation du fichier de  paiement des subsides  : " + selectedPaiement.getLibelle() + " du mois de : " + selectedPaiement.getMois();
            saveNotifMoov(4, detailNotif, typeNotification, userCo, selectedPaiement.getIdpaiement().toString());
        } else {
            System.out.println("c airtel ");
            msgSuccesValidePaiement();
            BigDecimal typn = BigDecimal.valueOf(4);
            typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
            libelleNotif = "fichier de Paiement de subside  :" + selectedPaiement.getLibelle() + " genere par le coordonnateur  : " + userCo.getLogin();
            detailNotif = "validation et generation du fichier de  paiement des subsides  : " + selectedPaiement.getLibelle() + " du mois de : " + selectedPaiement.getMois();
            saveNotifAirtel(4, detailNotif, typeNotification, userCo, selectedPaiement.getIdpaiement().toString());
        }
    }

    //validation d'un paiement avant envoi a l'opérateur
    public void validerPaie() {
//        System.out.println("debut validation d'une demande de paiement ");
//        System.out.println("paiement en validation  ------------------->: " + selectedPaiement.getLibelle() + "  nom operateur --->" + selectedPaiement.getOperateurmobile());
//        selectedPaiement.setForValidation(DateOfDay(),Boolean.FALSE,userCo,Boolean.TRUE);
//        etatpaiement = etatPaieMgr.etatPaiementbyId(BigDecimal.valueOf(4));
//        selectedPaiement.setEtatpaiement(etatpaiement);
        
    }

    //maj des infos de paiement de subsides selectionné
    public void updatePaiement() {
        Boolean mauvaisJrDePaieSuggeree, mauvaisMontantDePaie = false;
        Boolean mauvaisMoisDePaie = Boolean.FALSE;
        int rep;

        //verifi si il a changer le libelle 
        try {
            if (newLibellePaiement != null) {
                selectedPaiement.setLibelle(newLibellePaiement);
            }

        } catch (Exception e) {
            selectedPaiement.setLibelle(selectedPaiement.getLibelle());
        }

        //verifier que le montant total de paiement > au montant des mc payable de l'opérateur  ...
        listeMaitreAirtelpayable = mcManager.recupMcPayable(selectedPaiement.getOperateurmobile());
        //som des montant de subsides des mc payable 
        BigInteger som = BigInteger.ZERO;
        for (Maitrecommunautaire lis : listeMaitreAirtelpayable) {
            // som = som.add(lis.getIdcategoriepro().getMontantsubside());
            som = BigInteger.ZERO;
        }
        montantTotalSubsidesMcPayableAirtel = som;
        //System.out.println("ne pas etre en dessous de :" + montantTotalSubsidesMcPayableAirtel);
        try {
            //comparaison des montants : egal -1 si le montant total des maitre payable est > au montant saisi
            rep = newMontanttotalPaiement.compareTo(montantTotalSubsidesMcPayableAirtel);
            if (rep == -1) {
                //afficher msg de mauvais montant saisi et ne pas permettre le update 
                mauvaisMontantDePaie = true;
                System.out.println("mauvais montant saisie");
            } else {
                System.out.println("bravo on change le montant ");
                selectedPaiement.setMontanttotal(BigInteger.valueOf(newMontanttotalPaiement.intValue()));
                //il y a un montant restant afficher un msg en jaune pour alerte
                try {
                    montantRestantSubsides = newMontanttotalPaiement.subtract(montantTotalSubsidesMcPayableAirtel);
                    selectedPaiement.setMontantrestant(montantRestantSubsides.toString());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (montantRestantSubsides.intValue() > 0) {
                    msgAlerteMonnaie();
                }
            }

        } catch (Exception e) {
            System.out.println("pas de nouveau montant on garde l'ancien ");
            selectedPaiement.setMontanttotal(selectedPaiement.getMontanttotal());
        }

        try {
            System.out.println("mois recuperé dns le formulaire   " + newMoisPaiement.format(DateTimeFormatter.ISO_DATE).substring(0, 7));
            for (Paiement listeDesMoisDejaPaye : listeDesMoisDejaPayes) {
                System.out.println(" : ->" + listeDesMoisDejaPaye.getMois());

                if (listeDesMoisDejaPaye.getMois().equalsIgnoreCase(newMoisPaiement.format(DateTimeFormatter.ISO_DATE).substring(0, 7))) {
                    //presence du mois de paiement déjà en base de données 
                    System.out.println("attention le mois de paiement existe déjà en base des paiements  : ->" + listeDesMoisDejaPaye.getMois());
                    mauvaisMoisDePaie = Boolean.TRUE;
                    System.out.println("mauvais mois de paie");
                } else {
                    System.out.println("on prend le nouveau moi qui : " + newMoisPaiement.format(DateTimeFormatter.ISO_DATE).substring(0, 7));
                    selectedPaiement.setMois(newMoisPaiement.format(DateTimeFormatter.ISO_DATE).substring(0, 7));
                }
            }
        } catch (Exception e) {
            System.out.println("on garde le mm mois de paie  ...");
            selectedPaiement.setMois(selectedPaiement.getMois());
        }

        try {
            if (newDatepaiementPaiement.isBefore(LocalDate.now())) {
                mauvaisJrDePaieSuggeree = Boolean.TRUE;
                System.out.println("mauvais jr de paie sugeerer a l'opérateur");
            } else {
                System.out.println("la date saisie est bonne , c " + newDatepaiementPaiement);
                selectedPaiement.setDatepaiement(newDatepaiementPaiement.format(DateTimeFormatter.ISO_DATE));
            }
        } catch (Exception e) {
            System.out.println("on garde la mm date de paiement");
            selectedPaiement.setDatepaiement(selectedPaiement.getDatepaiement());
        }

        //aussi pr les détails 
        try {
            if (newDetailsPaiement != null) {
                selectedPaiement.setDetails(newDetailsPaiement);
            }
        } catch (Exception e) {
            selectedPaiement.setDetails(selectedPaiement.getDetails());
        }

        if (mauvaisMontantDePaie) {
            msgErrorNvoMontantPaie();
        }
        if (mauvaisMoisDePaie) {
            msgErrorCreaPaiement();
        }
        selectedPaiement.setDatemodification(DateOfDay());

        System.out.println("infos a persister " + selectedPaiement.getLibelle() + "  " + selectedPaiement.getDetails());

        //trace log
        Auditlog log = new Auditlog();
        log.setAuteurIdutilisateur(userCo);
        log.setLogin(userCo.getLogin());
        log.setAction("mise a jour des informations du paiement  : " + selectedPaiement.getLibelle() + " par l'utilisateur : " + userCo.getLogin());
        log.setDateaction(DateOfDay());
        auditMgr.persist(log);

        paieMgr.updatePaie(selectedPaiement);

        msgSuccesModif();

        //persister avec un update 
        PrimeFaces.current().executeScript("PF('managePaiementDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:paiementsEnAttente");
    }

    //valider un paiement en attente 
//    public void validerPaiement() {
//        System.out.println("valider le paiement ...");
//        // la date de validation  
//        selectedPaiement.setValidationcoordonnateur(Boolean.TRUE);
//        selectedPaiement.setDatemodification(DateOfDay());
//        selectedPaiement.setValideur(userCo);
//        selectedPaiement.setDatevalidationcoordo(DateOfDay());
//        //selectedPaiement.setMotifrejetvalidationcoordo("a corriger et ressoumettre pour validation ... ");
//        paieMgr.updatePaie(selectedPaiement);
//        listePaiementEnAttente.remove(selectedPaiement);
//        nbrePaiementEnAttente = nbrePaiementEnAttente - 1;
//        msgSuccesPaiementValide();
//        PrimeFaces.current().executeScript("PF('viewPaiementDialog').hide()");
//        PrimeFaces.current().ajax().update("form:messages", "form:paiementsEnAttente");
//        //trace log
//        Auditlog log = new Auditlog();
//        log.setAuteurIdutilisateur(userCo);
//        log.setLogin(userCo.getLogin());
//        log.setAction("validation du paiement mensuel : " + selectedPaiement.getLibelle() + " par le coordonnateur : " + userCo.getLogin());
//        log.setDateaction(DateOfDay());
//        auditMgr.persist(log);
//    }
    //rejeter le paiement 
    public void rejeterLePaiement() {
        System.out.println("rejeter le paiement...");
        msgSuccesRejetPaiement();
        PrimeFaces.current().executeScript("PF('viewPaiementDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:paiementsEnAttente");

    }

    public void msgErrorNvoMontantPaie() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'emettre ce montant de paiment", "le montant doit être supérieur au total des subsides maitres abonnés chez AIRTEL"));
    }

    public void msgErrorCreaPaiement() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'emettre ce paiment", "le paiement de ce mois pour cet Opérateur à été déjà émis"));
    }

    public void msgSuccesModif() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "les informations du paiement ont été mis à jour", "succès"));
    }

    public void msgErrorPaiementFileCreation() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreur", "Le repertoire de création du fichier doit etre crée : " + libelleNotif));
    }

    public void msgSuccesPaiementValide() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le paiement a été validé", "succès"));
    }

    public void msgAlerteMonnaie() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention !!", "un montant  de  " + montantRestantSubsides.intValueExact() + " FCFA restant chez AIRTEL pour ce Paiement "));
    }

    //message de succès de suppression d'un utilisateur
    public void msgSuccesDeletePaiement() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le paiement à été supprimé !", "Succès"));
    }

    //message de succès de suppression d'un utilisateur
    public void msgSuccesRejetPaiement() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le paiement à été Réjeté !", "prière le corriger"));
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

    //save de notifs 
    public void saveNotifMoov(int typeNotif, String details, Typenotifs typeNotification, Utilisateur auteur, String idInformation) {

        try {
            BigDecimal typn = BigDecimal.valueOf(typeNotif);
            typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
            notif.setLibelle("fichier de Paiement de subside MOOV :" + selectedPaiement.getLibelle() + " genere par le coordonnateur  : " + userCo.getLogin());
            notif.setDetails(details);
            notif.setDatecreation(DateOfDay());
            notif.setDateresolution(DateOfDay());
            notif.setTypenotif(typeNotification);
            notif.setCreateur(userCo);
            notif.setIdinfo(idInformation);
            //save de la notif
            notifMgr.persist(notif);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        notif = null;
    }

    public void saveNotifAirtel(int typeNotif, String details, Typenotifs typeNotification, Utilisateur auteur, String idInformation) {
        try {

            BigDecimal typn = BigDecimal.valueOf(typeNotif);
            typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
            notif.setLibelle("fichier de Paiement de subside AIRTEL :" + selectedPaiement.getLibelle() + " genere par le coordonnateur  : " + userCo.getLogin());
            notif.setDetails(details);
            notif.setDatecreation(DateOfDay());
            notif.setDateresolution(DateOfDay());
            notif.setTypenotif(typeNotification);
            notif.setCreateur(userCo);
            notif.setIdinfo(idInformation);
            //save de la notif
            notifMgr.persist(notif);
        } catch (Exception e) {
            System.out.println("MSG DERREUR : " + e.getMessage());
        }

        notif = null;
    }

    public String DateOfDayPaieFile() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd_MM_YYYY");
        String date = dt.format(dtFormat);
        return date;
    }

    public void msgSuccesValidePaiement() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le paiement à été validé", "En attente d'envoi a l'opérateur"));
    }
    
    public void msgAlerteConfigResau() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention !!", "veuillez verifier la connexion au serveur sftp Moov pour envoi du fichier de paiement "));
    }
}
