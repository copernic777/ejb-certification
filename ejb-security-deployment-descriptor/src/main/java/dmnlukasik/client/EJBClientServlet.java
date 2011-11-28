package dmnlukasik.client;

import dmnlukasik.ejb.StatelessSession1Bean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EJBClientServlet", urlPatterns = "/test.do")
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "GET", rolesAllowed = {"plainusers", "superusers"})
})
public class EJBClientServlet extends HttpServlet {

    @EJB
    private StatelessSession1Bean bean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("*** EJBClientServlet.doGet");
        PrintWriter responseWriter = response.getWriter();
        String name = request.getParameter("name");
        if (name == null) {
            name = "Anonymous";
        }
        String greeting = bean.greeting(name);
        responseWriter.println("Response from the EJB:\n" + greeting);
    }
}