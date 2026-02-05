package com.example.cursos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "estudiante")
public class Estudiante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    // LADO INVERSO: apunta al campo "estudiantes" de la clase Curso
    @ManyToMany(mappedBy = "estudiantes")
    private Set<Curso> cursos = new HashSet<>();
}