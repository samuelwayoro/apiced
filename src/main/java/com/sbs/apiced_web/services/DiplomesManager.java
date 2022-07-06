/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

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
public class DiplomesManager {

      @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
      
          
    /**
     * retourne la liste de tous les noms des derniers diplomes
     * @return 
     */
    public List<String> allDernierDiplomesNames(){
        Query q = em.createQuery("SELECT d.libelle FROM Diplome d");
        return q.getResultList();
    }
}
