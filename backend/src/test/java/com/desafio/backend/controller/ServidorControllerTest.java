package com.desafio.backend.controller;

import com.desafio.backend.dto.ServidorRequestDTO;
import com.desafio.backend.model.Secretaria;
import com.desafio.backend.model.Servidor;
import com.desafio.backend.service.ServidorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServidorController.class)
class ServidorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServidorService service;

    @Test
    @DisplayName("GET /servidores - Deve listar servidores com HTTP 200 OK")
    void deveListarServidores() throws Exception {
        Secretaria sec = new Secretaria(1L, "Saúde", "SESAU");
        Servidor servidor = new Servidor(1L, "Ana Maria", "ana@email.com", LocalDate.of(1990, 5, 10), sec);

        when(service.listarTodos()).thenReturn(List.of(servidor));

        mockMvc.perform(get("/servidores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Ana Maria"))
                .andExpect(jsonPath("$[0].email").value("ana@email.com"));
    }

    @Test
    @DisplayName("DELETE /servidores/{id} - Deve deletar servidor com HTTP 244 No Content")
    void deveDeletarServidor() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/servidores/1"))
                .andExpect(status().isNoContent());
    }
}
