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
import javax.persistence.TableGenerator;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author samuel
 */
@Entity
@Table(name = "VILLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Villes.findAll", query = "SELECT v FROM Villes v ORDER BY v.nomville "),
    @NamedQuery(name = "Villes.findByIdville", query = "SELECT v FROM Villes v WHERE v.idville = :idville"),
    @NamedQuery(name = "Villes.findByNomville", query = "SELECT v FROM Villes v WHERE v.nomville = :nomville"),
    @NamedQuery(name = "Villes.findByProvince", query = "SELECT v FROM Villes v WHERE v.province = :province")})
public class Villes implements Serializable {

    private static final long serialVersionUID = 1L;
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idVilles_generator")
    @SequenceGenerator(name="idVilles_generator", sequenceName = "idVilles_seq", allocationSize=1)
    @Basic(optional = false)
    @Id
    @Column(name = "IDVILLE")
    private Integer idville;
    @Size(max = 100)
    @Column(name = "NOMVILLE")
    private String nomville;
    @Size(max = 100)
    @Column(name = "PROVINCE")
    private String province;

    public Villes() {
    }

    public Villes(Integer idville) {
        this.idville = idville;
    }

    public Integer getIdville() {
        return idville;
    }

    public void setIdville(Integer idville) {
        this.idville = idville;
    }

    public String getNomville() {
        return nomville;
    }

    public void setNomville(String nomville) {
        this.nomville = nomville;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idville != null ? idville.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Villes)) {
            return false;
        }
        Villes other = (Villes) object;
        if ((this.idville == null && other.idville != null) || (this.idville != null && !this.idville.equals(other.idville))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idville.toString();
    }
    
}
