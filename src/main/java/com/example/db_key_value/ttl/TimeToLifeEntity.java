package com.example.db_key_value.ttl;

import com.example.db_key_value.model.EvidenceEntity;
import com.example.db_key_value.repository.EvidenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TimeToLifeEntity {
    private final EvidenceRepository evidenceRepository;

    @Scheduled(fixedRate = 60000)
    public void deleteExpiredEntity(){
        LocalDateTime start = LocalDateTime.now();

        List<EvidenceEntity> entities = evidenceRepository.findExpiredEvidence();
        if (!entities.isEmpty())
            entities.stream().forEach(e -> evidenceRepository.delete(e));

        LocalDateTime end = LocalDateTime.now();

        log.info("Find expired entity. Took seconds: {}",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
    }
}
