package dmnlukasik.ejb;

import javax.ejb.*;

@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Lock(LockType.READ)
public class SingletonSessionBeanA {
    public void slowMethod() {
        System.out.println("SingletonSessionBeanA - Entering slowMethod");
        waitSomeTime(10);
        System.out.println("SingletonSessionBeanA - Exiting slowMethod");
    }

    public void fastMethod() {
        System.out.println("SingletonSessionBeanA - Entering fastMethod");
        waitSomeTime(1);
        System.out.println("SingletonSessionBeanA - Exiting fastMethod");
    }

    private void waitSomeTime(final long inSecondsDelay) {
        try {
            Thread.sleep(1000L * inSecondsDelay);
        } catch (InterruptedException e) {
        }
    }
}
