package com.desafio.backend.service;

import com.desafio.backend.dto.ServidorRequestDTO;
import com.desafio.backend.model.Secretaria;
import com.desafio.backend.model.Servidor;
import com.desafio.backend.repository.ServidorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ServidorServiceImpl implements ServidorService {

    private final ServidorRepository servidorRepository;
    private final SecretariaService secretariaService;

    public ServidorServiceImpl(ServidorRepository servidorRepository, SecretariaService secretariaService) {
        this.servidorRepository = servidorRepository;
        this.secretariaService = secretariaService;
    }

    @Override
    public List<Servidor> listarTodos() {
        return servidorRepository.findAll();
    }

    @Override
    public Servidor salvar(ServidorRequestDTO dto) {
        validarIdade(dto.getDataNascimento());

        // Validação defensiva de unicidade do e-mail ao criar
        if (servidorRepository.existsByEmailIgnoreCase(dto.getEmail().trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um servidor cadastrado com este e-mail.");
        }

        Secretaria secretaria = secretariaService.buscarPorId(dto.getSecretariaId());

        Servidor servidor = new Servidor();
        servidor.setNome(dto.getNome().trim());
        servidor.setEmail(dto.getEmail().trim());
        servidor.setDataNascimento(dto.getDataNascimento());
        servidor.setSecretaria(secretaria);

        return servidorRepository.save(servidor);
    }

    @Override
    public Servidor atualizar(Long id, ServidorRequestDTO dto) {
        validarIdade(dto.getDataNascimento());

        Servidor servidorExistente = servidorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servidor não encontrado com ID: " + id));

        // Validação defensiva de unicidade do e-mail ao atualizar (ignorando o próprio ID)
        if (servidorRepository.existsByEmailIgnoreCaseAndIdNot(dto.getEmail().trim(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe outro servidor cadastrado com este e-mail.");
        }

        Secretaria secretaria = secretariaService.buscarPorId(dto.getSecretariaId());

        servidorExistente.setNome(dto.getNome().trim());
        servidorExistente.setEmail(dto.getEmail().trim());
        servidorExistente.setDataNascimento(dto.getDataNascimento());
        servidorExistente.setSecretaria(secretaria);

        return servidorRepository.save(servidorExistente);
    }

    @Override
    public void deletar(Long id) {
        if (!servidorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Servidor não encontrado com ID: " + id);
        }
        servidorRepository.deleteById(id);
    }

    private void validarIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória.");
        }
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade < 18 || idade > 75) {
            throw new IllegalArgumentException("A idade do servidor deve estar entre 18 e 75 anos.");
        }
    }
}
