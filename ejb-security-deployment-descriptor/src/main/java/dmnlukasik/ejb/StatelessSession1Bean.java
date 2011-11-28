package dmnlukasik.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

public class StatelessSession1Bean extends CommonStatelessSessionBean {

    private StatelessSession2Bean bean2;

    public String greeting(final String name) {
        System.out.println("*** StatelessSession1Bean.greeting");
        printSecurityInfo();
        try {
            System.out.println("    Message for the superuser: " + bean2.superusersOnly());
        } catch (Throwable theException) {
            System.out.println("    No message for the superuser.");
        }
        String greetingFromBean2 = bean2.greeting(name);
        return assembleGreeting(name, "StatelessSession1Bean") + "\n" + greetingFromBean2;
    }
}