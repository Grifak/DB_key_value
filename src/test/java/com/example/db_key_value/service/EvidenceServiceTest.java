package com.example.db_key_value.service;

import com.example.db_key_value.dto.request.EvidenceRequestDto;
import com.example.db_key_value.exception.RequiredFieldIsEmptyException;
import com.example.db_key_value.mapper.EvidenceMapper;
import com.example.db_key_value.model.EvidenceEntity;
import com.example.db_key_value.repository.EvidenceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class EvidenceServiceTest {
    @Autowired
    private EvidenceService evidenceService;

    @MockBean
    private EvidenceRepository evidenceRepository;

    @MockBean
    private EvidenceMapper evidenceMapper;

    @Test
    void createEvidence() {
        EvidenceRequestDto requestDto = EvidenceRequestDto.builder()
                .key(1L)
                .value("test")
                .ttl(Timestamp.valueOf(LocalDateTime.of(2022,12,30,23,59)))
                .build();

        Boolean isEvidenceCreated = evidenceService.createEvidence(requestDto);

        Assert.assertTrue(isEvidenceCreated);

        Mockito.verify(evidenceRepository, Mockito.times(1))
                .save(evidenceMapper.requestDtoToEntity(requestDto));
        Mockito.verify(evidenceMapper, Mockito.times(2)).requestDtoToEntity(requestDto);
    }

    @Test
    void createEvidenceThrowKeyException(){
        EvidenceRequestDto requestDto = EvidenceRequestDto.builder()
                .value("test")
                .ttl(Timestamp.valueOf(LocalDateTime.of(2022,12,30,23,59)))
                .build();

        Throwable thrown = assertThrows(RequiredFieldIsEmptyException.class, () -> {
            evidenceService.createEvidence(requestDto);
        });

        Assert.assertEquals("Field 'key' cant be empty", thrown.getMessage());
    }

    @Test
    void createEvidenceThrowValueException(){
        EvidenceRequestDto requestDto = EvidenceRequestDto.builder()
                .key(1L)
                .ttl(Timestamp.valueOf(LocalDateTime.of(2022,12,30,23,59)))
                .build();

        Throwable thrown = assertThrows(RequiredFieldIsEmptyException.class, () -> {
            evidenceService.createEvidence(requestDto);
        });

        Assert.assertEquals("Field 'value' cant be empty", thrown.getMessage());
    }
}