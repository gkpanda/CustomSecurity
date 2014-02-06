package com.randomcompany.security;

import java.security.Permission;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import oracle.adf.share.ADFContext;
import oracle.adf.share.security.providers.jps.JpsSecurityContext;

public class MySecurityContext extends JpsSecurityContext {
    public MySecurityContext(Map map) {
        
        super(map);
    }

    public MySecurityContext() {
        super();
    }
    
    private String impersonationOn = "N";

    @Override
    public boolean isAuthorizationEnabled() {
        return super.isAuthorizationEnabled();        
    }
    
    /**
     * Overriden method . If impersonation is not enabled then calls the superclass' method.
     * Else checks the role against sessionScope list object created during impersonation
     */

    @Override
    public boolean isUserInRole(String string) {
        String imp = (String)ADFContext.getCurrent().getSessionScope().get("isImpersonationOn");
        if(imp == null || !imp.equals("Y")){
            return super.isUserInRole(string);
        }
        else{
            List<String> userRoles = (List<String>)ADFContext.getCurrent().getSessionScope().get("userRoles");
            if(userRoles != null && userRoles.contains(string)){
                return true;
            }
            else{
                return false;
            }
            
        }
        
    }
    /**
     * Overridden method. Calls supercalls' method if impersonation disabled.
     * Else return true ( mimicking the behavior when authorization disabled)
     * The onus of actually rendering the appropriate taskflow falls on the rendered condition in the af:region , in such a scenario
     */

    @Override
    public boolean hasPermission(Permission permission) {
   //     FacesContext context = FacesContext.getCurrentInstance();
        String imp = (String)ADFContext.getCurrent().getSessionScope().get("isImpersonationOn");
//        String imp = (String)context.getExternalContext().getSessionMap().get("isImpersonationOn");
        if(imp == null || !imp.equals("Y")){
            return super.hasPermission(permission);
        }
        else{
            return true;
            
        }
        
    }

    public void setImpersonationOn(String impersonationOn) {
        this.impersonationOn = impersonationOn;
    }

    public String getImpersonationOn() {
        return impersonationOn;
    }
}
