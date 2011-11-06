package dmnlukasik.client;

import dmnlukasik.ejb.StatefulSession1Bean;

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

@WebServlet(name = "LocalEJBClientServlet", urlPatterns = "/test.do")
public class LocalEJBClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private StatefulSession1Bean bean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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