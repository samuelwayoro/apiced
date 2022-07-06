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
@Table(name = "ETABLISSEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etablissement.findAll", query = "SELECT e FROM Etablissement e"),
    @NamedQuery(name = "Etablissement.findByIdetablissement", query = "SELECT e FROM Etablissement e WHERE e.idetablissement = :idetablissement"),
    @NamedQuery(name = "Etablissement.findByInformations", query = "SELECT e FROM Etablissement e WHERE e.informations = :informations"),
    @NamedQuery(name = "Etablissement.findByNom", query = "SELECT e FROM Etablissement e WHERE e.nom = :nom")})
public class Etablissement implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idEtablissement_generator")
    @SequenceGenerator(name = "idEtablissement_generator", sequenceName = "idEtablissement_seq", allocationSize = 1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDETABLISSEMENT")
    private BigDecimal idetablissement;
    @Size(max = 255)
    @Column(name = "INFORMATIONS")
    private String informations;
    @Size(max = 255)
    @Column(name = "NOM")
    private String nom;
    private BigDecimal ipep;
    @Column(name = "TYPEECOLE")
    private String typeecole;
    @Column(name = "NOMIPEP")
    private String nomipep;
    @Column(name = "NOMIDEN")
    private String nomiden;
    @Column(name = "NOMDREJ")
    private String nomdrej;

    public Etablissement() {
    }

    public Etablissement(BigDecimal idetablissement) {
        this.idetablissement = idetablissement;
    }

    public BigDecimal getIdetablissement() {
        return idetablissement;
    }

    public void setIdetablissement(BigDecimal idetablissement) {
        this.idetablissement = idetablissement;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idetablissement != null ? idetablissement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etablissement)) {
            return false;
        }
        Etablissement other = (Etablissement) object;
        if ((this.idetablissement == null && other.idetablissement != null) || (this.idetablissement != null && !this.idetablissement.equals(other.idetablissement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Etablissement[ idetablissement=" + idetablissement + " ]";
    }

    public String getNomipep() {
        return nomipep;
    }

    public void setNomipep(String nomipep) {
        this.nomipep = nomipep;
    }

    public String getNomiden() {
        return nomiden;
    }

    public void setNomiden(String nomiden) {
        this.nomiden = nomiden;
    }

    public String getNomdrej() {
        return nomdrej;
    }

    public void setNomdrej(String nomdrej) {
        this.nomdrej = nomdrej;
    }
    
    

    public String getInformations() {
        return informations;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

//    public Ipep getIpep() {
//        return ipep;
//    }
//
//    public void setIpep(Ipep ipep) {
//        this.ipep = ipep;
//    }

    public BigDecimal getIpep() {
        return ipep;
    }

    public void setIpep(BigDecimal ipep) {
        this.ipep = ipep;
    }

    public String getTypeecole() {
        return typeecole;
    }

    public void setTypeecole(String typeecole) {
        this.typeecole = typeecole;
    }



    public String getMilieuResidence() {
        return typeecole;
    }

    public void setMilieuResidence(String typeecole) {
        this.typeecole = typeecole;
    }
    
    

}
