package com.tcf5.health.record.provider.repository;

import com.tcf5.health.record.provider.model.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, UUID> {

    List<HealthRecord> findByPatientIdOrderByProcessedAtDesc(String patientId);

    Optional<HealthRecord> findByClientId(UUID clientId);

}