package dmnlukasik.client;

import dmnlukasik.ejb.SingletonSessionBeanA;
import dmnlukasik.ejb.SingletonSessionBeanB;

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
    private static final long serialVersionUID = 1L;
    private final static String STORE_ACTION = "store";
    private final static String CLEAR_ACTION = "clear";

    @EJB
    private SingletonSessionBeanA mSingletonBeanA;
    @EJB
    private SingletonSessionBeanB mSingletonBeanB;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("**** Entering SingletonClientServlet");
        String theRequestNameParam = request.getParameter("name");
        String theRequestActionParam = request.getParameter("action");

        if (theRequestNameParam == null || theRequestNameParam.equals("")) {
            theRequestNameParam = "Anonymous Coward";
        }

        PrintWriter theResponseWriter = response.getWriter();
        String theMessage;
        theMessage = mSingletonBeanA.retrieveMessage();
        theResponseWriter.println(theMessage);
        theMessage = mSingletonBeanB.retrieveMessage();
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