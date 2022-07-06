/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Parametres;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author samuel
 */
@Stateless
public class ParamManager {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
    
    /**
     * methode de persistence d'un nouveau profil
     * @param p
     */
    public void persist(Parametres p) {
        try {
            System.out.println("entree dans la methode persist des parametres");
            em.persist(p);
        }catch(ConstraintViolationException e){
            e.getConstraintViolations().forEach(actual -> {
                System.out.println(actual.toString());
            });
        }
    }
    
    /***
     * renvoie un parametre a partir de son libelle 
     * @param valParam
     * @return 
     */
    public Parametres paramByVal(String valParam ){
        Query q = em.createNamedQuery("Parametres.findByValeur");
        q.setParameter("valeur", valParam);
        return (Parametres) q.getSingleResult();
    }
    
    /***
     * renvoi un parametre a partir de son libelle
     * @param libelle
     * @return 
     */
    public Parametres paramByLibelle (String libelle){
        Query q = em.createNamedQuery("Parametres.findByLibelleparam");
        q.setParameter("libelleparam", libelle);
        return (Parametres) q.getSingleResult();
    }
    
    /**
     * renvoi la valeur d'un parametre a partir de son libelle
     * 
     * @param libelle
     * @return 
     */
    public String paramValueByLibelle(String libelle){
        Query q = em.createQuery("SELECT p.valeur FROM Parametres p WHERE p.libelleparam = :nomLibelle");
        q.setParameter("nomLibelle", libelle);
        return (String) q.getSingleResult();
    }
    
    /**
     * methode permettan de update un parametre
     * 
     * @param p
     * @return 
     */
    public Parametres updateParam(Parametres p){
        return em.merge(p);
    }
    
}
