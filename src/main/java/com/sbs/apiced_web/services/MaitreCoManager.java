/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.services;

import com.sbs.apiced_web.entities.Categorie;
import com.sbs.apiced_web.entities.Etablissement;
import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.entities.Operateur;
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
public class MaitreCoManager {

    @PersistenceContext(unitName = "myPersistence")
    private EntityManager em;

    /**
     * nbre de maitre communautaire en fonction du nom
     *
     * @param nomOp
     * @return
     */
    public Long nbreMcByNomOp(String nomOp) {
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.operatortelco = :nom AND m.validationcoordonnateur = :etatV ");
        q.setParameter("nom", nomOp);
        q.setParameter("etatV", Boolean.TRUE);
        return (Long) q.getSingleResult();
    }

    /**
     * nbre de mc en fonction de la categorie
     *
     * @param nomCategorie
     * @return
     */
    public Integer nbreMcByCateg(String nomCategorie) {
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.categoriepro = :libcate AND m.validationcoordonnateur = :etatV  ");
        q.setParameter("libcate", nomCategorie);
        q.setParameter("etatV", Boolean.TRUE);
        Long val = (Long) q.getSingleResult();
        int i = val.intValue();
        return i;
    }

    /**
     * methode retournant la liste de tous les mc par ordre decroissant
     *
     * @return
     */
    public List<Maitrecommunautaire> allMc() {
        Query qr = em.createQuery("SELECT m FROM Maitrecommunautaire m ORDER BY m.id DESC");
        return qr.getResultList();
    }

    //liste de tous les mc a afficher dans l'annuaire 
    public List<Maitrecommunautaire> listeAnnuaire() {
        //String val = "1";
        // Boolean etatV = Boolean.TRUE;
        //Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.numerointerne <> :one AND m.validationcoordonnateur = :etat");
        //q.setParameter("one", val);
        //q.setParameter("etat", etatV);
        //Query q = em.createNativeQuery("SELECT *  FROM (SELECT   ID, CONTACTDEUX ,  DATECREATIONCOMPTE , DATEMODIFCOMPTE , DATENAISSANCE , DATEPRISEFONCTION , DOMICILE , LIEUDENAISSANCE , MATRICULE , NIVEAUSCOLAIRE  , NOM , PRENOMS , STATUTWALLET , ETABLISSEMENT , IDCATEGORIEPRO , IDCREATEUR ,  IDVALIDEURCOMPTE , ETATCOMPTEMC , DATEACTIVATIONCOMPTE  , DATEDESACTIVATIONCOMPTE, VALIDATIONCOORDONNATEUR  , DATERETRAITE , DATESUSPENSION , GENRE , DEMANDESUSP , MOTIFSUSPENSION , NBREENFANTS , SITMATRIMONIAL , DERNIERDIPLOME , CLASSEAFFECTEE ,OPERATORTELCO ,NUMEROINTERNE ,ETATRETRAITE,MOTIFREJETVALIDATION ,DATEREJETVALIDATION ,REJETVALIDATION ,VALEUREETATRETRAITE , VALEURESTATUTWALLET, STATUTCOMPTE , TYPEPIECEADMINISTRATIVE , NOMPIECEADMNINISTRATIVE  ,LANGUE , MILIEU_RESIDENCE  FROM MAITRECOMMUNAUTAIRE WHERE  VALIDATIONCOORDONNATEUR = ?1  ORDER BY ID DESC) WHERE ROWNUM <=2");
        //q.setParameter(1, val);
        //q.setParameter(1, etatV);
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m ORDER BY m.id DESC");
        return q.getResultList();
    }

    /**
     * retourne la liste de tous les mc qui sont activé par le(s)
     * coordonnateur(s)
     *
     * @return
     */
    public List<Maitrecommunautaire> allActivedMc() {
        Boolean etatActif = Boolean.TRUE;
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.etatcomptemc = :etatActif ORDER BY m.nom");
        q.setParameter("etatActif", etatActif);
        return q.getResultList();
    }

    /**
     * liste de tous les maitre de la base de données
     *
     * @return
     */
    public List<Maitrecommunautaire> allMaitreCommunautaires() {
        Boolean val = Boolean.TRUE;
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m where m.validationcoordonnateur = :val ");
        q.setParameter("val", val);
        return q.getResultList();
    }

