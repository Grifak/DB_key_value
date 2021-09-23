package com.example.db_key_value.contreller;

import com.example.db_key_value.dto.request.EvidenceRequestDto;
import com.example.db_key_value.dto.response.EvidenceResponseDto;
import com.example.db_key_value.service.EvidenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Управление данными в бд")
@RequestMapping("api/evidence/management")
public class EvidenceManagementController {
    private final EvidenceService evidenceService;

    @Operation(summary = "Создание записи в БД")
    @PostMapping
    public ResponseEntity<Boolean> createEvidence(@RequestBody EvidenceRequestDto requestDto){
        Boolean flag = evidenceService.createEvidence(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(flag);
    }

    @Operation(summary = "Удаление записи по ключу")
    @DeleteMapping("/{key}")
    public ResponseEntity<EvidenceResponseDto> deleteEvidence(@PathVariable Long key){
        EvidenceResponseDto responseDto = evidenceService.deleteEvidence(key);

        return ResponseEntity.ok().body(responseDto);
    }

}
