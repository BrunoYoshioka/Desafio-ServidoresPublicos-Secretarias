package com.desafio.backend.service;

import com.desafio.backend.dto.SecretariaRequestDTO;
import com.desafio.backend.model.Secretaria;
import java.util.List;

public interface SecretariaService {
    List<Secretaria> listarTodas();
    Secretaria buscarPorId(Long id);
    Secretaria salvar(SecretariaRequestDTO dto);
    Secretaria atualizar(Long id, SecretariaRequestDTO dto);
    void deletar(Long id);
}
