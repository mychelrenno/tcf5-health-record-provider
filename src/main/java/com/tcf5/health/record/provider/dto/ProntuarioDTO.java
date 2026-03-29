package com.tcf5.health.record.provider.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos")
    private String cpf;

    @Size(min = 15, max = 15, message = "O CNS deve ter 15 dígitos")
    private String cns;

    @NotBlank(message = "O tipo de registro é obrigatório")
    private String tipoRegistro;

    private String especialidade;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataRegistro;

    @NotBlank(message = "As observações não podem estar vazias")
    private String observacoes;
}