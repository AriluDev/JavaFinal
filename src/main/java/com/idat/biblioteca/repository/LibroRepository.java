package com.idat.biblioteca.repository;

import com.idat.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    boolean existsByIsbn(String isbn); // Para validar que no se repitan libros
}