/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author samuel
 */
@Entity
@Table(name = "PAIEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paiement.findAll", query = "SELECT p FROM Paiement p"),
    @NamedQuery(name = "Paiement.findByIdpaiement", query = "SELECT p FROM Paiement p WHERE p.idpaiement = :idpaiement"),
    @NamedQuery(name = "Paiement.findByDatepaiement", query = "SELECT p FROM Paiement p WHERE p.datepaiement = :datepaiement"),
    @NamedQuery(name = "Paiement.findByDetails", query = "SELECT p FROM Paiement p WHERE p.details = :details"),
    @NamedQuery(name = "Paiement.findByMontanttotal", query = "SELECT p FROM Paiement p WHERE p.montanttotal = :montanttotal"),
    @NamedQuery(name = "Paiement.findByMois", query = "SELECT p FROM Paiement p WHERE p.mois = :mois"),
    @NamedQuery(name = "Paiement.findByLibelle", query = "SELECT p FROM Paiement p WHERE p.libelle = :libelle"),
    @NamedQuery(name = "Paiement.findByValidationcoordonnateur", query = "SELECT p FROM Paiement p WHERE p.validationcoordonnateur = :validationcoordonnateur"),
    @NamedQuery(name = "Paiement.findByDateenvoiaoperateur", query = "SELECT p FROM Paiement p WHERE p.dateenvoiaoperateur = :dateenvoiaoperateur"),
    @NamedQuery(name = "Paiement.findByDatesaisiepaiement", query = "SELECT p FROM Paiement p WHERE p.datesaisiepaiement = :datesaisiepaiement"),
    @NamedQuery(name = "Paiement.findByDatepaiementrecubymc", query = "SELECT p FROM Paiement p WHERE p.datepaiementrecubymc = :datepaiementrecubymc"),
    @NamedQuery(name = "Paiement.findByDatereceptionbyop", query = "SELECT p FROM Paiement p WHERE p.datereceptionbyop = :datereceptionbyop"),
    @NamedQuery(name = "Paiement.findByMontantrestant", query = "SELECT p FROM Paiement p WHERE p.montantrestant = :montantrestant"),
    @NamedQuery(name = "Paiement.findByOperateurmobile", query = "SELECT p FROM Paiement p WHERE p.operateurmobile = :operateurmobile"),
    @NamedQuery(name = "Paiement.findByDatemodification", query = "SELECT p FROM Paiement p WHERE p.datemodification = :datemodification"),
    @NamedQuery(name = "Paiement.findByDatevalidationcoordo", query = "SELECT p FROM Paiement p WHERE p.datevalidationcoordo = :datevalidationcoordo"),
    @NamedQuery(name = "Paiement.findByMotifrejetvalidationcoordo", query = "SELECT p FROM Paiement p WHERE p.motifrejetvalidationcoordo = :motifrejetvalidationcoordo"),
    @NamedQuery(name = "Paiement.findByEtatenvoiop", query = "SELECT p FROM Paiement p WHERE p.etatenvoiop = :etatenvoiop")})
