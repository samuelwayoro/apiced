/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
@Table(name = "BAILLEUR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bailleur.findAll", query = "SELECT b FROM Bailleur b"),
    @NamedQuery(name = "Bailleur.findByIdbailleur", query = "SELECT b FROM Bailleur b WHERE b.idbailleur = :idbailleur"),
    @NamedQuery(name = "Bailleur.findByNom", query = "SELECT b FROM Bailleur b WHERE b.nom = :nom"),
    @NamedQuery(name = "Bailleur.findByDateinsertion", query = "SELECT b FROM Bailleur b WHERE b.dateinsertion = :dateinsertion"),
    @NamedQuery(name = "Bailleur.findByDatemodification", query = "SELECT b FROM Bailleur b WHERE b.datemodification = :datemodification"),
    @NamedQuery(name = "Bailleur.findByCreateur", query = "SELECT b FROM Bailleur b WHERE b.createur = :createur"),
    @NamedQuery(name = "Bailleur.findByModificateur", query = "SELECT b FROM Bailleur b WHERE b.modificateur = :modificateur"),
    @NamedQuery(name = "Bailleur.findByInfos", query = "SELECT b FROM Bailleur b WHERE b.infos = :infos")})
public class Bailleur implements Serializable {
private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idBailleur_generator")
    @SequenceGenerator(name="idBailleur_generator", sequenceName = "idBailleur_seq", allocationSize=1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDBAILLEUR")
    private BigDecimal idbailleur;
    @Size(max = 50)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 20)
    @Column(name = "DATEINSERTION")
    private String dateinsertion;
    @Size(max = 20)
    @Column(name = "DATEMODIFICATION")
    private String datemodification;
    @Size(max = 50)
    @Column(name = "CREATEUR")
    private String createur;
    @Size(max = 50)
    @Column(name = "MODIFICATEUR")
    private String modificateur;
    @Size(max = 255)
    @Column(name = "INFOS")
    private String infos;
    @Column(name = "LISTECATEGORIES")
    private String listeCategories;

    public Bailleur() {
    }

    public Bailleur(BigDecimal idbailleur) {
        this.idbailleur = idbailleur;
    }

    public BigDecimal getIdbailleur() {
        return idbailleur;
    }

    public void setIdbailleur(BigDecimal idbailleur) {
        this.idbailleur = idbailleur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateinsertion() {
        return dateinsertion;
    }

    public void setDateinsertion(String dateinsertion) {
        this.dateinsertion = dateinsertion;
    }

    public String getDatemodification() {
        return datemodification;
    }

    public void setDatemodification(String datemodification) {
        this.datemodification = datemodification;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public String getModificateur() {
        return modificateur;
    }

    public void setModificateur(String modificateur) {
        this.modificateur = modificateur;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public String getListeCategories() {
        return listeCategories;
    }

    public void setListeCategories(String listeCategories) {
        this.listeCategories = listeCategories;
    }

  

  
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbailleur != null ? idbailleur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bailleur)) {
            return false;
        }
        Bailleur other = (Bailleur) object;
        if ((this.idbailleur == null && other.idbailleur != null) || (this.idbailleur != null && !this.idbailleur.equals(other.idbailleur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  idbailleur.toString() ;
    }
    
}
