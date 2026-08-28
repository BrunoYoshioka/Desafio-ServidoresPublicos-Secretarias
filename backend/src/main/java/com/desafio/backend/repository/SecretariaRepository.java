package com.desafio.backend.repository;

import com.desafio.backend.model.Secretaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretariaRepository extends JpaRepository<Secretaria, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsBySiglaIgnoreCase(String sigla);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);

    boolean existsBySiglaIgnoreCaseAndIdNot(String sigla, Long id);
}
