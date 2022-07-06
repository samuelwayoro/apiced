/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TYPENOTIFS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Typenotifs.findAll", query = "SELECT t FROM Typenotifs t"),
    @NamedQuery(name = "Typenotifs.findByIdtype", query = "SELECT t FROM Typenotifs t WHERE t.idtype = :idtype"),
    @NamedQuery(name = "Typenotifs.findByNomtype", query = "SELECT t FROM Typenotifs t WHERE t.nomtype = :nomtype"),
    @NamedQuery(name = "Typenotifs.findByDetails", query = "SELECT t FROM Typenotifs t WHERE t.details = :details")})
public class Typenotifs implements Serializable {
    
    private static final long serialVersionUID = 1L;
 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idTypeNotif_generator")
    @SequenceGenerator(name="idTypeNotif_generator", sequenceName = "idTypeNotif_seq", allocationSize=1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDTYPE")
    private BigDecimal idtype;
    @Size(max = 25)
    @Column(name = "NOMTYPE")
    private String nomtype;
    @Size(max = 150)
    @Column(name = "DETAILS")
    private String details;
    @OneToMany(mappedBy = "typenotif")
    private Collection<Notifications> notificationsCollection;

    public Typenotifs() {
    }

    public Typenotifs(BigDecimal idtype) {
        this.idtype = idtype;
    }

    public BigDecimal getIdtype() {
        return idtype;
    }

    public void setIdtype(BigDecimal idtype) {
        this.idtype = idtype;
    }

    public String getNomtype() {
        return nomtype;
    }

    public void setNomtype(String nomtype) {
        this.nomtype = nomtype;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @XmlTransient
    public Collection<Notifications> getNotificationsCollection() {
        return notificationsCollection;
    }

    public void setNotificationsCollection(Collection<Notifications> notificationsCollection) {
        this.notificationsCollection = notificationsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtype != null ? idtype.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Typenotifs)) {
            return false;
        }
        Typenotifs other = (Typenotifs) object;
        if ((this.idtype == null && other.idtype != null) || (this.idtype != null && !this.idtype.equals(other.idtype))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.apiced_web.entities.Typenotifs[ idtype=" + idtype + " ]";
    }
    
}
