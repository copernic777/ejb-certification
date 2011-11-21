package dmnlukasik.client;

import dmnlukasik.ejb.AsynchStatefulSessionBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.System.out;

@WebServlet(name = "AsynchClientServlet", urlPatterns = "/asynch.do")
public class AsynchClientServlet extends HttpServlet {

    @EJB
    private AsynchStatefulSessionBean bean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        out.println("**** Entering AsynchClientServlet");
        Future<String> slowAsynchResult = null;
        Future<String> asynchWithExceptionResult = null;
        Future<String> canBeCancelledResult = null;
        PrintWriter responseWriter = response.getWriter();
        try {
            /* Call void asynchronous method. */
            out.println("***** AsynchClientServlet - About to call slowOneWayAsynch");
            bean.slowOneWayAsynch();
            out.println("***** AsynchClientServlet - Finished calling slowOneWayAsynch");
            /* Call slow asynchronous method. */
            out.println("***** AsynchClientServlet - About to call slowAsynch");
            slowAsynchResult = bean.slowAsynch();
            out.println("***** AsynchClientServlet - Finished calling slowAsynch");
            /* Call asynchronous method that will throw an exception. */
            out.println("***** AsynchClientServlet - About to call asynchWithException");
            asynchWithExceptionResult = bean.asynchWithException();
            out.println("***** AsynchClientServlet - Finished calling asynchWithException");
            /* Call asynchronous method that can be canceled and cancel it. */
            out.println("***** AsynchClientServlet - About to call canBeCancelled");
            canBeCancelledResult = bean.canBeCancelled();
            out.println("***** AsynchClientServlet - Finished calling canBeCancelled");
            waitSomeTime((long) (Math.random() * 1000) + 1000L);
            out.println("***** AsynchClientServlet - About to cancel canBeCancelled");
            canBeCancelledResult.cancel(true);
            out.println("***** AsynchClientServlet - Cancelled canBeCancelled");
        } catch (Exception theException) {
            out.println("***** AsynchClientServlet - An exception was thrown: " + theException.getMessage());
        }
        try {
            /* Retrieve results from asynchronous invocations. */
            out.println("\n***** AsynchClientServlet - slowAsynch result: " + slowAsynchResult.get());
            out.println("***** AsynchClientServlet - canBeCancelled result: " + canBeCancelledResult.get());
            /* Wait for asynchWithException to complete. */
            while (!asynchWithExceptionResult.isDone()) {
                out.println(" Waiting...");
            }
            out.println("***** AsynchClientServlet - asynchWithException result: " + asynchWithExceptionResult);
            asynchWithExceptionResult.get();
        } catch (InterruptedException e) {
            out.println("***** AsynchClientServlet - An InterruptedException was thrown");
        } catch (ExecutionException e) {
            out.println("***** AsynchClientServlet - An ExecutionException was thrown: " + e);
        }
        out.println("**** Exiting AsynchClientServlet");
        responseWriter.println("Finished invoking asynchronous session bean!");
    }

    private void waitSomeTime(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {
        }
    }
}