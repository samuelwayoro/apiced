/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Operateur;
import com.sbs.apiced_web.entities.Paiement;
import com.sbs.apiced_web.entities.Transactions;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Usersnotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.EtatPaiementManager;
import com.sbs.apiced_web.services.MaitreCoManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.OperateurTelcoManager;
import com.sbs.apiced_web.services.PaiementMensuelManager;
import com.sbs.apiced_web.services.TransactionsManager;
import com.sbs.apiced_web.services.TypeNotifManager;
import com.sbs.apiced_web.services.UsersNotifManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Future;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.util.LangUtils;

/**
 *
 * @author samuel
 */
@Named(value = "emissionPaieMensuelMBean")
@ViewScoped
public class EmissionPaieMensuelMBean implements Serializable {

    private Paiement p;
    private String libellePaie;
    private BigInteger montantPaie;
    private String Details;
    private Boolean selectionMc = Boolean.TRUE;
    private boolean skip;
    private List<Maitrecommunautaire> listeMaitresByOperateurId;
    private Maitrecommunautaire listeOperateurMcs;
    private List<Paiement> listeDesMoisDejaPayes;
    private Boolean verifMoisDejaPaye = false;
    private List<Operateur> listeDesOperateurs;
    private String choixOperateur;
    private Boolean step;
    private String text;
    private Map<String, List<Maitrecommunautaire>> data = new HashMap<>();
    private List<Maitrecommunautaire> listeDesMaitreAPayer;
    private List<Maitrecommunautaire> filteredMaitres;
    private List<Maitrecommunautaire> listeMaitreAirtel;
    private List<Maitrecommunautaire> listeMaitreAirtelApayer;
    private boolean impressionRapport = true;
    private BigInteger montantTotalSubsidesAirtel;
    private BigInteger montantRestantSubsides;
    private Boolean afficheMontantRestant;
    private List<Maitrecommunautaire> listeMaitreTigo;
    private Notifications notif = new Notifications();
    private Typenotifs typeNotification;
    private Utilisateur userCo;
    private Paiement lePaiement;
    private List<Utilisateur> listeUsers;
    private Boolean verifJourDePaiementSuggerre;
    private String libelleLog;
    private final Auditlog log = new Auditlog();

    @Future
    private LocalDate MoisDePaie;
    private LocalDate DateDePaiement;

    @EJB
    private AuditlogManager audit;
    @EJB
    private PaiementMensuelManager pm;
    @EJB
    private MaitreCoManager mcManager;
    @EJB
    private EtatPaiementManager etatPaiementMgr;
    @EJB
    private PaiementMensuelManager paiementMgr;
    @EJB
    private OperateurTelcoManager operateurMgr;
    @EJB
    private TypeNotifManager typeNotifMgr;
    @EJB
    private NotifsManager notifMgr;
    @EJB
    private TransactionsManager transacMgr;
    @EJB
    private UtilisateurManager utilisateurMgr;
    @EJB
    private UsersNotifManager userNotifMgr;
    @EJB
    private AuditlogManager auditMgr;

    public List<Utilisateur> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(List<Utilisateur> listeUsers) {
        this.listeUsers = listeUsers;
    }

