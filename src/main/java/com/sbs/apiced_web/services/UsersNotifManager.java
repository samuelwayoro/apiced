/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Usersnotifs;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author samuel
 */
@Stateless
public class UsersNotifManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
    
    
    /**
     * enregistrer un usernotif
     * @param us
     */
        public void persist(Usersnotifs us) {
        try {
            //System.out.println("entree dans la methode persist des usersNotifs ...");
            em.persist(us);
        }catch(ConstraintViolationException e){
            e.getConstraintViolations().forEach(actual -> {
                System.out.println(actual.toString());
            });
        }
    }
        
   /**
     * methode permettant de update un userNotif a partir de son id 
     * @param laNotif
     * @return 
     */
    public Usersnotifs updateUserNotif(Usersnotifs laNotif){
        return em.merge(laNotif);
    }
}
