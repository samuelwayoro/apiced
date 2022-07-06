/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Profil;
import com.sbs.apiced_web.entities.Utilisateur;
import java.util.List;
import javax.ejb.LocalBean;
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
@LocalBean
public class UtilisateurManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;

    /**
     * methode de connexion du user
     *
     * @param login
     * @param pass
     * @return
     */
    public Utilisateur findByLogin(String login, String pass) {
        Query query = em.createQuery("SELECT u from Utilisateur u where u.login = :login and u.motdepass =:pass");
        query.setParameter("login", login);
        query.setParameter("pass", pass);
        try {
            return (Utilisateur) query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            System.out.println("AUCUN UTILISATEUR TROUVE DANS LA BD AVEC SES CREDS");
            return null;
        }
    }
    
    /**
     * mthode de connexion a partir du login 
     * @param login
     * @return 
     */
    public Utilisateur findByUserLogin(String login){
        Query q = em.createQuery("SELECT u FROM Utilisateur u WHERE u.login = :login ");
        q.setParameter("login", login);
        try {
            return (Utilisateur) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("aucun utilisateur avec ce login");
            return null;
        }
    }
    
    /**
     * methode de verification de l'existance d'un utilisateur en bd
     * @param loginUtilisateur
     * @return 
     */
    public String verifExistanceUser(String loginUtilisateur){
        String resultat ;
        Query q = em.createQuery("SELECT count(u) FROM Utilisateur  u  WHERE u.login =:loginU");
        q.setParameter("loginU", loginUtilisateur);
            Long nbr = (Long) q.getSingleResult();
            if (nbr>(0L)) {
                resultat = "true";//un utilisateur avec ces infos existe deja 
            }else{
                System.out.println("AUCUN UTILISATEUR TROUVE DANS LA BD AVEC SES CREDS ON PEUX CREER L'UTILISATEUR AVEC LES INFOS ENTREES");
            resultat = "false";//il n'existe pas de user avec ces infos
            }
        System.out.println("valeur de Resultat : "+resultat);
        return resultat;
    }
    
public Utilisateur verif (String login){
        Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.login =  :userLogin");
        query.setParameter("userLogin", login);
        try {
            return (Utilisateur) query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            System.out.println("CE UTILISATEUR NE SE TROUVE PAS DANS LA BD "+e.getMessage());
            return null;
        }
    }
    
    
    public Utilisateur verifEmailUser(String userEmail){
        System.out.println("valeur de l'email : "+userEmail);
        Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE  u.email = :email");
        query.setParameter("email", userEmail);
        try {
            //return (Utilisateur)query.getSingleResult();
            Utilisateur u = (Utilisateur) query.getSingleResult();
            System.out.println("son nom est : "+u.getNom());
            return u;
        } catch (Exception e) {
            System.out.println("ce user n'existe pas en bd");
            return null;
        }
    }
    
    /**
     * methode de mise a jour du mot de passe de l'utilisateur en base de données
     * @param mdp
     * @param userLogin
     */
    public void updateUserPassword(String mdp , String userLogin){
//        Query q = em.createQuery("UPDATE Utilisateur u SET u.motdepass = :password  WHERE  u.login = :login");
//        q.setParameter("password", mdp);
//        q.setParameter("login", userLogin);
//        q.executeUpdate();
//        em.flush();
        
        Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.login = :login ");
        query.setParameter("login", userLogin);
        Utilisateur user = (Utilisateur) query.getSingleResult();
        user.setMotdepass(mdp);
        em.merge(user);
        
         
    }    
    //liste de tous les utilisateurs en bd 
    public List<Utilisateur> getAllActivedUsers(){
        //Query query = em.createNamedQuery("Utilisateur.allNewUsers");
        Query query = em.createQuery("SELECT u FROM Utilisateur u ");
        return query.getResultList();
    }
    
    //liste de tous les utilisateurs sauf celui qui consulte la liste
    public List<Utilisateur> getAllUsersWithoutUserCo(String login){
        //Query q = em.createNamedQuery("Utilisateur.allUsersWithoutUserCo");
        Query q = em.createQuery("SELECT u FROM Utilisateur u WHERE u.login <> :login");
        q.setParameter("login", login);
        return q.getResultList();
    }
    
    
    
    
    //retourner un utilisateur a la selection de son Id    
    public  Utilisateur getSelectedUser (Integer idUser){
        return em.find(Utilisateur.class, idUser);
    }
    
    /**
     * methode de persistence d'un nouveau profil
     * @param utilisateur
     */
    public void persist(Utilisateur utilisateur) {
        try {
            //System.out.println("entree dans la methode persist des utilisateurs");
            em.persist(utilisateur);
        }catch(ConstraintViolationException e){
            e.getConstraintViolations().forEach(actual -> {
                System.out.println(actual.toString());
            });
        }
    }
    
    //maj d'un utilisateur 
    public Utilisateur majEtatConnexion(Utilisateur u){
        return em.merge(u);
    } 

    /**
     * suppression d'un utilisateur
     * @param user 
     */
    public void deleteUser(Utilisateur user) {
        //recup de l'utilisateur
        Utilisateur u = this.getUtilisateur(user.getIdutilisateur());
         if (u!=null) {
            em.remove(em.merge(u));
            //System.out.println("suppresion de l'utilisateur executée avec succes");
        }
    }
    
      /**
     * methode permettant de modifier un profil a partir de son id
     * @param leUser
     * @return 
     */
    public Utilisateur updateUtilisateur(Utilisateur leUser){
        return em.merge(leUser);
    }
    


    private Utilisateur getUtilisateur(Integer idUser) {
        Utilisateur utilisateur = em.find(Utilisateur.class, idUser);
        return utilisateur;
    }
    
    /**
     * methode permettant de recuperer la valeur de l'activation de l'utilisateur selectionné en base
     * @param user
     * @return 
     */
    public Boolean recupStatuActifUser( Utilisateur user){
        Utilisateur u = em.find(Utilisateur.class, user.getIdutilisateur());
        return u.getActiver();
    }
    
    /**
     * mise a jour compte utilisateur : mes informations 
     * @param u
     * @return 
     */
    public int majInfosUser (Utilisateur u){
        Query q = em.createQuery("UPDATE Utilisateur u SET u.nom = :nom , u.prenoms = :prenoms , u.login = :login  WHERE  u.idutilisateur = :id ");
        q.setParameter("nom", u.getNom());
        q.setParameter("prenoms", u.getPrenoms());
        q.setParameter("login", u.getLogin());
        q.setParameter("id", u.getIdutilisateur());
        try {
            int r =  q.executeUpdate();
            System.out.println("val de r "+r);
            return r ;
        } catch (javax.persistence.NoResultException e) {
            return 0;
        }
    }

    public Profil recupProfil(String profilUtilisateur) {
        Query q = em.createQuery("SELECT p FROM Profil p WHERE p.nom= :nomP");
        q.setParameter("nomP", profilUtilisateur);
        return (Profil) q.getSingleResult();
    }
    
}
