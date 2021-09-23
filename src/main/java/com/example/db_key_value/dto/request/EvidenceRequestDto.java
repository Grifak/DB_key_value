package com.example.db_key_value.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Data
@Schema(description = "Объект хранения")
public class EvidenceRequestDto {

    @Schema(description = "Ключ")
    private Long key;

    @Schema(description = "Данные")
    private String value;

    @Schema(description = "Время жизни объекта")
    private Timestamp ttl;

}
