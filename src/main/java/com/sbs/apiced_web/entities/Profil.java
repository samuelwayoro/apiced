/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author samuel
 */
@Entity
@Table(name = "PROFIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profil.findAll", query = "SELECT p FROM Profil p"),
    @NamedQuery(name = "Profil.findByIdprofil", query = "SELECT p FROM Profil p WHERE p.idprofil = :idprofil"),
    @NamedQuery(name = "Profil.findByAccesaccueil", query = "SELECT p FROM Profil p WHERE p.accesaccueil = :accesaccueil"),
    @NamedQuery(name = "Profil.findByAccesannuairemc", query = "SELECT p FROM Profil p WHERE p.accesannuairemc = :accesannuairemc"),
    @NamedQuery(name = "Profil.findByAccesemissionpaie", query = "SELECT p FROM Profil p WHERE p.accesemissionpaie = :accesemissionpaie"),
    @NamedQuery(name = "Profil.findByAccesstatspaieparmc", query = "SELECT p FROM Profil p WHERE p.accesstatspaieparmc = :accesstatspaieparmc"),
    @NamedQuery(name = "Profil.findByAccesstatspaieparop", query = "SELECT p FROM Profil p WHERE p.accesstatspaieparop = :accesstatspaieparop"),
    @NamedQuery(name = "Profil.findByAccessuivipaie", query = "SELECT p FROM Profil p WHERE p.accessuivipaie = :accessuivipaie"),
    @NamedQuery(name = "Profil.findByDatecreation", query = "SELECT p FROM Profil p WHERE p.datecreation = :datecreation"),
    @NamedQuery(name = "Profil.findByEtat", query = "SELECT p FROM Profil p WHERE p.etat = :etat"),
    @NamedQuery(name = "Profil.findByInformations", query = "SELECT p FROM Profil p WHERE p.informations = :informations"),
    @NamedQuery(name = "Profil.findByNom", query = "SELECT p FROM Profil p WHERE p.nom = :nom"),
    @NamedQuery(name = "Profil.findByLibelle", query = "SELECT p FROM Profil p WHERE p.libelle = :libelle"),
    @NamedQuery(name = "Profil.findByPageaccueil", query = "SELECT p FROM Profil p WHERE p.pageaccueil = :pageaccueil")})
public class Profil implements Serializable {

