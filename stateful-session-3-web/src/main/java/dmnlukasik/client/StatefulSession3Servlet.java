package dmnlukasik.client;

import dmnlukasik.ejb.StatefulSession3Remote;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "StatefulSession3Servlet", urlPatterns = "/test.do")
public class StatefulSession3Servlet extends HttpServlet {

    @EJB
    private StatefulSession3Remote bean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        String name = request.getParameter("name");
        if (name == null) {
            name = "Anonymous Coward";
        }
        String greeting = bean.greeting(name);
        writer.println("Response from the EJB: " + greeting);
    }
}
