package ec.edu.uteq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de salida para representar un usuario en las respuestas JSON.
 * No incluye la contrasena ni su hash (buena practica de seguridad).
 */
@Data
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String email;
    private int edad;
}
