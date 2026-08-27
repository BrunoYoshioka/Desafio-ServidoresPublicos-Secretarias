package com.desafio.backend.service;

import com.desafio.backend.model.Secretaria;
import java.util.List;

public interface SecretariaService {
    List<Secretaria> listarTodas();
    Secretaria buscarPorId(Long id);
    Secretaria salvar(Secretaria secretaria);
    Secretaria atualizar(Long id, Secretaria secretaria);
    void deletar(Long id);
}
