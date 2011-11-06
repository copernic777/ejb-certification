package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import java.util.Date;
import java.util.List;

@Stateful
@LocalBean
public class StatefulSession1Bean {
    private static int sCurrentInstanceNumber = 1;
    private int mInstanceNumber;

    @PostConstruct
    public void initialize() {
        mInstanceNumber = sCurrentInstanceNumber++;
        System.out.println("*** StatefulSession1Bean " + mInstanceNumber +
                " created.");
    }

    public String greeting(final String inName) {
        return "Hello " + inName + ", I am stateful session bean " + mInstanceNumber + ". The time is now: " + new Date();
    }

    public void processList(List<String> list) {
        String elements = "";
        for (String string : list) {
            elements += string + ", ";
        }
        System.out.println("\nStatefulSession1Bean.processList: The list contains: [" + elements + "]");

        list.add("String added in EJB");
    }
}