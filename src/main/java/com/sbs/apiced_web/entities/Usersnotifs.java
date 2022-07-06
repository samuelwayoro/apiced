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
@Table(name = "USERSNOTIFS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usersnotifs.findAll", query = "SELECT u FROM Usersnotifs u"),
    @NamedQuery(name = "Usersnotifs.findById", query = "SELECT u FROM Usersnotifs u WHERE u.id = :id"),
    @NamedQuery(name = "Usersnotifs.findByIdutilisateur", query = "SELECT u FROM Usersnotifs u WHERE u.idutilisateur = :idutilisateur"),
    @NamedQuery(name = "Usersnotifs.findByIdnotif", query = "SELECT u FROM Usersnotifs u WHERE u.idnotif = :idnotif"),
    @NamedQuery(name = "Usersnotifs.findByEtat", query = "SELECT u FROM Usersnotifs u WHERE u.etat = :etat"),
    @NamedQuery(name = "Usersnotifs.findByDatelecture", query = "SELECT u FROM Usersnotifs u WHERE u.datelecture = :datelecture"),
    @NamedQuery(name = "Usersnotifs.findByDateinsert", query = "SELECT u FROM Usersnotifs u WHERE u.dateinsert = :dateinsert"),
    @NamedQuery(name = "Usersnotifs.findByTitre", query = "SELECT u FROM Usersnotifs u WHERE u.titre = :titre"),
    @NamedQuery(name = "Usersnotifs.findByInformation", query = "SELECT u FROM Usersnotifs u WHERE u.information = :information"),
    @NamedQuery(name = "Usersnotifs.findByValideur", query = "SELECT u FROM Usersnotifs u WHERE u.valideur = :valideur"),
    @NamedQuery(name = "Usersnotifs.findByCreateur", query = "SELECT u FROM Usersnotifs u WHERE u.createur = :createur"),
    @NamedQuery(name = "Usersnotifs.findByTypeusernotif", query = "SELECT u FROM Usersnotifs u WHERE u.typeusernotif = :typeusernotif"),
    @NamedQuery(name = "Usersnotifs.findByBtnvalidemc", query = "SELECT u FROM Usersnotifs u WHERE u.btnvalidemc = :btnvalidemc"),
    @NamedQuery(name = "Usersnotifs.findByBtnvalidepaie", query = "SELECT u FROM Usersnotifs u WHERE u.btnvalidepaie = :btnvalidepaie")})
public class Usersnotifs implements Serializable {

 private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "usersnotifs_seq", allocationSize = 1)
    @Basic(optional = false)
    @Id
    @Column(name = "ID")
    private Integer id;
    @Column(name = "IDUTILISATEUR")
    private BigInteger idutilisateur;
    @Column(name = "IDNOTIF")
    private BigInteger idnotif;
    @Column(name = "ETAT")
    private BigInteger etat;
    @Size(max = 11)
    @Column(name = "DATELECTURE")
    private String datelecture;
    @Size(max = 11)
    @Column(name = "DATEINSERT")
    private String dateinsert;
    @Size(max = 255)
    @Column(name = "TITRE")
    private String titre;
    @Size(max = 255)
    @Column(name = "INFORMATION")
    private String information;
    @Size(max = 255)
    @Column(name = "VALIDEUR")
    private String valideur;
    @Size(max = 255)
    @Column(name = "CREATEUR")
    private String createur;
    @Size(max = 50)
    @Column(name = "TYPEUSERNOTIF")
    private String typeusernotif;
    @Size(max = 30)
    @Column(name = "BTNVALIDEMC")
    private String btnvalidemc;
    @Size(max = 30)
    @Column(name = "BTNVALIDEPAIE")
    private String btnvalidepaie;
    @Column(name = "BTNDETAIL")
    private String btndetail;
    @Column(name = "BTNCORRIGEMC")
    private String btncorrigemc;

    public Usersnotifs() {
    }

    public String getBtncorrigemc() {
        return btncorrigemc;
    }

    public void setBtncorrigemc(String btncorrigemc) {
        this.btncorrigemc = btncorrigemc;
    }

    public String getBtndetail() {
        return btndetail;
    }

    public void setBtndetail(String btndetail) {
        this.btndetail = btndetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
 

    public BigInteger getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(BigInteger idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public BigInteger getIdnotif() {
        return idnotif;
    }

    public void setIdnotif(BigInteger idnotif) {
        this.idnotif = idnotif;
    }

    public BigInteger getEtat() {
        return etat;
    }

    public void setEtat(BigInteger etat) {
        this.etat = etat;
    }

    public String getDatelecture() {
        return datelecture;
    }

    public void setDatelecture(String datelecture) {
        this.datelecture = datelecture;
    }

    public String getDateinsert() {
        return dateinsert;
    }

    public void setDateinsert(String dateinsert) {
        this.dateinsert = dateinsert;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getValideur() {
        return valideur;
    }

    public void setValideur(String valideur) {
        this.valideur = valideur;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public String getTypeusernotif() {
        return typeusernotif;
    }

    public void setTypeusernotif(String typeusernotif) {
        this.typeusernotif = typeusernotif;
    }

    public String getBtnvalidemc() {
        return btnvalidemc;
    }

    public void setBtnvalidemc(String btnvalidemc) {
        this.btnvalidemc = btnvalidemc;
    }

    public String getBtnvalidepaie() {
        return btnvalidepaie;
    }

    public void setBtnvalidepaie(String btnvalidepaie) {
        this.btnvalidepaie = btnvalidepaie;
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
        if (!(object instanceof Usersnotifs)) {
            return false;
        }
        Usersnotifs other = (Usersnotifs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Usersnotifs[ id=" + id + " ]";
    }
    
}
