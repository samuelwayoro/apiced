package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Paiement;
import com.sbs.apiced_web.entities.Transactions;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Usersnotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.CategorieMcManager;
import com.sbs.apiced_web.services.EtatPaiementManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.OperateurTelcoManager;
import com.sbs.apiced_web.services.PaiementMensuelManager;
import com.sbs.apiced_web.services.TransactionsManager;
import com.sbs.apiced_web.services.TypeNotifManager;
import com.sbs.apiced_web.services.UsersNotifManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Future;
import org.primefaces.PrimeFaces;

/**
 *
 * @author SamuelWAYORO
 */
@Named(value = "sP")
@ViewScoped
public class SelectivPaie implements Serializable {

    private String operateurToPaie;
    private String categorieToPaie;
    private List<String> listeOperateurs;
    private List<String> listeCategories;
    private List<Maitrecommunautaire> listeDatatable;
    private String libellePaie;
    private BigInteger montantPaie;
    private String Details;
    private BigInteger dataTableTotalSubside;
    private BigInteger montantRestantSubsides;
    private List<Paiement> listeDesMoisDejaPayes;
    private Boolean afficheMontantRestant;
    private Utilisateur userCo;
    private Boolean verifMoisDejaPaye = false;
    private Boolean verifJourDePaiementSuggerre;
    private String libelleLog;
    private final Auditlog log = new Auditlog();
    private Typenotifs typeNotification;
    private List<Utilisateur> listeUsers;
    private Notifications notif = new Notifications();

    @Future
    private LocalDate MoisDePaie;
    private LocalDate DateDePaiement;

    @EJB
    private CategorieMcManager categMgr;
    @EJB
    private OperateurTelcoManager opTelcoMgr;
    @EJB
    private MaitreCoManager maitreMgr;
    @EJB
    private PaiementMensuelManager paiementMgr;
    @EJB
    private EtatPaiementManager etatPaiementMgr;
    @EJB
    private MaitreCoManager mcManager;
    @EJB
    private TransactionsManager transacMgr;
    @EJB
    private TypeNotifManager typeNotifMgr;
    @EJB
    private UtilisateurManager utilisateurMgr;
    @EJB
    private NotifsManager notifMgr;
    @EJB
    private AuditlogManager auditMgr;
    @EJB
    private UsersNotifManager userNotifMgr;
    @EJB
    private OperateurTelcoManager operateurMgr;

    /**
     * Creates a new instance of SelectivPaie
     */
    public SelectivPaie() {
    }

    @PostConstruct
    public void init() {
        listeCategories = categMgr.listNomsCategories();
        listeOperateurs = opTelcoMgr.getAllOperateurTelcoNames();
        listeDatatable = new ArrayList<>();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        userCo = (Utilisateur) session.getAttribute("utilisateurConnecte");

        //liste des mois dejà payés en fonction de la categorie et de l'opérateur choisi
        listeDesMoisDejaPayes = paiementMgr.listePaiementDejaEmis();

    }

    public Typenotifs getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(Typenotifs typeNotification) {
        this.typeNotification = typeNotification;
    }

    public List<Paiement> getListeDesMoisDejaPayes() {
        return listeDesMoisDejaPayes;
    }

    public void setListeDesMoisDejaPayes(List<Paiement> listeDesMoisDejaPayes) {
        this.listeDesMoisDejaPayes = listeDesMoisDejaPayes;
    }

    public Boolean getAfficheMontantRestant() {
        return afficheMontantRestant;
    }

    public void setAfficheMontantRestant(Boolean afficheMontantRestant) {
        this.afficheMontantRestant = afficheMontantRestant;
    }

    public Utilisateur getUserCo() {
        return userCo;
    }

    public void setUserCo(Utilisateur userCo) {
        this.userCo = userCo;
    }

    public Boolean getVerifMoisDejaPaye() {
        return verifMoisDejaPaye;
    }

    public void setVerifMoisDejaPaye(Boolean verifMoisDejaPaye) {
        this.verifMoisDejaPaye = verifMoisDejaPaye;
    }

    public Boolean getVerifJourDePaiementSuggerre() {
        return verifJourDePaiementSuggerre;
    }

    public void setVerifJourDePaiementSuggerre(Boolean verifJourDePaiementSuggerre) {
        this.verifJourDePaiementSuggerre = verifJourDePaiementSuggerre;
    }

    public String getLibelleLog() {
        return libelleLog;
    }

    public void setLibelleLog(String libelleLog) {
        this.libelleLog = libelleLog;
    }

