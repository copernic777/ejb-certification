package dmnlukasik.client;

import dmnlukasik.ejb.StatefulSession1Remote;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "RemoteEJBClientServlet", urlPatterns = "/test.do")
public class RemoteEJBClientServlet extends HttpServlet {

    @EJB(lookup = "java:global/remote-session-bean/StatefulSession1Bean")
    private StatefulSession1Remote bean;

    @Override
    protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse)
            throws ServletException, IOException {
        PrintWriter theResponseWriter = inResponse.getWriter();
        String theRequestNameParam = inRequest.getParameter("name");
        if (theRequestNameParam == null) {
            theRequestNameParam = "Anonymous";
        }
        String theResponse = bean.greeting(theRequestNameParam);
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