/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author samuel
 */
@Stateless
public class DrejManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
    
/**
 * retourne la liste de noms de toutes les localités (DREJ)
     * @return 
 */
 public List<String> listNomsDrej(){
     Query qr = em.createQuery("SELECT d.nomdrej FROM Drej d order by d.nomdrej");
     return qr.getResultList();
 }
    
    
    
}
