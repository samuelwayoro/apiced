/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Profil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author samuel
 */
@Stateless
public class ProfilsManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;

 
    
        //retourner un utilisateur a la selection de son Id    
    public  Profil findTheProfilById (Integer idProfil){
        return em.find(Profil.class, idProfil);
    }
    
    
    /**
     * methode pour lister tous les profils
     * @return 
     */
    public List<Profil> getAllProfils(){
        Query query = em.createNamedQuery("Profil.findAll");
        return query.getResultList();
    }
    
    /**
     * methode retournant la liste des noms des profils
     * @return 
     */
    public List<String> getAllProfilsName(){
        //Query query = em.createNamedQuery("Profil.allNames");
        Query q = em.createQuery("SELECT p.nom FROM Profil p ");
        return (List<String>) q.getResultList();
    }
    
   /**
     * mehtode de recuperation  d'un profil a partir de son nom
     * @param nomProfil
     * @return 
     */
    public Profil getProfilByName(String nomProfil){
        Query query = em.createQuery("SELECT p FROM Profil p WHERE  p.nom = :nomProfil");
        query.setParameter("nomProfil", nomProfil);
        try {
            Profil leProfil = (Profil)query.getSingleResult();
            System.out.println("ce profil existe deja , c'est :  "+leProfil.getNom());
            return (Profil)query.getSingleResult();
        } catch (Exception e) {
            System.out.println("ce profil n'existe pas en bd"+e.getMessage());
            return null;
        }
    }
    
    public Profil verifProfil (String nomProfil){
        Query query = em.createQuery("SELECT p FROM Profil p WHERE p.nom =  :np");
        query.setParameter("np", nomProfil);
        try {
            return (Profil) query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            System.out.println("CE PROFIL NE SE TROUVE PAS DANS LA BD ");
            return null;
        }
    }
    
    /**
     * methode de persistence d'un nouveau profil
     * @param profil
     */
    public void persist(Profil profil) {
        try {
            System.out.println("entree dans la methode persist de profils");
            em.persist(profil);
        }catch(ConstraintViolationException e){
            for(ConstraintViolation actual : e.getConstraintViolations()){
                System.out.println(actual.toString());
            }
        }
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "une erreur lors de lenregistrement", e);
//            throw new RuntimeException(e);
//        }
        
    }
    
    //retourner un profil par son id
       public Profil getProfil(Integer idProfil) {
        return em.find(Profil.class, idProfil);
    }
       
        /**
     * methode permettant de modifier un profil a partir de son id
     * @param leProfil
     * @return 
     */
    public Profil updateProfil(Profil leProfil){
        return em.merge(leProfil);
    }
    
     /**
     * methode permettant supprimer un profil de la bd 
     * @param idProfil 
     */
    public void deleteProfil(Integer idProfil){
        Profil pro = this.getProfil(idProfil);//verif de l'existance du profil en bd 
        if (pro!=null) {
            em.remove(em.merge(pro));
            System.out.println("suppresion du profil executé avec succes");
        }
        //System.out.println("le suppression ne s'est pas executée");
    }
    
    /**
     * verification de l'existance d'utilisateur ayant un profil selectionné
     * 
     * @param p
     * @return 
     */
    public int verifProfilUsers(Profil p){
        Query q = em.createQuery("SELECT COUNT(u) FROM Utilisateur u WHERE u.profilIdprofil =  :profil ");
        q.setParameter("profil", p);
        Integer res =(Integer.valueOf(q.getSingleResult().toString())) ;
        return res ;
            
    }


}
