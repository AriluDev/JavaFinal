package com.idat.biblioteca.controller;

import com.idat.biblioteca.model.Libro;
import com.idat.biblioteca.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroRepository repository;

    @GetMapping
    public List<Libro> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Libro libro) {
        if (repository.existsByIsbn(libro.getIsbn())) {
            return ResponseEntity.badRequest().body("El ISBN ya existe");
        }
        return ResponseEntity.ok(repository.save(libro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Integer id, @RequestBody Libro libroActualizado) {
        return repository.findById(id)
                .map(libro -> {
                    libro.setTitulo(libroActualizado.getTitulo());
                    libro.setAutor(libroActualizado.getAutor());
                    libro.setIsbn(libroActualizado.getIsbn());
                    libro.setStock(libroActualizado.getStock());
                    return ResponseEntity.ok(repository.save(libro));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        return repository.findById(id)
                .map(libro -> {
                    repository.delete(libro);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}