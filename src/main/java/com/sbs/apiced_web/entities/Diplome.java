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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author samuel
 */
@Entity
@Table(name = "DIPLOME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Diplome.findAll", query = "SELECT d FROM Diplome d"),
    @NamedQuery(name = "Diplome.findAllNames", query = "SELECT d.libelle FROM Diplome d"),
    @NamedQuery(name = "Diplome.findByIddiplome", query = "SELECT d FROM Diplome d WHERE d.iddiplome = :iddiplome"),
    @NamedQuery(name = "Diplome.findByLibelle", query = "SELECT d FROM Diplome d WHERE d.libelle = :libelle")})
public class Diplome implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iddiplome_generator")
    @SequenceGenerator(name="iddiplome_generator", sequenceName = "iddiplome_seq", allocationSize=1)
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDDIPLOME")
    private BigDecimal iddiplome;
    @Size(max = 255)
    @Column(name = "LIBELLE")
    private String libelle;

    public Diplome() {
    }

    public Diplome(BigDecimal iddiplome) {
        this.iddiplome = iddiplome;
    }

    public BigDecimal getIddiplome() {
        return iddiplome;
    }

    public void setIddiplome(BigDecimal iddiplome) {
        this.iddiplome = iddiplome;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddiplome != null ? iddiplome.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Diplome)) {
            return false;
        }
        Diplome other = (Diplome) object;
        if ((this.iddiplome == null && other.iddiplome != null) || (this.iddiplome != null && !this.iddiplome.equals(other.iddiplome))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Diplome[ iddiplome=" + iddiplome + " ]";
    }
    
}
