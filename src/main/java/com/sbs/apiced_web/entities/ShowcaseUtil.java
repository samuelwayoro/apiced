/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.apiced_web.entities;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

/**
 *
 * @author samuel
 */
class ShowcaseUtil {

    private ShowcaseUtil() {

    }



    public static final Object getPropertyValueViaReflection(Object o, String field)
                throws ReflectiveOperationException, IllegalArgumentException, IntrospectionException {
        return new PropertyDescriptor(field, o.getClass()).getReadMethod().invoke(o);
    }
    
}
