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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author samuel
 */
@Entity
@Table(name = "TRANSACTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t"),
    @NamedQuery(name = "Transactions.findByIdtransaction", query = "SELECT t FROM Transactions t WHERE t.idtransaction = :idtransaction"),
    @NamedQuery(name = "Transactions.findByNumerointerne", query = "SELECT t FROM Transactions t WHERE t.numerointerne = :numerointerne"),
    @NamedQuery(name = "Transactions.findByStatutwallet", query = "SELECT t FROM Transactions t WHERE t.statutwallet = :statutwallet"),
    @NamedQuery(name = "Transactions.findByTypecompte", query = "SELECT t FROM Transactions t WHERE t.typecompte = :typecompte"),
    @NamedQuery(name = "Transactions.findByMontantsubside", query = "SELECT t FROM Transactions t WHERE t.montantsubside = :montantsubside"),
    @NamedQuery(name = "Transactions.findByContactmaitre", query = "SELECT t FROM Transactions t WHERE t.contactmaitre = :contactmaitre"),
    @NamedQuery(name = "Transactions.findByLibellepaie", query = "SELECT t FROM Transactions t WHERE t.libellepaie = :libellepaie"),
    @NamedQuery(name = "Transactions.findByResponsecode", query = "SELECT t FROM Transactions t WHERE t.responsecode = :responsecode"),
    @NamedQuery(name = "Transactions.findByResponsemessage", query = "SELECT t FROM Transactions t WHERE t.responsemessage = :responsemessage"),
    @NamedQuery(name = "Transactions.findByDateenvoi", query = "SELECT t FROM Transactions t WHERE t.dateenvoi = :dateenvoi"),
    @NamedQuery(name = "Transactions.findByDatepaiement", query = "SELECT t FROM Transactions t WHERE t.datepaiement = :datepaiement"),
    @NamedQuery(name = "Transactions.findByMoisanneepaie", query = "SELECT t FROM Transactions t WHERE t.moisanneepaie = :moisanneepaie"),
    @NamedQuery(name = "Transactions.findByOperateurs", query = "SELECT t FROM Transactions t WHERE t.operateurs = :operateurs"),
    @NamedQuery(name = "Transactions.findByNommaitre", query = "SELECT t FROM Transactions t WHERE t.nommaitre = :nommaitre"),
    @NamedQuery(name = "Transactions.findByPrenomsmaitre", query = "SELECT t FROM Transactions t WHERE t.prenomsmaitre = :prenomsmaitre"),
    @NamedQuery(name = "Transactions.findByDateenvoiaoperateur", query = "SELECT t FROM Transactions t WHERE t.dateenvoiaoperateur = :dateenvoiaoperateur"),
    @NamedQuery(name = "Transactions.findByDatepaiementdemandee", query = "SELECT t FROM Transactions t WHERE t.datepaiementdemandee = :datepaiementdemandee"),
    @NamedQuery(name = "Transactions.findByDatereceptionclient", query = "SELECT t FROM Transactions t WHERE t.datereceptionclient = :datereceptionclient")})
public class Transactions implements Serializable {

private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idtransaction_generator")
    @SequenceGenerator(name = "idtransaction_generator", sequenceName = "IDTRANSACTION_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDTRANSACTION")
    private BigDecimal idtransaction;
    @Size(max = 255)
    @Column(name = "NUMEROINTERNE")
    private String numerointerne;
    @Column(name = "STATUTWALLET")
    private BigInteger statutwallet;
    @Size(max = 50)
    @Column(name = "TYPECOMPTE")
    private String typecompte;
    @Size(max = 100)
    @Column(name = "MONTANTSUBSIDE")
    private String montantsubside;
    @Size(max = 20)
    @Column(name = "CONTACTMAITRE")
    private String contactmaitre;
    @Size(max = 50)
    @Column(name = "LIBELLEPAIE")
    private String libellepaie;
    @Size(max = 50)
    @Column(name = "RESPONSECODE")
    private String responsecode;
    @Size(max = 100)
    @Column(name = "RESPONSEMESSAGE")
    private String responsemessage;
    @Size(max = 20)
    @Column(name = "DATEENVOI")
    private String dateenvoi;
    @Size(max = 20)
    @Column(name = "DATEPAIEMENT")
    private String datepaiement;
    @Size(max = 20)
    @Column(name = "MOISANNEEPAIE")
    private String moisanneepaie;
    @Size(max = 50)
    @Column(name = "OPERATEURS")
    private String operateurs;
    @Size(max = 50)
    @Column(name = "NOMMAITRE")
    private String nommaitre;
    @Size(max = 100)
    @Column(name = "PRENOMSMAITRE")
    private String prenomsmaitre;
    @Size(max = 20)
    @Column(name = "DATEENVOIAOPERATEUR")
    private String dateenvoiaoperateur;
    @Size(max = 20)
    @Column(name = "DATEPAIEMENTDEMANDEE")
    private String datepaiementdemandee;
    @Size(max = 20)
    @Column(name = "DATERECEPTIONCLIENT")
    private String datereceptionclient;
    @Column(name = "DATELIMITEPAIEMENT")
    private String datelimitepaiement;
    @Column(name = "ETATTRANSACTION")
    private String etattransaction;
    
