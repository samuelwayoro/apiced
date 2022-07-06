/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Auditlog;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author samuel
 */
@Stateless
@LocalBean
public class AuditlogManager {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
    
    /**
     * methode de persistence d'un noyveau profil
     * @param a
     */
    public void persist(Auditlog a) {
        try {
            System.out.println("entree dans la methode persist de auditlog");
            em.persist(a);
        }catch(ConstraintViolationException e){
            e.getConstraintViolations().forEach(actual -> {
                System.out.println(actual.toString());
            });
        }
    }

}
