package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.interceptor.Interceptors;
import java.util.Date;

@Singleton
@LocalBean
@Interceptors(LogInterceptor.class)
public class SingletonSessionBeanA {

    private final static String BEAN_NAME = "SingletonSessionBeanA";

    private String storedMessage = "[no message set]";

    @PostConstruct
    public void initialize() {
        System.out.println("*** " + BEAN_NAME + " - Initialized");
    }

    @PreDestroy
    public void cleanUp() {
        System.out.println("*** " + BEAN_NAME + " - Destroyed");
    }

    public String retrieveMessage() {
        return "Message from " + BEAN_NAME + " - " + storedMessage + " " + new Date();
    }

    public void storeMessage(final String inStoredMessage) {
        storedMessage = inStoredMessage;
    }

    @Schedule(second = "*/5", minute = "*", hour = "*")
    public void doPeriodic() {
        System.out.println("*** Do periodic: " + (new Date()));
    }
}