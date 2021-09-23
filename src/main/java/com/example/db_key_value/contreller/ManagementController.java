package com.example.db_key_value.contreller;

import com.example.db_key_value.dto.response.EvidenceResponseDto;
import com.example.db_key_value.service.EvidenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/management")
@Tag(name = "Управление базой данных")
@RestController
public class ManagementController {
    private final EvidenceService evidenceService;

    @Operation(summary = "Сохранение данных в файл")
    @GetMapping("/save")
    public ResponseEntity dump(HttpServletResponse response){
        Boolean result = evidenceService.evidenceToCsv(response);

        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Загрузка данных из файла")
    @PostMapping("/upload")
    public ResponseEntity<List<EvidenceResponseDto>> upload(@RequestParam("file") MultipartFile file){
        List<EvidenceResponseDto> response = evidenceService.createEvidenceFromCsv(file);

        return ResponseEntity.ok().body(response);
    }
}

