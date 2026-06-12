package ec.edu.uteq.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO de entrada para el registro, con validaciones declarativas
 * de Bean Validation. @Valid en el controlador las activa.
 */
@Data // Lombok: genera getters, setters, equals, hashCode, toString
public class RegistroRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100,
            message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene formato valido")
    private String email;

    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 8, message = "Min. 8 caracteres")
    private String clave;

    @Min(value = 1, message = "La edad minima es 1")
    @Max(value = 120, message = "La edad maxima es 120")
    private int edad;
}
