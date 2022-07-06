/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Paiement;
import com.sbs.apiced_web.entities.Transactions;
import java.math.BigInteger;
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
public class PaiementMensuelManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;

    /**
     * methode de persistence d'un nouveau profil
     *
     * @param paie
     */
    public void persist(Paiement paie) {
        try {
            //System.out.println("entree dans la methode persist de paiement mensuel de subsides "+paie.getMontanttotal());
            em.persist(paie);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(actual -> {
                System.out.println(actual.toString());
            });
        }
    }

    /**
     * update d'un paiement
     *
     * @param lePaiement
     * @return
     */
    public Paiement updatePaie(Paiement lePaiement) {
        return em.merge(lePaiement);
    }

    /**
     * *
     * renvoie la liste de toutes les valeurs mois de la table paiements
     *
     * @return
     */
    public List<Paiement> listePaiementDejaEmis() {
        Query q = em.createNamedQuery("Paiement.findAll");
        return q.getResultList();
    }
    
    

    public Integer recupLastPaie() {
        System.out.println("recuperation du dernier paiement");
        Query q = em.createQuery("SELECT MAX (p.idpaiement) FROM Paiement p ");
        return (Integer) q.getSingleResult();
    }

    public Paiement lastPaie(Integer infosPaie) {
        Query q = em.createQuery("SELECT p FROM Paiement p WHERE p.idpaiement = :id");
        q.setParameter("id", infosPaie);
        return (Paiement) q.getSingleResult();
    }

    /**
     * liste des paiements en attente de validation par le coordonnatteur
     *
     * @return
     */
    public List<Paiement> listePaiementEnAttente() {
        Boolean etatV = Boolean.FALSE;
        Query q = em.createQuery("SELECT p FROM Paiement p WHERE p.validationcoordonnateur = :etat  ORDER BY p.idpaiement DESC");
        q.setParameter("etat", etatV);
        return q.getResultList();
    }

    /**
     * liste des paiements en attente d'envoi vers l'opérateur
     *
     * @return
     */
    public List<Paiement> listePaiementEnAttenteDenvoieOp() {
        Boolean etatEnvoie = Boolean.FALSE;
        Query q = em.createQuery("SELECT p FROM Paiement p WHERE p.etatenvoiop = :etat ");
        q.setParameter("etat", etatEnvoie);
        return q.getResultList();
    }

    /**
     * liste des paiement validés et en attente d'envoi a l'opérateur
     *
     * @return
     */
    public List<Paiement> listePaiementEnAttenteDenvoi() {
        Boolean etatV = Boolean.TRUE;
        Boolean etatOp = Boolean.FALSE;
        Query q = em.createQuery("SELECT p FROM Paiement p WHERE p.validationcoordonnateur = :etat AND p.etatenvoiop = :etatOp ORDER BY p.idpaiement DESC");
        q.setParameter("etat", etatV);
        q.setParameter("etatOp", etatOp);
        return q.getResultList();
    }

    /**
     * qtes de paiement validés et en attente d'envoie a l'opérateur
     *
     * @return
     */
    public Long nbrePaiementEnAttenteDenvoi() {
        Boolean etatV = Boolean.TRUE;
        Boolean etatOp = Boolean.FALSE;
        Query q = em.createQuery("SELECT COUNT(p) FROM Paiement p WHERE p.validationcoordonnateur = :etat AND p.etatenvoiop = :etatOp ");
        q.setParameter("etat", etatV);
        q.setParameter("etatOp", etatOp);
        return (Long) q.getSingleResult();
    }

    //recup d'un paiement 
    private Paiement getAPaiement(Integer idPaie) {
        Paiement paie = em.find(Paiement.class, idPaie);
        return paie;
    }

    //suppresion d'un paiement
    public void deletePaiement(Paiement selectedPaiement) {
        Paiement p = this.getAPaiement(selectedPaiement.getIdpaiement());
        if (p != null) {
            em.remove(em.merge(p));
        }
    }

    public List<Transactions> listeTransactionsEnCours() {
        String etat = "En cours de paiement";
         String etatPayes = "payee";
         String etatEchec = "echouee";
         Query q = em.createQuery("SELECT t FROM Transactions t WHERE t.etattransaction = :etat  or t.etattransaction = :etatPayes or t.etattransaction = :etatEchec ");
        q.setParameter("etat", etat);
        q.setParameter("etatPayes", etatPayes);
        q.setParameter("etatEchec", etatEchec);
        return q.getResultList();
    }

    public Long nbreTransactionsEnCours() {
         String etatEnCours = "En cours de paiement";
                Query q = em.createQuery("SELECT COUNT(t) FROM Transactions t WHERE t.etattransaction = :etat");//nbrePaiementEnCours
        q.setParameter("etat", etatEnCours);
        return (Long) q.getSingleResult();
    }

}
