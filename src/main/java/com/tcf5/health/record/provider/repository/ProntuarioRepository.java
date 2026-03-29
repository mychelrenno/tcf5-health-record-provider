package com.tcf5.health.record.provider.repository;

import com.tcf5.health.record.provider.model.ProntuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProntuarioRepository extends JpaRepository<ProntuarioEntity, UUID> {

    List<ProntuarioEntity> findByCpfOrderByDataRegistroDesc(String cpf);

    List<ProntuarioEntity> findByCpfAndTipoRegistro(String cpf, String tipoRegistro);

    List<ProntuarioEntity> findByCpfAndEspecialidade(String cpf, String especialidade);
}