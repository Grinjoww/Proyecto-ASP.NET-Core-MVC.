package ec.edu.uteq.servlet;

import ec.edu.uteq.dao.UsuarioDAO;
import ec.edu.uteq.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet de registro de usuario.
 *
 * doGet  -> muestra el formulario de registro (JSP).
 * doPost -> valida en el servidor, hashea la clave con BCrypt y guarda en BD.
 *
 * Aplica el patron Post/Redirect/Get (PRG): tras un POST exitoso redirige
 * al login para evitar reenvios del formulario al recargar.
 */
@WebServlet(urlPatterns = {"/registro"})
public class RegistroServlet extends HttpServlet {

    // doGet: mostrar el formulario de registro
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // El JSP esta en WEB-INF/views (no accesible directamente por el navegador)
        req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
    }

    // doPost: procesar el envio del formulario
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Establecer codificacion UTF-8 para leer parametros con acentos
        req.setCharacterEncoding("UTF-8");

        // 2. Leer parametros del formulario
        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");
        String clave = req.getParameter("clave");
        String clave2 = req.getParameter("clave2");
        String edadStr = req.getParameter("edad");

        // 3. Validaciones server-side
        StringBuilder errores = new StringBuilder();

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.append("El nombre es obligatorio. ");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errores.append("El email no es valido. ");
        }
        if (clave == null || clave.length() < 8) {
            errores.append("La clave debe tener min. 8 caracteres. ");
        }
        if (clave != null && !clave.equals(clave2)) {
            errores.append("Las claves no coinciden. ");
        }

        int edad = 0;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad < 1 || edad > 120) {
                errores.append("La edad debe estar entre 1 y 120. ");
            }
        } catch (NumberFormatException e) {
            errores.append("La edad debe ser un numero entero. ");
        }

        // Validar email duplicado solo si lo anterior es valido
        if (errores.length() == 0) {
            try {
                if (UsuarioDAO.existeEmail(email)) {
                    errores.append("Ese email ya esta registrado. ");
                }
            } catch (SQLException e) {
                throw new ServletException("Error al verificar el email", e);
            }
        }

        // 4. Si hay errores, volver al formulario con los mensajes
        if (errores.length() > 0) {
            req.setAttribute("errores", errores.toString());
            req.setAttribute("nombre", nombre);   // Rellenar campos
            req.setAttribute("email", email);
            req.setAttribute("edad", edadStr);
            req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
            return;
        }

        // 5. Hashear la contrasena con BCrypt (costo 12)
        String hashClave = PasswordUtil.hashear(clave);

        // 6. Guardar el usuario en la base de datos (SQLite + PreparedStatement)
        try {
            UsuarioDAO.insertar(nombre, email, hashClave, edad);
        } catch (SQLException e) {
            throw new ServletException("Error al guardar el usuario", e);
        }

        // 7. Patron PRG: redirigir al login con mensaje de exito
        resp.sendRedirect(req.getContextPath() + "/login?registrado=true");
    }
}
