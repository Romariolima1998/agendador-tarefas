package com.romario.agendadortarefas.controller;

import com.romario.agendadortarefas.business.TarefasService;
import com.romario.agendadortarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("tarefas")
@RequiredArgsConstructor
public class TarefasController {
    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(
            @RequestHeader("Authorization") String token, @RequestBody TarefasDTO dto
    ){
        return ResponseEntity.status(201)
                .body(tarefasService.gravarTarefa(token, dto));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> buscaListaTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datainicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datafinal
            ){
        return ResponseEntity.ok(
                tarefasService.buscaTarefasAgendadaPorPeriodo(datainicial, datafinal)
        );
    }

    @GetMapping
    public ResponseEntity<List<TarefasDTO>> buscaTarefasPorEmail(
            @RequestHeader("Authorization") String token
            ){
        return ResponseEntity.ok(
                tarefasService.buscaTarefasPorEmail(token)
        );
    }
}