    private Integer paiementid;

    public Transactions() {
    }

    public String getEtattransaction() {
        return etattransaction;
    }

    public void setEtattransaction(String etattransaction) {
        this.etattransaction = etattransaction;
    }

    
    
    public String getDatelimitepaiement() {
        return datelimitepaiement;
    }

    public void setDatelimitepaiement(String datelimitepaiement) {
        this.datelimitepaiement = datelimitepaiement;
    }
    
    

    public Transactions(BigDecimal idtransaction) {
        this.idtransaction = idtransaction;
    }

    public BigDecimal getIdtransaction() {
        return idtransaction;
    }

    public void setIdtransaction(BigDecimal idtransaction) {
        this.idtransaction = idtransaction;
    }

    public String getNumerointerne() {
        return numerointerne;
    }

    public void setNumerointerne(String numerointerne) {
        this.numerointerne = numerointerne;
    }

    public BigInteger getStatutwallet() {
        return statutwallet;
    }

    public void setStatutwallet(BigInteger statutwallet) {
        this.statutwallet = statutwallet;
    }

    public String getTypecompte() {
        return typecompte;
    }

    public void setTypecompte(String typecompte) {
        this.typecompte = typecompte;
    }

    public String getMontantsubside() {
        return montantsubside;
    }

    public void setMontantsubside(String montantsubside) {
        this.montantsubside = montantsubside;
    }

    public String getContactmaitre() {
        return contactmaitre;
    }

    public void setContactmaitre(String contactmaitre) {
        this.contactmaitre = contactmaitre;
    }

    public String getLibellepaie() {
        return libellepaie;
    }

    public void setLibellepaie(String libellepaie) {
        this.libellepaie = libellepaie;
    }

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public String getResponsemessage() {
        return responsemessage;
    }

    public void setResponsemessage(String responsemessage) {
        this.responsemessage = responsemessage;
    }

    public String getDateenvoi() {
        return dateenvoi;
    }

    public void setDateenvoi(String dateenvoi) {
        this.dateenvoi = dateenvoi;
    }

    public String getDatepaiement() {
        return datepaiement;
    }

    public void setDatepaiement(String datepaiement) {
        this.datepaiement = datepaiement;
    }

    public String getMoisanneepaie() {
        return moisanneepaie;
    }

    public void setMoisanneepaie(String moisanneepaie) {
        this.moisanneepaie = moisanneepaie;
    }

    public String getOperateurs() {
        return operateurs;
    }

    public void setOperateurs(String operateurs) {
        this.operateurs = operateurs;
    }

    public String getNommaitre() {
        return nommaitre;
    }

    public void setNommaitre(String nommaitre) {
        this.nommaitre = nommaitre;
    }

    public String getPrenomsmaitre() {
        return prenomsmaitre;
    }

    public void setPrenomsmaitre(String prenomsmaitre) {
        this.prenomsmaitre = prenomsmaitre;
    }

    public String getDateenvoiaoperateur() {
        return dateenvoiaoperateur;
    }

    public void setDateenvoiaoperateur(String dateenvoiaoperateur) {
        this.dateenvoiaoperateur = dateenvoiaoperateur;
    }

    public String getDatepaiementdemandee() {
        return datepaiementdemandee;
    }

    public void setDatepaiementdemandee(String datepaiementdemandee) {
        this.datepaiementdemandee = datepaiementdemandee;
    }

    public String getDatereceptionclient() {
        return datereceptionclient;
    }

    public void setDatereceptionclient(String datereceptionclient) {
        this.datereceptionclient = datereceptionclient;
    }

    public Integer getPaiementid() {
        return paiementid;
    }

    public void setPaiementid(Integer paiementid) {
        this.paiementid = paiementid;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransaction != null ? idtransaction.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.idtransaction == null && other.idtransaction != null) || (this.idtransaction != null && !this.idtransaction.equals(other.idtransaction))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Transactions[ idtransaction=" + idtransaction + " ]";
    }
    
}
