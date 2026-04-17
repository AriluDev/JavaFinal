package com.idat.biblioteca.controller;

import com.idat.biblioteca.model.Libro;
import com.idat.biblioteca.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public Libro guardar(@RequestBody Libro libro) {
        return repository.save(libro);
    }
}