    public List<Maitrecommunautaire> allActivedMcGest() {
        Boolean etatActif = Boolean.TRUE;
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.etatcomptemc = :etatActif ORDER BY m.nom");
        q.setParameter("etatActif", etatActif);
        return q.getResultList();
    }

    //la liste des MCS de AIRTEL    
    public List<Maitrecommunautaire> getAirtelMcs() {
        String val = "Airtel";
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.operatortelco = :op");
        q.setParameter("op", val);
        return q.getResultList();
    }

    //la liste des MCS de Moov Africa
    public List<Maitrecommunautaire> getMoovAfricaMcs() {
        String val = "Moov";
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.operatortelco = :op");
        q.setParameter("op", val);
        System.out.println("total de mcs moov depuis maitrecomanager  "+q.getResultList().size());
        return q.getResultList();
    }

    //la liste des mcs en fonction du nom de l'operateur
    public List<Maitrecommunautaire> mcsByOpTelcoName(String opName) {
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.operatortelco = :name");
        q.setParameter("name", opName);
        return q.getResultList();
    }

    //une liste de mcs en a partir du noms et prenoms saisis
    public List<Maitrecommunautaire> mcsByNameFirstname(String nom, String prenoms) {
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.nom = :nom and m.prenoms = :prenoms");
        q.setParameter("nom", nom);
        q.setParameter("prenoms", prenoms);
        return q.getResultList();
    }

    //renvoi le nombre total de maitres co dans la bd 
    public Long nbreTotalDesMcs() {
        System.out.println("recup du last id des mcs ");
        Query q = em.createQuery("Select COUNT (m) FROM Maitrecommunautaire m ");
        System.out.println("---->"+(Long) q.getSingleResult());
        return (Long) q.getSingleResult();
    }

    public List<Maitrecommunautaire> rechercherParNomPrenoms(String nom, String prenoms) {
        Boolean val = Boolean.TRUE;
        Query qr = em.createQuery("Select m FROM Maitrecommunautaire m WHERE m.nom like :nom  and m.prenoms like :prenoms and m.validationcoordonnateur = :val ");
        qr.setParameter("nom", "%" + nom + "%");
        qr.setParameter("prenoms", "%" + prenoms + "%");
        qr.setParameter("val", val);
        return qr.getResultList();
    }
    
    public List<Maitrecommunautaire>searchByOpAndCtg(String operateur,String cate){
        Query q = em.createQuery("select m from Maitrecommunautaire m WHERE m.operatortelco = :op and m.categoriepro = :ctg");
        q.setParameter("op", operateur);
        q.setParameter("ctg", cate);
        return q.getResultList();
    }

    public List<Maitrecommunautaire> rechercherParNom(String nom) {
        Boolean val = Boolean.TRUE;
        Query q = em.createQuery("Select m FROM Maitrecommunautaire m WHERE m.nom like :nom and m.validationcoordonnateur = :val");
        q.setParameter("nom", "%" + nom + "%");
        q.setParameter("val", val);
        return q.getResultList();
    }

    public List<Maitrecommunautaire> rechercherParPrenoms(String prenomsMc) {
        Boolean val = Boolean.TRUE;
        Query q = em.createQuery("Select m FROM Maitrecommunautaire m WHERE m.prenoms like :prenoms and m.validationcoordonnateur = :val");
        q.setParameter("prenoms", "%" + prenomsMc + "%");
        q.setParameter("val", val);
        return q.getResultList();
    }

    public List<Maitrecommunautaire> rechercherParMatricule(String matricule) {
        Boolean val = Boolean.TRUE;
        Query q = em.createQuery("Select m FROM Maitrecommunautaire m WHERE m.matricule = :mat and m.validationcoordonnateur = :val");
        q.setParameter("mat", matricule);
        q.setParameter("val", val);
        return q.getResultList();
    }

    //retourne une liste de taille 1 de Maitres co
    public List<Maitrecommunautaire> McByPhone(String num) {
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.contactun = :number");
        q.setParameter("number", num);
        System.out.println("le resultat  --->" + q.getResultList().size());
        return q.getResultList();
    }

    //modification du nom de l'operateur du mc
    public void upOpName() {

    }

    //liste des maitre abonnés airtel qu'on peux payer , statut wallet actif 
    public List<Maitrecommunautaire> listeMcAirtelPayable() {
        Boolean LeStatut = Boolean.TRUE;
        String op = "AIRTEL";
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.statutwallet = :statut  AND m.operatortelco = :ope");
        q.setParameter("statut", LeStatut);
        q.setParameter("ope", op);
        return q.getResultList();
    }

