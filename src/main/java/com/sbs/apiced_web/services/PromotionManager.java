/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Promotion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author samuel
 */
@Stateless
public class PromotionManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;

    /**
     *
     * @param promo
     */
    public void persist(Promotion promo) {
        try {
            System.out.println("entree dans la methode persist de promotionManager");
            em.persist(promo);
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                System.out.println(actual.toString());
            }
        }
    }
    
    
    
}
