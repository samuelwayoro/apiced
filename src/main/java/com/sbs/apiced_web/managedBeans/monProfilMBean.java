/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.Auditlog;
import com.sbs.apiced_web.entities.Notifications;
import com.sbs.apiced_web.entities.Paiement;
import com.sbs.apiced_web.entities.Typenotifs;
import com.sbs.apiced_web.entities.Usersnotifs;
import com.sbs.apiced_web.entities.Utilisateur;
import com.sbs.apiced_web.services.AuditlogManager;
import com.sbs.apiced_web.services.NotifsManager;
import com.sbs.apiced_web.services.PaiementMensuelManager;
import com.sbs.apiced_web.services.TypeNotifManager;
import com.sbs.apiced_web.services.UtilisateurManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author samuel
 */
@Named(value = "monProfilMBean")
@RequestScoped
public class monProfilMBean implements Serializable {

    private String nomUser;
    private String prenomsUser;
    private String loginUser;
    private String passwordUser;
    private String actualPassword;
    private String newPassOne;
    private String newPassTwo;
    private Integer idUser;
    private Utilisateur utilisateur = new Utilisateur();
    private List<Usersnotifs> listeNotifs;
    private List<Notifications> listeDemandePaiementEnAttente;
    private List<Paiement> listePaiementEnAttente;
    private List<Notifications> listeNotifsCoordonnateur;
    private List<Notifications> listeNotifsCoordoCreaMcs;
    private List<Notifications> listeNotifsCoordoCreaPaie;
    private Long notifEnAttente;
    private Long nbrePaiementAvalider;
    private Long nbreDmdPaieEnAttente;
    private Long mcEnAttenteDeValidation;
    private Long qtesNotifCoordonnateur;
    private Notifications selectedNotif;
    private Long qtesNotifsCoordoCreaMaitre;
    private Long qtesNotifsCoordoCreaPaie;
    private Long qteMcvalides;
    private Long qtesPaiementAvalider;
    private Long qtesPaiementValides;
    private Long mcCreeValides;
    private Long totalMc;
    private Long totalMcValides;
    private Long nbrePaiementEmisValides;
    private Long nbreTotalPaiements;
    private Long nbrePaiementEnAttente;
    private Long nbrePaiementAenvoyeAOp;
    private Long qteMcAvalider;

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

    /**
     * Creates a new instance of monProfilMBean
     */
    public monProfilMBean() {
    }

    public Long getQteMcvalides() {
        return qteMcvalides;
    }

    public void setQteMcvalides(Long qteMcvalides) {
        this.qteMcvalides = qteMcvalides;
    }

    public Long getQtesPaiementValides() {
        return qtesPaiementValides;
    }

    public void setQtesPaiementValides(Long qtesPaiementValides) {
        this.qtesPaiementValides = qtesPaiementValides;
    }
    
    public Long getQtesPaiementAvalider() {
        return qtesPaiementAvalider;
    }

    public void setQtesPaiementAvalider(Long qtesPaiementAvalider) {
        this.qtesPaiementAvalider = qtesPaiementAvalider;
    }

    public Long getQteMcAvalider() {
        return qteMcAvalider;
    }

    public void setQteMcAvalider(Long qteMcAvalider) {
        this.qteMcAvalider = qteMcAvalider;
    }

    public PaiementMensuelManager getPaieMgr() {
        return paieMgr;
    }

    public void setPaieMgr(PaiementMensuelManager paieMgr) {
        this.paieMgr = paieMgr;
    }

    public Long getNbreTotalPaiements() {
        return nbreTotalPaiements;
    }

    public void setNbreTotalPaiements(Long nbreTotalPaiements) {
        this.nbreTotalPaiements = nbreTotalPaiements;
    }

    public List<Notifications> getListeDemandePaiementEnAttente() {
        return listeDemandePaiementEnAttente;
    }

    public void setListeDemandePaiementEnAttente(List<Notifications> listeDemandePaiementEnAttente) {
        this.listeDemandePaiementEnAttente = listeDemandePaiementEnAttente;
    }

    public List<Paiement> getListePaiementEnAttente() {
        return listePaiementEnAttente;
    }

    public void setListePaiementEnAttente(List<Paiement> listePaiementEnAttente) {
        this.listePaiementEnAttente = listePaiementEnAttente;
    }

    public Long getTotalMc() {
        return totalMc;
    }

    public void setTotalMc(Long totalMc) {
        this.totalMc = totalMc;
    }

