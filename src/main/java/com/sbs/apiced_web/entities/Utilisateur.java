package com.sbs.apiced_web.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author samuel
 */
@Entity
@Table(name = "UTILISATEUR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u"),
    @NamedQuery(name = "Utilisateur.findByIdutilisateur", query = "SELECT u FROM Utilisateur u WHERE u.idutilisateur = :idutilisateur"),
    @NamedQuery(name = "Utilisateur.findByActiver", query = "SELECT u FROM Utilisateur u WHERE u.activer = :activer"),
    @NamedQuery(name = "Utilisateur.findByDateactivation", query = "SELECT u FROM Utilisateur u WHERE u.dateactivation = :dateactivation"),
    @NamedQuery(name = "Utilisateur.findByDatecreation", query = "SELECT u FROM Utilisateur u WHERE u.datecreation = :datecreation"),
    @NamedQuery(name = "Utilisateur.findByDatedesactivation", query = "SELECT u FROM Utilisateur u WHERE u.datedesactivation = :datedesactivation"),
    @NamedQuery(name = "Utilisateur.findByDatemodification", query = "SELECT u FROM Utilisateur u WHERE u.datemodification = :datemodification"),
    @NamedQuery(name = "Utilisateur.findByEmail", query = "SELECT u FROM Utilisateur u WHERE u.email = :email"),
    @NamedQuery(name = "Utilisateur.findByEtatconnexion", query = "SELECT u FROM Utilisateur u WHERE u.etatconnexion = :etatconnexion"),
    @NamedQuery(name = "Utilisateur.findByLogin", query = "SELECT u FROM Utilisateur u WHERE u.login = :login"),
    @NamedQuery(name = "Utilisateur.findByMotdepass", query = "SELECT u FROM Utilisateur u WHERE u.motdepass = :motdepass"),
    @NamedQuery(name = "Utilisateur.findByNom", query = "SELECT u FROM Utilisateur u WHERE u.nom = :nom"),
    @NamedQuery(name = "Utilisateur.findByPrenoms", query = "SELECT u FROM Utilisateur u WHERE u.prenoms = :prenoms")})

public class Utilisateur implements Serializable {
    @Column(name = "ACTIVER")
    private Boolean activer;
    @Size(max = 255)
    @Column(name = "DATEACTIVATION")
    private String dateactivation;
    @Size(max = 255)
    @Column(name = "DATECREATION")
    private String datecreation;
    @Size(max = 255)
    @Column(name = "DATEDESACTIVATION")
    private String datedesactivation;
    @Size(max = 255)
    @Column(name = "DATEMODIFICATION")
    private String datemodification;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 255)
    @Column(name = "ETATCONNEXION")
    private String etatconnexion;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "LOGIN")
    private String login;
    @Size(max = 255)
    @Column(name = "MOTDEPASS")
    private String motdepass;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 255)
    @Column(name = "PRENOMS")
    private String prenoms;
    @Size(max = 255)
    @Column(name = "CREATEUR")
    private String createur;
    @Size(max = 255)
    @Column(name = "VALIDEUR")
    private String valideur;
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idUtilisateur_generator")
    @SequenceGenerator(name = "idUtilisateur_generator", sequenceName = "idUtilisateur_seq", allocationSize = 1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDUTILISATEUR")
    private Integer idutilisateur;
    @OneToMany(mappedBy = "valideur")
    private Collection<Paiement> paiementCollection;
    @OneToMany(mappedBy = "emeteur")
    private Collection<Paiement> paiementCollection1;
//    @OneToMany(mappedBy = "idcreateur")
//    private Collection<Maitrecommunautaire> maitrecommunautaireCollection;
    @JoinColumn(name = "PROFIL_IDPROFIL", referencedColumnName = "IDPROFIL")
    @ManyToOne
    private Profil profilIdprofil;
    @OneToMany(mappedBy = "createur")
    private Collection<Notifications> notificationsCollection;
    @OneToMany(mappedBy = "destinataire")
    private Collection<Notifications> notificationsCollection1;

    public Utilisateur() {
    }

    public Utilisateur(Integer idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public Integer getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(Integer idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public Profil getProfilIdprofil() {
        return profilIdprofil;
    }

    public void setProfilIdprofil(Profil profilIdprofil) {
        this.profilIdprofil = profilIdprofil;
    }

    @XmlTransient
    public Collection<Notifications> getNotificationsCollection() {
        return notificationsCollection;
    }

    public void setNotificationsCollection(Collection<Notifications> notificationsCollection) {
        this.notificationsCollection = notificationsCollection;
    }

    @XmlTransient
    public Collection<Notifications> getNotificationsCollection1() {
        return notificationsCollection1;
    }

    public void setNotificationsCollection1(Collection<Notifications> notificationsCollection1) {
        this.notificationsCollection1 = notificationsCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idutilisateur != null ? idutilisateur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.idutilisateur == null && other.idutilisateur != null) || (this.idutilisateur != null && !this.idutilisateur.equals(other.idutilisateur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Utilisateur[ idutilisateur=" + idutilisateur + " ]";
    }
//
//    @XmlTransient
//    public Collection<Maitrecommunautaire> getMaitrecommunautaireCollection() {
//        return maitrecommunautaireCollection;
//    }
//
//    public void setMaitrecommunautaireCollection(Collection<Maitrecommunautaire> maitrecommunautaireCollection) {
//        this.maitrecommunautaireCollection = maitrecommunautaireCollection;
//    }

    @XmlTransient
    public Collection<Paiement> getPaiementCollection() {
        return paiementCollection;
    }

    public void setPaiementCollection(Collection<Paiement> paiementCollection) {
        this.paiementCollection = paiementCollection;
    }

    @XmlTransient
    public Collection<Paiement> getPaiementCollection1() {
        return paiementCollection1;
    }

    public void setPaiementCollection1(Collection<Paiement> paiementCollection1) {
        this.paiementCollection1 = paiementCollection1;
    }

    public Boolean getActiver() {
        return activer;
    }

    public void setActiver(Boolean activer) {
        this.activer = activer;
    }

    public String getDateactivation() {
        return dateactivation;
    }

    public void setDateactivation(String dateactivation) {
        this.dateactivation = dateactivation;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public String getDatedesactivation() {
        return datedesactivation;
    }

    public void setDatedesactivation(String datedesactivation) {
        this.datedesactivation = datedesactivation;
    }

    public String getDatemodification() {
        return datemodification;
    }

    public void setDatemodification(String datemodification) {
        this.datemodification = datemodification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEtatconnexion() {
        return etatconnexion;
    }

    public void setEtatconnexion(String etatconnexion) {
        this.etatconnexion = etatconnexion;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotdepass() {
        return motdepass;
    }

    public void setMotdepass(String motdepass) {
        this.motdepass = motdepass;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public String getValideur() {
        return valideur;
    }

    public void setValideur(String valideur) {
        this.valideur = valideur;
    }

}
