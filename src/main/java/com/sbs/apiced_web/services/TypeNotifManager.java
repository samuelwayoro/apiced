/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Typenotifs;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author samuel
 */
@Stateless
public class TypeNotifManager {
   
    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
    
    
        /**
     * methode retournant la liste de tous les types de notifications
     * @return 
     */
    public List<Typenotifs> getAllNotifsType(){
        //Query query = em.createNamedQuery("Profil.allNames");
        Query q = em.createQuery("SELECT t FROM Typenotifs t ");
        return q.getResultList();
    }
    
    //retourne le type de notification : CREATIONMAITRE
    public Typenotifs creaMcTypeNotifMaitre(String typeNotif){
        Query q = em.createNamedQuery("Typenotifs.findByNomtype");
        q.setParameter("nomtype", typeNotif);
        return (Typenotifs) q.getSingleResult();
    }
    
       public Typenotifs creaMcTypeNotifById(BigDecimal id){
        Query q = em.createNamedQuery("Typenotifs.findByIdtype");
        q.setParameter("idtype", id);
        return (Typenotifs) q.getSingleResult();
    }
    
    
    //retourne le type de notification : PAIEMENT SUBSIDE
    public Typenotifs creaMcTypeNotifPaiement(){
        String t = "CREATIONPAIEMENT";
        Query q = em.createNamedQuery("Typenotifs.findByNomtype");
        q.setParameter("nomtype", t);
        return (Typenotifs) q.getSingleResult();
    }
    
    //retourne le type de notification : CREATION PAIEMENT DE SUBSIDE DE AIRTEL
       public Typenotifs creaAirtelPaiementTypeNotif(){
        String t = "PAIEMENT_SUBSIDES_AIRTEL";
        Query q = em.createNamedQuery("Typenotifs.findByNomtype");
        q.setParameter("nomtype", t);
        return (Typenotifs) q.getSingleResult();
    }
       
       //retourne un type de notif a partir de son id renseigné
          public Typenotifs getOneTypeNotif(BigDecimal id) {
        return em.find(Typenotifs.class, id);
    }

    
}
