package com.idat.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "prestamos")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucion;

    private String estado; // Ej: "ACTIVO", "DEVUELTO"

    // Relación Muchos a Uno con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación Muchos a Uno con Libro
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;
}