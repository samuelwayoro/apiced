/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.managedBeans;

import com.sbs.apiced_web.entities.TextExporter;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;

/**
 *
 * @author samuel
 */
@Named(value = "dataExporterView")
@ViewScoped
public class DataExporterView  implements Serializable{

    private Exporter<DataTable> textExporter;

 
    @PostConstruct
    public void init() {
        textExporter = new TextExporter();
    }




    public Exporter<DataTable> getTextExporter() {
        return textExporter;
    }

    public void setTextExporter(Exporter<DataTable> textExporter) {
        this.textExporter = textExporter;
    }
    
}
