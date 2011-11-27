package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.transaction.*;
import java.util.Date;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class StatelessSession1Bean {

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

    public String greeting(final String inName) {
        Date theCurrentTime = new Date();
        String message = "";
        callCounter++;

        try {
            /*
             *  Retrieve the UserTransaction object and start the transaction.
             */
            UserTransaction theUserTransaction = sessionContext.getUserTransaction();
            System.out.println("*** Transaction begin...");
            theUserTransaction.begin();

            /*
            * Do what is to be done inside the transaction, accessing any
            * transactional resources, other EJBs etc.
            */
            message = "Hello " + inName + ", I am stateless session bean " +
                    instanceNumber + ". The time is now: " + theCurrentTime;

            /*
            * End the transaction.
            * Rollback the transaction every third call to one and the
            * same session bean instance.
            */
            if (callCounter % 3 == 0) {
                System.out.println("*** Transaction rollback.");
                theUserTransaction.rollback();
            } else {
                System.out.println("*** Transaction commit.");
                theUserTransaction.commit();
            }
        } catch (NotSupportedException e) {
            /*
             * Current thread is already associated with a transaction
             * and nested transactions are not supported.
             */
            e.printStackTrace();
        } catch (SystemException e) {
            /* Transaction manager encountered unexpected problem. */
            e.printStackTrace();
        } catch (SecurityException e) {
            /* Thread is not allowed to commit the transaction. */
            e.printStackTrace();
        } catch (IllegalStateException e) {
            /* The current tread is not associated with the transaction. */
            e.printStackTrace();
        } catch (RollbackException e) {
            /* Transaction has been rolled back instead of committed. */
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            /* Some updates has been committed, some rolled back. */
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            /* All updates have been rolled back. */
            e.printStackTrace();
        }

        return message;
    }
}