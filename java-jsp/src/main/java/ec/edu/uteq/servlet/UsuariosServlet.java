package ec.edu.uteq.servlet;

import ec.edu.uteq.dao.UsuarioDAO;
import ec.edu.uteq.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * EJERCICIO 2 - CRUD con JSP + JSTL.
 *
 * doGet  -> obtiene la lista de usuarios y la pasa al JSP, que la recorre
 *           con c:forEach (sin una sola linea de Java en la vista).
 * doPost -> elimina el usuario cuyo ID llega por POST y vuelve al listado (PRG).
 *
 * Esta ruta esta protegida por AuthFilter (/app/*): requiere sesion activa.
 */
@WebServlet(urlPatterns = {"/app/usuarios"})
public class UsuariosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Usuario> usuarios = UsuarioDAO.listarTodos();
            req.setAttribute("usuarios", usuarios);
        } catch (SQLException e) {
            throw new ServletException("Error al listar usuarios", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/usuarios.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Eliminar por ID (enviado mediante POST desde el enlace "Eliminar").
        String idStr = req.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            eliminar(id);
        } catch (NumberFormatException e) {
            // ID invalido: se ignora y se vuelve al listado.
        }
        // PRG: redirigir al listado tras eliminar.
        resp.sendRedirect(req.getContextPath() + "/app/usuarios");
    }

    private void eliminar(int id) throws ServletException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (var conn = java.sql.DriverManager.getConnection("jdbc:sqlite:practica7.db");
             var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Error al eliminar el usuario", e);
        }
    }
}