    //liste des mcs de tigo
    public List<Maitrecommunautaire> getTigoMcs() {
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.operatortelco = :operateurId");
        q.setParameter("operateurId", 2);
        return q.getResultList();
    }

    /**
     * methode retournant la liste des noms de tous les noms de categorie
     *
     * @return
     */
    public List<String> getAllCategorieName() {
        //Query query = em.createNamedQuery("Profil.allNames");
        Query q = em.createQuery("SELECT c.libelle FROM Categorie c");
        return (List<String>) q.getResultList();
    }

    public List<String> getAllEtablissementsName() {
        Query q = em.createQuery("SELECT e.nom FROM Etablissement e");
        return (List<String>) q.getResultList();
    }

    public List<String> getOpNames() {
        Query q = em.createQuery("SELECT o.nom FROM Operateur o");
        return (List<String>) q.getResultList();
    }

    public void persist(Maitrecommunautaire mc) {
        try {
            System.out.println("entree dans la methode persist des maitre communautaires");
            em.persist(mc);
            em.flush();
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(actual -> {
                System.out.println(actual.toString());
            });
        }
    }

    //persister un mc a partir d'un fichier excel 
    public Boolean persistByExcelFile(Maitrecommunautaire mc) {
        //verifier si il y deja un mc avec ce contact dans la table des mcs 
        List<Maitrecommunautaire> listeVerif = McByPhone(mc.getContactun());
        if (!listeVerif.isEmpty()) {
            System.out.println("il ya deja un mc avec ce numero de phone....");
            return Boolean.FALSE;
        } else {
            System.out.println("on peux l'enregistrer ...");
            return Boolean.TRUE;
        }

//        //verifier si il y deja un mc avec ce contact dans la table des mcs 
//        List<Maitrecommunautaire> listeVerif = McByPhone(mc.getContactun());
//        //si c'est le cas afficher un msg d'erreur
//        if (listeVerif.size() > 0) {
//                System.out.println("il ya deja un mc avec ce numero de phone....");
//        } else {
//            try {
//                //System.out.println("entree dans la methode persist des maitre communautaires");
//                em.persist(mc);
//            } catch (ConstraintViolationException e) {
//                e.getConstraintViolations().forEach(actual -> {
//                    System.out.println(actual.toString());
//                });
//            }
//
//        }
    }

    public Long verifUniciteMatricule(String matricule) {
        //Query q = em.createNamedQuery("Maitrecommunautaire.findByMatricule");
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.matricule = :matricule");
        q.setParameter("matricule", matricule);
        //Maitrecommunautaire leMc = (Maitrecommunautaire) q.getSingleResult();
        return (Long) q.getSingleResult();
    }
    
    /***
     * retourne le nombre de maitre co pour une categorie donnee
     * @param cate
     * @return 
     */
    public Long loadMcByCategoripro(String cate){
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.categoriepro = :catePro");
        q.setParameter("catePro", cate);
        return (Long) q.getSingleResult();
    }
    
    /***
     * retourne le nbre de maitre communautaire a partir du nom d'un DREJ
     * @param nomDrej
     * @param 
     * @return 
     */
    public Long nbreMcByDrej(String nomDrej){
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.drej = :drej");
        q.setParameter("drej", nomDrej);
        return (Long) q.getSingleResult();
    }

    //renvoyer un mc a partir de son matricule 
    public Maitrecommunautaire loadMcByMatricule(String leMatricule) {
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.matricule = :matricule");
        q.setParameter("matricule", leMatricule);
        return (Maitrecommunautaire) q.getSingleResult();
    }

