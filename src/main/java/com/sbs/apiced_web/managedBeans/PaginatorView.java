///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.sbs.apiced_web.managedBeans;
//
//import java.io.Serializable;
//import java.util.List;
//import javax.annotation.PostConstruct;
//import javax.faces.view.ViewScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//
///**
// *
// * @author samuel
// */
//@Named("dtPaginatorView")
//@ViewScoped
//public class PaginatorView implements Serializable {
//
//    private List<Customer> customers;
//
//    @Inject
//    private CustomerService service;
//
//    @PostConstruct
//    public void init() {
//        customers = service.getCustomers(50);
//    }
//
//    public List<Customer> getCustomers() {
//        return customers;
//    }
//
//    public void setService(CustomerService service) {
//        this.service = service;
//    }
//}
