/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.utility;

import com.sbs.apiced_web.entities.Maitrecommunautaire;
import com.sbs.apiced_web.services.MaitreCoManager;
import java.math.BigDecimal;
import javax.ejb.EJB;

/**
 *
 * @author samuel
 */
public class Utility {

    private static int numInterneMaitre;
    private static String matricule;
    private static Maitrecommunautaire mc = new Maitrecommunautaire();

    /**
     * EJB UTILISES
     *
     * @return
     */
    @EJB
    private static MaitreCoManager mcMgr;

    public static int getNumInterneMaitre() {
        return numInterneMaitre;
    }

    public static void setNumInterneMaitre(int numInterneMaitre) {
        Utility.numInterneMaitre = numInterneMaitre;
    }

    public String getMatricule() {
        return matricule;
    }

    public Maitrecommunautaire getMc() {
        return mc;
    }

    public void setMc(Maitrecommunautaire mc) {
        this.mc = mc;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Utility() {
        numInterneMaitre++;
    }

    /*
     * Methode de Bourrage à Gauche de Zero par defaut : provient de webclring
     */
    static String bourrageGZero() {
//        if (chaine == null) {
//            chaine = "";
//        }
        String chaine = String.valueOf(numInterneMaitre);
        chaine = chaine.trim();
        while (chaine.length() < 10) {
            chaine = "0" + chaine;
        }
        return chaine;
    }

    /**
     * generation des matricules des MCS
     */
    public static void generationMatricule() {

        String prefixMC = "MC";
        //dernier ID 
        BigDecimal x = (BigDecimal) mcMgr.getLastMcId();
        Integer i = x.intValue();
        System.out.println("la valeur de son id   " + ++i);
        String lastMcId = i.toString();
        //initiaux Bailleur en fonction du choix 
        String initalBailleur;
        if (mc.getBailleur().equalsIgnoreCase("PARSET2")) {
            initalBailleur = "PRST";
        } else {
            initalBailleur = "PREA";
        }

        //recup du genre choisi
        String prefixGenre = mc.getGenre();
        String genre;
        if (prefixGenre.equalsIgnoreCase("M")) {
            genre = "M";
        } else {
            genre = "F";
        }
        //System.out.println("le genre du mc  :" + prefixGenre);
        matricule = prefixMC + initalBailleur + lastMcId + genre;
    }

}
