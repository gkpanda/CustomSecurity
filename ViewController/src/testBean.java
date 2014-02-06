import java.io.IOException;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.security.auth.Subject;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

public class testBean {
    private RichPanelGroupLayout parent;

    public testBean() {
    }

    public void details(ActionEvent actionEvent) {
        // Add event code here...
        Subject sub =
            ADFContext.getCurrent().getSecurityContext().getSubject();
        System.out.println("sddsd");
        Set<Principal> principals = sub.getPrincipals();
    }

    public void impersonate(ActionEvent actionEvent) {
        //FacesContext context = FacesContext.getCurrentInstance();
        //ADFContext.getCurrent().getADFConfig().getConfigObject("http://xmlns.oracle.com/adf/security/config").put("oracle.adf.security.authorization.enforce", false);
        ADFContext.getCurrent().getSessionScope().put("isImpersonationOn",
                                                      "Y");
        List<String> userRoles = new ArrayList<String>();
        userRoles.add("CanSeeProtected");
        ADFContext.getCurrent().getSessionScope().put("userRoles", userRoles);
    }

    public void stop(ActionEvent actionEvent) {
        //ADFContext.getCurrent().getADFConfig().getConfigObject("http://xmlns.oracle.com/adf/security/config").put("oracle.adf.security.authorization.enforce", true);
        FacesContext context = FacesContext.getCurrentInstance();
        ADFContext.getCurrent().getSessionScope().put("isImpersonationOn",
                                                      "N");
        List<String> userRoles = new ArrayList<String>();
        ADFContext.getCurrent().getSessionScope().put("userRoles", userRoles);
    }

    public void setParent(RichPanelGroupLayout parent) {
        this.parent = parent;
    }

    public RichPanelGroupLayout getParent() {
        return parent;
    }

    public void logout(ActionEvent actionEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        String url =
            ectx.getRequestContextPath() + "/adfAuthentication?logout=true&end_url=/faces/Details.jspx";
        try {
            ectx.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fctx.responseComplete();
    }
}
