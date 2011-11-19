package dmnlukasik.client;

import dmnlukasik.ejb.StatefulSession1Remote;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

public class RemoteSESessionBeanClient {

    private StatefulSession1Remote remoteBean1;
    private StatefulSession1Remote remoteBean2;

    private void lookupEJB() throws NamingException {
        System.out.println("*** Starting Remote EJB Lookup...");

        InitialContext initialContext = new InitialContext();

        String name1 = "java:global/remote-session-bean/StatefulSession1Bean";
        remoteBean1 = (StatefulSession1Remote) initialContext.lookup(name1);

        String name2 = "java:global/remote-session-bean/StatefulSession1Bean!dmnlukasik.ejb.StatefulSession1Remote";
        remoteBean2 = (StatefulSession1Remote) initialContext.lookup(name2);

        System.out.println(" Remote EJB Lookup Finished.");
    }

    private void invokedRemoteEJB(StatefulSession1Remote bean) {
        String theResponse = bean.greeting("Java SE");
        System.out.println("*** Response from the EJB: " + theResponse);

        List<String> theList = new ArrayList<String>();
        theList.add("string 1");
        theList.add("string 2");
        theList.add("last string");
        bean.processList(theList);

        String theListStrings = "";
        for (String theString : theList) {
            theListStrings += theString + ", ";
        }
        System.out.println("*** List after having invoked EJB processList: [" + theListStrings + "]");
    }

    public static void main(String[] args) {
        RemoteSESessionBeanClient client = new RemoteSESessionBeanClient();
        try {
            client.lookupEJB();
            client.invokedRemoteEJB(client.remoteBean1);
            client.invokedRemoteEJB(client.remoteBean2);
        } catch (Exception theException) {
            theException.printStackTrace();
        }
    }
}