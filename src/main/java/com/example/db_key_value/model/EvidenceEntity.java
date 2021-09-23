package com.example.db_key_value.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evidence")
public class
EvidenceEntity {
    @Id
    @Column(name = "key", nullable = false)
    @CsvBindByName
    private Long key;

    @CsvBindByName
    @Column(name = "value", nullable = false)
    private String value;

    @CsvBindByName
    @Column(name = "ttl", columnDefinition = "TIMESTAMP(1)")
    @CsvDate("yyyy-MM-dd HH:mm")
    private Timestamp ttl;

}
