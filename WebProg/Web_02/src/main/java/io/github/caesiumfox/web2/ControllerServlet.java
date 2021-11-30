package io.github.caesiumfox.web2;

import java.io.*;
import java.rmi.ServerError;
import java.rmi.ServerException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

public class ControllerServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/area").forward(request, response);
    }
}