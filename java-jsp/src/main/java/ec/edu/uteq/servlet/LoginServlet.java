package ec.edu.uteq.servlet;

import ec.edu.uteq.dao.UsuarioDAO;
import ec.edu.uteq.model.Usuario;
import ec.edu.uteq.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Servlet de inicio de sesion (login).
 *
 * doGet  -> genera un token CSRF, lo guarda en sesion y muestra el formulario.
 * doPost -> valida el token CSRF, verifica la clave con BCrypt y crea la sesion.
 *
 * Mitiga OWASP A07 (autenticacion) y A08 (CSRF + Session Fixation).
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final int TREINTA_MINUTOS = 30 * 60;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Generar token CSRF y guardarlo en la sesion
        String csrfToken = UUID.randomUUID().toString();
        req.getSession().setAttribute("csrfToken", csrfToken);
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 1. Validar token CSRF (debe coincidir con el guardado en sesion)
        String tokenFormulario = req.getParameter("csrfToken");
        String tokenSesion = (String) req.getSession().getAttribute("csrfToken");
        if (tokenSesion == null || !tokenSesion.equals(tokenFormulario)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Token CSRF invalido");
            return;
        }

        String email = req.getParameter("email");
        String clave = req.getParameter("clave");

        // 2. Buscar el usuario en la base de datos
        Usuario usuario;
        try {
            usuario = UsuarioDAO.buscarPorEmail(email);
        } catch (SQLException e) {
            throw new ServletException("Error al buscar el usuario", e);
        }

        // 3. Verificar contrasena con BCrypt
        boolean credencialesOk = usuario != null
                && PasswordUtil.verificar(clave, usuario.getPasswordHash());

        if (credencialesOk) {
            // 4. Crear sesion autenticada
            HttpSession sesion = req.getSession(true);
            sesion.setAttribute("usuarioEmail", usuario.getEmail());
            sesion.setAttribute("usuarioId", usuario.getId());
            sesion.setAttribute("usuarioNombre", usuario.getNombre());
            sesion.setMaxInactiveInterval(TREINTA_MINUTOS); // 30 minutos

            // 5. Regenerar el ID de sesion (previene Session Fixation)
            req.changeSessionId();

            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
        } else {
            // 6. Credenciales incorrectas (mensaje generico, sin revelar cual fallo)
            req.setAttribute("errorLogin", "Email o contrasena incorrectos.");
            // Regenerar token CSRF para el nuevo intento
            req.getSession().setAttribute("csrfToken", UUID.randomUUID().toString());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
