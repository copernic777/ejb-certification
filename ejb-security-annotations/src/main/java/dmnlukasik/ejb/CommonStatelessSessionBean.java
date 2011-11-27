package dmnlukasik.ejb;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import java.security.Principal;
import java.util.Date;

public class CommonStatelessSessionBean {

    protected static int currentInstanceNumber = 1;

    @Resource
    protected SessionContext sessionContext;
    protected int instanceNumber;

    public CommonStatelessSessionBean() {
        instanceNumber = currentInstanceNumber++;
    }

    protected void printSecurityInfo() {
        if (sessionContext != null) {
            /*
             * Container may never return a null principal, so we don't
             * need to make sure it is not null.
             */
            Principal principal = sessionContext.getCallerPrincipal();
            System.out.println("Principal name: " + principal.getName());
            System.out.println("Principal object: " + principal);
            System.out.println("Principal type: " + principal.getClass());
            testCallerRole("superusers");
            testCallerRole("plainusers");
            testCallerRole("ivan");
        } else {
            System.out.println("    No session context available.");
        }
    }

    private void testCallerRole(String role) {
        try {
            System.out.println("Caller in '" + role + "' role? " + sessionContext.isCallerInRole(role));
        } catch (Throwable e) {
            System.out.println("Cannot determine caller role: '" + role + "'");
        }
    }

    protected String assembleGreeting(String inName, String inBeanName) {
        return "Hello " + inName + ", " + inBeanName + ", instance: " +
                instanceNumber + ". The time is now: " + new Date();
    }
}