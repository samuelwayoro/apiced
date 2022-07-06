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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author samuel
 */
@Entity
@Table(name = "CLASSEAFFECTEE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Classeaffectee.findAll", query = "SELECT c FROM Classeaffectee c"),
    @NamedQuery(name = "Classeaffectee.allNames", query="SELECT c.nomclasse FROM Classeaffectee c" ),
    @NamedQuery(name = "Classeaffectee.findByIdclasse", query = "SELECT c FROM Classeaffectee c WHERE c.idclasse = :idclasse"),
    @NamedQuery(name = "Classeaffectee.findByNomclasse", query = "SELECT c FROM Classeaffectee c WHERE c.nomclasse = :nomclasse")})
public class Classeaffectee implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCLASSE")
    private BigDecimal idclasse;
    @Size(max = 50)
    @Column(name = "NOMCLASSE")
    private String nomclasse;

    public Classeaffectee() {
    }

    public Classeaffectee(BigDecimal idclasse) {
        this.idclasse = idclasse;
    }

    public BigDecimal getIdclasse() {
        return idclasse;
    }

    public void setIdclasse(BigDecimal idclasse) {
        this.idclasse = idclasse;
    }

    public String getNomclasse() {
        return nomclasse;
    }

    public void setNomclasse(String nomclasse) {
        this.nomclasse = nomclasse;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idclasse != null ? idclasse.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Classeaffectee)) {
            return false;
        }
        Classeaffectee other = (Classeaffectee) object;
        if ((this.idclasse == null && other.idclasse != null) || (this.idclasse != null && !this.idclasse.equals(other.idclasse))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Classeaffectee[ idclasse=" + idclasse + " ]";
    }
    
}
