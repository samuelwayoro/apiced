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
@Table(name = "TYPEPIECEADMINISTRATIVE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Typepieceadministrative.findAll", query = "SELECT t FROM Typepieceadministrative t"),
    @NamedQuery(name = "Typepieceadministrative.findByIdpiece", query = "SELECT t FROM Typepieceadministrative t WHERE t.idpiece = :idpiece"),
    @NamedQuery(name = "Typepieceadministrative.findByLibellepiece", query = "SELECT t FROM Typepieceadministrative t WHERE t.libellepiece = :libellepiece")})
public class Typepieceadministrative implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPIECE")
    private BigDecimal idpiece;
    @Size(max = 150)
    @Column(name = "LIBELLEPIECE")
    private String libellepiece;

    public Typepieceadministrative() {
    }

    public Typepieceadministrative(BigDecimal idpiece) {
        this.idpiece = idpiece;
    }

    public BigDecimal getIdpiece() {
        return idpiece;
    }

    public void setIdpiece(BigDecimal idpiece) {
        this.idpiece = idpiece;
    }

    public String getLibellepiece() {
        return libellepiece;
    }

    public void setLibellepiece(String libellepiece) {
        this.libellepiece = libellepiece;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpiece != null ? idpiece.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Typepieceadministrative)) {
            return false;
        }
        Typepieceadministrative other = (Typepieceadministrative) object;
        if ((this.idpiece == null && other.idpiece != null) || (this.idpiece != null && !this.idpiece.equals(other.idpiece))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Typepieceadministrative[ idpiece=" + idpiece + " ]";
    }
    
}
