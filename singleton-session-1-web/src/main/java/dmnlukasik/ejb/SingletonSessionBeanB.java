package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.Date;

@Singleton
@LocalBean
public class SingletonSessionBeanB {
    private final static String BEAN_NAME = "SingletonSessionBeanB";

    @PostConstruct
    public void initialize() {
        System.out.println("*** " + BEAN_NAME + " - Initialized");
    }

    @PreDestroy
    public void cleanUp() {
        System.out.println("*** " + BEAN_NAME + " - Destroyed");
    }

    public String retrieveMessage() {
        Date theCurrentTime = new Date();
        return "Message from " + BEAN_NAME + " - " + theCurrentTime;
    }
}