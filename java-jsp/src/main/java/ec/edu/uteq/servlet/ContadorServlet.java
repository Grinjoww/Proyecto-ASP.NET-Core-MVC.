package ec.edu.uteq.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * EJERCICIO 1 - Ciclo de vida del Servlet.
 *
 * init()    -> inicializa el contador en cero, guardado en ServletContext
 *              (compartido entre TODAS las solicitudes y navegadores).
 * doGet()   -> incrementa el contador y lo muestra en un JSP.
 * destroy() -> imprime el valor final del contador en los logs.
 *
 * Se usa AtomicInteger para que el incremento sea seguro ante concurrencia.
 */
@WebServlet(urlPatterns = {"/contador"})
public class ContadorServlet extends HttpServlet {

    private static final String CLAVE_CONTADOR = "contadorGlobal";

    @Override
    public void init() throws ServletException {
        // Se ejecuta UNA sola vez, al cargar el Servlet.
        getServletContext().setAttribute(CLAVE_CONTADOR, new AtomicInteger(0));
        log("ContadorServlet inicializado. Contador en 0.");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Recuperar el contador compartido del ServletContext.
        AtomicInteger contador =
                (AtomicInteger) getServletContext().getAttribute(CLAVE_CONTADOR);

        int valorActual = contador.incrementAndGet();

        // Pasar el valor al JSP mediante un atributo del request.
        req.setAttribute("visitas", valorActual);
        req.getRequestDispatcher("/WEB-INF/views/contador.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        // Se ejecuta UNA sola vez, al descargar el Servlet (parada de Tomcat).
        AtomicInteger contador =
                (AtomicInteger) getServletContext().getAttribute(CLAVE_CONTADOR);
        int valorFinal = (contador != null) ? contador.get() : 0;
        log("ContadorServlet destruido. Total de visitas: " + valorFinal);
    }
}
