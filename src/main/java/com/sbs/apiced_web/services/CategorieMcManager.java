/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Categorie;
import java.math.BigDecimal;
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
public class CategorieMcManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;

    //retourner une catégorie a partir de son id    
    public Categorie findCategorieById(Integer idCategorie) {
        return em.find(Categorie.class, idCategorie);
    }

    /**
     * methode d'ajout d'une nouvelle categorie
     *
     * @param cate
     */
    public void addMcCategorie(Categorie cate) {
        System.out.println("ajoute de la categorie " + cate.getLibelle());
    }

    /**
     * methode retournant la liste de toutes les categories
     *
     * @return
     */
    public List<Categorie> allMcCategorie() {
        Query qr = em.createNamedQuery("Categorie.findAll");
        return qr.getResultList();
    }

    public List<String> listNomsCategories() {
        Query qr = em.createQuery("SELECT c.libelle FROM Categorie c ");
        return qr.getResultList();
    }

    /**
     * methode de modification d'une categorie
     *
     * @param cate
     */
    public void modifMcCategorie(Categorie cate) {
        System.out.println("modification de la categorie :" + cate.getLibelle());
    }

    /**
     * methode de suppression d'une categorie
     *
     * @param cate
     */
    public void deleteMcCategorie(Categorie cate) {
        System.out.println("suppression de la categorie :" + cate.getLibelle());
    }

    /**
     * retourne une categorie en fonction de son libelle
     *
     * @param libelleCate
     * @return
     */
    public Categorie getCategorieByName(String libelleCate) {
        Query q = em.createNamedQuery("Categorie.findByLibelle");
        q.setParameter("libelle", libelleCate);
        return (Categorie) q.getSingleResult();
    }

    /**
     * retourne une categorie a partir de son id
     *
     * @param idCateg
     * @return
     */
    public Categorie getCategorieById(BigDecimal idCateg) {
        Query q = em.createQuery("SELECT c FROM Categorie c WHERE c.idcategorie = :id ");
        q.setParameter("id", idCateg);
        return (Categorie) q.getSingleResult();
    }

    //liste des noms de toutes les categories
    public List<String> getAllCategoriesNames() {
        Query q = em.createNamedQuery("Categorie.findAllNames");
        return q.getResultList();
    }

}
