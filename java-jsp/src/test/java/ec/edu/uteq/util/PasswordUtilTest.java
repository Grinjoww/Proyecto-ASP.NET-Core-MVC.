package ec.edu.uteq.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias del hashing de contrasenas con BCrypt (PasswordUtil).
 * Cubre el item del checklist: "4 tests de JUnit ejecutandose con resultado verde".
 */
class PasswordUtilTest {

    @Test
    @DisplayName("El hash generado no debe ser igual a la clave original")
    void hashNoDebeSerIgualAClave() {
        String clave = "MiClave123";
        String hash = PasswordUtil.hashear(clave);
        assertNotEquals(clave, hash,
                "El hash no debe coincidir con la clave original");
    }

    @Test
    @DisplayName("verificar debe devolver true con la clave correcta")
    void verificacionConClaveCorrecta() {
        String clave = "MiClave123";
        String hash = PasswordUtil.hashear(clave);
        assertTrue(PasswordUtil.verificar(clave, hash),
                "verificar debe retornar true con la clave correcta");
    }

    @Test
    @DisplayName("verificar debe devolver false con la clave incorrecta")
    void verificacionConClaveIncorrecta() {
        String clave = "MiClave123";
        String claveErronea = "OtraClave456";
        String hash = PasswordUtil.hashear(clave);
        assertFalse(PasswordUtil.verificar(claveErronea, hash),
                "verificar debe retornar false con clave incorrecta");
    }

    @Test
    @DisplayName("Dos hashes del mismo texto deben ser distintos (salt aleatorio)")
    void dosHashesSonDistintos() {
        String clave = "MiClave123";
        String hash1 = PasswordUtil.hashear(clave);
        String hash2 = PasswordUtil.hashear(clave);
        assertNotEquals(hash1, hash2,
                "Cada hash debe tener un salt diferente (no determinista)");
    }
}