    @PostConstruct
    public void init() {
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        userCo = (Utilisateur) session.getAttribute("utilisateurConnecte");

        //ts les maitres abonnés chez airtel
        listeMaitreAirtel = mcManager.getAirtelMcs();

        //liste des maitres abonnés chez airtel payable pour le mois : mc dont le wallet et le compte sont activés dns la plateforme
        String nomOp = "Airtel";
        listeMaitreAirtelApayer = mcManager.getMcsPayables(nomOp);

        BigInteger som = BigInteger.ZERO;
        BigInteger mnt;
        for (Maitrecommunautaire lis : listeMaitreAirtelApayer) {
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
        montantTotalSubsidesAirtel = som;

        //liste des mois dejà payés
        listeDesMoisDejaPayes = paiementMgr.listePaiementDejaEmis();

        //liste des opérateurs mobile
        listeDesOperateurs = operateurMgr.getAllOperateur();
        //
        List<Maitrecommunautaire> listeMaitreCoDeAirtel = mcManager.getAirtelMcs();
        data.put("AIRTEL", listeMaitreCoDeAirtel);
    }

    public BigInteger getMontantPaie() {
        return montantPaie;
    }

    public void setMontantPaie(BigInteger montantPaie) {
        this.montantPaie = montantPaie;
    }

    public List<Maitrecommunautaire> getListeMaitreAirtelApayer() {
        return listeMaitreAirtelApayer;
    }

    public void setListeMaitreAirtelApayer(List<Maitrecommunautaire> listeMaitreAirtelApayer) {
        this.listeMaitreAirtelApayer = listeMaitreAirtelApayer;
    }

    public Paiement getLePaiement() {
        return lePaiement;
    }

    public void setLePaiement(Paiement lePaiement) {
        this.lePaiement = lePaiement;
    }

    public Maitrecommunautaire getListeOperateurMcs() {
        return listeOperateurMcs;
    }

    public void setListeOperateurMcs(Maitrecommunautaire listeOperateurMcs) {
        this.listeOperateurMcs = listeOperateurMcs;
    }

    public Typenotifs getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(Typenotifs typeNotification) {
        this.typeNotification = typeNotification;
    }

    public NotifsManager getNotifMgr() {
        return notifMgr;
    }

    public void setNotifMgr(NotifsManager notifMgr) {
        this.notifMgr = notifMgr;
    }

    public Utilisateur getUserCo() {
        return userCo;
    }

    public void setUserCo(Utilisateur userCo) {
        this.userCo = userCo;
    }

    public boolean isImpressionRapport() {
        return impressionRapport;
    }

    public void setImpressionRapport(boolean impressionRapport) {
        this.impressionRapport = impressionRapport;
    }

    public List<Maitrecommunautaire> getListeMaitresByOperateurId() {
        return listeMaitresByOperateurId;
    }

    public BigInteger getMontantTotalSubsidesAirtel() {
        return montantTotalSubsidesAirtel;
    }

    public Boolean getStep() {
        return step;
    }

    public void setStep(Boolean step) {
        this.step = step;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BigInteger getMontantRestantSubsides() {
        return montantRestantSubsides;
    }

    public void setMontantRestantSubsides(BigInteger montantRestantSubsides) {
        this.montantRestantSubsides = montantRestantSubsides;
    }

    public MaitreCoManager getMcManager() {
        return mcManager;
    }

    public void setMcManager(MaitreCoManager mcManager) {
        this.mcManager = mcManager;
    }

    public Paiement getP() {
        return p;
    }

    public void setP(Paiement p) {
        this.p = p;
    }

    public String getLibellePaie() {
        return libellePaie;
    }

    public void setLibellePaie(String libellePaie) {
        this.libellePaie = libellePaie;
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

    public Boolean getSelectionMc() {
        return selectionMc;
    }

    public void setSelectionMc(Boolean selectionMc) {
        this.selectionMc = selectionMc;
    }

    public Map<String, List<Maitrecommunautaire>> getData() {
        return data;
    }

    public Maitrecommunautaire getSelectedProduct() {
        return listeOperateurMcs;
    }

    public void setSelectedProduct(Maitrecommunautaire listeOperateurMcs) {
        this.listeOperateurMcs = listeOperateurMcs;
    }

    public AuditlogManager getAudit() {
        return audit;
    }

    public void setAudit(AuditlogManager audit) {
        this.audit = audit;
    }

    public PaiementMensuelManager getPm() {
        return pm;
    }

    public void setPm(PaiementMensuelManager pm) {
        this.pm = pm;
    }

    public EtatPaiementManager getEtatPaiementMgr() {
        return etatPaiementMgr;
    }

    public void setEtatPaiementMgr(EtatPaiementManager etatPaiementMgr) {
        this.etatPaiementMgr = etatPaiementMgr;
    }

    public List<Paiement> getListeDesMoisDejaPayes() {
        return listeDesMoisDejaPayes;
    }

    public void setListeDesMoisDejaPayes(List<Paiement> listeDesMoisDejaPayes) {
        this.listeDesMoisDejaPayes = listeDesMoisDejaPayes;
    }

    public PaiementMensuelManager getPaiementMgr() {
        return paiementMgr;
    }

    public void setPaiementMgr(PaiementMensuelManager paiementMgr) {
        this.paiementMgr = paiementMgr;
    }

    public List<Operateur> getListeDesOperateurs() {
        return listeDesOperateurs;
    }

    public void setListeDesOperateurs(List<Operateur> listeDesOperateurs) {
        this.listeDesOperateurs = listeDesOperateurs;
    }

    public OperateurTelcoManager getOperateurMgr() {
        return operateurMgr;
    }

    public void setOperateurMgr(OperateurTelcoManager operateurMgr) {
        this.operateurMgr = operateurMgr;
    }

    public String getChoixOperateur() {
        return choixOperateur;
    }

    public void setChoixOperateur(String choixOperateur) {
        this.choixOperateur = choixOperateur;
    }

    public List<Maitrecommunautaire> getListeDesMaitreAPayer() {
        return listeDesMaitreAPayer;
    }

    public List<Maitrecommunautaire> getFilteredMaitres() {
        return filteredMaitres;
    }

    public void setFilteredMaitres(List<Maitrecommunautaire> filteredMaitres) {
        this.filteredMaitres = filteredMaitres;
    }

    public List<Maitrecommunautaire> getListeMaitreTigo() {
        return listeMaitreTigo;
    }

    public List<Maitrecommunautaire> getListeMaitreAirtel() {
        return listeMaitreAirtel;
    }

    public Notifications getNotif() {
        return notif;
    }

    public void setNotif(Notifications notif) {
        this.notif = notif;
    }

    public TypeNotifManager getTypeNotifMgr() {
        return typeNotifMgr;
    }

    public void setTypeNotifMgr(TypeNotifManager typeNotifMgr) {
        this.typeNotifMgr = typeNotifMgr;
    }

    public TransactionsManager getTransacMgr() {
        return transacMgr;
    }

    public void setTransacMgr(TransactionsManager transacMgr) {
        this.transacMgr = transacMgr;
    }

    //creation d'un nouveau paiement mensuel 
    public void newPaiementMensuelAirtel() {
        System.out.println("creation d'un nouveau paiement de mcs abonnes airtel");
        if ((montantPaie.compareTo(montantTotalSubsidesAirtel) == 0) || (montantPaie.compareTo(montantTotalSubsidesAirtel) == 1)) {
            System.out.println("le montant saisi est correct");
            System.out.println("**************verif si le mois a ete deja paye************");
            System.out.println("nbre des mois deja payes  " + listeDesMoisDejaPayes.size());
            //il y a un montant restant afficher un msg en jaune pour alerte
            try {
                montantRestantSubsides = montantPaie.subtract(montantTotalSubsidesAirtel);
            } catch (Exception e) {
            }

            if (montantRestantSubsides.intValue() >= 0) {
                System.out.println("le montant restant de paiement est  " + montantRestantSubsides);
                afficheMontantRestant = Boolean.TRUE;
            }

            String categoriePaiement = "ALL";
            //si le paiement existe deja parmis les mois payé : rejeter 
            Paiement paie = new Paiement(DateDePaiement.format(DateTimeFormatter.ISO_DATE),
                    Details,
                    montantPaie,
                    montantRestantSubsides.toString(),
                    this.MoisDePaie.format(DateTimeFormatter.ISO_DATE).substring(0, 7),
                    libellePaie,
                    Boolean.FALSE, DateOfDay(),
                    "MOOV",
                    Boolean.FALSE,
                    etatPaiementMgr.etatPaiementbyId(BigDecimal.ONE),
                    userCo,
                    categoriePaiement);

            //controle sur le paiement deja effectue d'un mois 
            for (Paiement unMois : listeDesMoisDejaPayes) {
                System.out.println(" : ->" + unMois.getMois() + " montant " + unMois.getMontanttotal());

                if (unMois.getMois().equalsIgnoreCase(paie.getMois()) && unMois.getOperateurmobile().equalsIgnoreCase("Airtel")) {
                    System.out.println("attention ce mois à déjà été payé pour ce opérateur  :");
                    verifMoisDejaPaye = Boolean.TRUE;
                    break;

                } else {
                    System.out.println("le paiement peut etre effectué");
                    verifMoisDejaPaye = Boolean.FALSE;
                    //     pm.persist(paie);
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
                List<Maitrecommunautaire> LesMc = mcManager.recupMcPayable("Airtel");
                System.out.println("on a recuperer :  " + LesMc.size() + " maitres abonnés Airtel payables ");
                LesMc.forEach(maitre -> {
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

                libelleLog = "emission du paiement des subsides  : " + paie.getLibelle() + " par l'utilisateur : " + userCo.getLogin() + " d'un montant de  :" + paie.getMontanttotal() + " à l'opérateur : AIRTEL";

                saveLog(libelleLog, userCo);

                typeNotification = typeNotifMgr.creaMcTypeNotifById(BigDecimal.valueOf(4));

                String libelleNotif = "création de paiement de subsides mensuel AIRTEL  par l'utilisateur : " + userCo.getLogin() + " pour validation";
                String details = "création de paiement de subsides du mois de " + paie.getMois() + "d'un montant de  " + paie.getMontanttotal();

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

    //au choix de l'opérateur
    public void onOperateurMobileChange() {
        if (choixOperateur != null) {
            System.out.println("affichage de la liste de l'operateur " + choixOperateur);
            listeMaitresByOperateurId = data.get(choixOperateur);
        }
    }

    public Boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        Maitrecommunautaire maitreco = (Maitrecommunautaire) value;
        return maitreco.getMatricule().toLowerCase().contains(filterText) || maitreco.getNom().toLowerCase().contains(filterText) || maitreco.getPrenoms().toLowerCase().contains(filterText);
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

    public String dateOfVerifPaiement() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String date = dt.format(dtFormat);
        return date.substring(3, 10);
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
        log.setAction(msg);
        log.setDateaction(date);
        audit.persist(log);
    }

    //vidage de tous les champs 
    public void clearChamps() {
        this.DateDePaiement = null;
        this.Details = null;
        this.MoisDePaie = null;
        this.audit = null;
        this.libellePaie = null;
        this.montantPaie = null;
        this.p = null;
    }

    public void msgSuccessNewPaie() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Demande de paiement enregistrée", "En attente de validation par le coordonnateur ,Prière Imprimer le rapport de demande à l'écran paiement à valider"));
    }

    public void msgAlerteMonnaie() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention !!", "un montant  de  " + montantRestantSubsides.intValueExact() + " FCFA restant chez MOOV pour ce Paiement "));
    }

    public void msgErrorCreaPaiement() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'emettre ce paiment", "le paiement de ce mois pour cet Opérateur à été déjà émis"));
    }

    public void msgErrorDateDemandePaiement() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'emettre ce paiment", "le jour suggeré à l'opérateur est déjà passé"));
    }

    public void msgErrorMauvaisMontant() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'emettre le paiment", "le montant a entré : " + montantPaie + "  est inférieur aux montant des subsides a payer  :" + montantTotalSubsidesAirtel));
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    //gestion de la pagination de paiement
    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;	//reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onTabClose(TabCloseEvent event) {
        FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
        public void msgErrorPaiementExistant() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'emettre le paiment", "le paiement des maitres pour ce mois est dejà crée "));
    }


    public void enable() {
        if (step) {
            System.out.println("la valeur de step : " + step.toString());
            step = Boolean.FALSE;
            System.out.println("la valeur après : " + step.toString());
        } else {
            System.out.println("la valeur est resté : " + step.toString());
        }
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
}
