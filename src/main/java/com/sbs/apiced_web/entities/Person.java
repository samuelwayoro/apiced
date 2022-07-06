/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author samuel
 */
@Entity
public class Person implements Serializable {

    private static final long serialVersionUid = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String noms;
    private String prenoms;

    public Person() {
    }

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public String getnoms() {
        return noms;
    }

    public void setnoms(String noms) {
        this.noms = noms;
    }

    public String getprenoms() {
        return prenoms;
    }

    public void setprenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public Person(String noms, String prenoms) {
        this.noms = noms;
        this.prenoms = prenoms;
    }
   
}
