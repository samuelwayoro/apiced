/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.io.Serializable;
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
@Table(name = "OPERATEUR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operateur.findAll", query = "SELECT o FROM Operateur o"),
    @NamedQuery(name = "Operateur.findByIdoperateur", query = "SELECT o FROM Operateur o WHERE o.idoperateur = :idoperateur"),
    @NamedQuery(name = "Operateur.findByLogo", query = "SELECT o FROM Operateur o WHERE o.logo = :logo"),
    @NamedQuery(name = "Operateur.findByNom", query = "SELECT o FROM Operateur o WHERE o.nom = :nom"),
    @NamedQuery(name = "Operateur.findByActivationabonnement", query = "SELECT o FROM Operateur o WHERE o.activationabonnement = :activationabonnement"),
    @NamedQuery(name = "Operateur.findByActivationpaiements", query = "SELECT o FROM Operateur o WHERE o.activationpaiements = :activationpaiements")})
public class Operateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idOperateur_generator")
    @SequenceGenerator(name="idOperateur_generator", sequenceName = "idOperateur_seq", allocationSize=1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDOPERATEUR")
    private Integer idoperateur;
    @Size(max = 255)
    @Column(name = "LOGO")
    private String logo;
    @Size(max = 255)
    @Column(name = "NOM")
    private String nom;
    @Column(name = "ACTIVATIONABONNEMENT")
    private Boolean activationabonnement;
    @Column(name = "ACTIVATIONPAIEMENTS")
    private Boolean activationpaiements;
    @Column(name = "DATEMODIF")
    private String datemodif;

    public Operateur() {
    }

    public Operateur(Integer idoperateur) {
        this.idoperateur = idoperateur;
    }

    public Integer getIdoperateur() {
        return idoperateur;
    }

    public void setIdoperateur(Integer idoperateur) {
        this.idoperateur = idoperateur;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getActivationabonnement() {
        return activationabonnement;
    }

    public void setActivationabonnement(Boolean activationabonnement) {
        this.activationabonnement = activationabonnement;
    }

    public Boolean getActivationpaiements() {
        return activationpaiements;
    }

    public void setActivationpaiements(Boolean activationpaiements) {
        this.activationpaiements = activationpaiements;
    }

    public String getDatemodif() {
        return datemodif;
    }

    public void setDatemodif(String datemodif) {
        this.datemodif = datemodif;
    }


    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idoperateur != null ? idoperateur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operateur)) {
            return false;
        }
        Operateur other = (Operateur) object;
        if ((this.idoperateur == null && other.idoperateur != null) || (this.idoperateur != null && !this.idoperateur.equals(other.idoperateur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return idoperateur.toString() ;
        return nom;
    }

}
