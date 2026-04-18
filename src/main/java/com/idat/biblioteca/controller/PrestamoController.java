package com.idat.biblioteca.controller;

import com.idat.biblioteca.model.Libro;
import com.idat.biblioteca.model.Prestamo;
import com.idat.biblioteca.model.Usuario;
import com.idat.biblioteca.repository.LibroRepository;
import com.idat.biblioteca.repository.PrestamoRepository;
import com.idat.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> registrarPrestamo(@RequestBody Map<String, Integer> request) {
        Integer usuarioId = request.get("usuario_id");
        Integer libroId = request.get("libro_id");

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Libro libro = libroRepository.findById(libroId).orElse(null);

        if (usuario == null || libro == null) {
            return ResponseEntity.badRequest().body("Usuario o Libro no encontrado");
        }

        if (libro.getStock() <= 0) {
            return ResponseEntity.badRequest().body("No hay stock disponible para este libro");
        }

        // Restar stock
        libro.setStock(libro.getStock() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setEstado("ACTIVO");

        return ResponseEntity.ok(prestamoRepository.save(prestamo));
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolverPrestamo(@PathVariable Integer id) {
        return prestamoRepository.findById(id).map(prestamo -> {
            if ("DEVUELTO".equals(prestamo.getEstado())) {
                return ResponseEntity.badRequest().body("Este préstamo ya fue devuelto");
            }

            prestamo.setEstado("DEVUELTO");
            prestamo.setFechaDevolucion(LocalDateTime.now());
            
            // Aumentar stock del libro devuelto
            Libro libro = prestamo.getLibro();
            libro.setStock(libro.getStock() + 1);
            libroRepository.save(libro);
            
            return ResponseEntity.ok(prestamoRepository.save(prestamo));
        }).orElse(ResponseEntity.notFound().build());
    }
}
