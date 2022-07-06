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
@Table(name = "CATEGORIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorie.findAll", query = "SELECT c FROM Categorie c"),
    @NamedQuery(name = "Categorie.findByIdcategorie", query = "SELECT c FROM Categorie c WHERE c.idcategorie = :idcategorie"),
    @NamedQuery(name = "Categorie.findByDatecreation", query = "SELECT c FROM Categorie c WHERE c.datecreation = :datecreation"),
    @NamedQuery(name = "Categorie.findByDatemodification", query = "SELECT c FROM Categorie c WHERE c.datemodification = :datemodification"),
    @NamedQuery(name = "Categorie.findByLibelle", query = "SELECT c FROM Categorie c WHERE c.libelle = :libelle"),
    @NamedQuery(name = "Categorie.findByMontantsubside", query = "SELECT c FROM Categorie c WHERE c.montantsubside = :montantsubside")})
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idCategorie_generator")
    @SequenceGenerator(name = "idCategorie_generator", sequenceName = "idCategorie_seq", allocationSize = 1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDCATEGORIE")
    private Integer idcategorie;
    @Size(max = 255)
    @Column(name = "DATECREATION")
    private String datecreation;
    @Size(max = 255)
    @Column(name = "DATEMODIFICATION")
    private String datemodification;
    @Size(max = 255)
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "MONTANTSUBSIDE")
    private BigInteger montantsubside;
    @Size(max = 100)
    @Column(name = "BAILLEUR")
    private String bailleur;

    public Categorie() {
    }

    public Categorie(Integer idcategorie) {
        this.idcategorie = idcategorie;
    }

    public Integer getIdcategorie() {
        return idcategorie;
    }

    public void setIdcategorie(Integer idcategorie) {
        this.idcategorie = idcategorie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcategorie != null ? idcategorie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categorie)) {
            return false;
        }
        Categorie other = (Categorie) object;
        if ((this.idcategorie == null && other.idcategorie != null) || (this.idcategorie != null && !this.idcategorie.equals(other.idcategorie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idcategorie.toString();
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public String getDatemodification() {
        return datemodification;
    }

    public void setDatemodification(String datemodification) {
        this.datemodification = datemodification;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigInteger getMontantsubside() {
        return montantsubside;
    }

    public void setMontantsubside(BigInteger montantsubside) {
        this.montantsubside = montantsubside;
    }

    public String getBailleur() {
        return bailleur;
    }

    public void setBailleur(String bailleur) {
        this.bailleur = bailleur;
    }
    
    

}
