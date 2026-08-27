package com.desafio.backend.controller;

import com.desafio.backend.model.Secretaria;
import com.desafio.backend.service.SecretariaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/secretarias")
public class SecretariaController {

    private final SecretariaService service;

    public SecretariaController(SecretariaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Secretaria>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PostMapping
    public ResponseEntity<Secretaria> criar(@Valid @RequestBody Secretaria secretaria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(secretaria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Secretaria> atualizar(@PathVariable Long id, @Valid @RequestBody Secretaria secretaria) {
        return ResponseEntity.ok(service.atualizar(id, secretaria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
