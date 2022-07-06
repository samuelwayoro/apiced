/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Operateur;
import java.util.ArrayList;
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
public class OperateurTelcoManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;

    /**
     * *
     * methode qui retourne la liste de tous les opérateur télécoms
     *
     * @return
     */
    public List<Operateur> getAllOperateur() {
        //System.out.println("recuperation de tous les operateurs");
        Query q = em.createNamedQuery("Operateur.findAll");
        return q.getResultList();
    }

    /**
     * *
     * renvoi la liste des noms de tous les opérateurs
     *
     * @return
     */
    public List<String> getAllOperateurTelcoNames() {
        Query q = em.createQuery("SELECT o.nom FROM Operateur o");
        return q.getResultList();
    }

    /**
     * methode de persistence d'un nouvel opérateur
     *
     * @param op
     */
    public void persist(Operateur op) {
        try {
            System.out.println("entree dans la methode persist des opérateur télécom");
            em.persist(op);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(actual -> {
                System.out.println(actual.toString());
            });
        }
    }

    /**
     * methode permettant de modifier les infos d'un opérateur
     * @param op
     * @return
     */
    public Operateur updateOperateur(Operateur op) {
        System.out.println("entree ds la methode update de lop");
        return em.merge(op);
    }

    //retourne le nom d'un opérateur  a partir de son nom
    public String oneOpByName(String nomOp) {
        Query q = em.createQuery("SELECT o.nom FROM Operateur o WHERE o.nom = :nomOperateur");
        q.setParameter("nomOperateur", nomOp);
        return (String) q.getSingleResult();
    }

    /**
     * retouner un operateur a partir de son id
     *
     * @param idOp
     * @return
     */
    public Operateur OneOpById(Integer idOp) {
        return em.find(Operateur.class, idOp);
    }

//    public Integer nbreAbonneesAirtel() {
//                Integer idDeLoperateur = 2;
//        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.id = :idOperateur");
//        q.setParameter("idOperateur", idDeLoperateur);
//        return  (Integer) q.getSingleResult();
//    }
//    
//    public Integer nbreAbonnesTigo(){
//        Integer idDeLoperateur =1;
//        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.id = :idOperateur");
//        q.setParameter("idOperateur", idDeLoperateur);
//        return (Integer)q.getSingleResult();
//    }
    /**
     * retoune le nbre de maitres abonnés a partir de l'operateur donné
     *
     * @param nomOperateur
     * @return
     */
    public Long nbreAbonnesByIdOp(String nomOperateur) {
        //System.out.println("comptage des maitres abonneés pr l'operateur "+idOperateur);
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.operatortelco = :nomOp");
        q.setParameter("nomOp", nomOperateur);
        return (Long) q.getSingleResult();
    }

    /**
     * verifie l'existance de maitre abonné a un opérateur donné
     *
     * @param op
     * @return
     */
    public int verifMcsAbonnéChezOp(String op) {
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.operatortelco = :operateur");
        q.setParameter("operateur", op);
        return Integer.valueOf(q.getSingleResult().toString());
    }

    /**
     * suppresion d'un operateur a partir de son id
     *
     * @param idoperateur
     */
    public void deleteOperateur(Integer idoperateur) {
        //existance de l'operateur a partir de son id
        Operateur loperateur = OneOpById(idoperateur);
        if (loperateur != null) {
            em.remove(em.merge(loperateur));
            System.out.println("opearteur supprime avec succes");
        }

    }



    public void updateTelcoMc(Operateur selectedOperateur, String nvoNomOpTelco) {
        System.out.println("ancien nom operateur " + selectedOperateur.getNom() + " nouvau nom " + nvoNomOpTelco);
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.operatortelco = :operateur");
        q.setParameter("operateur", selectedOperateur.getNom());
        
       
 System.out.println("il y a  " + q.getSingleResult().toString() + " avec cet operateur qui vont etre modifiés");
       Query qr = em.createNativeQuery("UPDATE Maitrecommunautaire SET m.operatortelco = :operateur WHERE m.operatortelco = :opExName  ");
       qr.setParameter("operateur", nvoNomOpTelco);
       qr.setParameter("opExName", selectedOperateur.getNom());
        System.out.println("modifs du nom operateur chez les mc ok !!");
    }

}
