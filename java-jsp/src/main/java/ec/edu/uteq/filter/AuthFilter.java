package ec.edu.uteq.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filtro de autenticacion y cabeceras de seguridad.
 *
 * Protege todas las rutas bajo /app/*: si no hay sesion activa, redirige al login.
 * Ademas anade cabeceras de seguridad HTTP en TODA respuesta.
 *
 * Mitiga OWASP A01 (control de acceso) y A05 (configuracion de seguridad).
 */
@WebFilter("/app/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 1. Agregar cabeceras de seguridad HTTP en TODA respuesta.
        resp.setHeader("X-Content-Type-Options", "nosniff");
        resp.setHeader("X-Frame-Options", "DENY");
        resp.setHeader("X-XSS-Protection", "1; mode=block");
        resp.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        resp.setHeader("Content-Security-Policy",
                "default-src 'self'; "
                        + "script-src 'self'; "
                        + "style-src 'self' 'unsafe-inline'");

        // 2. Verificar sesion activa.
        HttpSession sesion = req.getSession(false);
        boolean autenticado = sesion != null
                && sesion.getAttribute("usuarioEmail") != null;

        if (autenticado) {
            // 3. Pasar la solicitud al Servlet destino.
            chain.doFilter(request, response);
        } else {
            // 4. Sin sesion: redirigir al login.
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
