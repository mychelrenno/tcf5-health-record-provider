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
    private String pacienteId;

    @Column(nullable = false)
    private String tipoRegistro;

    @Column(columnDefinition = "TEXT")
    private String conteudoHl7;

    private String especialidade;

    private LocalDateTime dataRegistro;

    @Column(length = 500)
    private String observacoes;
}