package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;
import java.util.concurrent.Future;

@Stateful
@LocalBean
public class AsynchStatefulSessionBean {

    @Resource
    private SessionContext mSessionContext;

    @PostConstruct
    public void initialize() {
        System.out.println("***** AsynchStatefulSessionBean initialized");
    }

    @Asynchronous
    public Future<String> asynchWithException() throws Exception {
        throw new Exception("Exception from AsynchStatefulSessionBean");
    }

    @Asynchronous
    public Future<String> slowAsynch() {
        String theResult = (new Date()).toString();
        waitSomeTime(5000L);
        System.out.println("***** AsynchStatefulSessionBean Exiting slowAsynch");
        return new AsyncResult<String>(theResult);
    }

    @Asynchronous
    public void slowOneWayAsynch() {
        waitSomeTime(5000L);
        System.out.println("***** AsynchStatefulSessionBean Exiting slowOneWayAsynch");
    }

    @Asynchronous
    public Future<String> canBeCancelled() {
        String theResult = "Not cancelled " + new Date();
        for (int i = 1; i < 100; i++) {
            waitSomeTime(100L);
            System.out.println("***** AsynchStatefulSessionBean canBeCancelled waited " + i);
            /* Check if client attempted to cancel the method. */
            if (mSessionContext.wasCancelCalled()) {
                theResult = "Cancelled " + new Date();
                break;
            }
        }
        return new AsyncResult<String>(theResult);
    }

    private void waitSomeTime(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException theException) {
        }
    }
}