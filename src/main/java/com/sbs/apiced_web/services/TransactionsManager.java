/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Paiement;
import com.sbs.apiced_web.entities.Transactions;
import com.sbs.apiced_web.entities.Utilisateur;
import java.math.BigDecimal;
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
public class TransactionsManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;

    //enregister en base 
    public void persist(Transactions t) {
        try {
            System.out.println("entree dans la methode persist des transactions...");
            em.persist(t);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(actual -> {
                System.out.println(actual.toString());
            });
        }
    }

    /**
     * update d'une transaction
     * @param t
     * @return 
     */
    public Transactions updatePaie(Transactions t) {
        return em.merge(t);
    }

    //recuperer la liste de toutes les transactions d'un paiement selectionné
    public List<Transactions> selectedPaieTransactions(Integer id) {
        Query q = em.createQuery("SELECT t FROM Transactions t WHERE t.paiementid = :idPaie");
        q.setParameter("idPaie", id);
        return q.getResultList();
    }

    /**
     * *
     * retourn la liste de toutes les transacrtion pas encore validées
     *
     * @return
     */
    public List<Transactions> allTransactions() {
        Query q = em.createQuery("SELECT t FROM Transactions t ");
        return q.getResultList();
    }

    /**
     * retourner une transaction
     *
     * @param idUser
     * @return
     */

    private Transactions getTransaction(BigDecimal idTransact) {
        Transactions t = em.find(Transactions.class, idTransact);
        return t;
    }

    /**
     * retourner une liste de transactions a partir de l'id du paiement
     */
    private List<Transactions> getListTransactions(BigInteger paiementId) {
        Query q = em.createQuery("SELECT t FROM Transactions t WHERE t.paiementid = :idpaie ");
        q.setParameter("idpaie", paiementId);
        return q.getResultList();
    }

    /**
     * suppresion de transactions d'un paiement
     *
     * @param t
     */
    public void deleteTransactions(Transactions t) {
        //recup de l'utilisateur
        Transactions tr = this.getTransaction(t.getIdtransaction());
        if (tr != null) {
            em.remove(em.merge(tr));
            //System.out.println("suppresion de l'utilisateur executée avec succes");
        }
    }
}
