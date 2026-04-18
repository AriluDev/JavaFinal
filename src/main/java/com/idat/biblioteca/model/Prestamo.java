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

    private String estado; 
    
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

   
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;
}