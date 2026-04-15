package com.tcf5.health.record.provider.service;

import com.tcf5.health.record.provider.model.HealthRecord;
import com.tcf5.health.record.provider.repository.HealthRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProntuarioService {

    private final HealthRecordRepository healthRecordRepository;

    public List<HealthRecord> listarHistorico(String cpf) {
        return healthRecordRepository.findByPatientIdOrderByProcessedAtDesc(cpf);
    }

    public HealthRecord buscarPorId(UUID id) {
        return healthRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Health Record não encontrado"));
    }
}