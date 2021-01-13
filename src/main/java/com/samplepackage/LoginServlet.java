package com.samplepackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(
        description = "Login Servlet Testing",
        urlPatterns = {"/LoginServlet"},
        initParams = {
                @WebInitParam(name = "user", value = "Ramana"),
                @WebInitParam(name = "password", value = "Password@123")
        }
)
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        String userID = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");

        String userRegex = "(^[A-Z]{1}[a-z]{2,}$)";
        String passwordRegex = "(^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#&$]).{8,}$)";

        if(Pattern.matches(userRegex, user)) {
            if (Pattern.matches(passwordRegex, pwd)) {
                if(userID.equals(user) && password.equals(pwd)) {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
                } else {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                    PrintWriter out = response.getWriter();
                    out.println("<font color=red>Either user name or password is wrong.<font>");
                    rd.include(request, response);
                }
            }else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                PrintWriter out = response.getWriter();
                out.println("<font color=red>Password is not meeting the requirements.<font>");
                rd.include(request, response);
            }
        }else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>username is not meeting the requirements<font>");
            rd.include(request, response);
        }
    }
}
