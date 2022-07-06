/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.io.Serializable;
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
@Table(name = "NIVEAUSCOLAIRE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Niveauscolaire.findAll", query = "SELECT n FROM Niveauscolaire n"),
    @NamedQuery(name = "Niveauscolaire.findByIdniveau", query = "SELECT n FROM Niveauscolaire n WHERE n.idniveau = :idniveau"),
    @NamedQuery(name = "Niveauscolaire.findByLibelle", query = "SELECT n FROM Niveauscolaire n WHERE n.libelle = :libelle"),
    @NamedQuery(name = "Niveauscolaire.AllNames", query = "SELECT n.libelle FROM Niveauscolaire n")})

public class Niveauscolaire implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idNiveauScolaire_generator")
    @SequenceGenerator(name = "idNiveauScolaire_generator", sequenceName = "idNiveauScolaire_seq", allocationSize = 1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDNIVEAU")
    private Integer idniveau;
    @Size(max = 20)
    @Column(name = "LIBELLE")
    private String libelle;

    public Niveauscolaire() {
    }

    public Niveauscolaire(Integer idniveau) {
        this.idniveau = idniveau;
    }

    public Integer getIdniveau() {
        return idniveau;
    }

    public void setIdniveau(Integer idniveau) {
        this.idniveau = idniveau;
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
        hash += (idniveau != null ? idniveau.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Niveauscolaire)) {
            return false;
        }
        Niveauscolaire other = (Niveauscolaire) object;
        if ((this.idniveau == null && other.idniveau != null) || (this.idniveau != null && !this.idniveau.equals(other.idniveau))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idniveau.toString();
    }

}
