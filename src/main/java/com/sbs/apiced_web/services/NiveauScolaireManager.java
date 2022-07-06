/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Niveauscolaire;
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
public class NiveauScolaireManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
    
    
      /**
     * methode retournant la liste de tous les niveau académique des mc
     * @return 
     */
    public List<Niveauscolaire> getAllNiveauSco(){
       Query query = em.createNamedQuery("Niveauscolaire.findAll");
        return  query.getResultList();
    }
    
    
    /**
     * retourne la liste de tous les noms des niveaux scolaire  
     * @return 
     */
    public List<String> allNiveauScolaireNames(){
        Query q = em.createNamedQuery("Niveauscolaire.AllNames");
        return q.getResultList();
    }
    
    
            //retourner une ville a partir de son id   
    public  Niveauscolaire findNiveauAcademiq (Integer idNiveau){
        return em.find(Niveauscolaire.class, idNiveau);
    }
}
