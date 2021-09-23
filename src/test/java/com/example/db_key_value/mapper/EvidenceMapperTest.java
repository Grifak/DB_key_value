package com.example.db_key_value.mapper;

import com.example.db_key_value.dto.request.EvidenceRequestDto;
import com.example.db_key_value.dto.response.EvidenceResponseDto;
import com.example.db_key_value.model.EvidenceEntity;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EvidenceMapperTest {
    private EvidenceMapper mapper = Mappers.getMapper(EvidenceMapper.class);

    @Test
    public void testEntityToResponseDto(){
        EvidenceEntity entity = EvidenceEntity.builder()
                .value("2345")
                .build();

        EvidenceResponseDto responseDto = mapper.entityToResponseDto(entity);

        Assert.assertEquals("2345", responseDto.getValue());
    }

    @Test
    public void testRequestDtoToEntity(){
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        Long key = Long.valueOf(1);
        EvidenceRequestDto requestDto = new  EvidenceRequestDto(
                key,
                "test",
                dateTime
        );

        EvidenceEntity entity = mapper.requestDtoToEntity(requestDto);

        Assert.assertNotEquals(entity, null);
        Assert.assertEquals(key, entity.getKey());
        Assert.assertEquals("test", entity.getValue());
        Assert.assertEquals(dateTime, entity.getTtl());
    }
}
