package com.desafio.backend.service;

import com.desafio.backend.dto.ServidorRequestDTO;
import com.desafio.backend.model.Secretaria;
import com.desafio.backend.model.Servidor;
import com.desafio.backend.repository.ServidorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServidorServiceImplTest {

    @Mock
    private ServidorRepository servidorRepository;

    @Mock
    private SecretariaService secretariaService;

    @InjectMocks
    private ServidorServiceImpl servidorService;

    private ServidorRequestDTO dtoValido;
    private Secretaria secretaria;

    @BeforeEach
    void setUp() {
        secretaria = new Secretaria(1L, "Educação", "SEDUC");

        dtoValido = new ServidorRequestDTO();
        dtoValido.setNome("Carlos Eduardo");
        dtoValido.setEmail("carlos@email.com");
        dtoValido.setDataNascimento(LocalDate.now().minusYears(30));
        dtoValido.setSecretariaId(1L);
    }

    @Test
    @DisplayName("Deve salvar servidor com sucesso")
    void deveSalvarServidorComSucesso() {
        Servidor servidorSalvo = new Servidor(1L, "Carlos Eduardo", "carlos@email.com", dtoValido.getDataNascimento(), secretaria);

        when(servidorRepository.existsByEmailIgnoreCase("carlos@email.com")).thenReturn(false);
        when(secretariaService.buscarPorId(1L)).thenReturn(secretaria);
        when(servidorRepository.save(any(Servidor.class))).thenReturn(servidorSalvo);

        Servidor resultado = servidorService.salvar(dtoValido);

        assertNotNull(resultado);
        assertEquals("Carlos Eduardo", resultado.getNome());
    }

    @Test
    @DisplayName("Deve rejeitar servidor com menos de 18 anos")
    void deveBarrarMenorDeIdade() {
        dtoValido.setDataNascimento(LocalDate.now().minusYears(17));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> servidorService.salvar(dtoValido));
        assertEquals("A idade do servidor deve estar entre 18 e 75 anos.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar servidor com mais de 75 anos")
    void deveBarrarIdadeSuperiorA75() {
        dtoValido.setDataNascimento(LocalDate.now().minusYears(76));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> servidorService.salvar(dtoValido));
        assertEquals("A idade do servidor deve estar entre 18 e 75 anos.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve barrar cadastro de e-mail duplicado (409 CONFLICT)")
    void deveBarrarEmailDuplicado() {
        when(servidorRepository.existsByEmailIgnoreCase("carlos@email.com")).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> servidorService.salvar(dtoValido));
        assertEquals(409, ex.getStatusCode().value());
    }
}
