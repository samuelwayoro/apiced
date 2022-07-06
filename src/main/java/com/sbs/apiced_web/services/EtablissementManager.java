/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Etablissement;
import com.sbs.apiced_web.entities.Ipep;
import java.math.BigDecimal;
import java.util.List;
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
public class EtablissementManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
    
    /**
     * creation nouvel etablissement
     * @param ets
     */
     public void persist(Etablissement ets) {
        try {
            //System.out.println("entree dans la methode persist des utilisateurs");
            em.persist(ets);
        }catch(ConstraintViolationException e){
            e.getConstraintViolations().forEach(actual -> {
                System.out.println(actual.toString());
            });
        }
    }

     /**
      * retoune un etablissement a partir de son nom saisi
     * @param nom
      * @return 
      */
    public Etablissement getEtsByNom(String nom) {
        //Query q = em.createQuery("Etablissement.findByNom");
        Query q = em.createQuery("SELECT e FROM Etablissement e WHERE e.nom = :nom");
        q.setParameter("nom", nom);
        return (Etablissement) q.getSingleResult();
    }
    
    /**
     * retourne un etablissement a partir de son id
     * @param ets
     * @return 
     */
    public Etablissement findEtsById(BigDecimal ets ){
        return em.find(Etablissement.class , ets);
    }
    
      /**
     * *
     * methode qui retourne la liste de tous les établissement
     *
     * @return
     */
    public List<Etablissement> getEtablissements() {
        //System.out.println("recuperation de tous les operateurs");
        Query q = em.createNamedQuery("Etablissement.findAll");
        return q.getResultList();
    }
    
    /***
     * retrouver l'ipep d'un etablissement
     * @param idIpep
     * @return 
     */
    public Ipep EtablissementIpep(BigDecimal idIpep){
        Query q = em.createQuery("SELECT i FROM Ipep i WHERE i.idipep = :idipep");
        q.setParameter("idipep", idIpep);
        return (Ipep) q.getSingleResult();
    }
}
