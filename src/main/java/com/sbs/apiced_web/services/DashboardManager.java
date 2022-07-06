/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Categorie;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author samuel
 */
@Stateless
public class DashboardManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
    
    //retourner le nbre total de maitre communautaire dans la bd 
    public Long totalMc(){
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m");
        return (Long) q.getSingleResult();
    }

 

    public Long qtesMcDebutant(Categorie debutant) {
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.idcategoriepro= :idcateg");
         q.setParameter("idcateg", debutant);
        return (Long) q.getSingleResult();
    }
}
