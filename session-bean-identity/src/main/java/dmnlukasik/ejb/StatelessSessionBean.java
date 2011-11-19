package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class StatelessSessionBean implements StatelessSessionBeanLocalBusiness {

    public StatelessSessionBean() {
        System.out.println("***** StatelessSessionBean created");
    }

    @PostConstruct
    public void initialize() {
        System.out.println("***** StatelessSessionBean initialized");
    }
}