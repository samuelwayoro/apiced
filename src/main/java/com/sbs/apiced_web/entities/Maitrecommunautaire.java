/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author samuel
 */
@Entity
@Table(name = "MAITRECOMMUNAUTAIRE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maitrecommunautaire.findAll", query = "SELECT m FROM Maitrecommunautaire m where m.validationcoordonnateur = :val "),
    @NamedQuery(name = "Maitrecommunautaire.findById", query = "SELECT m FROM Maitrecommunautaire m WHERE m.id = :id"),
    @NamedQuery(name = "Maitrecommunautaire.findByContactdeux", query = "SELECT m FROM Maitrecommunautaire m WHERE m.contactdeux = :contactdeux"),
    @NamedQuery(name = "Maitrecommunautaire.findByContactun", query = "SELECT m FROM Maitrecommunautaire m WHERE m.contactun = :contactun"),
    @NamedQuery(name = "Maitrecommunautaire.findByDatecreationcompte", query = "SELECT m FROM Maitrecommunautaire m WHERE m.datecreationcompte = :datecreationcompte"),
    @NamedQuery(name = "Maitrecommunautaire.findByDatemodifcompte", query = "SELECT m FROM Maitrecommunautaire m WHERE m.datemodifcompte = :datemodifcompte"),
    @NamedQuery(name = "Maitrecommunautaire.findByDatenaissance", query = "SELECT m FROM Maitrecommunautaire m WHERE m.datenaissance = :datenaissance"),
    @NamedQuery(name = "Maitrecommunautaire.findByDateprisefonction", query = "SELECT m FROM Maitrecommunautaire m WHERE m.dateprisefonction = :dateprisefonction"),
    @NamedQuery(name = "Maitrecommunautaire.findByDomicile", query = "SELECT m FROM Maitrecommunautaire m WHERE m.domicile = :domicile"),
    @NamedQuery(name = "Maitrecommunautaire.findByLieudenaissance", query = "SELECT m FROM Maitrecommunautaire m WHERE m.lieudenaissance = :lieudenaissance"),
    @NamedQuery(name = "Maitrecommunautaire.findByMatricule", query = "SELECT m FROM Maitrecommunautaire m WHERE m.matricule = :matricule"),
    @NamedQuery(name = "Maitrecommunautaire.findByNiveauscolaire", query = "SELECT m FROM Maitrecommunautaire m WHERE m.niveauscolaire = :niveauscolaire"),
    @NamedQuery(name = "Maitrecommunautaire.findByNom", query = "SELECT m FROM Maitrecommunautaire m WHERE m.nom = :nom"),
    @NamedQuery(name = "Maitrecommunautaire.findByPrenoms", query = "SELECT m FROM Maitrecommunautaire m WHERE m.prenoms = :prenoms"),
    @NamedQuery(name = "Maitrecommunautaire.findByStatutwallet", query = "SELECT m FROM Maitrecommunautaire m WHERE m.statutwallet = :statutwallet"),
    @NamedQuery(name = "Maitrecommunautaire.findByIdvalideurcompte", query = "SELECT m FROM Maitrecommunautaire m WHERE m.idvalideurcompte = :idvalideurcompte"),
    @NamedQuery(name = "Maitrecommunautaire.findByEtatcomptemc", query = "SELECT m FROM Maitrecommunautaire m WHERE m.etatcomptemc = :etatcomptemc"),
    @NamedQuery(name = "Maitrecommunautaire.findByDateactivationcompte", query = "SELECT m FROM Maitrecommunautaire m WHERE m.dateactivationcompte = :dateactivationcompte"),
    @NamedQuery(name = "Maitrecommunautaire.findByDatedesactivationcompte", query = "SELECT m FROM Maitrecommunautaire m WHERE m.datedesactivationcompte = :datedesactivationcompte"),
    @NamedQuery(name = "Maitrecommunautaire.findByValidationcoordonnateur", query = "SELECT m FROM Maitrecommunautaire m WHERE m.validationcoordonnateur = :validationcoordonnateur"),
    @NamedQuery(name = "Maitrecommunautaire.findByDateretraite", query = "SELECT m FROM Maitrecommunautaire m WHERE m.dateretraite = :dateretraite"),
    @NamedQuery(name = "Maitrecommunautaire.findByDatesuspension", query = "SELECT m FROM Maitrecommunautaire m WHERE m.datesuspension = :datesuspension"),
    @NamedQuery(name = "Maitrecommunautaire.findByGenre", query = "SELECT m FROM Maitrecommunautaire m WHERE m.genre = :genre"),
    @NamedQuery(name = "Maitrecommunautaire.findByDemandesusp", query = "SELECT m FROM Maitrecommunautaire m WHERE m.demandesusp = :demandesusp"),
    @NamedQuery(name = "Maitrecommunautaire.findByMotifsuspension", query = "SELECT m FROM Maitrecommunautaire m WHERE m.motifsuspension = :motifsuspension"),
    @NamedQuery(name = "Maitrecommunautaire.findByNbreenfants", query = "SELECT m FROM Maitrecommunautaire m WHERE m.nbreenfants = :nbreenfants"),
    @NamedQuery(name = "Maitrecommunautaire.findBySitmatrimonial", query = "SELECT m FROM Maitrecommunautaire m WHERE m.sitmatrimonial = :sitmatrimonial"),
    @NamedQuery(name = "Maitrecommunautaire.findByDernierdiplome", query = "SELECT m FROM Maitrecommunautaire m WHERE m.dernierdiplome = :dernierdiplome"),
    @NamedQuery(name = "Maitrecommunautaire.findByClasseaffectee", query = "SELECT m FROM Maitrecommunautaire m WHERE m.classeaffectee = :classeaffectee"),
    @NamedQuery(name = "Maitrecommunautaire.findByOperatortelco", query = "SELECT m FROM Maitrecommunautaire m WHERE m.operatortelco = :operatortelco"),
    @NamedQuery(name = "Maitrecommunautaire.findByNumerointerne", query = "SELECT m FROM Maitrecommunautaire m WHERE m.numerointerne = :numerointerne"),
    @NamedQuery(name = "Maitrecommunautaire.findByEtatretraite", query = "SELECT m FROM Maitrecommunautaire m WHERE m.etatretraite = :etatretraite"),
    @NamedQuery(name = "Maitrecommunautaire.findByMotifrejetvalidation", query = "SELECT m FROM Maitrecommunautaire m WHERE m.motifrejetvalidation = :motifrejetvalidation"),
    @NamedQuery(name = "Maitrecommunautaire.findByDaterejetvalidation", query = "SELECT m FROM Maitrecommunautaire m WHERE m.daterejetvalidation = :daterejetvalidation"),
    @NamedQuery(name = "Maitrecommunautaire.findByRejetvalidation", query = "SELECT m FROM Maitrecommunautaire m WHERE m.rejetvalidation = :rejetvalidation"),
    @NamedQuery(name = "Maitrecommunautaire.findByValeureetatretraite", query = "SELECT m FROM Maitrecommunautaire m WHERE m.valeureetatretraite = :valeureetatretraite"),
    @NamedQuery(name = "Maitrecommunautaire.findByValeurestatutwallet", query = "SELECT m FROM Maitrecommunautaire m WHERE m.valeurestatutwallet = :valeurestatutwallet"),
    @NamedQuery(name = "Maitrecommunautaire.findByStatutcompte", query = "SELECT m FROM Maitrecommunautaire m WHERE m.statutcompte = :statutcompte"),
    @NamedQuery(name = "Maitrecommunautaire.findByTypepieceadministrative", query = "SELECT m FROM Maitrecommunautaire m WHERE m.typepieceadministrative = :typepieceadministrative"),
    @NamedQuery(name = "Maitrecommunautaire.findByNompieceadmninistrative", query = "SELECT m FROM Maitrecommunautaire m WHERE m.nompieceadmninistrative = :nompieceadmninistrative"),
    @NamedQuery(name = "Maitrecommunautaire.findByLangue", query = "SELECT m FROM Maitrecommunautaire m WHERE m.langue = :langue"),
    @NamedQuery(name = "Maitrecommunautaire.findByMilieuResidence", query = "SELECT m FROM Maitrecommunautaire m WHERE m.milieuResidence = :milieuResidence"),
    @NamedQuery(name = "Maitrecommunautaire.findByBailleur", query = "SELECT m FROM Maitrecommunautaire m WHERE m.bailleur = :bailleur"),
    @NamedQuery(name = "Maitrecommunautaire.findByCategoriepro", query = "SELECT m FROM Maitrecommunautaire m WHERE m.categoriepro = :categoriepro"),
    @NamedQuery(name = "Maitrecommunautaire.findByEcole", query = "SELECT m FROM Maitrecommunautaire m WHERE m.ecole = :ecole")})
