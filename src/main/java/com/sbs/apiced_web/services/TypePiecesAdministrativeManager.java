/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

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
public class TypePiecesAdministrativeManager {

 @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;
 
 /**
  * retourner la liste des toutes les types de pièces administrative
     * @return 
  */
    public List<String> allTypesPieces(){
        Query q = em.createQuery("SELECT t.libellepiece FROM Typepieceadministrative t");
        return q.getResultList();
    }
 
 
}
