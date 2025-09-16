package com.romario.agendadortarefas.controller;

import com.romario.agendadortarefas.business.TarefasService;
import com.romario.agendadortarefas.business.dto.TarefasDTO;
import com.romario.agendadortarefas.infrastructure.entity.enums.StatusNotificacaoEnum;
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

    @DeleteMapping
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id){
        tarefasService.deletaTarefaPorId(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<TarefasDTO> alteraStatusNotificacao(
            @RequestParam("status")StatusNotificacaoEnum status,
            @RequestParam("id") String id
            ){
        return ResponseEntity.ok(
                tarefasService.alteraStatus(status, id)
        );
    }

    @PutMapping
    public ResponseEntity<TarefasDTO> atualizaNotificacao(
            @RequestBody TarefasDTO dto,
            @RequestParam("id") String id
    ){
        return ResponseEntity.ok(
                tarefasService.updateTarefas(dto, id)
        );
    }
}
