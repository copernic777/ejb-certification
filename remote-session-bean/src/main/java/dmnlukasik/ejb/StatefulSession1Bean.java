package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import java.util.Date;
import java.util.List;

@Stateful
public class StatefulSession1Bean implements StatefulSession1Remote {

    private static int currentInstanceNumber = 1;

    private int instanceNumber;

    @PostConstruct
    public void initialize() {
        instanceNumber = currentInstanceNumber++;
        System.out.println("*** StatefulSession1Bean " + instanceNumber + " created.");
    }

    @Override
    public String greeting(final String inName) {
        return "Hello " + inName + ", I am stateful session bean " + instanceNumber + ". The time is now: " + new Date();
    }

    @Override
    public void processList(List<String> list) {
        String theListStrings = "";
        for (String theString : list) {
            theListStrings += theString + ", ";
        }
        System.out.println("\nStatefulSession1Bean.processList: The list contains: [" + theListStrings + "]");

        /**
         * Add string to list.
         * If parameter passing is by reference, the client will also be able
         * to see this string, but if parameter passing is by value, then
         * this modification to the list is local only.
         */
        list.add("String added in EJB");
    }
}
