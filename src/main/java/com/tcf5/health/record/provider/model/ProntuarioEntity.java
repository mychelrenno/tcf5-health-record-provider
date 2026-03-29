package com.tcf5.health.record.provider.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_prontuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProntuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String cpf;

    private String cns;

    @Column(nullable = false)
    private String tipoRegistro;

    private String especialidade;

    private LocalDateTime dataRegistro;

    @Column(length = 2000)
    private String observacoes;
}