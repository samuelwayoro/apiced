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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "AUDITLOG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auditlog.findAll", query = "SELECT a FROM Auditlog a"),
    @NamedQuery(name = "Auditlog.findByIdaudit", query = "SELECT a FROM Auditlog a WHERE a.idaudit = :idaudit"),
    @NamedQuery(name = "Auditlog.findByAction", query = "SELECT a FROM Auditlog a WHERE a.action = :action"),
    @NamedQuery(name = "Auditlog.findByDateaction", query = "SELECT a FROM Auditlog a WHERE a.dateaction = :dateaction"),
    @NamedQuery(name = "Auditlog.findByLogin", query = "SELECT a FROM Auditlog a WHERE a.login = :login")})
public class Auditlog implements Serializable {
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idAudit_generator")
    @SequenceGenerator(name="idAudit_generator", sequenceName = "idAudit_seq", allocationSize=1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDAUDIT")
    private Integer idaudit;
    @Size(max = 255)
    @Column(name = "ACTION")
    private String action;
    @Size(max = 255)
    @Column(name = "DATEACTION")
    private String dateaction;
    @Size(max = 255)
    @Column(name = "LOGIN")
    private String login;
    @JoinColumn(name = "AUTEUR_IDUTILISATEUR", referencedColumnName = "IDUTILISATEUR")
    @ManyToOne
    private Utilisateur auteurIdutilisateur;

    public Auditlog() {
    }

    public Auditlog(Integer idaudit) {
        this.idaudit = idaudit;
    }

    public Integer getIdaudit() {
        return idaudit;
    }

    public void setIdaudit(Integer idaudit) {
        this.idaudit = idaudit;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDateaction() {
        return dateaction;
    }

    public void setDateaction(String dateaction) {
        this.dateaction = dateaction;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Utilisateur getAuteurIdutilisateur() {
        return auteurIdutilisateur;
    }

    public void setAuteurIdutilisateur(Utilisateur auteurIdutilisateur) {
        this.auteurIdutilisateur = auteurIdutilisateur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idaudit != null ? idaudit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auditlog)) {
            return false;
        }
        Auditlog other = (Auditlog) object;
        if ((this.idaudit == null && other.idaudit != null) || (this.idaudit != null && !this.idaudit.equals(other.idaudit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Auditlog[ idaudit=" + idaudit + " ]";
    }
    
}
