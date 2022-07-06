/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author samuel
 */
@ManagedBean
@ApplicationScoped
public class NavigationBean implements Serializable{

    /**
     * Creates a new instance of NavigationBean
     */
    public NavigationBean() {
    }
    
    public String redirectToLogin(){
        System.out.println("entree dans la deconnexion");
        //return "login.xhtml?faces-redirect=true";
        return "login";
    }
    
}
