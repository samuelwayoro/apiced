/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author samuel
 */
@Named(value = "basicView")
@RequestScoped
public class BasicView  implements Serializable{

    private int number;
    
    /**
     * Creates a new instance of BasicView
     */
    public BasicView() {
    }
    
     public void increment() {
        number++;
    }
     
     public int getNumber() {
        return number;
    }
}
