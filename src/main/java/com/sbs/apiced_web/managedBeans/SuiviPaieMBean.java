/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Etatpaiement;
import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Paiement;
import com.sbs.apiced_web.entities.Parametres;
import com.sbs.apiced_web.entities.Transactions;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Usersnotifs;
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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.primefaces.PrimeFaces;

/**
 *
 * @author samuel
 */
@Named(value = "suiviPaieMBean")
@ViewScoped
public class SuiviPaieMBean implements Serializable {
//Délimiteurs qui doivent être dans le fichier CSV

    private static final String DELIMITER = ",";
    private static final String SEPARATOR = "\n";
    //En-tête de fichier
    private static final String HEADER = "idtransaction ,numerointerne ,statutwallet ,typecompte ,montantsubside ,contactmaitre ,libellepaie ,responsecode ,responsemessage ,dateenvoi ,datepaiement ,moisanneepaie ,operateurs ,nommaitre ,prenomsmaitre ,dateenvoiaoperateur ,datepaiementdemandee ,datereceptionclient ,datelimitepaiement ,etattransaction ,paiementid";

    private String serveur;
    private int port = 21;
    private String user;
    private String password;

    private Paiement selectedPaiement;
    private Long nbrePaiementEnAttente;
    private List<Paiement> listePaiementEnAttenteDenvoi;
    private List<Transactions> listeTransactionsEnCours;
    private Long nbrePaiementEnAttenteDenvoi;
    private Long nbrePaiementEnCours;
    private Utilisateur userCo;
    private List<Transactions> transactionsAenvoyer;
    private Typenotifs typeNotification;
        private String libelleNotif;
    private String detailNotif;
    private Notifications notif = new Notifications();
    private List<Utilisateur> listeUsers;

    @ManagedProperty(value = "#{monProfilMBean}")
    private monProfilMBean monProfilMBean;

    public Utilisateur getUserCo() {
        return userCo;
    }

    public void setUserCo(Utilisateur userCo) {
        this.userCo = userCo;
    }

    public AuditlogManager getAudit() {
        return audit;
    }

    public void setAudit(AuditlogManager audit) {
        this.audit = audit;
    }

    public Long getNbrePaiementEnCours() {
        return nbrePaiementEnCours;
    }

