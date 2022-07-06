/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "IDEN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Iden.findAll", query = "SELECT i FROM Iden i"),
    @NamedQuery(name = "Iden.findByIdiden", query = "SELECT i FROM Iden i WHERE i.ididen = :ididen"),
    @NamedQuery(name = "Iden.findByNom", query = "SELECT i FROM Iden i WHERE i.nom = :nom"),
    @NamedQuery(name = "Iden.findByDatecreation", query = "SELECT i FROM Iden i WHERE i.datecreation = :datecreation"),
    @NamedQuery(name = "Iden.findByCreateur", query = "SELECT i FROM Iden i WHERE i.createur = :createur"),
    @NamedQuery(name = "Iden.findByDatemodif", query = "SELECT i FROM Iden i WHERE i.datemodif = :datemodif"),
    @NamedQuery(name = "Iden.findByModificateur", query = "SELECT i FROM Iden i WHERE i.modificateur = :modificateur")})
public class Iden implements Serializable {
    private static final long serialVersionUID = 1L;
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ididen_generator")
    @SequenceGenerator(name="ididen_generator", sequenceName = "ididen_seq", allocationSize=1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDIDEN")
    private BigDecimal ididen;
    @Size(max = 50)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 12)
    @Column(name = "DATECREATION")
    private String datecreation;
    @Size(max = 80)
    @Column(name = "CREATEUR")
    private String createur;
    @Size(max = 12)
    @Column(name = "DATEMODIF")
    private String datemodif;
    @Size(max = 50)
    @Column(name = "MODIFICATEUR")
    private String modificateur;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iden")
//    private Collection<Ipep> ipepCollection;
//    @JoinColumn(name = "DREJ", referencedColumnName = "IDDREN")
//    @ManyToOne(optional = false)
//    private Drej drej;
    private BigDecimal drej;

    public Iden() {
    }

    public Iden(BigDecimal ididen) {
        this.ididen = ididen;
    }

    public BigDecimal getIdiden() {
        return ididen;
    }

    public void setIdiden(BigDecimal ididen) {
        this.ididen = ididen;
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

    public BigDecimal getDrej() {
        return drej;
    }

    public void setDrej(BigDecimal drej) {
        this.drej = drej;
    }
    
    

//    @XmlTransient
//    public Collection<Ipep> getIpepCollection() {
//        return ipepCollection;
//    }
//
//    public void setIpepCollection(Collection<Ipep> ipepCollection) {
//        this.ipepCollection = ipepCollection;
//    }
//
//    public Drej getDren() {
//        return drej;
//    }
//
//    public void setDren(Drej drej) {
//        this.drej = drej;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ididen != null ? ididen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iden)) {
            return false;
        }
        Iden other = (Iden) object;
        if ((this.ididen == null && other.ididen != null) || (this.ididen != null && !this.ididen.equals(other.ididen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Iden[ ididen=" + ididen + " ]";
    }
    
}