public class Paiement implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idPaiement_generator")
    @SequenceGenerator(name = "idPaiement_generator", sequenceName = "idPaiement_seq", allocationSize = 1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDPAIEMENT")
    private Integer idpaiement;
    @Size(max = 255)
    @Column(name = "DATEPAIEMENT")
    private String datepaiement;
    @Size(max = 255)
    @Column(name = "DETAILS")
    private String details;
    @Column(name = "MONTANTTOTAL")
    private BigInteger montanttotal;
    @Size(max = 50)
    @Column(name = "MOIS")
    private String mois;
    @Size(max = 100)
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "VALIDATIONCOORDONNATEUR")
    private Boolean validationcoordonnateur;
    @Size(max = 15)
    @Column(name = "DATEENVOIAOPERATEUR")
    private String dateenvoiaoperateur;
    @Size(max = 15)
    @Column(name = "DATESAISIEPAIEMENT")
    private String datesaisiepaiement;
    @Size(max = 12)
    @Column(name = "DATEPAIEMENTRECUBYMC")
    private String datepaiementrecubymc;
    @Size(max = 12)
    @Column(name = "DATERECEPTIONBYOP")
    private String datereceptionbyop;
    @Size(max = 100)
    @Column(name = "MONTANTRESTANT")
    private String montantrestant;
    @Size(max = 100)
    @Column(name = "OPERATEURMOBILE")
    private String operateurmobile;
    @Size(max = 250)
    @Column(name = "DATEMODIFICATION")
    private String datemodification;
    @Size(max = 255)
    @Column(name = "DATEVALIDATIONCOORDO")
    private String datevalidationcoordo;
    @Size(max = 255)
    @Column(name = "MOTIFREJETVALIDATIONCOORDO")
    private String motifrejetvalidationcoordo;
    @Column(name = "ETATENVOIOP")
    private Boolean etatenvoiop;
    @Column(name = "ENVOYEURAOP")
    private String envoyeuraop;
    @JoinColumn(name = "ETATPAIEMENT", referencedColumnName = "IDETAT")
    @ManyToOne
    private Etatpaiement etatpaiement;
    @JoinColumn(name = "VALIDEUR", referencedColumnName = "IDUTILISATEUR")
    @ManyToOne
    private Utilisateur valideur;
    @JoinColumn(name = "EMETEUR", referencedColumnName = "IDUTILISATEUR")
    @ManyToOne
    private Utilisateur emeteur;

    public Paiement() {
    }

    public String getEnvoyeuraop() {
        return envoyeuraop;
    }

    public void setEnvoyeuraop(String envoyeuraop) {
        this.envoyeuraop = envoyeuraop;
    }

    
    
    public Integer getIdpaiement() {
        return idpaiement;
    }

    public void setIdpaiement(Integer idpaiement) {
        this.idpaiement = idpaiement;
    }

  
    public String getDatepaiement() {
        return datepaiement;
    }

    public void setDatepaiement(String datepaiement) {
        this.datepaiement = datepaiement;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigInteger getMontanttotal() {
        return montanttotal;
    }

    public void setMontanttotal(BigInteger montanttotal) {
        this.montanttotal = montanttotal;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean getValidationcoordonnateur() {
        return validationcoordonnateur;
    }

    public void setValidationcoordonnateur(Boolean validationcoordonnateur) {
        this.validationcoordonnateur = validationcoordonnateur;
    }

    public String getDateenvoiaoperateur() {
        return dateenvoiaoperateur;
    }

    public void setDateenvoiaoperateur(String dateenvoiaoperateur) {
        this.dateenvoiaoperateur = dateenvoiaoperateur;
    }

    public String getDatesaisiepaiement() {
        return datesaisiepaiement;
    }

    public void setDatesaisiepaiement(String datesaisiepaiement) {
        this.datesaisiepaiement = datesaisiepaiement;
    }

    public String getDatepaiementrecubymc() {
        return datepaiementrecubymc;
    }

    public void setDatepaiementrecubymc(String datepaiementrecubymc) {
        this.datepaiementrecubymc = datepaiementrecubymc;
    }

    public String getDatereceptionbyop() {
        return datereceptionbyop;
    }

    public void setDatereceptionbyop(String datereceptionbyop) {
        this.datereceptionbyop = datereceptionbyop;
    }

    public String getMontantrestant() {
        return montantrestant;
    }

    public void setMontantrestant(String montantrestant) {
        this.montantrestant = montantrestant;
    }

    public String getOperateurmobile() {
        return operateurmobile;
    }

    public void setOperateurmobile(String operateurmobile) {
        this.operateurmobile = operateurmobile;
    }

    public String getDatemodification() {
        return datemodification;
    }

    public void setDatemodification(String datemodification) {
        this.datemodification = datemodification;
    }

    public String getDatevalidationcoordo() {
        return datevalidationcoordo;
    }

    public void setDatevalidationcoordo(String datevalidationcoordo) {
        this.datevalidationcoordo = datevalidationcoordo;
    }

    public String getMotifrejetvalidationcoordo() {
        return motifrejetvalidationcoordo;
    }

    public void setMotifrejetvalidationcoordo(String motifrejetvalidationcoordo) {
        this.motifrejetvalidationcoordo = motifrejetvalidationcoordo;
    }

    public Boolean getEtatenvoiop() {
        return etatenvoiop;
    }

    public void setEtatenvoiop(Boolean etatenvoiop) {
        this.etatenvoiop = etatenvoiop;
    }

    public Etatpaiement getEtatpaiement() {
        return etatpaiement;
    }

    public void setEtatpaiement(Etatpaiement etatpaiement) {
        this.etatpaiement = etatpaiement;
    }

    public Utilisateur getValideur() {
        return valideur;
    }

    public void setValideur(Utilisateur valideur) {
        this.valideur = valideur;
    }

    public Utilisateur getEmeteur() {
        return emeteur;
    }

    public void setEmeteur(Utilisateur emeteur) {
        this.emeteur = emeteur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpaiement != null ? idpaiement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Paiement)) {
            return false;
        }
        Paiement other = (Paiement) object;
        if ((this.idpaiement == null && other.idpaiement != null) || (this.idpaiement != null && !this.idpaiement.equals(other.idpaiement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Paiement[ idpaiement=" + idpaiement + " ]";
    }
    
}
