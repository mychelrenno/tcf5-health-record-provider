package com.tcf5.health.record.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProntuarioDTO {

    private UUID id;
    private String pacienteId;
    private String tipoRegistro;
    private String especialidade;
    private LocalDateTime dataRegistro;
    private String conteudoHl7;
    private String observacoes;
}