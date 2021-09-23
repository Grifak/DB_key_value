package com.example.db_key_value.mapper;

import com.example.db_key_value.dto.request.EvidenceRequestDto;
import com.example.db_key_value.dto.response.EvidenceResponseDto;
import com.example.db_key_value.model.EvidenceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author vadim
 * @version 1.0
 * Mapper for {@link EvidenceEntity}
 */
@Mapper
public abstract class EvidenceMapper {
    /**
     * Convert entity to responseDto
     * @param entity - entity from dataBase
     * @return responseDto
     */
    public abstract EvidenceResponseDto entityToResponseDto(EvidenceEntity entity);

    /**
     * Convert requestDto to entity
     * @param requestDto - request from controller
     * @return entity
     */
    public EvidenceEntity requestDtoToEntity(EvidenceRequestDto requestDto){
        if ( requestDto == null ) {
            return null;
        }

        EvidenceEntity entity = new EvidenceEntity();
        entity.setKey(requestDto.getKey());
        entity.setValue(requestDto.getValue());

        if(requestDto.getTtl() != null) {
            entity.setTtl(requestDto.getTtl());
        } else {
            entity.setTtl(Timestamp
                    .valueOf(LocalDateTime.of(2022,12,30,23,59)));
        }

        return entity;
    }
}
