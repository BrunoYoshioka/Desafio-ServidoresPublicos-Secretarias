package com.desafio.backend.service;

import com.desafio.backend.model.Secretaria;
import com.desafio.backend.repository.SecretariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretariaServiceImpl implements SecretariaService {

    private final SecretariaRepository repository;

    public SecretariaServiceImpl(SecretariaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Secretaria> listarTodas() {
        return repository.findAll();
    }

    @Override
    public Secretaria buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Secretaria não encontrada com ID: " + id));
    }

    @Override
    public Secretaria salvar(Secretaria secretaria) {
        return repository.save(secretaria);
    }

    @Override
    public Secretaria atualizar(Long id, Secretaria secretaria) {
        Secretaria existente = buscarPorId(id);
        existente.setNome(secretaria.getNome());
        existente.setSigla(secretaria.getSigla());
        return repository.save(existente);
    }

    @Override
    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
