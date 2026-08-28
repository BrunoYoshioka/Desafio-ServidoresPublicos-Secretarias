package com.desafio.backend.service;

import com.desafio.backend.dto.SecretariaRequestDTO;
import com.desafio.backend.model.Secretaria;
import com.desafio.backend.repository.SecretariaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Secretaria não encontrada com ID: " + id));
    }

    @Override
    public Secretaria salvar(SecretariaRequestDTO dto) {
        if (repository.existsByNomeIgnoreCase(dto.getNome().trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma secretaria cadastrada com este nome.");
        }
        if (repository.existsBySiglaIgnoreCase(dto.getSigla().trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma secretaria cadastrada com esta sigla.");
        }

        Secretaria novaSecretaria = new Secretaria();
        novaSecretaria.setNome(dto.getNome().trim());
        novaSecretaria.setSigla(dto.getSigla().trim());

        return repository.save(novaSecretaria);
    }

    @Override
    public Secretaria atualizar(Long id, SecretariaRequestDTO dto) {
        Secretaria existente = buscarPorId(id);

        if (repository.existsByNomeIgnoreCaseAndIdNot(dto.getNome().trim(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe outra secretaria cadastrada com este nome.");
        }
        if (repository.existsBySiglaIgnoreCaseAndIdNot(dto.getSigla().trim(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe outra secretaria cadastrada com esta sigla.");
        }

        existente.setNome(dto.getNome().trim());
        existente.setSigla(dto.getSigla().trim());

        return repository.save(existente);
    }

    @Override
    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
