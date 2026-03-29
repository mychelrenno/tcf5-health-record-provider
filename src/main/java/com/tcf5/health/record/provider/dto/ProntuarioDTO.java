package com.tcf5.health.record.provider.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String cpf;
    private String cns;
    private String tipoRegistro;
    private String especialidade;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataRegistro;

    private String observacoes;
}