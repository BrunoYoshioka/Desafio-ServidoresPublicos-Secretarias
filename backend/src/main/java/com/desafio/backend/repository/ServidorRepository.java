package com.desafio.backend.repository;

import com.desafio.backend.model.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServidorRepository extends JpaRepository<Servidor, Long> {
}
