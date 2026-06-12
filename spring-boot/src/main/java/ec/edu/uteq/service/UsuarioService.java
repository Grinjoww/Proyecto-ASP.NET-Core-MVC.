package ec.edu.uteq.service;

import ec.edu.uteq.dto.RegistroRequest;
import ec.edu.uteq.dto.UsuarioResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Capa de servicio (logica de negocio).
 *
 * Anotada con @Service para que Spring la detecte por component scanning
 * y la inyecte en el controlador (Inyeccion de Dependencias / IoC).
 *
 * Usa una lista en memoria como almacen, con datos de ejemplo precargados,
 * para no depender de una base de datos en este modulo de demostracion REST.
 */
@Service
public class UsuarioService {

    private final List<UsuarioResponse> usuarios = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public UsuarioService() {
        // Datos de ejemplo para que GET /api/usuarios devuelva algo visible.
        registrarInterno("Maria Garcia", "maria@uteq.edu.ec", 22);
        registrarInterno("Juan Perez", "juan@uteq.edu.ec", 25);
        registrarInterno("Ana Lopez", "ana@uteq.edu.ec", 21);
    }

    public List<UsuarioResponse> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    public Optional<UsuarioResponse> buscarPorId(Long id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public UsuarioResponse registrar(RegistroRequest req) {
        return registrarInterno(req.getNombre(), req.getEmail(), req.getEdad());
    }

    public void eliminar(Long id) {
        usuarios.removeIf(u -> u.getId().equals(id));
    }

    private UsuarioResponse registrarInterno(String nombre, String email, int edad) {
        UsuarioResponse nuevo = new UsuarioResponse(
                secuencia.incrementAndGet(), nombre, email, edad);
        usuarios.add(nuevo);
        return nuevo;
    }
}
