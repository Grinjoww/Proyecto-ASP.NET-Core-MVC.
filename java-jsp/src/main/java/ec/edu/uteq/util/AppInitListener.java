package ec.edu.uteq.util;

import ec.edu.uteq.dao.UsuarioDAO;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Listener de arranque de la aplicacion.
 * Crea la tabla 'usuarios' en SQLite cuando Tomcat inicia el contexto web,
 * para que el registro y el login funcionen desde la primera solicitud.
 */
@WebListener
public class AppInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            UsuarioDAO.inicializar();
            sce.getServletContext().log("Base de datos SQLite inicializada (tabla usuarios).");
        } catch (Exception e) {
            // No tumbar la aplicacion si la BD falla al iniciar:
            // la tabla se creara igualmente en la primera consulta.
            sce.getServletContext().log(
                    "AVISO: no se pudo inicializar la BD al arranque: " + e.getMessage(), e);
        }
    }
}