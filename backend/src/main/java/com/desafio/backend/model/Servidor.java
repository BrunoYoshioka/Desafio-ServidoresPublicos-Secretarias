package com.desafio.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "servidores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "secretaria_id", nullable = false)
    private Secretaria secretaria;
}
