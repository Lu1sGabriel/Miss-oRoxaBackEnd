package org.missao.roxa.missaoroxabackend.core.shared.helper.mapper;

import org.springframework.data.domain.Page;

import java.util.List;

public interface IMapper<ResponseDTO extends IMappableDto, Entity extends IMappableEntity> {

    ResponseDTO toDto(Entity entity);

    List<ResponseDTO> toDtoList(List<Entity> entities);

    Page<ResponseDTO> toDtoPage(Page<Entity> entities);

    default ResponseDTO toDto(Entity entity, List<Entity> relatedEntities) {
        return toDto(entity);
    }

}