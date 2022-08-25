/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sbs.apiced_web.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
import org.apache.commons.collections.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.filter.FilterConstraint;
import org.primefaces.util.LocaleUtils;

/**
 *
 * @author SamuelWAYORO
 */
public class LazyMaitresCoDataModel extends LazyDataModel<Maitrecommunautaire> {

    private List<Maitrecommunautaire> dataSource = new ArrayList<>();

    public LazyMaitresCoDataModel(List<Maitrecommunautaire> dataSource) {
        this.dataSource = dataSource;
    }

    public LazyMaitresCoDataModel() {
    }
    
    

    @Override
    public Maitrecommunautaire getRowData(String rowKey) {
        for (Maitrecommunautaire maitre : dataSource) {
            if (maitre.getId() == new BigDecimal(rowKey)) {
                return maitre;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(Maitrecommunautaire mc) {
        return String.valueOf(mc.getId());
    }

    public int count(Map<String, FilterMeta> filterBy) {
        return (int) dataSource.stream()
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .count();
    }

    @Override
    public List<Maitrecommunautaire> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        System.out.println("val de offset   " + offset + " val de pagesize " + pageSize + "  val de sortBy " + sortBy.size() + " taille de filterBy  " + filterBy.size());
       
        List<Maitrecommunautaire> maitreCos = dataSource.stream()
                .skip(offset)
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .limit(pageSize)
                .collect(Collectors.toList());
        
        if (!sortBy.isEmpty()) {
            List<Comparator<Maitrecommunautaire>> comparators = sortBy.values().stream()
                    .map(o -> new LazySorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<Maitrecommunautaire> mc = ComparatorUtils.chainedComparator(comparators);
            maitreCos.sort(mc);
        }

//        System.out.println("TAILLE DE LA LISTE LAZY LOADING DE MC "+maitreCos.size());
        for (Maitrecommunautaire maitreCo : maitreCos) {
            System.out.println("---->" + maitreCo.getNom() + "  " + maitreCo.getPrenoms());
        }

        return maitreCos;
    }

    private boolean filter(FacesContext context, Collection<FilterMeta> filterby, Object o) {
        boolean matching = true;

        for (FilterMeta filter : filterby) {
            FilterConstraint constraint = filter.getConstraint();
            Object filterValue = filter.getFilterValue();

            try {
                Object columnValue = String.valueOf(ShowcaseUtil.getPropertyValueViaReflection(o, filter.getField()));
                matching = constraint.isMatching(context, columnValue, filterValue, LocaleUtils.getCurrentLocale());

            } catch (Exception e) {
                matching = false;
            }

            if (!matching) {
                break;
            }
        }

        return matching;
    }

}
