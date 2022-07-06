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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author samuel
 */
@Entity
@Table(name = "NOTIFICATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notifications.findAll", query = "SELECT n FROM Notifications n"),
    @NamedQuery(name = "Notifications.findByIdnotif", query = "SELECT n FROM Notifications n WHERE n.idnotif = :idnotif"),
    @NamedQuery(name = "Notifications.findByLibelle", query = "SELECT n FROM Notifications n WHERE n.libelle = :libelle"),
    @NamedQuery(name = "Notifications.findByDetails", query = "SELECT n FROM Notifications n WHERE n.details = :details"),
    @NamedQuery(name = "Notifications.findByDatecreation", query = "SELECT n FROM Notifications n WHERE n.datecreation = :datecreation"),
    @NamedQuery(name = "Notifications.findByDateresolution", query = "SELECT n FROM Notifications n WHERE n.dateresolution = :dateresolution"),
    @NamedQuery(name = "Notifications.findByEtat", query = "SELECT n FROM Notifications n WHERE n.etat = :etat")})
public class Notifications implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idNotifications_generator")
    @SequenceGenerator(name = "idNotifications_generator", sequenceName = "idNotifications_seq", allocationSize = 1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDNOTIF")
    private Integer idnotif;
    @Column(name = "IDINFO")
    private String idinfo;
      @Size(max = 255)
    @Column(name = "LIBELLE")
    private String libelle;
    @Size(max = 255)
    @Column(name = "DETAILS")
    private String details;
    @Size(max = 13)
    @Column(name = "DATECREATION")
    private String datecreation;
    @Size(max = 13)
    @Column(name = "DATERESOLUTION")
    private String dateresolution;
    @Column(name = "ETAT")
    private BigInteger etat;
    @JoinColumn(name = "TYPENOTIF", referencedColumnName = "IDTYPE")
    @ManyToOne
    private Typenotifs typenotif;
    @JoinColumn(name = "CREATEUR", referencedColumnName = "IDUTILISATEUR")
    @ManyToOne
    private Utilisateur createur;
    @JoinColumn(name = "DESTINATAIRE", referencedColumnName = "IDUTILISATEUR")
    @ManyToOne
    private Utilisateur destinataire;

    public Notifications() {
    }


    public Notifications(Integer idnotif) {
        this.idnotif = idnotif;
    }

    public String getIdinfo() {
        return idinfo;
    }

    public void setIdinfo(String idinfo) {
        this.idinfo = idinfo;
    }

    public Integer getIdnotif() {
        return idnotif;
    }

    public void setIdnotif(Integer idnotif) {
        this.idnotif = idnotif;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public String getDateresolution() {
        return dateresolution;
    }

    public void setDateresolution(String dateresolution) {
        this.dateresolution = dateresolution;
    }

    public BigInteger getEtat() {
        return etat;
    }

    public void setEtat(BigInteger etat) {
        this.etat = etat;
    }

    public Typenotifs getTypenotif() {
        return typenotif;
    }

    public void setTypenotif(Typenotifs typenotif) {
        this.typenotif = typenotif;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    public Utilisateur getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Utilisateur destinataire) {
        this.destinataire = destinataire;
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
        hash += (idnotif != null ? idnotif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notifications)) {
            return false;
        }
        Notifications other = (Notifications) object;
        if ((this.idnotif == null && other.idnotif != null) || (this.idnotif != null && !this.idnotif.equals(other.idnotif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Notifications[ idnotif=" + idnotif + " ]";
    }

}
