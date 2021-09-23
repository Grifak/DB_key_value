package com.example.db_key_value.repository;

import com.example.db_key_value.model.EvidenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenceRepository extends JpaRepository<EvidenceEntity, Long>{
    @Modifying
    @Query(nativeQuery = true,
            value = "SELECT * FROM evidence WHERE ttl < current_timestamp")
    List<EvidenceEntity> findExpiredEvidence();
}
