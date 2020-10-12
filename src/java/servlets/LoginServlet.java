/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AccountService;
import model.User;

/**
 *
 * @author cprg352
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        HttpSession session = request.getSession();

        if (request.getParameter("logout") != null) {
            session.invalidate();
        }

        if (user != null) {
            response.sendRedirect("home");

        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountService accountService = new AccountService();
        HttpSession session = request.getSession();

        if (!request.getParameter("username").equals("") && !request.getParameter("password").equals("")) {
            User user = accountService.login(request.getParameter("username"), request.getParameter("password"));

            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect("home");
                return;
            }
        }
        
        // bad input or invalid user/password
        request.setAttribute("username", request.getParameter("username"));
        request.setAttribute("password", request.getParameter("password"));
        request.setAttribute("message", "Invalid login");
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

    }
}
