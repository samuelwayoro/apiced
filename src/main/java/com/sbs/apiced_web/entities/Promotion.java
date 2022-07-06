/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PROMOTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promotion.findAll", query = "SELECT p FROM Promotion p"),
    @NamedQuery(name = "Promotion.findByIdpromo", query = "SELECT p FROM Promotion p WHERE p.idpromo = :idpromo"),
    @NamedQuery(name = "Promotion.findByDateemission", query = "SELECT p FROM Promotion p WHERE p.dateemission = :dateemission"),
    @NamedQuery(name = "Promotion.findByLibelle", query = "SELECT p FROM Promotion p WHERE p.libelle = :libelle"),
    @NamedQuery(name = "Promotion.findByValidationcoordonnateur", query = "SELECT p FROM Promotion p WHERE p.validationcoordonnateur = :validationcoordonnateur")})
public class Promotion implements Serializable {

   private static final long serialVersionUID = 1L;
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idPromotion_generator")
    @SequenceGenerator(name="idPromotion_generator", sequenceName = "idPromotion_seq", allocationSize=1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDPROMO")
    private Integer idpromo;
    @Size(max = 20)
    @Column(name = "DATEEMISSION")
    private String dateemission;
    @Size(max = 255)
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "VALIDATIONCOORDONNATEUR")
    private BigInteger validationcoordonnateur;
    @JoinColumn(name = "IDMC", referencedColumnName = "ID")
    @ManyToOne
    private Maitrecommunautaire idmc;

    public Promotion() {
    }

    public Promotion(Integer idpromo) {
        this.idpromo = idpromo;
    }

    public Promotion(String dateModif, String infos, Maitrecommunautaire mc) {
        dateemission = dateModif;
        libelle = infos;
        idmc = mc;
    }




    public Integer getIdpromo() {
        return idpromo;
    }

    public void setIdpromo(Integer idpromo) {
        this.idpromo = idpromo;
    }

    public String getDateemission() {
        return dateemission;
    }

    public void setDateemission(String dateemission) {
        this.dateemission = dateemission;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigInteger getValidationcoordonnateur() {
        return validationcoordonnateur;
    }

    public void setValidationcoordonnateur(BigInteger validationcoordonnateur) {
        this.validationcoordonnateur = validationcoordonnateur;
    }

    public Maitrecommunautaire getIdmc() {
        return idmc;
    }

    public void setIdmc(Maitrecommunautaire idmc) {
        this.idmc = idmc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpromo != null ? idpromo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promotion)) {
            return false;
        }
        Promotion other = (Promotion) object;
        if ((this.idpromo == null && other.idpromo != null) || (this.idpromo != null && !this.idpromo.equals(other.idpromo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Promotion[ idpromo=" + idpromo + " ]";
    }
    
}
    