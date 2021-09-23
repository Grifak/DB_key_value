package com.example.db_key_value.service;

import com.example.db_key_value.csv.EvidenceCsvReader;
import com.example.db_key_value.csv.EvidenceCsvWriter;
import com.example.db_key_value.dto.request.EvidenceRequestDto;
import com.example.db_key_value.dto.response.EvidenceResponseDto;
import com.example.db_key_value.exception.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Vadim
 * @version 1.0
 * Interface for working with evidence
 */
public interface EvidenceService {
    /**
     * Search value in DataBase by key
     * @param key Long for value search
     * @throws EntityNotFoundException if key not found in DataBase
     * @return value from DataBase
     */
    EvidenceResponseDto getValueByKey(Long key);

    /**
     * Create new evidence
     * @param requestDto DTO with new evidence
     * @return true if evidence created, false if didn't created
     * @throws RequiredFieldIsEmptyException if 'key' or 'value' fields in the requestDto is empty
     */
    Boolean createEvidence(EvidenceRequestDto requestDto);

    /**
     * Delete value by key
     * @param key Long for delete value
     * @throws EntityNotFoundException if key not found in DataBase
     * @return responseDto
     */
    EvidenceResponseDto deleteEvidence(Long key);

    /**
     * Creating entity from csv file {@link EvidenceCsvReader}
     * @param file file to read
     * @return List of created entities
     * @throws InvalidFileFormatException if file format is not CSV
     * @throws FileEmptyException if file is empty
     */
    List<EvidenceResponseDto> createEvidenceFromCsv(MultipartFile file);

    /**
     * Write all entities to CSV file {@link EvidenceCsvWriter}
     * @param response from controller
     * @return true if writing is completed
     * @throws DataBaseIsEmptyException if dataBase is empty
     */
    Boolean evidenceToCsv(HttpServletResponse response);

    List<EvidenceResponseDto> getAllValues();
}
