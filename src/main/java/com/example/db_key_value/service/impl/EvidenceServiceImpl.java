package com.example.db_key_value.service.impl;

import com.example.db_key_value.csv.EvidenceCsvReader;
import com.example.db_key_value.csv.EvidenceCsvWriter;
import com.example.db_key_value.dto.request.EvidenceRequestDto;
import com.example.db_key_value.dto.response.EvidenceResponseDto;
import com.example.db_key_value.exception.*;
import com.example.db_key_value.mapper.EvidenceMapper;
import com.example.db_key_value.model.EvidenceEntity;
import com.example.db_key_value.repository.EvidenceRepository;
import com.example.db_key_value.service.EvidenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EvidenceServiceImpl implements EvidenceService {
    private final EvidenceRepository evidenceRepository;
    private final EvidenceMapper evidenceMapper;
    private final EvidenceCsvWriter csvWriter;
    private final EvidenceCsvReader csvReader;

    @Transactional
    @Override
    public EvidenceResponseDto getValueByKey(Long key) {
        EvidenceEntity evidenceEntity = evidenceRepository.findById(key).orElseThrow(
                () -> new EntityNotFoundException(key)
        );

        return evidenceMapper.entityToResponseDto(evidenceEntity);
    }

    @Transactional
    @Override
    public Boolean createEvidence(EvidenceRequestDto requestDto) {
        if(requestDto.getKey() == null) throw new RequiredFieldIsEmptyException(requestDto.getKey());
        if(requestDto.getValue() == null) throw new RequiredFieldIsEmptyException(requestDto.getValue());

        EvidenceEntity evidenceEntity = evidenceMapper.requestDtoToEntity(requestDto);
        evidenceRepository.save(evidenceEntity);

        return true;
    }

    @Transactional
    @Override
    public EvidenceResponseDto deleteEvidence(Long key) {
        EvidenceEntity evidenceEntity = evidenceRepository.findById(key).orElseThrow(
                ()-> new EntityNotFoundException(key)
        );

        evidenceRepository.deleteById(key);

        return evidenceMapper.entityToResponseDto(evidenceEntity);
    }

    @Transactional
    @Override
    public List<EvidenceResponseDto> createEvidenceFromCsv(MultipartFile file) {
        if(!file.getOriginalFilename().contains(".csv"))
            throw new InvalidFileFormatException(file.getOriginalFilename());
        if(file.isEmpty()) throw new FileEmptyException(file.getOriginalFilename());
        List<EvidenceEntity> entities = csvReader.csvToTask(file);

        entities.stream().forEach(e -> evidenceRepository.save(e));

        return entities.stream()
                .map(e -> evidenceMapper.entityToResponseDto(e))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Boolean evidenceToCsv(HttpServletResponse response) {
        List<EvidenceEntity> entities = evidenceRepository.findAll();
        if(entities.isEmpty()) throw new DataBaseIsEmptyException();

        csvWriter.taskToCsv(entities, response);

        return true;
    }

    @Transactional
    @Override
    public List<EvidenceResponseDto> getAllValues() {
        List<EvidenceEntity> entities = evidenceRepository.findAll();

        return entities.stream()
                .map(e -> evidenceMapper.entityToResponseDto(e))
                .collect(Collectors.toList());
    }
}
