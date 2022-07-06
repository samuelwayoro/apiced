/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.apiced_web.entities.Transactions;
import com.sbs.apiced_web.services.TransactionsManager;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author samuel
 */
@Named(value = "transactionsPaiementsMBean")
@ViewScoped
public class transactionsPaiementsMBean implements Serializable{
    
    private Integer info;
    List<Transactions> listeTransactions ;
    
        @EJB
    private TransactionsManager transacMgr;

    /**
     * Creates a new instance of transactionsPaiementsMBean
     */
    public transactionsPaiementsMBean() {
    }

    public Integer getInfo() {
        return info;
    }

    public void setInfo(Integer info) {
        this.info = info;
    }



  

    /***
     * retoune les details des transactions 
     * @return 
     */
    public List<Transactions> getListTransactions(){
        return listeTransactions;
    }
    
    
    /***
     * methode recuperation des listes des transactions d'un paiement choisi
     */
    public void loadTransactions(){
        listeTransactions = transacMgr.selectedPaieTransactions(info);
    }
    
    public void genratePaie(){
        System.out.println("genration du fichier json...");
        ObjectMapper mapper = new ObjectMapper();
        List<Transactions> lesTransactions = listeTransactions;
        //generation du fichier json 
        File f = new File("D:/transactions.json");
        try {
            mapper.writeValue(f, lesTransactions);
        } catch (IOException ex) {
            Logger.getLogger(transactionsPaiementsMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
