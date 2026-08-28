package com.desafio.backend.controller;

import com.desafio.backend.dto.SecretariaRequestDTO;
import com.desafio.backend.model.Secretaria;
import com.desafio.backend.service.SecretariaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecretariaController.class)
class SecretariaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SecretariaService service;

    @Test
    @DisplayName("GET /secretarias - Deve retornar lista de secretarias com HTTP 200 OK")
    void deveListarSecretarias() throws Exception {
        when(service.listarTodas()).thenReturn(List.of(new Secretaria(1L, "Educação", "SEDUC")));

        mockMvc.perform(get("/secretarias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Educação"))
                .andExpect(jsonPath("$[0].sigla").value("SEDUC"));
    }

    @Test
    @DisplayName("POST /secretarias - Deve criar secretaria com HTTP 201 Created")
    void deveCriarSecretaria() throws Exception {
        Secretaria secretariaSalva = new Secretaria(1L, "Finanças", "SEFIN");

        when(service.salvar(any(SecretariaRequestDTO.class))).thenReturn(secretariaSalva);

        String jsonPayload = """
                {
                    "nome": "Finanças",
                    "sigla": "SEFIN"
                }
                """;

        mockMvc.perform(post("/secretarias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Finanças"))
                .andExpect(jsonPath("$.sigla").value("SEFIN"));
    }
}
