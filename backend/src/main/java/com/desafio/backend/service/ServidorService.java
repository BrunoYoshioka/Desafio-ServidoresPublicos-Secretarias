package com.desafio.backend.service;

import com.desafio.backend.dto.ServidorRequestDTO;
import com.desafio.backend.model.Servidor;
import java.util.List;

public interface ServidorService {
    List<Servidor> listarTodos();
    Servidor salvar(ServidorRequestDTO dto);
    Servidor atualizar(Long id, ServidorRequestDTO dto);
    void deletar(Long id);
}
