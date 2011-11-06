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
    @EJB
    private SingletonSessionBeanA mSingletonBeanA;
    @EJB
    private SingletonSessionBeanB mSingletonBeanB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("*** Entering SingletonClientServlet");

        String selectorParameter = request.getParameter("selector");
        if (selectorParameter == null || selectorParameter.equals("")) {
            selectorParameter = "1";
        }
        int selector = Integer.parseInt(selectorParameter);
        switch (selector) {
            case 1:
                testContainerManagedConcurrency();
                break;
            case 2:
                testBeanManagedConcurrency();
                break;
            default:
                break;
        }

        System.out.println("*** Exiting SingletonClientServlet");

        PrintWriter writer = response.getWriter();
        writer.println("Finished invoking singleton session bean concurrency test " + selector);
    }

    private void testContainerManagedConcurrency() {
        System.out.println("*** Entering testContainerManagedConcurrency");

        System.out.println(" Calling slowMethod...");
        new Thread() {
            @Override
            public void run() {
                mSingletonBeanA.slowMethod();
            }
        }.start();

        System.out.println(" Calling fastMethod...");
        new Thread() {
            @Override
            public void run() {
                mSingletonBeanA.fastMethod();
            }
        }.start();

        System.out.println("*** Exiting testContainerManagedConcurrency");
    }

    private void testBeanManagedConcurrency() {
        System.out.println("*** Entering testBeanManagedConcurrency");

        System.out.println(" Calling slowMethod...");
        new Thread() {
            @Override
            public void run() {
                mSingletonBeanB.slowMethod();
            }
        }.start();

        System.out.println(" Calling fastMethod...");
        new Thread() {
            @Override
            public void run() {
                mSingletonBeanB.fastMethod();
            }
        }.start();

        System.out.println("*** Exiting testBeanManagedConcurrency");
    }
}