    @Column(name = "ACCESACCUEIL")
    private Boolean accesaccueil;
    @Column(name = "DESACTIVATIONLIEN")
    private Boolean desactivationlien;
    @Column(name = "ACCESANNUAIREMC")
    private Boolean accesannuairemc;
    @Column(name = "ACCESEMISSIONPAIE")
    private Boolean accesemissionpaie;
    @Column(name = "ACCESSTATSPAIEPARMC")
    private Boolean accesstatspaieparmc;
    @Column(name = "ACCESSTATSPAIEPAROP")
    private Boolean accesstatspaieparop;
    @Column(name = "ACCESSUIVIPAIE")
    private Boolean accessuivipaie;
    @Size(max = 255)
    @Column(name = "DATECREATION")
    private String datecreation;
    @Column(name = "ETAT")
    private Boolean etat;
    @Size(max = 255)
    @Column(name = "INFORMATIONS")
    private String informations;
    @Size(max = 255)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 100)
    @Column(name = "LIBELLE")
    private String libelle;
    @Size(max = 100)
    @Column(name = "PAGEACCUEIL")
    private String pageaccueil;
    @Column(name = "ACCESPARAMETRES")
    private Boolean accesparametres;
    @Size(max = 200)
    @Column(name = "DASHBOARD")
    private String dashboard;
    @Column(name = "ACCESPAIEMENTAIRTEL")
    private Boolean accespaiementairtel;
    @Column(name = "ACCESPARAMETRAGES")
    private Boolean accesparametrages;
    @Column(name = "ACCESMODIFMC")
    private Boolean accesmodifmc;
    @Column(name = "ACCESVALIDATIONMC")
    private Boolean accesvalidationmc;
    @Column(name = "BTNVALIDNOTIFS")
    private Boolean btnvalidnotifs;
    @Column(name = "ACCESGESTIONCARRIEREMC")
    private Boolean accesgestioncarrieremc;
    @Column(name = "ACCESNOUVEAUMC")
    private Boolean accesnouveaumc;
    @Column(name = "PAIEMENTSUBSIDES")
    private Boolean paiementsubsides;
    @Column(name = "BTNVALIDEPAIEMENT")
    private Boolean btnvalidepaiement;
    @Column(name = "GESTIONPAIEBYEMETTEUR")
    private Boolean gestionpaiebyemetteur;
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idProfil_generator")
    @SequenceGenerator(name = "idProfil_generator", sequenceName = "idProfil_seq", allocationSize = 1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDPROFIL")
    private Integer idprofil;
    @OneToMany(mappedBy = "profilIdprofil")
    private Collection<Utilisateur> utilisateurCollection;

    
    public Profil() {
    }

    public Boolean getDesactivationlien() {
        return desactivationlien;
    }

    public void setDesactivationlien(Boolean desactivationlien) {
        this.desactivationlien = desactivationlien;
    }

   
    public Profil(Integer idprofil) {
        this.idprofil = idprofil;
    }

    public Integer getIdprofil() {
        return idprofil;
    }


    public void setIdprofil(Integer idprofil) {
        this.idprofil = idprofil;
    }

    @XmlTransient
    public Collection<Utilisateur> getUtilisateurCollection() {
        return utilisateurCollection;
    }

    public void setUtilisateurCollection(Collection<Utilisateur> utilisateurCollection) {
        this.utilisateurCollection = utilisateurCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprofil != null ? idprofil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profil)) {
            return false;
        }
        Profil other = (Profil) object;
        if ((this.idprofil == null && other.idprofil != null) || (this.idprofil != null && !this.idprofil.equals(other.idprofil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idprofil.toString();
    }

    public Boolean getAccesaccueil() {
        return accesaccueil;
    }

    public void setAccesaccueil(Boolean accesaccueil) {
        this.accesaccueil = accesaccueil;
    }

    public Boolean getAccesannuairemc() {
        return accesannuairemc;
    }

    public void setAccesannuairemc(Boolean accesannuairemc) {
        this.accesannuairemc = accesannuairemc;
    }


    public Boolean getAccesemissionpaie() {
        return accesemissionpaie;
    }

    public void setAccesemissionpaie(Boolean accesemissionpaie) {
        this.accesemissionpaie = accesemissionpaie;
    }

    public Boolean getAccesstatspaieparmc() {
        return accesstatspaieparmc;
    }

    public void setAccesstatspaieparmc(Boolean accesstatspaieparmc) {
        this.accesstatspaieparmc = accesstatspaieparmc;
    }

    public Boolean getAccesstatspaieparop() {
        return accesstatspaieparop;
    }

    public void setAccesstatspaieparop(Boolean accesstatspaieparop) {
        this.accesstatspaieparop = accesstatspaieparop;
    }

    public Boolean getAccessuivipaie() {
        return accessuivipaie;
    }

    public void setAccessuivipaie(Boolean accessuivipaie) {
        this.accessuivipaie = accessuivipaie;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
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

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getPageaccueil() {
        return pageaccueil;
    }

    public void setPageaccueil(String pageaccueil) {
        this.pageaccueil = pageaccueil;
    }

    public Boolean getAccesparametres() {
        return accesparametres;
    }

    public void setAccesparametres(Boolean accesparametres) {
        this.accesparametres = accesparametres;
    }

    public String getDashboard() {
        return dashboard;
    }

    public void setDashboard(String dashboard) {
        this.dashboard = dashboard;
    }

    public Boolean getAccespaiementairtel() {
        return accespaiementairtel;
    }

    public void setAccespaiementairtel(Boolean accespaiementairtel) {
        this.accespaiementairtel = accespaiementairtel;
    }

    public Boolean getAccesparametrages() {
        return accesparametrages;
    }

    public void setAccesparametrages(Boolean accesparametrages) {
        this.accesparametrages = accesparametrages;
    }

    public Boolean getAccesmodifmc() {
        return accesmodifmc;
    }

    public void setAccesmodifmc(Boolean accesmodifmc) {
        this.accesmodifmc = accesmodifmc;
    }

    public Boolean getAccesvalidationmc() {
        return accesvalidationmc;
    }

    public void setAccesvalidationmc(Boolean accesvalidationmc) {
        this.accesvalidationmc = accesvalidationmc;
    }

    public Boolean getBtnvalidnotifs() {
        return btnvalidnotifs;
    }

    public void setBtnvalidnotifs(Boolean btnvalidnotifs) {
        this.btnvalidnotifs = btnvalidnotifs;
    }

    public Boolean getAccesgestioncarrieremc() {
        return accesgestioncarrieremc;
    }

    public void setAccesgestioncarrieremc(Boolean accesgestioncarrieremc) {
        this.accesgestioncarrieremc = accesgestioncarrieremc;
    }

    public Boolean getAccesnouveaumc() {
        return accesnouveaumc;
    }

    public void setAccesnouveaumc(Boolean accesnouveaumc) {
        this.accesnouveaumc = accesnouveaumc;
    }

    public Boolean getPaiementsubsides() {
        return paiementsubsides;
    }

    public void setPaiementsubsides(Boolean paiementsubsides) {
        this.paiementsubsides = paiementsubsides;
    }

    public Boolean getBtnvalidepaiement() {
        return btnvalidepaiement;
    }

    public void setBtnvalidepaiement(Boolean btnvalidepaiement) {
        this.btnvalidepaiement = btnvalidepaiement;
    }

    public Boolean getGestionpaiebyemetteur() {
        return gestionpaiebyemetteur;
    }

    public void setGestionpaiebyemetteur(Boolean gestionpaiebyemetteur) {
        this.gestionpaiebyemetteur = gestionpaiebyemetteur;
    }
    
    

}