    public Long getNbrePaiementEnAttente() {
        return nbrePaiementEnAttente;
    }

    public void setNbrePaiementEnAttente(Long nbrePaiementEnAttente) {
        this.nbrePaiementEnAttente = nbrePaiementEnAttente;
    }

    public Long getNbrePaiementEmisValides() {
        return nbrePaiementEmisValides;
    }

    public void setNbrePaiementEmisValides(Long nbrePaiementEmisValides) {
        this.nbrePaiementEmisValides = nbrePaiementEmisValides;
    }

    public Long getMcCreeValides() {
        return mcCreeValides;
    }

    public void setMcCreeValides(Long mcCreeValides) {
        this.mcCreeValides = mcCreeValides;
    }

    public List<Notifications> getListeNotifsCoordoCreaMcs() {
        return listeNotifsCoordoCreaMcs;
    }

    public void setListeNotifsCoordoCreaMcs(List<Notifications> listeNotifsCoordoCreaMcs) {
        this.listeNotifsCoordoCreaMcs = listeNotifsCoordoCreaMcs;
    }

    public Long getMcEnAttenteDeValidation() {
        return mcEnAttenteDeValidation;
    }

    public void setMcEnAttenteDeValidation(Long mcEnAttenteDeValidation) {
        this.mcEnAttenteDeValidation = mcEnAttenteDeValidation;
    }

    public List<Notifications> getListeNotifsCoordonnateur() {
        return listeNotifsCoordonnateur;
    }

    public void setListeNotifsCoordonnateur(List<Notifications> listeNotifsCoordonnateur) {
        this.listeNotifsCoordonnateur = listeNotifsCoordonnateur;
    }

    public Notifications getSelectedNotif() {
        return selectedNotif;
    }

    public void setSelectedNotif(Notifications selectedNotif) {
        this.selectedNotif = selectedNotif;
    }

    public Long getQtesNotifsCoordoCreaMaitre() {
        return qtesNotifsCoordoCreaMaitre;
    }

    public void setQtesNotifsCoordoCreaMaitre(Long qtesNotifsCoordoCreaMaitre) {
        this.qtesNotifsCoordoCreaMaitre = qtesNotifsCoordoCreaMaitre;
    }

    public TypeNotifManager getTypeNotifMgr() {
        return typeNotifMgr;
    }

    public void setTypeNotifMgr(TypeNotifManager typeNotifMgr) {
        this.typeNotifMgr = typeNotifMgr;
    }

    public Long getQtesNotifsCoordoCreaPaie() {
        return qtesNotifsCoordoCreaPaie;
    }

    public void setQtesNotifsCoordoCreaPaie(Long qtesNotifsCoordoCreaPaie) {
        this.qtesNotifsCoordoCreaPaie = qtesNotifsCoordoCreaPaie;
    }

    public List<Notifications> getListeNotifsCoordoCreaPaie() {
        return listeNotifsCoordoCreaPaie;
    }

    public void setListeNotifsCoordoCreaPaie(List<Notifications> listeNotifsCoordoCreaPaie) {
        this.listeNotifsCoordoCreaPaie = listeNotifsCoordoCreaPaie;
    }

    public List<Usersnotifs> getListeNotifs() {
        return listeNotifs;
    }

