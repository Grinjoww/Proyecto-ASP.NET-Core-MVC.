package ec.edu.uteq.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * EJERCICIO 4 - Prueba de XSS (Cross-Site Scripting).
 *
 * Recibe un parametro 'nombre' y lo muestra de DOS formas para comparar:
 *   1. VULNERABLE: se imprime directo con out.println (el navegador ejecuta scripts).
 *   2. SEGURA: se escapa con escapeHtml antes de imprimir (el script se muestra como texto).
 *
 * Para reproducir el ataque, enviar como nombre:
 *   <script>alert('xss')</script>
 *
 * Mitiga OWASP A03 (Inyeccion - XSS).
 */
@WebServlet("/xss")
public class XssDemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String nombre = req.getParameter("nombre");
        if (nombre == null) {
            nombre = "";
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html><html lang='es'><head>");
            out.println("<meta charset='UTF-8'><title>Demo XSS</title>");
            out.println("<style>body{font-family:Arial;max-width:700px;margin:2rem auto}"
                    + ".box{border:1px solid #ccc;border-radius:6px;padding:1rem;margin:1rem 0}"
                    + ".vuln{border-left:6px solid #c0392b}"
                    + ".safe{border-left:6px solid #27ae60}"
                    + "code{background:#f4f4f4;padding:2px 5px;border-radius:3px}</style>");
            out.println("</head><body>");
            out.println("<h1>Demostracion de XSS</h1>");
            out.println("<form method='get' action='xss'>");
            out.println("Nombre: <input type='text' name='nombre' size='40' "
                    + "placeholder=\"&lt;script&gt;alert('xss')&lt;/script&gt;\">");
            out.println("<button type='submit'>Enviar</button></form>");

            // 1. VERSION VULNERABLE: imprime el valor tal cual (PELIGRO).
            out.println("<div class='box vuln'>");
            out.println("<h2>1. Version VULNERABLE (out.println directo)</h2>");
            out.println("<p>Salida: <strong>Hola " + nombre + "</strong></p>");
            out.println("<p><em>Si ingresaste un &lt;script&gt;, el navegador lo EJECUTA.</em></p>");
            out.println("</div>");

            // 2. VERSION SEGURA: escapa los caracteres especiales.
            out.println("<div class='box safe'>");
            out.println("<h2>2. Version SEGURA (escapeHtml)</h2>");
            out.println("<p>Salida: <strong>Hola " + escapeHtml(nombre) + "</strong></p>");
            out.println("<p><em>El script aparece como TEXTO, no se ejecuta.</em></p>");
            out.println("</div>");

            out.println("</body></html>");
        }
    }

    /**
     * Escapa los caracteres especiales de HTML para neutralizar XSS.
     * Equivale a lo que hace automaticamente la etiqueta JSTL c:out.
     */
    private String escapeHtml(String input) {
        if (input == null) {
            return "";
        }
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}
