package com.romario.agendadortarefas.business;

import com.romario.agendadortarefas.business.dto.TarefasDTO;
import com.romario.agendadortarefas.business.mapper.TarefasConverter;
import com.romario.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.romario.agendadortarefas.infrastructure.entity.enums.StatusNotificacaoEnum;
import com.romario.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.romario.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {
    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token,TarefasDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity tarefa = tarefasRepository.save(tarefasConverter.paraTarefasEntity(dto));
        return tarefasConverter.paraTarefasDTO(tarefa);
    }
}
