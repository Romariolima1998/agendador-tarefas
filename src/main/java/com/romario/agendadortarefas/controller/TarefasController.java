package com.romario.agendadortarefas.controller;

import com.romario.agendadortarefas.business.TarefasService;
import com.romario.agendadortarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
