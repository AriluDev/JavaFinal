package com.idat.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    private String autor;

    @Column(unique = true)
    @NotBlank(message = "El ISBN es obligatorio")
    private String isbn;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}