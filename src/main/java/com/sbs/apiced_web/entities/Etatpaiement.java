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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author samuel
 */
@Entity
@Table(name = "ETATPAIEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etatpaiement.findAll", query = "SELECT e FROM Etatpaiement e"),
    @NamedQuery(name = "Etatpaiement.findByIdetat", query = "SELECT e FROM Etatpaiement e WHERE e.idetat = :idetat"),
    @NamedQuery(name = "Etatpaiement.findByLibelle", query = "SELECT e FROM Etatpaiement e WHERE e.libelle = :libelle")})
public class Etatpaiement implements Serializable {

    @Size(max = 255)
    @Column(name = "LIBELLE")
    private String libelle;

private static final long serialVersionUID = 1L;
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idEtatPaiement_generator")
    @SequenceGenerator(name="idEtatPaiement_generator", sequenceName = "idEtatPaiement_seq", allocationSize=1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDETAT")
    private BigDecimal idetat;
    @OneToMany(mappedBy = "etatpaiement")
    private Collection<Paiement> paiementCollection;

    public Etatpaiement() {
    }

    public Etatpaiement(BigDecimal idetat) {
        this.idetat = idetat;
    }

    public BigDecimal getIdetat() {
        return idetat;
    }

    public void setIdetat(BigDecimal idetat) {
        this.idetat = idetat;
    }


    @XmlTransient
    public Collection<Paiement> getPaiementCollection() {
        return paiementCollection;
    }

    public void setPaiementCollection(Collection<Paiement> paiementCollection) {
        this.paiementCollection = paiementCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idetat != null ? idetat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etatpaiement)) {
            return false;
        }
        Etatpaiement other = (Etatpaiement) object;
        if ((this.idetat == null && other.idetat != null) || (this.idetat != null && !this.idetat.equals(other.idetat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Etatpaiement[ idetat=" + idetat + " ]";
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
}