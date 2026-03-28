package com.tcf5.health.record.provider.controller;

import com.tcf5.health.record.provider.dto.ProntuarioDTO;
import com.tcf5.health.record.provider.model.ProntuarioEntity;
import com.tcf5.health.record.provider.service.ProntuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/prontuarios")
@RequiredArgsConstructor
@Tag(name = "Prontuário Provider", description = "Endpoints para consulta e gestão de prontuários unificados")
public class ProntuarioController {

    private final ProntuarioService service;

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Lista o histórico completo de um paciente",
            description = "Retorna todos os registros ordenados pelo mais recente")
    public ResponseEntity<List<ProntuarioDTO>> listarHistorico(@PathVariable String pacienteId) {
        List<ProntuarioDTO> dtos = service.listarHistorico(pacienteId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/paciente/{pacienteId}/filtro")
    @Operation(summary = "Consulta filtrada por tipo ou especialidade",
            description = "Realiza buscas filtradas no histórico unificado do paciente.")
    public ResponseEntity<List<ProntuarioDTO>> filtrar(
            @PathVariable String pacienteId,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String especialidade) {

        List<ProntuarioEntity> resultados;

        if (tipo != null) {
            resultados = service.filtrarPorTipo(pacienteId, tipo);
        } else if (especialidade != null) {
            resultados = service.filtrarPorEspecialidade(pacienteId, especialidade);
        } else {
            resultados = service.listarHistorico(pacienteId);
        }

        return ResponseEntity.ok(resultados.stream().map(this::mapToDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um registro detalhado pelo ID")
    public ResponseEntity<ProntuarioDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(mapToDTO(service.buscarPorId(id)));
    }

    private ProntuarioDTO mapToDTO(ProntuarioEntity entity) {
        return ProntuarioDTO.builder()
                .id(entity.getId())
                .pacienteId(entity.getPacienteId())
                .tipoRegistro(entity.getTipoRegistro())
                .especialidade(entity.getEspecialidade())
                .dataRegistro(entity.getDataRegistro())
                .conteudoHl7(entity.getConteudoHl7())
                .observacoes(entity.getObservacoes())
                .build();
    }
}