package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

@Singleton
@LocalBean
public class SingletonSessionBean {

    public SingletonSessionBean() {
        System.out.println("***** SingletonSessionBean created");
    }

    @PostConstruct
    public void initialize() {
        System.out.println("***** SingletonSessionBean initialized");
    }
}