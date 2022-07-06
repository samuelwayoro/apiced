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
@Table(name = "DREJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Drej.findAll", query = "SELECT d FROM Drej d"),
    @NamedQuery(name = "Drej.findByIddren", query = "SELECT d FROM Drej d WHERE d.iddren = :iddren"),
    @NamedQuery(name = "Drej.findByNomdren", query = "SELECT d FROM Drej d WHERE d.nomdrej = :nomdrej"),
    @NamedQuery(name = "Drej.findByDatecreation", query = "SELECT d FROM Drej d WHERE d.datecreation = :datecreation"),
    @NamedQuery(name = "Drej.findByCreateur", query = "SELECT d FROM Drej d WHERE d.createur = :createur"),
    @NamedQuery(name = "Drej.findByDatemodif", query = "SELECT d FROM Drej d WHERE d.datemodif = :datemodif"),
    @NamedQuery(name = "Drej.findByModificateur", query = "SELECT d FROM Drej d WHERE d.modificateur = :modificateur")})
public class Drej implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iddren_generator")
    @SequenceGenerator(name="iddren_generator", sequenceName = "iddren_seq", allocationSize=1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDDREN")
    private BigDecimal iddren;
    @Size(max = 80)
    @Column(name = "NOMDREJ")
    private String nomdrej;
    @Size(max = 12)
    @Column(name = "DATECREATION")
    private String datecreation;
    @Size(max = 20)
    @Column(name = "CREATEUR")
    private String createur;
    @Size(max = 12)
    @Column(name = "DATEMODIF")
    private String datemodif;
    @Size(max = 12)
    @Column(name = "MODIFICATEUR")
    private String modificateur;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "drej")
//    private Collection<Iden> idenCollection;

    public Drej() {
    }

    public Drej(BigDecimal iddren) {
        this.iddren = iddren;
    }

    public BigDecimal getIddren() {
        return iddren;
    }

    public void setIddren(BigDecimal iddren) {
        this.iddren = iddren;
    }

    public String getNomdren() {
        return nomdrej;
    }

    public void setNomdren(String nomdrej) {
        this.nomdrej = nomdrej;
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

//    @XmlTransient
//    public Collection<Iden> getIdenCollection() {
//        return idenCollection;
//    }
//
//    public void setIdenCollection(Collection<Iden> idenCollection) {
//        this.idenCollection = idenCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddren != null ? iddren.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Drej)) {
            return false;
        }
        Drej other = (Drej) object;
        if ((this.iddren == null && other.iddren != null) || (this.iddren != null && !this.iddren.equals(other.iddren))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Drej[ iddren=" + iddren + " ]";
    }
    
}
