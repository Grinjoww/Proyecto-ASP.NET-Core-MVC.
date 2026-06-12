package ec.edu.uteq.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Cierra la sesion del usuario (logout) y redirige al login.
 * Esta bajo /app/* para que solo un usuario autenticado pueda cerrar sesion.
 */
@WebServlet("/app/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        if (sesion != null) {
            sesion.invalidate(); // Destruye la sesion del servidor.
        }
        resp.sendRedirect(req.getContextPath() + "/login?logout=true");
    }
}
