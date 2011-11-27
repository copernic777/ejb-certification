package dmnlukasik.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
/*
 * The roles that are to be used from within the code when invoking
 * isCallerInRole need to be declared using the @DeclareRoles annotation,
 * otherwise an exception will be thrown when calling isCallerInRole.
 */
@DeclareRoles({"superusers", "plainusers"})
/*
 * When applied at class-level, the @RolesAllowed annotation specifies
 * which security-roles are allowed to access all the methods in the
 * EJB. @RolesAllowed may also be used at method-level.
 */
@RolesAllowed({"superusers", "plainusers"})
public class StatelessSession1Bean extends CommonStatelessSessionBean {

    public String greeting(final String name) {
        System.out.println("*** StatelessSession1Bean.greeting");
        printSecurityInfo();
        return assembleGreeting(name, "StatelessSession1Bean");
    }
}