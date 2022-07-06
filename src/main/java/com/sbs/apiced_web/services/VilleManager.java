/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Villes;
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
public class VilleManager {

      @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
      
          /***
     * methode qui retourne la liste de toutes les villes du pays 
     * @return 
     */
    public List<Villes> getAllVilles(){
        //System.out.println("recuperation de toutes les villes");
       Query q = em.createNamedQuery("Villes.findAll");
       return q.getResultList();
    }
    
            //retourner une ville a partir de son id   
    public  Villes findVilleById (Integer idVille){
        return em.find(Villes.class, idVille);
    }
}
