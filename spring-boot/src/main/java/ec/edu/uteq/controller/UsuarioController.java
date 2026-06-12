package ec.edu.uteq.controller;

import ec.edu.uteq.dto.RegistroRequest;
import ec.edu.uteq.dto.UsuarioResponse;
import ec.edu.uteq.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * EJERCICIO 5 - Controlador REST con Spring Boot.
 *
 * @RestController = @Controller + @ResponseBody: convierte el retorno a JSON.
 * Expone el recurso /api/usuarios con operaciones GET, POST y DELETE.
 *
 * La dependencia UsuarioService se inyecta por constructor (recomendado),
 * generado por Lombok con @RequiredArgsConstructor.
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // GET /api/usuarios -> listar todos
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // GET /api/usuarios/42 -> buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscar(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/usuarios/registro -> crear nuevo usuario
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrar(
            @Valid @RequestBody RegistroRequest req) {
        // @Valid activa las anotaciones de validacion del DTO.
        // @RequestBody convierte el JSON del cuerpo a objeto Java.
        UsuarioResponse nuevo = usuarioService.registrar(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // DELETE /api/usuarios/42
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