public class Maitrecommunautaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idMaitrecomm_generator")
    @SequenceGenerator(name = "idMaitrecomm_generator", sequenceName = "idMaitrecomm_seq", allocationSize = 1)
    @Id
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 255)
    @Column(name = "CONTACTDEUX")
    private String contactdeux;
    @Size(max = 255)
    @Column(name = "CONTACTUN")
    private String contactun;
    @Size(max = 255)
    @Column(name = "DATECREATIONCOMPTE")
    private String datecreationcompte;
    @Size(max = 255)
    @Column(name = "DATEMODIFCOMPTE")
    private String datemodifcompte;
    @Size(max = 255)
    @Column(name = "DATENAISSANCE")
    private String datenaissance;
    @Size(max = 255)
    @Column(name = "DATEPRISEFONCTION")
    private String dateprisefonction;
    @Size(max = 255)
    @Column(name = "DOMICILE")
    private String domicile;
    @Size(max = 255)
    @Column(name = "LIEUDENAISSANCE")
    private String lieudenaissance;
    @Size(max = 255)
    @Column(name = "MATRICULE")
    private String matricule;
    @Size(max = 255)
    @Column(name = "NIVEAUSCOLAIRE")
    private String niveauscolaire;
    @Size(max = 255)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 255)
    @Column(name = "PRENOMS")
    private String prenoms;
    @Column(name = "STATUTWALLET")
    private Boolean statutwallet;
    @Column(name = "IDVALIDEURCOMPTE")
    private Long idvalideurcompte;
    @Column(name = "ETATCOMPTEMC")
    private Boolean etatcomptemc;
    @Size(max = 12)
    @Column(name = "DATEACTIVATIONCOMPTE")
    private String dateactivationcompte;
    @Size(max = 12)
    @Column(name = "DATEDESACTIVATIONCOMPTE")
    private String datedesactivationcompte;
    @Column(name = "VALIDATIONCOORDONNATEUR")
    private Boolean validationcoordonnateur;
    @Size(max = 15)
    @Column(name = "DATERETRAITE")
    private String dateretraite;
    @Size(max = 12)
    @Column(name = "DATESUSPENSION")
    private String datesuspension;
    @Size(max = 8)
    @Column(name = "GENRE")
    private String genre;
    @Column(name = "DEMANDESUSP")
    private BigInteger demandesusp;
    @Size(max = 50)
    @Column(name = "MOTIFSUSPENSION")
    private String motifsuspension;
    @Column(name = "NBREENFANTS")
    private BigInteger nbreenfants;
    @Size(max = 30)
    @Column(name = "SITMATRIMONIAL")
    private String sitmatrimonial;
    @Size(max = 255)
    @Column(name = "DERNIERDIPLOME")
    private String dernierdiplome;
    @Size(max = 50)
    @Column(name = "CLASSEAFFECTEE")
    private String classeaffectee;
    @Size(max = 150)
    @Column(name = "OPERATORTELCO")
    private String operatortelco;
    @Size(max = 150)
    @Column(name = "NUMEROINTERNE")
    private String numerointerne;
    @Column(name = "ETATRETRAITE")
    private Boolean etatretraite;
    @Size(max = 255)
    @Column(name = "MOTIFREJETVALIDATION")
    private String motifrejetvalidation;
    @Size(max = 20)
    @Column(name = "DATEREJETVALIDATION")
    private String daterejetvalidation;
    @Column(name = "REJETVALIDATION")
    private Boolean rejetvalidation;
    @Size(max = 50)
    @Column(name = "VALEUREETATRETRAITE")
    private String valeureetatretraite;
    @Size(max = 20)
    @Column(name = "VALEURESTATUTWALLET")
    private String valeurestatutwallet;
    @Size(max = 20)
    @Column(name = "STATUTCOMPTE")
    private String statutcompte;
    @Size(max = 255)
    @Column(name = "TYPEPIECEADMINISTRATIVE")
    private String typepieceadministrative;
    @Size(max = 255)
    @Column(name = "NOMPIECEADMNINISTRATIVE")
    private String nompieceadmninistrative;
    @Size(max = 50)
    @Column(name = "LANGUE")
    private String langue;
    @Size(max = 50)
    @Column(name = "MILIEU_RESIDENCE")
    private String milieuResidence;
    @Size(max = 50)
    @Column(name = "BAILLEUR")
    private String bailleur;
    @Size(max = 100)
    @Column(name = "CATEGORIEPRO")
    private String categoriepro;
    @Size(max = 100)
    @Column(name = "ECOLE")
    private String ecole;
    @Column(name = "TYPE_ECOLE")
    private String type_ecole;
    @Column(name = "MENSUEL")
    private String mensuel;
    @Column(name = "IPEP")
    private String ipep;
    @Column(name = "IDEN")
    private String iden;
    @Column(name = "DREJ")
    private String drej;

    public Maitrecommunautaire() {
    }

    public Maitrecommunautaire(String nom, String prenoms) {
        this.nom = nom;
        this.prenoms = prenoms;
    }

    public Maitrecommunautaire(BigDecimal id) {
        this.id = id;
    }

    public String getIden() {
        return iden;
    }

    public void setIden(String iden) {
        this.iden = iden;
    }

    public String getDrej() {
        return drej;
    }

    public void setDrej(String drej) {
        this.drej = drej;
    }
    
    

    public String getIpep() {
        return ipep;
    }

    public void setIpep(String ipep) {
        this.ipep = ipep;
    }


    public BigDecimal getId() {
        return id;
    }

    public String getType_ecole() {
        return type_ecole;
    }

    public void setType_ecole(String type_ecole) {
        this.type_ecole = type_ecole;
    }

    public String getMensuel() {
        return mensuel;
    }

    public void setMensuel(String mensuel) {
        this.mensuel = mensuel;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getContactdeux() {
        return contactdeux;
    }

    public void setContactdeux(String contactdeux) {
        this.contactdeux = contactdeux;
    }

    public String getContactun() {
        return contactun;
    }

    public void setContactun(String contactun) {
        this.contactun = contactun;
    }

    public String getDatecreationcompte() {
        return datecreationcompte;
    }

    public void setDatecreationcompte(String datecreationcompte) {
        this.datecreationcompte = datecreationcompte;
    }

    public String getDatemodifcompte() {
        return datemodifcompte;
    }

    public void setDatemodifcompte(String datemodifcompte) {
        this.datemodifcompte = datemodifcompte;
    }

    public String getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(String datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getDateprisefonction() {
        return dateprisefonction;
    }

    public void setDateprisefonction(String dateprisefonction) {
        this.dateprisefonction = dateprisefonction;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getLieudenaissance() {
        return lieudenaissance;
    }

    public void setLieudenaissance(String lieudenaissance) {
        this.lieudenaissance = lieudenaissance;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNiveauscolaire() {
        return niveauscolaire;
    }

    public void setNiveauscolaire(String niveauscolaire) {
        this.niveauscolaire = niveauscolaire;
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

    public Boolean getStatutwallet() {
        return statutwallet;
    }

    public void setStatutwallet(Boolean statutwallet) {
        this.statutwallet = statutwallet;
    }

    public Long getIdvalideurcompte() {
        return idvalideurcompte;
    }

    public void setIdvalideurcompte(Long idvalideurcompte) {
        this.idvalideurcompte = idvalideurcompte;
    }

    public Boolean getEtatcomptemc() {
        return etatcomptemc;
    }

    public void setEtatcomptemc(Boolean etatcomptemc) {
        this.etatcomptemc = etatcomptemc;
    }

    public String getDateactivationcompte() {
        return dateactivationcompte;
    }

    public void setDateactivationcompte(String dateactivationcompte) {
        this.dateactivationcompte = dateactivationcompte;
    }

    public String getDatedesactivationcompte() {
        return datedesactivationcompte;
    }

    public void setDatedesactivationcompte(String datedesactivationcompte) {
        this.datedesactivationcompte = datedesactivationcompte;
    }

    public Boolean getValidationcoordonnateur() {
        return validationcoordonnateur;
    }

    public void setValidationcoordonnateur(Boolean validationcoordonnateur) {
        this.validationcoordonnateur = validationcoordonnateur;
    }

    public String getDateretraite() {
        return dateretraite;
    }

    public void setDateretraite(String dateretraite) {
        this.dateretraite = dateretraite;
    }

    public String getDatesuspension() {
        return datesuspension;
    }

    public void setDatesuspension(String datesuspension) {
        this.datesuspension = datesuspension;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public BigInteger getDemandesusp() {
        return demandesusp;
    }

    public void setDemandesusp(BigInteger demandesusp) {
        this.demandesusp = demandesusp;
    }

    public String getMotifsuspension() {
        return motifsuspension;
    }

    public void setMotifsuspension(String motifsuspension) {
        this.motifsuspension = motifsuspension;
    }

    public BigInteger getNbreenfants() {
        return nbreenfants;
    }

    public void setNbreenfants(BigInteger nbreenfants) {
        this.nbreenfants = nbreenfants;
    }

    public String getSitmatrimonial() {
        return sitmatrimonial;
    }

    public void setSitmatrimonial(String sitmatrimonial) {
        this.sitmatrimonial = sitmatrimonial;
    }

    public String getDernierdiplome() {
        return dernierdiplome;
    }

    public void setDernierdiplome(String dernierdiplome) {
        this.dernierdiplome = dernierdiplome;
    }

    public String getClasseaffectee() {
        return classeaffectee;
    }

    public void setClasseaffectee(String classeaffectee) {
        this.classeaffectee = classeaffectee;
    }

    public String getOperatortelco() {
        return operatortelco;
    }

    public void setOperatortelco(String operatortelco) {
        this.operatortelco = operatortelco;
    }

    public String getNumerointerne() {
        return numerointerne;
    }

    public void setNumerointerne(String numerointerne) {
        this.numerointerne = numerointerne;
    }

    public Boolean getEtatretraite() {
        return etatretraite;
    }

    public void setEtatretraite(Boolean etatretraite) {
        this.etatretraite = etatretraite;
    }

    public String getMotifrejetvalidation() {
        return motifrejetvalidation;
    }

    public void setMotifrejetvalidation(String motifrejetvalidation) {
        this.motifrejetvalidation = motifrejetvalidation;
    }

    public String getDaterejetvalidation() {
        return daterejetvalidation;
    }

    public void setDaterejetvalidation(String daterejetvalidation) {
        this.daterejetvalidation = daterejetvalidation;
    }

    public Boolean getRejetvalidation() {
        return rejetvalidation;
    }

    public void setRejetvalidation(Boolean rejetvalidation) {
        this.rejetvalidation = rejetvalidation;
    }

    public String getValeureetatretraite() {
        return valeureetatretraite;
    }

    public void setValeureetatretraite(String valeureetatretraite) {
        this.valeureetatretraite = valeureetatretraite;
    }

    public String getValeurestatutwallet() {
        return valeurestatutwallet;
    }

    public void setValeurestatutwallet(String valeurestatutwallet) {
        this.valeurestatutwallet = valeurestatutwallet;
    }

    public String getStatutcompte() {
        return statutcompte;
    }

    public void setStatutcompte(String statutcompte) {
        this.statutcompte = statutcompte;
    }

    public String getTypepieceadministrative() {
        return typepieceadministrative;
    }

    public void setTypepieceadministrative(String typepieceadministrative) {
        this.typepieceadministrative = typepieceadministrative;
    }

    public String getNompieceadmninistrative() {
        return nompieceadmninistrative;
    }

    public void setNompieceadmninistrative(String nompieceadmninistrative) {
        this.nompieceadmninistrative = nompieceadmninistrative;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getMilieuResidence() {
        return milieuResidence;
    }

    public void setMilieuResidence(String milieuResidence) {
        this.milieuResidence = milieuResidence;
    }

    public String getBailleur() {
        return bailleur;
    }

    public void setBailleur(String bailleur) {
        this.bailleur = bailleur;
    }

    public String getCategoriepro() {
        return categoriepro;
    }

    public void setCategoriepro(String categoriepro) {
        this.categoriepro = categoriepro;
    }

    public String getEcole() {
        return ecole;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maitrecommunautaire)) {
            return false;
        }
        Maitrecommunautaire other = (Maitrecommunautaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Maitrecommunautaire[ id=" + id + " ]";
    }

}
