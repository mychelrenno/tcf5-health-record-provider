package com.tcf5.health.record.provider.controller;

import com.tcf5.health.record.provider.dto.HealthRecordDTO;
import com.tcf5.health.record.provider.model.HealthRecord;
import com.tcf5.health.record.provider.service.ProntuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/prontuarios")
@RequiredArgsConstructor
@Validated
@Tag(name = "Prontuário Provider", description = "Gestão de prontuários via CPF/CNS")
public class ProntuarioController {

    private final ProntuarioService service;

    @GetMapping("/paciente/{cpf}")
    @Operation(summary = "Lista o histórico completo pelo CPF")
    public ResponseEntity<List<HealthRecordDTO>> listarHistorico(
            @PathVariable @Size(min = 11, max = 14, message = "O CPF deve ter 11 dígitos") String cpf) {

        List<HealthRecordDTO> dtos = service.listarHistorico(cpf).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um registro detalhado pelo ID interno")
    public ResponseEntity<HealthRecordDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(mapToDTO(service.buscarPorId(id)));
    }

    private HealthRecordDTO mapToDTO(HealthRecord entity) {
        return HealthRecordDTO.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .resourceType(entity.getResourceType())
                .clientId(entity.getClientId())
                .resourceContent(entity.getResourceContent())
                .processedAt(entity.getProcessedAt())
                .build();
    }
}