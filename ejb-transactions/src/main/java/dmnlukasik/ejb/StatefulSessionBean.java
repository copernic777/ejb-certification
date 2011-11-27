package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StatefulSessionBean implements SessionSynchronization {
    private static int currentInstanceNumber = 1;

    private int instanceNumber;
    private int callCounter;
    @Resource
    private SessionContext sessionContext;

    @PostConstruct
    public void initialize() {
        instanceNumber = currentInstanceNumber++;
        System.out.println("*** StatelessSession1Bean " + instanceNumber + " created: " + new Date());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String greeting(final String inName) {
        Date theCurrentTime = new Date();
        String message = "";
        callCounter++;

        /* Do what is to be done inside the transaction. */
        message = "Hello " + inName + ", I am stateless session bean " +
                instanceNumber + ". The time is now: " + theCurrentTime;

        /*
        * Every third call to the same session bean instance
        * the transaction will be marked for rollback.
        * Marking a transaction for rollback should be done before
        * throwing application exceptions that have not been
        * marked as causing transaction rollback when being thrown
        * using the @ApplicationException annotation or corresponding
        * ejb-jar.xml deployment descriptor element.
        */
        if (callCounter % 3 == 0) {
            System.out.println("*** Transaction rollback.");
            sessionContext.setRollbackOnly();
        }

        System.out.println("*** Transaction marked for rollback: " + sessionContext.getRollbackOnly());

        return message;
    }

    @Override
    public void afterBegin() throws EJBException, RemoteException {
        System.out.println("*** StatefulSessionBean.afterBegin");
    }

    @Override
    public void afterCompletion(boolean inCommitted) throws EJBException, RemoteException {
        System.out.println("*** StatefulSessionBean.afterCompletion: " + inCommitted);
    }

    @Override
    public void beforeCompletion() throws EJBException, RemoteException {
        System.out.println("*** StatefulSessionBean.beforeCompletion");
    }
}