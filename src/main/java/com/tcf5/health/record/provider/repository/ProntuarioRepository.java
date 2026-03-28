package com.tcf5.health.record.provider.repository;

import com.tcf5.health.record.provider.model.ProntuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProntuarioRepository extends JpaRepository<ProntuarioEntity, UUID> {

    List<ProntuarioEntity> findByPacienteIdOrderByDataRegistroDesc(String pacienteId);

    List<ProntuarioEntity> findByPacienteIdAndTipoRegistro(String pacienteId, String tipoRegistro);

    List<ProntuarioEntity> findByPacienteIdAndEspecialidade(String pacienteId, String especialidade);

    List<ProntuarioEntity> findByPacienteIdAndDataRegistroBetween(
            String pacienteId, LocalDateTime dataInicio, LocalDateTime dataFim);
}