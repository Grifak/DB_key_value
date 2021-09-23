package com.example.db_key_value.contreller;

import com.example.db_key_value.dto.response.EvidenceResponseDto;
import com.example.db_key_value.service.EvidenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/evidence")
@Tag(name = "Данные")
@RestController
public class EvidenceController {
    private final EvidenceService evidenceService;

    @Operation(summary = "Получение значения по ключу")
    @GetMapping("/{key}")
    public ResponseEntity<EvidenceResponseDto> getValueByKey(@PathVariable Long key){
        EvidenceResponseDto responseDto = evidenceService.getValueByKey(key);

        return ResponseEntity.ok().body(responseDto);
    }


    @GetMapping
    public ResponseEntity<List<EvidenceResponseDto>> getAllValue(){
        List<EvidenceResponseDto> response = evidenceService.getAllValues();

        return ResponseEntity.ok().body(response);
    }
}