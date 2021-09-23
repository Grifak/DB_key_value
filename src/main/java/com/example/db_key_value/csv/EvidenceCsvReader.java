package com.example.db_key_value.csv;

import com.example.db_key_value.exception.CsvIoException;
import com.example.db_key_value.model.EvidenceEntity;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Vadim
 * @version 1.0
 * Reader for csv files
 */
@Component
public class EvidenceCsvReader {
    /**
     * Convert csv file to entities
     * @param file file to read
     * @return new entities
     * @throws CsvIoException if problems with IO streams
     */
    public List<EvidenceEntity> csvToTask(MultipartFile file){
        List<EvidenceEntity> entities = null;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            entities = new CsvToBeanBuilder(reader)
                    .withType(EvidenceEntity.class).build().parse();

        }catch(IOException ex){
            throw new CsvIoException();
        }

        return entities;
    }
}
