package com.example.db_key_value.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Объект хранеия")
public class EvidenceResponseDto {

    @Schema(description = "Данные")
    private String value;

}
