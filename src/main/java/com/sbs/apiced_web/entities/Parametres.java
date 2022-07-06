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
import javax.persistence.TableGenerator;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author samuel
 */
@Entity
@Table(name = "PARAMETRES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametres.findAll", query = "SELECT p FROM Parametres p"),
    @NamedQuery(name = "Parametres.findByIdparametre", query = "SELECT p FROM Parametres p WHERE p.idparametre = :idparametre"),
    @NamedQuery(name = "Parametres.findByLibelleparam", query = "SELECT p FROM Parametres p WHERE p.libelleparam = :libelleparam"),
    @NamedQuery(name = "Parametres.findByValeur", query = "SELECT p FROM Parametres p WHERE p.valeur = :valeur"),
    @NamedQuery(name = "Parametres.findByTypeparam", query = "SELECT p FROM Parametres p WHERE p.typeparam = :typeparam")})
public class Parametres implements Serializable {

    private static final long serialVersionUID = 1L;
 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idParametres_generator")
    @SequenceGenerator(name="idParametres_generator", sequenceName = "idParametres_seq", allocationSize=1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDPARAMETRE")
    private BigDecimal idparametre;
    @Size(max = 255)
    @Column(name = "LIBELLEPARAM")
    private String libelleparam;
    @Size(max = 255)
    @Column(name = "VALEUR")
    private String valeur;
    @Size(max = 100)
    @Column(name = "TYPEPARAM")
    private String typeparam;

    public Parametres() {
    }

    public Parametres(BigDecimal idparametre) {
        this.idparametre = idparametre;
    }

    public BigDecimal getIdparametre() {
        return idparametre;
    }

    public void setIdparametre(BigDecimal idparametre) {
        this.idparametre = idparametre;
    }

    public String getLibelleparam() {
        return libelleparam;
    }

    public void setLibelleparam(String libelleparam) {
        this.libelleparam = libelleparam;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getTypeparam() {
        return typeparam;
    }

    public void setTypeparam(String typeparam) {
        this.typeparam = typeparam;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idparametre != null ? idparametre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametres)) {
            return false;
        }
        Parametres other = (Parametres) object;
        if ((this.idparametre == null && other.idparametre != null) || (this.idparametre != null && !this.idparametre.equals(other.idparametre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Parametres[ idparametre=" + idparametre + " ]";
    }

}
