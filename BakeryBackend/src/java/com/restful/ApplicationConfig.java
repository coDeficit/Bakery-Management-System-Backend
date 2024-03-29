/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restful;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author CODEFICIT
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.controllers.CategoryController.class);
        resources.add(com.controllers.CustomerController.class);
        resources.add(com.controllers.EmployeeController.class);
        resources.add(com.controllers.ItemController.class);
        resources.add(com.controllers.JobController.class);
        resources.add(com.controllers.OrderStatusController.class);
        resources.add(com.controllers.PayMethodController.class);
        resources.add(com.controllers.RoleController.class);
        resources.add(com.controllers.UserController.class);
        resources.add(com.restful.GenericResource.class);
    }
    
}
