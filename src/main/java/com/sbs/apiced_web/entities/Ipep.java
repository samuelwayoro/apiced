/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "IPEP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ipep.findAll", query = "SELECT i FROM Ipep i"),
    @NamedQuery(name = "Ipep.findByIdipep", query = "SELECT i FROM Ipep i WHERE i.idipep = :idipep"),
    @NamedQuery(name = "Ipep.findByNom", query = "SELECT i FROM Ipep i WHERE i.nom = :nom"),
    @NamedQuery(name = "Ipep.findByDatecreation", query = "SELECT i FROM Ipep i WHERE i.datecreation = :datecreation"),
    @NamedQuery(name = "Ipep.findByCreateur", query = "SELECT i FROM Ipep i WHERE i.createur = :createur"),
    @NamedQuery(name = "Ipep.findByDatemodif", query = "SELECT i FROM Ipep i WHERE i.datemodif = :datemodif"),
    @NamedQuery(name = "Ipep.findByModificateur", query = "SELECT i FROM Ipep i WHERE i.modificateur = :modificateur")})
public class Ipep implements Serializable {
    private static final long serialVersionUID = 1L;
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idipep_generator")
    @SequenceGenerator(name="idipep_generator", sequenceName = "idipep_seq", allocationSize=1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDIPEP")
    private BigDecimal idipep;
    @Size(max = 50)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 12)
    @Column(name = "DATECREATION")
    private String datecreation;
    @Size(max = 30)
    @Column(name = "CREATEUR")
    private String createur;
    @Size(max = 12)
    @Column(name = "DATEMODIF")
    private String datemodif;
    @Size(max = 25)
    @Column(name = "MODIFICATEUR")
    private String modificateur;
    private BigDecimal iden;
    
    public Ipep() {
    }

    public Ipep(BigDecimal idipep) {
        this.idipep = idipep;
    }

    public BigDecimal getIdipep() {
        return idipep;
    }

    public void setIdipep(BigDecimal idipep) {
        this.idipep = idipep;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public String getDatemodif() {
        return datemodif;
    }

    public void setDatemodif(String datemodif) {
        this.datemodif = datemodif;
    }

    public String getModificateur() {
        return modificateur;
    }

    public void setModificateur(String modificateur) {
        this.modificateur = modificateur;
    }

    public BigDecimal getIden() {
        return iden;
    }

    public void setIden(BigDecimal iden) {
        this.iden = iden;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idipep != null ? idipep.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ipep)) {
            return false;
        }
        Ipep other = (Ipep) object;
        if ((this.idipep == null && other.idipep != null) || (this.idipep != null && !this.idipep.equals(other.idipep))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Ipep[ idipep=" + idipep + " ]";
    }
    
}
