package com.example.db_key_value.csv;

import com.example.db_key_value.exception.CsvIoException;
import com.example.db_key_value.model.EvidenceEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Vadim
 * @version 1.0
 * Writer for csv files
 */
@Component
public class EvidenceCsvWriter {

    @Value("${csv.filename}")
    private String filename;

    /**
     * Convert entities to csv files
     * @param entities list with entities
     * @param response httpServletresponse from controller
     * @throws CsvIoException if problems with IO streams
     */
    public void taskToCsv(List<EvidenceEntity> entities, HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s", filename));

        try {
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"key", "value", "ttl"};
            String[] nameMapping = {"key", "value", "ttl"};

            csvWriter.writeHeader(csvHeader);

            for (EvidenceEntity entity : entities) {
                csvWriter.write(entity, nameMapping);
            }

            csvWriter.close();
        } catch (IOException e) {
            throw new CsvIoException();
        }
    }
}
