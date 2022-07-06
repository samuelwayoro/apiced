/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Etatpaiement;
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
public class EtatPaiementManager {

        @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
        
         /**
     * methode retournant la liste des noms des profils
     * @return 
     */
    public List<Etatpaiement> getAllEtatPaiementsSubsides(){
        Query query = em.createNamedQuery("Etatpaiement.findAll");
        return query.getResultList();
    }
    
    /**
     * retourner un paiement a partir de son id 
     * @param idEtatPaiement
     * @return 
     */
    public Etatpaiement etatPaiementbyId(BigDecimal idEtatPaiement){
        Query q = em.createNamedQuery("Etatpaiement.findByIdetat");
        q.setParameter("idetat", idEtatPaiement);
        return (Etatpaiement) q.getSingleResult();
    }
}
