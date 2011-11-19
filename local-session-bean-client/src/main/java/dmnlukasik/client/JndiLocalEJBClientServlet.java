package dmnlukasik.client;

import dmnlukasik.ejb.StatefulSession1Bean;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "JndiLocalEJBClientServlet", urlPatterns = "/test2.do")
public class JndiLocalEJBClientServlet extends HttpServlet {

    private StatefulSession1Bean bean1;
    private StatefulSession1Bean bean2;
    private StatefulSession1Bean bean3;
    private StatefulSession1Bean bean4;
    private StatefulSession1Bean bean5;
    private StatefulSession1Bean bean6;

    @PostConstruct
    public void initialize() {
        try {
            InitialContext initialContext = new InitialContext();
            bean1 = (StatefulSession1Bean) initialContext.lookup("java:global/local-session-bean-client/StatefulSession1Bean");
            bean2 = (StatefulSession1Bean) initialContext.lookup("java:global/local-session-bean-client/StatefulSession1Bean!dmnlukasik.ejb.StatefulSession1Bean");
            bean3 = (StatefulSession1Bean) initialContext.lookup("java:app/local-session-bean-client/StatefulSession1Bean");
            bean4 = (StatefulSession1Bean) initialContext.lookup("java:app/local-session-bean-client/StatefulSession1Bean!dmnlukasik.ejb.StatefulSession1Bean");
            bean5 = (StatefulSession1Bean) initialContext.lookup("java:module/StatefulSession1Bean");
            bean6 = (StatefulSession1Bean) initialContext.lookup("java:module/StatefulSession1Bean!dmnlukasik.ejb.StatefulSession1Bean");
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        runBean(response, bean1);
        runBean(response, bean2);
        runBean(response, bean3);
        runBean(response, bean4);
        runBean(response, bean5);
        runBean(response, bean6);
    }

    private void runBean(HttpServletResponse response, StatefulSession1Bean bean) throws IOException {
        System.out.println("EJB reference: " + bean);
        System.out.println("EJB reference type: " + bean.getClass());

        PrintWriter theResponseWriter = response.getWriter();
        String theResponse = bean.greeting("Anonymous");
        theResponseWriter.println("Response from the EJB: " + theResponse);

        List<String> theList = new ArrayList<String>();
        theList.add("string 1");
        theList.add("string 2");
        theList.add("last string");

        bean.processList(theList);

        String theListStrings = "";
        for (String theString : theList) {
            theListStrings += theString + ", ";
        }

        System.out.println("\nList after having invoked EJB processList: [" + theListStrings + "]");
    }
}