    public void setNbrePaiementEnCours(Long nbrePaiementEnCours) {
        this.nbrePaiementEnCours = nbrePaiementEnCours;
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

    
    
    @EJB
    private AuditlogManager auditMgr;
    @EJB
    private UtilisateurManager userMgr;
    @EJB
    private NotifsManager notifsMgr;
    @EJB
    private TypeNotifManager typeNotifMgr;
    @EJB
    private PaiementMensuelManager paieMgr;
    @EJB
    private MaitreCoManager mcManager;
    @EJB
    private PaiementMensuelManager paiementMgr;
    @EJB
    private TransactionsManager transacMgr;
    @EJB
    private AuditlogManager audit;
    @EJB
    private EtatPaiementManager etatPaieMgr;
    @EJB
    private UsersNotifManager userNotifMgr;
    @EJB
    private NotifsManager notifMgr;
    @EJB
    private UtilisateurManager utilisateurMgr;
    @EJB
    private ParamManager paramMgr;

    @PostConstruct
    public void init() {
        //connexion par ftp pour recuperer fichier retour de paiement AIRTEL 
//        recupRetourPaiementAirtel();
        //liste des demande de  paiement en attente de validation par le coordo
        listePaiementEnAttenteDenvoi = paieMgr.listePaiementEnAttenteDenvoi();
        listeTransactionsEnCours = paieMgr.listeTransactionsEnCours();
        nbrePaiementEnAttente = notifsMgr.qtesPaiementAEnvoyer();
        nbrePaiementEnAttenteDenvoi = paieMgr.nbrePaiementEnAttenteDenvoi();
        nbrePaiementEnCours = paieMgr.nbreTransactionsEnCours();
        // recuperation du user en session 
        //recuperation de la facecontext pour travailler avec le context courant de la requette
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //recuperation de la session a partir de la facescontext pour annuler la session de l'utilisateur
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        //System.out.println("loggin de l'action de l'utilisateur : " + session.getAttribute("utilisateur"));
        userCo = (Utilisateur) session.getAttribute("utilisateurConnecte");
    }
    
     /**
     * Creates a new instance of SuiviPaieMBean
     */
    public SuiviPaieMBean() {

    }

    public List<Transactions> getTransactionsAenvoyer() {
        return transactionsAenvoyer;
    }

    public void setTransactionsAenvoyer(List<Transactions> transactionsAenvoyer) {
        this.transactionsAenvoyer = transactionsAenvoyer;
    }

    public Long getNbrePaiementEnAttenteDenvoi() {
        return nbrePaiementEnAttenteDenvoi;
    }

    public void setNbrePaiementEnAttenteDenvoi(Long nbrePaiementEnAttenteDenvoi) {
        this.nbrePaiementEnAttenteDenvoi = nbrePaiementEnAttenteDenvoi;
    }

    public List<Transactions> getListeTransactionsEnCours() {
        return listeTransactionsEnCours;
    }

    public void setListeTransactionsEnCours(List<Transactions> listeTransactionsEnCours) {
        this.listeTransactionsEnCours = listeTransactionsEnCours;
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

    public List<Utilisateur> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(List<Utilisateur> listeUsers) {
        this.listeUsers = listeUsers;
    }

    public EtatPaiementManager getEtatPaieMgr() {
        return etatPaieMgr;
    }

    public void setEtatPaieMgr(EtatPaiementManager etatPaieMgr) {
        this.etatPaieMgr = etatPaieMgr;
    }

    public UsersNotifManager getUserNotifMgr() {
        return userNotifMgr;
    }

    public void setUserNotifMgr(UsersNotifManager userNotifMgr) {
        this.userNotifMgr = userNotifMgr;
    }

    public NotifsManager getNotifMgr() {
        return notifMgr;
    }

    public void setNotifMgr(NotifsManager notifMgr) {
        this.notifMgr = notifMgr;
    }

    public UtilisateurManager getUtilisateurMgr() {
        return utilisateurMgr;
    }

    public void setUtilisateurMgr(UtilisateurManager utilisateurMgr) {
        this.utilisateurMgr = utilisateurMgr;
    }

   

    public Paiement getSelectedPaiement() {
        return selectedPaiement;
    }

    public void setSelectedPaiement(Paiement selectedPaiement) {
        this.selectedPaiement = selectedPaiement;
    }

    public Long getNbrePaiementEnAttente() {
        return nbrePaiementEnAttente;
    }

    public void setNbrePaiementEnAttente(Long nbrePaiementEnAttente) {
        this.nbrePaiementEnAttente = nbrePaiementEnAttente;
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

    public MaitreCoManager getMcManager() {
        return mcManager;
    }

    public void setMcManager(MaitreCoManager mcManager) {
        this.mcManager = mcManager;
    }

    public PaiementMensuelManager getPaiementMgr() {
        return paiementMgr;
    }

    public void setPaiementMgr(PaiementMensuelManager paiementMgr) {
        this.paiementMgr = paiementMgr;
    }

    public TransactionsManager getTransacMgr() {
        return transacMgr;
    }

    public void setTransacMgr(TransactionsManager transacMgr) {
        this.transacMgr = transacMgr;
    }

    public List<Paiement> getListePaiementEnAttenteDenvoi() {
        return listePaiementEnAttenteDenvoi;
    }

    public void setListePaiementEnAttenteDenvoi(List<Paiement> listePaiementEnAttenteDenvoi) {
        this.listePaiementEnAttenteDenvoi = listePaiementEnAttenteDenvoi;
    }

    public String getServeur() {
        return serveur;
    }

    public void setServeur(String serveur) {
        this.serveur = serveur;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public monProfilMBean getMonProfilMBean() {
        return monProfilMBean;
    }

    public void setMonProfilMBean(monProfilMBean monProfilMBean) {
        this.monProfilMBean = monProfilMBean;
    }

    public ParamManager getParamMgr() {
        return paramMgr;
    }

    public void setParamMgr(ParamManager paramMgr) {
        this.paramMgr = paramMgr;
    }

//    public void envoiAOp() {
//        //System.out.println("paiement  " + selectedPaiement.getLibelle() + " en envoi vers l'operateur");
//        //donner la date du jr a la date denvoi a operateur 
//        selectedPaiement.setDateenvoiaoperateur(DateOfDay());
//        //etat envoi a op
//        selectedPaiement.setEtatenvoiop(Boolean.TRUE);
//        //date modification 
//        selectedPaiement.setDatemodification(DateOfDay());
//        selectedPaiement.setEnvoyeuraop(userCo.getLogin());
//        //setter la date d'envoi des transactions ... et date envoi operateur
//        //recup de toutes les transactions du paiement selectionneé
//        transactionsAenvoyer = transacMgr.selectedPaieTransactions(selectedPaiement.getIdpaiement());
//        //boucle pr supprimer
//        //System.out.println("debut de maj des transactions");
//        for (Transactions t : transactionsAenvoyer) {
//            t.setDateenvoi(DateOfDay());
//            t.setDateenvoiaoperateur(DateOfDay());
//            t.setDatelimitepaiement(DateOfDay());
//            //etat transactions .. : envoyé (transaction envoyé a op) / recu op(accusé reception du paiement recu) / echoué (soucis lors de l'envoi) / payé (mc a recu son blé) ...
//            t.setEtattransaction("envoye");
//            transacMgr.updatePaie(t);
//        }
//        //System.out.println("fin de maj des transactions");
//        //recup de l'etat paiement en attente de validatation
//        Etatpaiement etatpaiement = etatPaieMgr.etatPaiementbyId(BigDecimal.valueOf(2));
//        selectedPaiement.setEtatpaiement(etatpaiement);
//        paieMgr.updatePaie(selectedPaiement);
//        /**
//         * generation du fichier json
//         */
//        //System.out.println("genration du fichier json...");
//        ObjectMapper mapper = new ObjectMapper();
//        //generation du fichier json 
//        File f = new File("D:/transactions.json");
//        try {
//            mapper.writeValue(f, transactionsAenvoyer);
//        } catch (IOException ex) {
//            Logger.getLogger(transactionsPaiementsMBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //logging et infos dashboard
//        //auditlog
//        saveLog("validation du paiement de subside  :" + selectedPaiement.getLibelle(), DateOfDay());
//        BigDecimal typn = BigDecimal.valueOf(4);
//        typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
//        String libelleNotif = "Paiement de subside " + selectedPaiement.getLibelle() + " validé par le coordonnateur" + userCo.getLogin();
//        String details = "validation de paiement des subsides  " + selectedPaiement.getLibelle() + " du mois de  " + selectedPaiement.getMois();
//        notif.setLibelle(libelleNotif);
//        notif.setDetails(details);
//        notif.setDatecreation(DateOfDay());
//        notif.setDateresolution(DateOfDay());
//        notif.setTypenotif(typeNotification);
//        notif.setCreateur(userCo);
//        notif.setIdinfo(selectedPaiement.getIdpaiement().toString());
//        //save de la notif
//        notifMgr.persist(notif);
//        //persistance de toutes les notifications pour tous les utilisateurs ...
//        listeUsers = utilisateurMgr.getAllActivedUsers();
//        //je recupère la dernière notif créée pour le setting a venir 
//        Integer lastNotifId = notifMgr.lastNotif();
//        //creation des btns 
//        for (Utilisateur u : listeUsers) {
//            Usersnotifs userNotif = new Usersnotifs();
//            userNotif.setDateinsert(DateOfDay());
//            userNotif.setEtat(BigInteger.ZERO);
//            userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
//            userNotif.setTitre(libelleNotif);
//            userNotif.setInformation(details);
//            userNotif.setCreateur(userCo.getLogin());
//            userNotif.setTypeusernotif("ENVOYES_A_OP");
//            //construction des btn en fonction des profils
//            if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur")) {
//                userNotif.setBtnvalidemc("false");
//                userNotif.setBtnvalidepaie("false");
//                userNotif.setBtndetail("true");
//            } else if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
//                userNotif.setBtnvalidemc("false");
//                userNotif.setBtnvalidepaie("false");
//                userNotif.setBtndetail("true");
//            } else {
//                userNotif.setBtnvalidemc("false");
//                userNotif.setBtnvalidepaie("false");
//                userNotif.setBtndetail("true");
//            }
//            //setter l'id du notif
//            userNotif.setIdnotif(BigInteger.valueOf(lastNotifId));
//            //on persist la notifUser pr finir
//            userNotifMgr.persist(userNotif);
//        }
//
//        listePaiementEnAttenteDenvoi.remove(selectedPaiement);
//        //maj du tableau des mc et raffraichissement de la vue 
//        msgSuccesPaiementEnvoye();
//        PrimeFaces.current().ajax().update(":form:messages", ":form:paiementsEnAttente");
//    }
    public void sendPaieToTelcoCsv() {
        //recup de l'etat paiement 2 pour maj du paiement 
        Etatpaiement etatpaiement = etatPaieMgr.etatPaiementbyId(BigDecimal.valueOf(2));
        selectedPaiement.setEtatpaiement(etatpaiement);
        selectedPaiement.setDateenvoiaoperateur(DateOfDay());
        selectedPaiement.setDatereceptionbyop(DateOfDay());
        selectedPaiement.setEtatenvoiop(Boolean.TRUE);//pas encore envoyé a l'opérateur
        selectedPaiement.setDatemodification(DateOfDay());
        selectedPaiement.setEnvoyeuraop(user);
        //system d'envoi de fichier par ftp 
        //     p = paramMgr.paramByLibelle("DERNIER_FICHIER_PAIE_AIRTEL");
        File file = new File("");
        //creation du client ftp
        FTPClient client_ftp = new FTPClient();
        //creation d'un objet stream pour l'ecriture et la lecture 
        FileInputStream f_stream = null;
        try {
            //connexion en fonction de l'operateur
            if (selectedPaiement.getOperateurmobile().equalsIgnoreCase("AIRTEL")) {
                serveur = paramMgr.paramValueByLibelle("IP_ADRESS_LOCAL_SERVER_AIRTEL");
                user = paramMgr.paramValueByLibelle("LOGIN_USER_LOCAL_FTP_AIRTEL");
                password = paramMgr.paramValueByLibelle("PASSWORD_USER_LOCAL_FTP_AIRTEL");
                client_ftp.connect(serveur, port);
                client_ftp.login(user, password);
                client_ftp.enterLocalPassiveMode();
                client_ftp.setFileType(FTP.BINARY_FILE_TYPE);
            } else {
                serveur = paramMgr.paramValueByLibelle("IP_ADRESS_LOCAL_SERVER_MOOV");
                user = paramMgr.paramValueByLibelle("LOGIN_USER_LOCAL_FTP_MOOV");
                password = paramMgr.paramValueByLibelle("PASSWORD_USER_LOCAL_FTP_MOOV");
                client_ftp.connect(serveur, port);
                client_ftp.login(user, password);
                client_ftp.enterLocalPassiveMode();
                client_ftp.setFileType(FTP.BINARY_FILE_TYPE);
            }

//recup du dernier fichier de paiement en fonction de l'operateur a payer 
            Parametres p = new Parametres();
            if (selectedPaiement.getOperateurmobile().equalsIgnoreCase("AIRTEL")) {
                p = paramMgr.paramByLibelle("DERNIER_FICHIER_PAIE_AIRTEL");
                System.out.println("nom du dernier fichier de paiement recupere =>" + p.getValeur());
                file = new File("D:/paiements_apiced/PAIEMENT_ALLER_AIRTEL/" + p.getValeur());
            } else {
                p = paramMgr.paramByLibelle("DERNIER_FICHIER_PAIE_MOOV");
                System.out.println("nom du dernier fichier de paiement recupere =>" + p.getValeur());
                file = new File("D:/paiements_apiced/PAIEMENT_ALLER_MOOV/" + p.getValeur());//fichier ou a ete genere le fichier de paiement csv pour moov 
            }
            String chemin = p.getValeur();
            InputStream inputStream = new FileInputStream(file);
            System.out.println("debut de l'upload du fichier ");
            //resultat de l'upload
            boolean res = client_ftp.storeFile(chemin, inputStream);//copie du fichier de chemin(distant) vers inputStream (local) ...
            //fermer le flux de lecture
            //deconnexion et fermeture du stream 
            inputStream.close();
            if (res == true) {
                System.out.println("le fichier a ete transfere avec succes");
                paieMgr.updatePaie(selectedPaiement);
                transactionsAenvoyer = transacMgr.selectedPaieTransactions(selectedPaiement.getIdpaiement());
                for (Transactions t : transactionsAenvoyer) {
                    t.setDateenvoi(DateOfDay());
                    t.setDateenvoiaoperateur(DateOfDay());
                    t.setDatelimitepaiement(DateOfDay());
                    t.setEtattransaction("En cours de paiement"); // a parametrer
                    transacMgr.updatePaie(t);
                }
                //logging et infos dashboard
                //auditlog
                saveLog("envoi du fichier de paiement  :" + selectedPaiement.getLibelle() + "a l_operateur " + selectedPaiement.getOperateurmobile(), DateOfDay());
                BigDecimal typn = BigDecimal.valueOf(4);
                typeNotification = typeNotifMgr.creaMcTypeNotifById(typn);
                 libelleNotif = "transfert du fichier de paiement de subside " + selectedPaiement.getLibelle() + " du mois de  " + selectedPaiement.getMois();
                 detailNotif = "transfert du fichier de paiement de subside " + selectedPaiement.getLibelle() + " a l'operateur " + selectedPaiement.getOperateurmobile() + " par l'utilisateur " + userCo.getLogin();
                notif.setLibelle(libelleNotif);
                notif.setDetails(detailNotif);
                notif.setDatecreation(DateOfDay());
                notif.setDateresolution(DateOfDay());
                notif.setTypenotif(typeNotification);
                notif.setCreateur(userCo);
                notif.setIdinfo(selectedPaiement.getIdpaiement().toString());
                //save de la notif
                notifMgr.persist(notif);

                listePaiementEnAttenteDenvoi.remove(selectedPaiement);
                //maj du tableau des mc et raffraichissement de la vue 
                msgSuccesPaiementEnvoye();
                PrimeFaces.current().ajax().update(":form:messages", ":form:paiementsEnAttente");
                //persistance de toutes les notifications pour tous les utilisateurs ...
                listeUsers = utilisateurMgr.getAllActivedUsers();
                //je recupère la dernière notif créée pour le setting a venir 
                Integer lastNotifId = notifMgr.lastNotif();
                //creation des btns teableau notifs pour touts les profils 
                for (Utilisateur u : listeUsers) {
                    Usersnotifs userNotif = new Usersnotifs();
                    userNotif.setDateinsert(DateOfDay());
                    userNotif.setEtat(BigInteger.ZERO);
                    userNotif.setIdutilisateur(BigInteger.valueOf(u.getIdutilisateur()));
                    userNotif.setTitre(libelleNotif);
                    userNotif.setInformation(detailNotif);
                    userNotif.setCreateur(userCo.getLogin());
                    userNotif.setTypeusernotif("TRANSFERT_FICHIER_PAIEMENT");
                    //construction des btns en fonction des profils
                    if (u.getProfilIdprofil().getLibelle().equalsIgnoreCase("emetteur") || u.getProfilIdprofil().getLibelle().equalsIgnoreCase("coordonnateur")) {
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
                    userNotif = null;
                }
            } else {
                System.out.println("ERREUR LE FICHIER N'PAS ETE ENVOYE A L'OPERATEUR!!!");
                msgErrorPaiementNonEnvoye();
            }
        } catch (IOException ex) {
            Logger.getLogger(SuiviPaieMBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (client_ftp.isConnected()) {
                try {
                    client_ftp.logout();
                    client_ftp.disconnect();
                } catch (IOException ex) {
                    Logger.getLogger(SuiviPaieMBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        notif = null;
    }

    public String DateOfDay() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String date = dt.format(dtFormat);
        return date;
    }

    public String DateOfDayPaieFile() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd_MM_YYYY");
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

    public void msgSuccesPaiementEnvoye() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "paiement envoyé à l'opérateur", "les transactions sont en cours de paiement "));
    }

    public void msgErrorPaiementNonEnvoye() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erreur envoi de fichier", "le fichier de paiement n'a pu être envoyé"));
    }

    //recuperation du fichier de paiement retour airtel
//    private void recupRetourPaiementAirtel() {
//        System.out.println("RECUP DU FICHIER DE PAIEMENT ******");
//        //system d'envoi de fichier par ftp 
//        String serveur = "localhost";
//        int port = 21;
//        String login = "airtel";
//        String pass = "airtel123";
//        Parametres p;
//        File downloadFile1;
//        FTPClient ftpClientRetour = new FTPClient();
//
//        try {
//            ftpClientRetour.connect(serveur, port);
//            ftpClientRetour.login(login, pass);
//            ftpClientRetour.enterLocalPassiveMode();
//            ftpClientRetour.setFileType(FTP.BINARY_FILE_TYPE);
//
//            //recup du dernier fichier de paiement de AIRTEL 
//            //p = paramMgr.paramByLibelle("DERNIER_FICHIER_PAIE_AIRTEL");
//            p = paramMgr.paramByLibelle("MOOV_FINAL_PAIE_LASTFILENAME");
//            System.out.println("nom du dernier fichier de paiement recupere =>" + p.getValeur());
//
//            //String remoteFile1 = "D:/paiements_apiced/AIRTEL/" + p.getValeur();//repertoire distant de airtel pour retour paiement 
//            String remoteFile1 = p.getValeur();//repertoire distant de airtel pour retour paiement 
//
//            //downloadFile1 = new File("D:/paiements_apiced/PAIEMENT_RETOUR_AIRTEL/" + p.getValeur());
//            downloadFile1 = new File("D:/paiements_apiced/PAIEMENT_RETOUR_MOOV/" + p.getValeur());
//            boolean success;
//            //deplacement du fichier
//            try (OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1))) {
//                //deplacement du fichier
//                success = ftpClientRetour.retrieveFile(remoteFile1, outputStream1);
//            }
//
//            if (success) {
//                System.out.println("FICHIER TELECHARGE");
//            } else {
//                System.out.println("DESOLE PAS MARCHE ");
//            }
//
//        } catch (IOException ex) {
//            Logger.getLogger(SuiviPaieMBean.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//
//            if (ftpClientRetour.isConnected()) {
//                try {
//                    ftpClientRetour.logout();
//                    ftpClientRetour.disconnect();
//                } catch (IOException ex) {
//                    Logger.getLogger(SuiviPaieMBean.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//
//        System.out.println("FIN DE TELECHARGEMENT DU FICHIER **************************");
//
//    }

}
