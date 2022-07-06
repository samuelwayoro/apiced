/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Classeaffectee;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author samuel
 */
@Singleton
public class ClasseAffecteeManager {

       @PersistenceContext(unitName = "myPersistence")
         private EntityManager em;
       
       /**
        * retourne la liste de toutes les classes 
     * @return 
        */
       public List<Classeaffectee> allClasses(){
           Query q = em.createNamedQuery("Classeaffectee.findAll");
           return q.getResultList();
       }
       
       /***
        * retourne le noms de toutes les classes 
     * @return 
        */
       public List<String> allClassesNames(){
           Query q = em.createNamedQuery("Classeaffectee.allNames");
           return q.getResultList();
       }
}
