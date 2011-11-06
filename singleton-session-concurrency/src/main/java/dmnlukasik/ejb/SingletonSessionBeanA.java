package dmnlukasik.ejb;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
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