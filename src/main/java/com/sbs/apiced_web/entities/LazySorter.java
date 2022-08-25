/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sbs.apiced_web.entities;

import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author SamuelWAYORO
 */
public class LazySorter implements Comparator<Maitrecommunautaire>{
    
    private String sortField;
    private SortOrder sortOrder;

    LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField ;
        this.sortOrder = sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    

    @Override
    public int compare(Maitrecommunautaire o1, Maitrecommunautaire o2) {
         try {
            Object value1 = ShowcaseUtil.getPropertyValueViaReflection(o1, sortField);
            Object value2 = ShowcaseUtil.getPropertyValueViaReflection(o2, sortField);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
