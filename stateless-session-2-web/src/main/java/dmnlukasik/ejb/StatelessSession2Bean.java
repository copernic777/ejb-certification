package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import java.util.Date;

@Stateless
public class StatelessSession2Bean implements StatelessSession2Local {

    private static int currentInstanceNumber = 1;

    private int instanceNumber;

    @PostConstruct
    public void initialize() {
        instanceNumber = currentInstanceNumber++;
        System.out.println("SessionBean " + instanceNumber + " created");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("SessionBean " + instanceNumber + " destroyed");
    }

    @Remove
    public void remove() {
        System.out.println("SessionBean " + instanceNumber + " removed");
    }

    public String greeting(String name) {
        return "Hello " + name + ", I session bean " + instanceNumber + ". The time is now: " + new Date();
    }
}

