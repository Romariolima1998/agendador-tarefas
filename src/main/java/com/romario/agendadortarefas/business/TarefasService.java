package com.romario.agendadortarefas.business;

import com.romario.agendadortarefas.business.dto.TarefasDTO;
import com.romario.agendadortarefas.business.mapper.TarefaUpdateconverter;
import com.romario.agendadortarefas.business.mapper.TarefasConverter;
import com.romario.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.romario.agendadortarefas.infrastructure.entity.enums.StatusNotificacaoEnum;
import com.romario.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.romario.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.romario.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {
    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateconverter tarefaUpdateconverter;

    public TarefasDTO gravarTarefa(String token,TarefasDTO dto){
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity tarefa = tarefasRepository.save(tarefasConverter.paraTarefasEntity(dto));
        return tarefasConverter.paraTarefasDTO(tarefa);
    }

    public List<TarefasDTO> buscaTarefasAgendadaPorPeriodo(
            LocalDateTime dataInicial, LocalDateTime dataFinal
    ){
    return tarefasConverter.paraListaTarefasDTO(
            tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(
                    dataInicial, dataFinal, StatusNotificacaoEnum.PENDENTE)
    );
    }
    public List<TarefasDTO> buscaTarefasPorEmail(String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        return tarefasConverter.paraListaTarefasDTO(
                tarefasRepository.findByEmailUsuario(email)
        );
    }

    public void deletaTarefaPorId(String id){
        try {
            tarefasRepository.deleteById(id);
        } catch ( ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa, id nao localizado " + id, e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id){
        TarefasEntity tarefa = tarefasRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Erro ao atualizar status, id nao localizado " + id));
        tarefa.setStatusNotificacaoEnum(status);
        return tarefasConverter.paraTarefasDTO(
                tarefasRepository.save(tarefa)
        );
    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id){
        TarefasEntity tarefa = tarefasRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Erro ao atualizar tarefa, id nao localizado " + id));
        tarefaUpdateconverter.updateTarefas(dto, tarefa);
        return tarefasConverter.paraTarefasDTO(
                tarefasRepository.save(tarefa)
        );
    }
}
