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
@Tag(name = "Prontuário Provider", description = "Gestão de prontuários via CPF/CNS")
public class ProntuarioController {

    private final ProntuarioService service;

    @GetMapping("/paciente/{cpf}")
    @Operation(summary = "Lista o histórico completo pelo CPF")
    public ResponseEntity<List<ProntuarioDTO>> listarHistorico(@PathVariable String cpf) {
        List<ProntuarioDTO> dtos = service.listarHistorico(cpf).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/paciente/{cpf}/filtro")
    @Operation(summary = "Consulta filtrada por tipo ou especialidade")
    public ResponseEntity<List<ProntuarioDTO>> filtrar(
            @PathVariable String cpf,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String especialidade) {

        List<ProntuarioEntity> resultados;

        if (tipo != null) {
            resultados = service.filtrarPorTipo(cpf, tipo);
        } else if (especialidade != null) {
            resultados = service.filtrarPorEspecialidade(cpf, especialidade);
        } else {
            resultados = service.listarHistorico(cpf);
        }

        return ResponseEntity.ok(resultados.stream().map(this::mapToDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um registro detalhado pelo ID interno")
    public ResponseEntity<ProntuarioDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(mapToDTO(service.buscarPorId(id)));
    }

    @PatchMapping("/{id}/observacoes")
    @Operation(summary = "Atualiza notas clínicas do prontuário")
    public ResponseEntity<ProntuarioDTO> atualizarEdicao(@PathVariable UUID id, @RequestBody String observacoes) {
        ProntuarioEntity atualizado = service.atualizarObservacoes(id, observacoes);
        return ResponseEntity.ok(mapToDTO(atualizado));
    }

    private ProntuarioDTO mapToDTO(ProntuarioEntity entity) {
        return ProntuarioDTO.builder()
                .id(entity.getId())
                .cpf(entity.getCpf())
                .cns(entity.getCns())
                .tipoRegistro(entity.getTipoRegistro())
                .especialidade(entity.getEspecialidade())
                .dataRegistro(entity.getDataRegistro())
                .observacoes(entity.getObservacoes())
                .build();
    }
}