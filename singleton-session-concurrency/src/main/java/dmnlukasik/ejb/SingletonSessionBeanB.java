package dmnlukasik.ejb;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class SingletonSessionBeanB {
    public void slowMethod() {
        System.out.println("SingletonSessionBeanB - Entering slowMethod");
        waitSomeTime(10);
        System.out.println("SingletonSessionBeanB - Exiting slowMethod");
    }

    public void fastMethod() {
        System.out.println("SingletonSessionBeanB - Entering fastMethod");
        waitSomeTime(1);
        System.out.println("SingletonSessionBeanB - Exiting fastMethod");
    }

    private void waitSomeTime(final long inSecondsDelay) {
        try {
            Thread.sleep(1000L * inSecondsDelay);
        } catch (InterruptedException e) {
        }
    }
}