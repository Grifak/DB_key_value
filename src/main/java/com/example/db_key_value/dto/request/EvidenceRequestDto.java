package com.example.db_key_value.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Объект хранения")
public class EvidenceRequestDto {

    @Schema(description = "Ключ")
    private Long key;

    @Schema(description = "Данные")
    private String value;

    @Schema(description = "Время жизни объекта")
    private Timestamp ttl;

    public EvidenceRequestDto(String value, Timestamp ttl) {
        this.value = value;
        this.ttl = ttl;
    }

    public EvidenceRequestDto(Long key, Timestamp ttl) {
        this.key = key;
        this.ttl = ttl;
    }
}
