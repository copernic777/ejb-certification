package dmnlukasik.client;

import dmnlukasik.ejb.IdentityCheckingBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "IdentityCheckingServlet", urlPatterns = "/test.do")
public class IdentityCheckingServlet extends HttpServlet {

    @EJB
    private IdentityCheckingBean identityCheckingBean;

    @Override
    protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
        PrintWriter theResponseWriter = inResponse.getWriter();
        identityCheckingBean.checkBeanIdentities();
        identityCheckingBean.checkBeanHashCodes();
        theResponseWriter.println("Identity checks and hash code checks result printed to console.");
    }
}