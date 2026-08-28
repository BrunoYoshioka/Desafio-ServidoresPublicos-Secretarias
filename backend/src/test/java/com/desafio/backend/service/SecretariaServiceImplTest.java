package com.desafio.backend.service;

import com.desafio.backend.dto.SecretariaRequestDTO;
import com.desafio.backend.model.Secretaria;
import com.desafio.backend.repository.SecretariaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecretariaServiceImplTest {

    @Mock
    private SecretariaRepository repository;

    @InjectMocks
    private SecretariaServiceImpl service;

    private SecretariaRequestDTO dto;
    private Secretaria secretaria;

    @BeforeEach
    void setUp() {
        dto = new SecretariaRequestDTO();
        dto.setNome("Secretaria de Saúde");
        dto.setSigla("SESAU");

        secretaria = new Secretaria(1L, "Secretaria de Saúde", "SESAU");
    }

    @Test
    @DisplayName("Deve listar todas as secretarias com sucesso")
    void deveListarTodas() {
        when(repository.findAll()).thenReturn(List.of(secretaria));

        List<Secretaria> resultado = service.listarTodas();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve buscar secretaria por ID existente")
    void deveBuscarPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(secretaria));

        Secretaria resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Deve lançar 404 NOT_FOUND ao buscar ID inexistente")
    void deveLancarExcecaoIdInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.buscarPorId(99L));
        assertEquals(404, ex.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve salvar secretaria com dados válidos")
    void deveSalvarComSucesso() {
        when(repository.existsByNomeIgnoreCase("Secretaria de Saúde")).thenReturn(false);
        when(repository.existsBySiglaIgnoreCase("SESAU")).thenReturn(false);
        when(repository.save(any(Secretaria.class))).thenReturn(secretaria);

        Secretaria resultado = service.salvar(dto);

        assertNotNull(resultado);
        verify(repository, times(1)).save(any(Secretaria.class));
    }

    @Test
    @DisplayName("Deve barrar salvar secretaria com Nome duplicado (409 CONFLICT)")
    void deveLancarExcecaoNomeDuplicadoAoSalvar() {
        when(repository.existsByNomeIgnoreCase("Secretaria de Saúde")).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.salvar(dto));
        assertEquals(409, ex.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve barrar salvar secretaria com Sigla duplicada (409 CONFLICT)")
    void deveLancarExcecaoSiglaDuplicadaAoSalvar() {
        when(repository.existsByNomeIgnoreCase("Secretaria de Saúde")).thenReturn(false);
        when(repository.existsBySiglaIgnoreCase("SESAU")).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.salvar(dto));
        assertEquals(409, ex.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve atualizar secretaria com sucesso ignorando o próprio ID")
    void deveAtualizarComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(secretaria));
        when(repository.existsByNomeIgnoreCaseAndIdNot("Secretaria de Saúde", 1L)).thenReturn(false);
        when(repository.existsBySiglaIgnoreCaseAndIdNot("SESAU", 1L)).thenReturn(false);
        when(repository.save(any(Secretaria.class))).thenReturn(secretaria);

        Secretaria resultado = service.atualizar(1L, dto);

        assertNotNull(resultado);
        verify(repository, times(1)).save(any(Secretaria.class));
    }

    @Test
    @DisplayName("Deve deletar secretaria por ID existente")
    void deveDeletarComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(secretaria));
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.deletar(1L));

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