    public String getLibellePaie() {
        return libellePaie;
    }

    public void setLibellePaie(String libellePaie) {
        this.libellePaie = libellePaie;
    }

    public BigInteger getMontantPaie() {
        return montantPaie;
    }

    public void setMontantPaie(BigInteger montantPaie) {
        this.montantPaie = montantPaie;
    }

    public String getOperateurToPaie() {
        return operateurToPaie;
    }

    public void setOperateurToPaie(String operateurToPaie) {
        this.operateurToPaie = operateurToPaie;
    }

    public String getCategorieToPaie() {
        return categorieToPaie;
    }

    public void setCategorieToPaie(String categorieToPaie) {
        this.categorieToPaie = categorieToPaie;
    }

    public List<String> getListeOperateurs() {
        return listeOperateurs;
    }

    public void setListeOperateurs(List<String> listeOperateurs) {
        this.listeOperateurs = listeOperateurs;
    }

    public List<String> getListeCategories() {
        return listeCategories;
    }

    public void setListeCategories(List<String> listeCategories) {
        this.listeCategories = listeCategories;
    }

    public List<Maitrecommunautaire> getListeDatatable() {
        return listeDatatable;
    }

    public void setListeDatatable(List<Maitrecommunautaire> listeDatatable) {
        this.listeDatatable = listeDatatable;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String Details) {
        this.Details = Details;
    }

    public LocalDate getMoisDePaie() {
        return MoisDePaie;
    }

    public void setMoisDePaie(LocalDate MoisDePaie) {
        this.MoisDePaie = MoisDePaie;
    }

    public LocalDate getDateDePaiement() {
        return DateDePaiement;
    }

    public void setDateDePaiement(LocalDate DateDePaiement) {
        this.DateDePaiement = DateDePaiement;
    }

    public BigInteger getMontantRestantSubsides() {
        return montantRestantSubsides;
    }

    public void setMontantRestantSubsides(BigInteger montantRestantSubsides) {
        this.montantRestantSubsides = montantRestantSubsides;
    }

    public BigInteger getDataTableTotalSubside() {
        return dataTableTotalSubside;
    }

    public void setDataTableTotalSubside(BigInteger dataTableTotalSubside) {
        this.dataTableTotalSubside = dataTableTotalSubside;
    }

    public void searchSelectivPaie() {
        System.out.println("operateur telco   " + this.operateurToPaie + " categorie pour paiement  " + this.categorieToPaie);
        listeDatatable = maitreMgr.searchByOpAndCtg(operateurToPaie, categorieToPaie);

        System.out.println("la taille de la liste des maitres a payer " + listeDatatable.size());
        BigInteger som = BigInteger.ZERO;
        BigInteger mnt;
        for (Maitrecommunautaire lis : listeDatatable) {
            //System.out.println("le montant des subsides  de ce maitre -------->: " + lis.getMensuel());
            mnt = new BigInteger(lis.getMensuel());
            try {
                som = som.add(mnt);
                //som = BigInteger.ZERO;
            } catch (Exception e) {
                System.out.println("--->" + e.getMessage());
            }
        }
        System.out.println("fin de la somme  , la somme totale a payer  est de : " + som);
        dataTableTotalSubside = som;
    }

    public List<Utilisateur> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(List<Utilisateur> listeUsers) {
        this.listeUsers = listeUsers;
    }

    public Notifications getNotif() {
        return notif;
    }

    public void setNotif(Notifications notif) {
        this.notif = notif;
    }

