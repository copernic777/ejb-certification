package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;

import static java.lang.System.out;

/**
 * Stateless session bean that contains two timer callback methods that
 * are scheduled using the @Schedule annotation.
 * An EJB may contain any number of methods annotated with @Schedule
 * that all are invoked according to their own schedule.
 */
@Stateless
public class ScheduledStatelessSessionBean {

    private static int currentInstanceNo = 1;

    private int invocationCounter;
    private int instanceNo = currentInstanceNo++;
    @Resource
    private SessionContext sessionContext;

    @PostConstruct
    public void initialize() {
        out.println("ScheduledStatelessSessionBean created: " + instanceNo + " at: " + new Date());
    }

    /**
     * Scheduled method invoked every 20th and 45th second every minute
     * between 6 o'clock in the morning and 22 o'clock in the evening.
     *
     * @param timer Timer that caused the timeout callback invocation.
     */
    @Schedule(second = "20, 45", minute = "*", hour = "6-22", dayOfWeek = "Mon-Fri", info = "MyTimer")
    private void scheduledMethod1(Timer timer) {
        out.println("ScheduledStatelessSessionBean.scheduledMethod1: "
                + instanceNo + " entering at: " + new Date());
        out.println("   Rollback only: " + sessionContext.getRollbackOnly());
        out.println("   Timer info: " + timer.getInfo());
        /*
         * Wait some time to show what happens with multiple timer
         * callback methods being invoked on a session bean that
         * has container managed concurrency.
         */
        waitSeconds(15);
        cancelOverdue(timer);
        out.println("ScheduledStatelessSessionBean.scheduledMethod1: "
                + instanceNo + " exiting at: " + new Date());
    }

    /**
     * Scheduled method invoked every 10th second within the minute
     * starting at 15th second.
     * Timeout callback methods need not take a Timer object, as is the
     * case with this method.
     */
    @SuppressWarnings("unused")
    @Schedule(second = "15/10", minute = "*", hour = "*")
    private void scheduledMethod2() {
        out.println("ScheduledStatelessSessionBean.scheduledMethod2: "
                + instanceNo + " entering at: " + new Date());
        /*
         * Wait some time to show what happens with multiple timer
         * callback methods being invoked on a session bean that
         * has container managed concurrency.
         */
        waitSeconds(2);

        out.println("ScheduledStatelessSessionBean.scheduledMethod2: "
                + instanceNo + " exiting at: " + new Date());
    }

    private void cancelOverdue(Timer timer)
            throws IllegalStateException, EJBException {
        /* Cancel timer after certain number of invocations. */
        if (invocationCounter++ > 5) {
            out.println("Cancelling " + instanceNo + "...");
            timer.cancel();
        }
    }

    private void waitSeconds(final long inSeconds) {
        try {
            Thread.sleep(inSeconds * 1000L);
        } catch (InterruptedException ignored) {
        }
    }
}