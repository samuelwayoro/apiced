/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Bailleur;
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
public class BailleurManager {

     @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
     
     /***
      * retourne la liste des bailleurs des mc
     * @return liste des bailleurs
      */
     public List<Bailleur> allBailleurs(){
         Query q = em.createQuery("SELECT b FROM Bailleur b ORDER BY b.nom ");
         return q.getResultList();
     }
     
     public List<String> listeNomsBailleurs(){
         Query q = em.createQuery("SELECT b.nom FROM Bailleur b ");
         return q.getResultList();
     }
     
         //retourne un bailleur  a partir de son nom
    public Bailleur oneBailleurByName(String nomB) {
        Query q = em.createQuery("SELECT b FROM Bailleur b WHERE b.nom = :nom");
        q.setParameter("nom", nomB);
        return   (Bailleur) q.getSingleResult();
    }
    //retourne le bailleur a partir de son id 
    public Bailleur oneBailleurById(BigDecimal idBailleur){
        return em.find(Bailleur.class, idBailleur);
    }
    
    public String listeCate(String bailleurName){
        Query q = em.createQuery("SELECT b.listeCategories FROM Bailleur b WHERE b.nom = :nomBailleur");
        q.setParameter("nomBailleur", bailleurName);
        return  (String) q.getSingleResult();
    }
    
}
