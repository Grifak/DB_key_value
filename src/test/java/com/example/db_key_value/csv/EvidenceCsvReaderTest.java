package com.example.db_key_value.csv;

import com.example.db_key_value.service.EvidenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EvidenceCsvReaderTest {
    @Autowired
    private EvidenceService evidenceService;

    @Test
    public void csvToEntityTest() {

    }
}
