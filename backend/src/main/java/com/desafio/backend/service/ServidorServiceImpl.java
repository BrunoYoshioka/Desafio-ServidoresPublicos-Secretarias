package com.desafio.backend.service;

import com.desafio.backend.dto.ServidorRequestDTO;
import com.desafio.backend.model.Secretaria;
import com.desafio.backend.model.Servidor;
import com.desafio.backend.repository.ServidorRepository;
import org.springframework.stereotype.Service;

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
        Secretaria secretaria = secretariaService.buscarPorId(dto.getSecretariaId());

        Servidor servidor = new Servidor();
        servidor.setNome(dto.getNome());
        servidor.setEmail(dto.getEmail());
        servidor.setDataNascimento(dto.getDataNascimento());
        servidor.setSecretaria(secretaria);

        return servidorRepository.save(servidor);
    }

    @Override
    public Servidor atualizar(Long id, ServidorRequestDTO dto) {
        validarIdade(dto.getDataNascimento());
        Servidor servidorExistente = servidorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servidor não encontrado com ID: " + id));

        Secretaria secretaria = secretariaService.buscarPorId(dto.getSecretariaId());

        servidorExistente.setNome(dto.getNome());
        servidorExistente.setEmail(dto.getEmail());
        servidorExistente.setDataNascimento(dto.getDataNascimento());
        servidorExistente.setSecretaria(secretaria);

        return servidorRepository.save(servidorExistente);
    }

    @Override
    public void deletar(Long id) {
        if (!servidorRepository.existsById(id)) {
            throw new RuntimeException("Servidor não encontrado com ID: " + id);
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
