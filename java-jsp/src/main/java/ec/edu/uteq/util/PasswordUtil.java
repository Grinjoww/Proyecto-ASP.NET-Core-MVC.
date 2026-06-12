package ec.edu.uteq.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utilidad de hashing de contrasenas con BCrypt.
 *
 * Aplica el principio de Responsabilidad Unica (SOLID-S): esta clase SOLO
 * se encarga del hashing, separando esa responsabilidad de los Servlets.
 *
 * Aplica Clean Code: el factor de costo es una constante con nombre
 * (sin numeros magicos como BCrypt.gensalt(12) dispersos por el codigo).
 */
public final class PasswordUtil {

    /** Factor de costo de BCrypt. 12 = 2^12 = 4096 iteraciones. */
    private static final int BCRYPT_COSTO = 12;

    private PasswordUtil() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Genera el hash BCrypt de una contrasena en texto plano.
     * Cada llamada usa un salt aleatorio distinto (no determinista).
     */
    public static String hashear(String claveTextoPlano) {
        return BCrypt.hashpw(claveTextoPlano, BCrypt.gensalt(BCRYPT_COSTO));
    }

    /**
     * Verifica si una contrasena en texto plano coincide con un hash guardado.
     */
    public static boolean verificar(String claveTextoPlano, String hashGuardado) {
        if (claveTextoPlano == null || hashGuardado == null) {
            return false;
        }
        return BCrypt.checkpw(claveTextoPlano, hashGuardado);
    }
}
