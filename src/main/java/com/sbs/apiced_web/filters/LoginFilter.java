/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author samuel
 */
public class LoginFilter implements Filter {

    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String contextPath = ((HttpServletRequest) request).getContextPath();

        String utilisateurEstConnecte = "";

        try {
            //recupeartion de la valeur en session de "utilisateurEstConnecte" , true si le user est bien identifié depuis la bd 
            utilisateurEstConnecte = ((HttpServletRequest) request).getSession().getAttribute("utilisateurEstConnecte").toString();
            System.out.println("VALEUR DE UTILISATEURESTCONNECTE :       " + utilisateurEstConnecte);
            //System.out.println("recup de l'adresse ip de l'utilisateur :  ********************"+request.getRemoteAddr());

        } catch (Exception e) {
            System.out.println("essai d'accès au page privee par le user");
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
        }

        if (utilisateurEstConnecte == null || utilisateurEstConnecte == "") {
            System.out.println("l'utilisateur est il connecté  : " + utilisateurEstConnecte);
//            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
