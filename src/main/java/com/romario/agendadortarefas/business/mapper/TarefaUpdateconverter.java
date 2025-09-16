package com.romario.agendadortarefas.business.mapper;

import com.romario.agendadortarefas.business.dto.TarefasDTO;
import com.romario.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaUpdateconverter {
    void updateTarefas(TarefasDTO dto, @MappingTarget TarefasEntity entity);
}