    //creation d'un nouveau paiement mensuel 
    public void newPaiementMensuelSelectif() {
        System.out.println("creation d'un nouveau paiement selectif ");

        if ((montantPaie.compareTo(dataTableTotalSubside) == 0) || (montantPaie.compareTo(dataTableTotalSubside) == 1)) {
            System.out.println("le montant saisi est correct");
            System.out.println("**************verif si le mois a ete deja paye************");
            System.out.println("nbre des mois deja payes  " + listeDesMoisDejaPayes.size());
            //il y a un montant restant afficher un msg en jaune pour alerte
            try {
                montantRestantSubsides = montantPaie.subtract(dataTableTotalSubside);
            } catch (Exception e) {
                e.getMessage();
            }

            if (montantRestantSubsides.intValue() >= 0) {
                System.out.println("le montant restant de ce piament selectif est  " + montantRestantSubsides);
                afficheMontantRestant = Boolean.TRUE;
            }

            //si le paiement existe deja parmis les mois payé : rejeter 
            Paiement paie = new Paiement(DateDePaiement.format(DateTimeFormatter.ISO_DATE),
                    Details,
                    montantPaie,
                    montantRestantSubsides.toString(),
                    this.MoisDePaie.format(DateTimeFormatter.ISO_DATE).substring(0, 7),
                    libellePaie,
                    Boolean.FALSE, DateOfDay(),
                    this.operateurToPaie,
                    Boolean.FALSE,
                    etatPaiementMgr.etatPaiementbyId(BigDecimal.ONE),
                    userCo,
                    this.categorieToPaie);

            //controle sur le paiement deja effectue d'un mois 
            for (Paiement unMois : listeDesMoisDejaPayes) {
                // System.out.println(" : ->" + unMois.getMois() + " montant " + unMois.getMontanttotal());

                if (unMois.getMois().equalsIgnoreCase(paie.getMois()) && unMois.getOperateurmobile().equalsIgnoreCase(this.operateurToPaie) && unMois.getCategoriePaiement().equalsIgnoreCase(this.categorieToPaie)) {
                    System.out.println("attention ce mois à déjà été payé pour ce opérateur, et pour ce type de catégorie :");
                    verifMoisDejaPaye = Boolean.TRUE;
                    break;

                } else {
                    System.out.println("le paiement peut etre effectué");
                    verifMoisDejaPaye = Boolean.FALSE;
                }
            }

            //control : la date de paiement suggeré ne doit pas etre inférieure a la date du jour de saisie 
            //affichge de msg d'erreur si la date de paiement est inf a la date de today 
            boolean rep = DateDePaiement.isBefore(LocalDate.now());
            System.out.println("date de paiement suggeree est inférieur a la date d'aujourdhui : " + rep);
            if (rep) {
                System.out.println("erreur on ne peux payer car la date est inférieur a celle de la date de saisie du paiement  " + DateDePaiement + " date d'audjourd'hui : " + LocalDate.now());
                verifJourDePaiementSuggerre = Boolean.TRUE;
            } else {
                System.out.println("la date suggeree a l'opérateur telco pour le paiement est bonne ");
                verifJourDePaiementSuggerre = Boolean.FALSE;
            }

            if (verifMoisDejaPaye) { //un msg d'erreur et l'arrêt du process si le mois a payé existe deja en base 
                msgErrorCreaPaiement();
            } else if (verifJourDePaiementSuggerre) {
                msgErrorDateDemandePaiement();
            } else { //sinn on paie a present 

                //verif inexistance paiement 
                if (!paiementMgr.verifUnicitePaie(paie)) {

                    paiementMgr.persist(paie);
                    //creation d'une liste de transactions pour persistance ...
                    //recuperation d'un ligne de mc a payer 
                    System.out.println("debut de recup des mc payable");
                    System.out.println("on a recuperer :  " + listeDatatable.size() + " maitres abonnés payables ");
                    listeDatatable.forEach(maitre -> {
                        //System.out.println("infos mc ->" + maitre.getNom() + " " + maitre.getPrenoms() + " " + maitre.getContactun() + " " + maitre.getOperatortelco());
                        //convertir en transaction ...
                        Transactions t = new Transactions();
                        t.setNumerointerne(maitre.getNumerointerne());
                        if (maitre.getStatutwallet()) {
                            t.setStatutwallet(BigInteger.ONE);
                        } else {
                            t.setStatutwallet(BigInteger.ZERO);
                        }
                        t.setTypecompte("compte APICED");
                        t.setMontantsubside(maitre.getMensuel());
                        t.setContactmaitre(maitre.getContactun());
                        t.setLibellepaie(paie.getLibelle());
                        t.setDatepaiement(paie.getDatepaiement());
                        t.setMoisanneepaie(paie.getMois());
                        t.setOperateurs(maitre.getOperatortelco());
                        //System.out.println("le contenu de paie " + paie.getIdpaiement());
                        t.setPaiementid(paie.getIdpaiement());
                        t.setNommaitre(maitre.getNom());
                        t.setPrenomsmaitre(maitre.getPrenoms());
                        t.setDatepaiementdemandee(paie.getDatepaiement());
                        //persistance dans la table transaction ...
                        transacMgr.persist(t);
                        //System.out.println("enregistrement ok de la transaction...");
                    });

                    libelleLog = "emission du paiement des subsides  : " + paie.getLibelle() + " par l'utilisateur : " + userCo.getLogin() + " d'un montant de  :" + paie.getMontanttotal() + " aux abonnés de catégorie:" + this.categorieToPaie + " de l'opérateur " + this.operateurToPaie;

                    saveLog(libelleLog, userCo);

                    typeNotification = typeNotifMgr.creaMcTypeNotifById(BigDecimal.valueOf(4));

                    String libelleNotif = "création de paiement de subsides mensuel selectif  par l'utilisateur : " + userCo.getLogin() + " pour validation";
                    String details = "création de paiement de subsides du mois de " + paie.getMois() + "d'un montant de  " + paie.getMontanttotal() + " des abonnés " + this.categorieToPaie + " de " + this.operateurToPaie;
                    listeUsers = utilisateurMgr.getAllActivedUsers();
                    //save de la notif de paiement 
                    savePaieNotif(libelleNotif, details, 4, paie, listeUsers);

                    msgSuccessNewPaie();

                    if (afficheMontantRestant) {
                        msgAlerteMonnaie();
                    }

                    PrimeFaces.current().ajax().update(":form:menu");
                } else {
                    msgErrorPaiementExistant();
                }

            }

        } else {
            System.out.println("le montant saisi est inférieur au montant total des subsides a payer ");
            msgErrorMauvaisMontant();
        }
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

    //sauvegarde d'un log 
    public void saveLog(String msg, Utilisateur userConnecte) {
        System.out.println("msg du log " + msg);
        System.out.println("la date " + DateOfDay());
        System.out.println("utilisateur connecte  " + userConnecte.getLogin());

        log.setAuteurIdutilisateur(userConnecte);
        log.setLogin(userConnecte.getLogin());
        log.setAction(msg + " par l'utilisateur " + userConnecte.getLogin());
        log.setDateaction(DateOfDay());
        auditMgr.persist(log);
    }

    //sauvegarde d'une notification
    public void savePaieNotif(String libelleNotif, String details, Integer idType, Paiement p, List<Utilisateur> userList) {
        System.out.println("libelle  " + libelleNotif);
        System.out.println("details  " + details);
        System.out.println("idtype  " + idType);
        System.out.println("paiement  " + p.getLibelle());
        System.out.println("nbre de user pour notifs  " + userList.size());

        BigDecimal typn = BigDecimal.valueOf(idType);
        typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
        notif.setDateresolution(DateOfDay());
        notif.setLibelle(libelleNotif);
        notif.setDetails(details);
        notif.setDatecreation(DateOfDay());
        notif.setEtat(BigInteger.ZERO);
        notif.setTypenotif(typeNotification);
        notif.setCreateur(userCo);
        notif.setIdinfo(p.getIdpaiement().toString());
        notifMgr.persist(notif);

        listeUsers = utilisateurMgr.getAllActivedUsers();
        //je recupère la dernière notif créée pour le setting a venir 
        Integer lastNotifId = notifMgr.lastNotif();
        //creation des btns 
        for (Utilisateur u : listeUsers) {
            Usersnotifs userNotif = new Usersnotifs();
            userNotif.setDateinsert(DateOfDay());
            userNotif.setEtat(BigInteger.ZERO);
            userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
            userNotif.setTitre(libelleNotif);
            userNotif.setInformation(details);
            userNotif.setCreateur(userCo.getLogin());
            userNotif.setTypeusernotif("VALIDATION_PAIE_SUBSIDES");
            //construction des btn en fonction des profils
            if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
                userNotif.setBtnvalidemc("false");
                userNotif.setBtnvalidepaie("false");
                userNotif.setBtndetail("true");
            } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
                userNotif.setBtnvalidemc("false");
                userNotif.setBtnvalidepaie("true");
                userNotif.setBtndetail("false");
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
    }

    public void msgSuccessNewPaie() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Demande de paiement enregistrée", "En attente de validation par le coordonnateur ,Prière Imprimer le rapport de demande à l'écran paiement à valider"));
    }

    public void msgErrorCreaPaiement() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'emettre ce paiment", "le paiement de ce mois pour cet Opérateur à été déjà émis"));
    }

    public void msgErrorDateDemandePaiement() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'emettre ce paiment", "le jour suggeré à l'opérateur est déjà passé"));
    }

    public void msgErrorMauvaisMontant() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'emettre le paiment", "le montant a entré : " + montantPaie + "  est inférieur aux montant des subsides a payer  :" + dataTableTotalSubside));
    }

    public void msgErrorPaiementExistant() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'emettre le paiment", "le paiement des maitres pour ce mois est dejà crée "));
    }

    public void msgAlerteMonnaie() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention !!", "un montant  de  " + montantRestantSubsides.intValueExact() + " FCFA restant chez MOOV pour ce Paiement "));
    }
}
