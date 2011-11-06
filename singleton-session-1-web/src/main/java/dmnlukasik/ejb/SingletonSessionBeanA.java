package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Date;

@Singleton
@LocalBean
@Startup
@DependsOn("SingletonSessionBeanB")
public class SingletonSessionBeanA {
    private final static String BEAN_NAME = "SingletonSessionBeanA";

    private String mStoredMessage = "[no message set]";

    @PostConstruct
    public void initialize() {
        System.out.println("*** " + BEAN_NAME + " - Initialized");
    }

    @PreDestroy
    public void cleanUp() {
        System.out.println("*** " + BEAN_NAME + " - Destroyed");
    }

    public String retrieveMessage() {
        return "Message from " + BEAN_NAME + " - " + mStoredMessage + " " + new Date();
    }

    public void storeMessage(final String inStoredMessage) {
        mStoredMessage = inStoredMessage;
    }
}