    //renvoyer un mc a partir de son numero phone 
    public Maitrecommunautaire loadMcByPhoneNumber(String phone) {
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.contactun = :phone");
        q.setParameter("phone", phone);
        try {
            return (Maitrecommunautaire) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Maitrecommunautaire getMaitreCoInfo(BigDecimal id) {
        return em.find(Maitrecommunautaire.class, id);
    }

    public Operateur getProfilByIdOp(BigInteger operatortelco) {
        Query q = em.createQuery("SELECT o FROM Operateur o WHERE o.idoperateur = :idop");
        q.setParameter("idop", operatortelco);
        return (Operateur) q.getSingleResult();
    }

    //retourne le nom de tous les établissements sauf celui du mc selectionné
//    public List<String> getAllEtablissementsNameWithoutMcEts(String idEtablissement) {
//        Query q = em.createQuery("SELECT e.nom FROM Etablissement e WHERE e.idetablissement <> :ide");
//        q.setParameter("ide", idEtablissement);
//        return q.getResultList();
//    }
    //retourne le nom de la liste de tous les opérateurs sauf celui du mc selectionné
    public List<String> getAllOperateurNameWithoutMcOp(String Op) {
        Query q = em.createQuery("SELECT o.nom FROM Operateur o WHERE o.nom <> :idNomOp");
        q.setParameter("idNomOp", Op);
        return q.getResultList();
    }

    //retourne le nom de l'opérateur du mc selectionné
    public String getOperateurName(String operatortelco) {
        Query q = em.createQuery("SELECT o.nom FROM Operateur o WHERE o.nom = :nomOp ");
        q.setParameter("nomOp", operatortelco);
        return (String) q.getSingleResult();
    }

    /**
     * methode permettant de modifier les infos d'un mc
     *
     * @param mc
     * @return
     */
    public Maitrecommunautaire updateMaitre(Maitrecommunautaire mc) {
        System.out.println("mise a jour du maitre en cours ....");
        return em.merge(mc);

    }

    //retouner la liste de tous les maitres creer par un utilisateur pour modification avant soummission au coordonnateur
    public List<Maitrecommunautaire> listeMcEnAttenteDeValidation(Utilisateur user, Boolean etat) {
        Query query = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.etatcomptemc = :etat and m.idcreateur = :createur");
        query.setParameter("createur", user);
        query.setParameter("etat", etat);
        return query.getResultList();
    }

    //retourne la liste de tous les maitres a partir de l'id de l'opérateur 
    public List<Maitrecommunautaire> listeMcByIdOperateur(Integer idOperateur) {
        Query qr = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.operatortelco = :idOp");
        qr.setParameter("idOp", idOperateur);
        return qr.getResultList();
    }

    public Etablissement getEtablissementByName(String nomEtablissement) {
        Query q = em.createQuery("SELECT e FROM Etablissement e WHERE e.nom = :nomEts");
        q.setParameter("nomEts", nomEtablissement);
        return (Etablissement) q.getSingleResult();
    }

    public String getEtablissementNameById(String idEtablissement) {
        Query q = em.createQuery("SELECT  e.nom  FROM Etablissement e WHERE e.idetablissement = :id");
        q.setParameter("id", idEtablissement);
        return (String) q.getSingleResult();
    }

    public Integer getIdOpByName(String operateurMc) {
        Query q = em.createQuery("SELECT o.idoperateur FROM Operateur o WHERE o.nom = :nom");
        q.setParameter("nom", operateurMc);
        return (Integer) q.getSingleResult();
    }

    public List<String> getAllCategorieNameWithoutMcCategorie(String categorie) {
        Query q = em.createQuery("SELECT c.libelle FROM Categorie c WHERE c.libelle <> :nomCate");
        q.setParameter("nomCate", categorie);
        return q.getResultList();
    }

    public Categorie getCategorieByName(String nomCategorie) {
        Query q = em.createQuery("SELECT c FROM Categorie c WHERE c.libelle = :nomCate");
        q.setParameter("nomCate", nomCategorie);
        return (Categorie) q.getSingleResult();
    }

    public List<Maitrecommunautaire> allActifMc(Categorie c) {
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.idcategoriepro <> :id");
        q.setParameter("id", c);
        return q.getResultList();
    }

    public List<Maitrecommunautaire> maitreCoWalletActif() {
        Boolean etatWallet = Boolean.TRUE;
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.statutwallet = :etatWallet ");
        q.setParameter("etatWallet", etatWallet);
        return q.getResultList();
    }
//    
//    //mc abonnes airtel : id = 1
//    public Long listeAbonnesAirtel(){
//        Integer idDeLoperateur = 1;
//        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.id = :idOperateur and m.etatcomptemc > 0");
//        q.setParameter("idOperateur", idDeLoperateur);
//        return (Long) q.getSingleResult();
//    }

//    //liste des mc abonnées tigo : id =2
//        public Long listeAbonnesTigo(){
//        Integer idDeLoperateur = 2;
//        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.id = :idOperateur and m.etatcomptemc > 0");
//        q.setParameter("idOperateur", idDeLoperateur);
//        return (Long) q.getSingleResult();
//    }
//
//        //qte de demande de creation de mc en cours
//    public Long QtesDemandeCreaMc() {
//        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE  m.etatcomptemc = 0");
//        return (Long) q.getSingleResult();
//    }
//    //nbre de demande de suppression de mc
//    public Long QtesDemandeDelMc() {
//        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE  m.demandesupp > 0   ");
//        return (Long) q.getSingleResult();
//    }
//
//    public Long QtesDemandeUpdateMc() {
//        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.demandemodif > 0 ");
//        return (Long) q.getSingleResult();
//    }
//
//    public Long QtesDemandeSuspension() {
//        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.datedemandesusp >0 ");
//        return (Long) q.getSingleResult();
//    }
    //retourner un profil par son id
    public Maitrecommunautaire getmaitreCommunautaire(BigDecimal idMaitre) {
        return em.find(Maitrecommunautaire.class, idMaitre);
    }

    /**
     * methode permettant supprimer un mc
     *
     * @param idMaitre
     */
    public void deleteProfil(BigDecimal idMaitre) {
        Maitrecommunautaire mc = this.getmaitreCommunautaire(idMaitre);
        if (mc != null) {
            em.remove(em.merge(mc));
            System.out.println("suppresion du maitre executé avec succes");
        }
    }

    public List<Maitrecommunautaire> listeMcByIdOperateur(String choixOperateur) {
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.operatortelco = :idOp");
        q.setParameter("idOp", choixOperateur);
        return q.getResultList();
    }

    public List<Maitrecommunautaire> getAnnuaire() {
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m");
        return q.getResultList();
    }

    /**
     * retourne la valeure numeroInterne du dernier mc en base
     *
     * @return
     */
    public String getLastMcInternNumber() {
        //Query q = em.createQuery("SELECT m.numerointerne FROM Maitrecommunautaire m ORDER BY m.id DESC");
        Query q = em.createNativeQuery("select numerointerne from maitrecommunautaire where id = (select max(id) from maitrecommunautaire)");
        return (String) q.getSingleResult();
    }

    /**
     * *
     * retourne le denier id
     *
     * @return
     */
    public Object getLastMcId() {
        Query q = em.createNativeQuery("select max(id) from maitrecommunautaire");
        return q.getSingleResult();

    }



    //liste des mcs a payer chez moov africa : ceux dont le wallet est actif chez l'opérateur
    public List<Maitrecommunautaire> getMcsPayables(String nomOp) {
        System.out.println("nom de l'opérateur  "+nomOp);
        Boolean statut = Boolean.TRUE;
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.operatortelco = :nomOperateur AND m.statutwallet = :statutwallet");
        q.setParameter("nomOperateur", nomOp);
        q.setParameter("statutwallet", statut);
        return q.getResultList();
    }

    public List<Maitrecommunautaire> recupMcPayable(String nomOperateur) {
        Boolean etatWalette = Boolean.TRUE;
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.operatortelco = :nomOperateur AND m.statutwallet = :etatWallet ");
        q.setParameter("nomOperateur", nomOperateur);
        q.setParameter("etatWallet", etatWalette);
        return q.getResultList();
    }

    public List<Maitrecommunautaire> listeMcAvalider() {
        Boolean etat = Boolean.FALSE;
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.validationcoordonnateur = :etatValidation");
        q.setParameter("etatValidation", etat);
        return q.getResultList();
    }

    //listes des mc a corriger 
    public List<Maitrecommunautaire> listeMcAcorriger() {
        Boolean etat = Boolean.TRUE;
        Boolean rejet = Boolean.TRUE;
        Query q = em.createQuery("SELECT m FROM Maitrecommunautaire m WHERE m.validationcoordonnateur = :etatValidation AND m.rejetvalidation = :rejet");
        q.setParameter("etatValidation", etat);
        q.setParameter("rejet", rejet);
        return q.getResultList();
    }

    public Long nbreMcAvalider() {
        Boolean e = Boolean.FALSE;
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.validationcoordonnateur = :etat ");
        q.setParameter("etat", e);
        return (Long) q.getSingleResult();
    }

    public Long nbreMcAcorriger() {
        Boolean e = Boolean.TRUE;
        Boolean rejet = Boolean.TRUE;
        Query q = em.createQuery("SELECT COUNT(m) FROM Maitrecommunautaire m WHERE m.validationcoordonnateur = :etat AND m.rejetvalidation = :rejet ");
        q.setParameter("etat", e);
        q.setParameter("rejet", rejet);
        return (Long) q.getSingleResult();
    }

}
