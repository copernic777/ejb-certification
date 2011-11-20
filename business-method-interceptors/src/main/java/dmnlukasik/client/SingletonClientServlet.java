package dmnlukasik.client;

import dmnlukasik.ejb.SingletonSessionBeanA;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SingletonClientServlet", urlPatterns = "/test.do")
public class SingletonClientServlet extends HttpServlet {

    private final static String STORE_ACTION = "store";
    private final static String CLEAR_ACTION = "clear";

    @EJB
    private SingletonSessionBeanA mSingletonBeanA;

    @Override
    protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse)
            throws ServletException, IOException {

        System.out.println("**** Entering SingletonClientServlet");
        String theRequestNameParam = inRequest.getParameter("name");
        String theRequestActionParam = inRequest.getParameter("action");
        if (theRequestNameParam == null || theRequestNameParam.equals("")) {
            theRequestNameParam = "Anonymous Coward";
        }

        PrintWriter theResponseWriter = inResponse.getWriter();
        String theMessage;
        theMessage = mSingletonBeanA.retrieveMessage();
        theResponseWriter.println(theMessage);

        if (theRequestActionParam != null) {
            if (STORE_ACTION.equals(theRequestActionParam)) {
                mSingletonBeanA.storeMessage(theRequestNameParam);
            }
            if (CLEAR_ACTION.equals(theRequestActionParam)) {
                mSingletonBeanA.storeMessage("[CLEARED]");
            }
        }
        System.out.println("**** Exiting SingletonClientServlet");
        theResponseWriter.println("Finished invoking singleton session beans!");
    }
}