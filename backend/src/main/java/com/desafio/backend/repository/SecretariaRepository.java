package com.desafio.backend.repository;

import com.desafio.backend.model.Secretaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretariaRepository extends JpaRepository<Secretaria, Long> {
}