    public void setListeNotifs(List<Usersnotifs> listeNotifs) {
        this.listeNotifs = listeNotifs;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getActualPassword() {
        return actualPassword;
    }

    public void setActualPassword(String actualPassword) {
        this.actualPassword = actualPassword;
    }

    public String getPrenomsUser() {
        return prenomsUser;
    }

    public void setPrenomsUser(String prenomsUser) {
        this.prenomsUser = prenomsUser;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getNewPassOne() {
        return newPassOne;
    }

    public void setNewPassOne(String newPassOne) {
        this.newPassOne = newPassOne;
    }

    public String getNewPassTwo() {
        return newPassTwo;
    }

    public void setNewPassTwo(String newPassTwo) {
        this.newPassTwo = newPassTwo;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }



    public AuditlogManager getAudit() {
        return auditMgr;
    }

    public UtilisateurManager getUserMgr() {
        return userMgr;
    }

    public NotifsManager getNotifsMgr() {
        return notifsMgr;
    }

    public Long getNotifEnAttente() {
        return notifEnAttente;
    }

    public Long getNbreDmdPaieEnAttente() {
        return nbreDmdPaieEnAttente;
    }

    public void setNbreDmdPaieEnAttente(Long nbreDmdPaieEnAttente) {
        this.nbreDmdPaieEnAttente = nbreDmdPaieEnAttente;
    }

    public Long getQtesNotifCoordonnateur() {
        return qtesNotifCoordonnateur;
    }

    public void setQtesNotifCoordonnateur(Long qtesNotifCoordonnateur) {
        this.qtesNotifCoordonnateur = qtesNotifCoordonnateur;
    }

    public AuditlogManager getAuditMgr() {
        return auditMgr;
    }

    public void setAuditMgr(AuditlogManager auditMgr) {
        this.auditMgr = auditMgr;
    }

    public Long getTotalMcValides() {
        return totalMcValides;
    }

    public void setTotalMcValides(Long totalMcValides) {
        this.totalMcValides = totalMcValides;
    }

    public Long getNbrePaiementAvalider() {
        return nbrePaiementAvalider;
    }

    public void setNbrePaiementAvalider(Long nbrePaiementAvalider) {
        this.nbrePaiementAvalider = nbrePaiementAvalider;
    }

    public Long getNbrePaiementAenvoyeAOp() {
        return nbrePaiementAenvoyeAOp;
    }

    public void setNbrePaiementAenvoyeAOp(Long nbrePaiementAenvoyeAOp) {
        this.nbrePaiementAenvoyeAOp = nbrePaiementAenvoyeAOp;
    }
    
    


    /**
     * methode retournant le nbre de notifications en attente en fonction du
     * profil de l'utilisateur connecté
     *
     * @return
     */
    public Long QtesNofits() {
        //si user de profil emeteur
        if (userCo().getProfilIdprofil().getIdprofil() == 2) {
            return notifEnAttente;
        } else {        //si c un coordonnateur 
            return qtesNotifCoordonnateur;
        }
    }

    @PostConstruct
    public void init() {

        Typenotifs notifCreaMc = typeNotifMgr.getOneTypeNotif(BigDecimal.ONE);
        Typenotifs notifPaieEnAttente = typeNotifMgr.getOneTypeNotif(BigDecimal.valueOf(4));
        Typenotifs notifPaieValides = typeNotifMgr.getOneTypeNotif(BigDecimal.valueOf(11));
        
          totalMcValides = notifsMgr.nbreMcEnAttenteValidation();
        
        //notification de type creation de paiement
        BigDecimal idtype = BigDecimal.valueOf(4);
        Typenotifs typeDemandePaiement = typeNotifMgr.getOneTypeNotif(idtype);

        //liste des notif de l'emet
        listeNotifs = notifsMgr.oneUserListeNotifications(BigInteger.valueOf(userCo().getIdutilisateur()));
        //liste des notif de demande de paiements en attente de validation 
        listeDemandePaiementEnAttente = notifsMgr.listeDmdPaieEnAttente(typeDemandePaiement);

        //nbre de demandes de paiement en attente de validation par le coordo
        nbreDmdPaieEnAttente = notifsMgr.nbreDmdPaieEnAttente(notifPaieEnAttente);
        //renvoi le nbre de notification pour un utilisateur
        notifEnAttente = notifsMgr.nbreNotifsEnAttente(BigInteger.valueOf(userCo().getIdutilisateur()), 0);
        
        //nbre de paiement en attente de validation
        nbrePaiementAvalider = notifsMgr.nbrePaiementsEnAttenteDeValidation();

        //nbre de mc émis par un emetteur , et en attente de validation 
        mcEnAttenteDeValidation = notifsMgr.nbreMcEnAttente(Boolean.FALSE);
        //mcEnAttenteDeValidation = notifsMgr.nbreMcEnAttenteValidation();
        //mcCreeValides = notifsMgr.nbreMcEnAttente(userCo(), Boolean.TRUE);

        totalMc = notifsMgr.nbreTotalMc();

        int x = 1;
        nbrePaiementEmisValides = notifsMgr.qtesPaiementEmisParEmetteur(userCo(), Boolean.TRUE);
        //nbre de paiement en attente de validation par le coordo
        nbrePaiementEnAttente = notifsMgr.qtesPaiementEnAttenteDeValidation();
        //nbre de paiement en attente d'envoi à l'opérateur 
        nbrePaiementAenvoyeAOp = notifsMgr.qtesPaiementsEnAttenteDenvoi();

        /**
         * STATS DU COORDONNATEUR POUR DASHBOARD
         */
        qtesNotifCoordonnateur = notifsMgr.nbreNotifsCoordonnateur(); //total des notifs du coordonateur

        listeNotifsCoordoCreaMcs = notifsMgr.notificationsCoordoCreaMaitre(notifCreaMc);
        qtesNotifsCoordoCreaMaitre = notifsMgr.nbreNotifsCoordoCreaMaitre(notifCreaMc);

        qteMcAvalider = notifsMgr.nbreDmdCreaMcEnAttente(notifCreaMc);

        qtesPaiementAvalider = notifsMgr.nbreDmdPaieEnAttente(notifPaieEnAttente);//nbre de paiement en attente de validation par le coordonnateur
        
        qteMcvalides = notifsMgr.nbreTotalMcValides(notifCreaMc);
        
        qtesPaiementValides = notifsMgr.nbreTotalPaieValides(); //nbre de paiement validés par le coordo

        qtesNotifsCoordoCreaPaie = notifsMgr.nbreNotifsCoordoCreaMaitre(typeDemandePaiement);
        listeNotifsCoordoCreaPaie = notifsMgr.notifCoordoCreaPaie(typeDemandePaiement);

        nbreTotalPaiements = notifsMgr.totalPaiement();

    }

    
    
    public void validNotif() {
        System.out.println("validation  de la notif");
    }

    //methode de modification des informations de l'utilisateur 
    public void updateInfos() {
        System.out.println("***************ENTREE DANS LA METHODE DE MAJ DE VOS INFOS****************************");
        //System.out.println("modif en cours de l'utilisateur ..." + this.nomUser);
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");

//formatage de la date
        String dateModif = dt.format(dtFormat);
        //System.out.println("date a enregister : "+dateModif);

//recup du user en session pour recup de son id 
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Utilisateur userConnected = (Utilisateur) session.getAttribute("utilisateurConnecte");

        //recup du user dans la bd 
        utilisateur = userMgr.getSelectedUser(1);

        if (utilisateur != null) {
            utilisateur.setNom(nomUser);
            userMgr.updateUtilisateur(utilisateur);
        }
        System.out.println("***************FIN  METHODE DE MAJ DE VOS INFOS****************************");

    }

    /**
     * modification du mot de passe de l'utilisateur
     */
    public void modifPassword() {
        //System.out.println("mise a jour du mot de passe :  ACTUEL =>" + passwordUser + "    CONFIRM1 =>" + newPassOne + "    CONFIRM2" + newPassTwo);
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dateModif = dt.format(datePattern);

        //recuperation du mdp du user connecté
        actualPassword = userCo().getMotdepass();
        String userLogin = userCo().getLogin();
        //si le mot de pass actuel esti different que celui en bd : lever un msg d'erreur 
        if (!(actualPassword).equals(DigestUtils.shaHex(this.passwordUser))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : le mot de passe actuel est incorrect", ""));
        } else {//sinon : conparer confirm1 et confirm2
            if (!newPassOne.equals(newPassTwo)) { //si il sont different : lever un msg d'erreur 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de saisie dans la confirmation du nouveau  mot de passe", ""));
            } else {
                String pass = DigestUtils.shaHex(this.newPassOne);
                //maj dans la bd 
                utilisateur = userMgr.findByUserLogin(userLogin);
                utilisateur.setMotdepass(pass);
                userMgr.updateUtilisateur(utilisateur);
                //System.out.println("la confirmation du nouveau mdp n'est pas ok");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mot de passe modifié ! veuillez vous reconnecter ", ""));
                Auditlog log = new Auditlog();
                log.setAuteurIdutilisateur(userCo());
                log.setLogin(userLogin);
                log.setAction("modification de mot de passe par utilisateur  : " + userLogin);
                //  System.out.println("date et heure :"+dt.format(datePattern));
                log.setDateaction(dateModif);
                auditMgr.persist(log);
            }
        }
    }

//    //renvoi le nbre de notifs en attente de validation 
//    public Long qtesNotifsEnAttente(){
//      return notifEnAttente = notifsMgr.nbreNotifsEnAttente(userCo(),0);
//    }
    //formatage des messages de traitements
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void msgExistanceUserEnBd() {
        //  addMessage(FacesMessage.SEVERITY_ERROR, "erreur", "un utilisateur existe déjà avec ce login");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur ", "veuillez utiliser un autre"));
    }

    public Utilisateur userCo() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Utilisateur userConnected = (Utilisateur) session.getAttribute("utilisateurConnecte");
        return userConnected;
    }

}
