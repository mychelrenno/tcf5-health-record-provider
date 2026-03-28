package com.tcf5.health.record.provider.service;

import com.tcf5.health.record.provider.model.ProntuarioEntity;
import com.tcf5.health.record.provider.repository.ProntuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProntuarioService {

    private final ProntuarioRepository repository;

    public List<ProntuarioEntity> listarHistorico(String pacienteId) {
        return repository.findByPacienteIdOrderByDataRegistroDesc(pacienteId);
    }

    public List<ProntuarioEntity> filtrarPorTipo(String pacienteId, String tipo) {
        return repository.findByPacienteIdAndTipoRegistro(pacienteId, tipo.toUpperCase());
    }

    public List<ProntuarioEntity> filtrarPorEspecialidade(String pacienteId, String especialidade) {
        return repository.findByPacienteIdAndEspecialidade(pacienteId, especialidade);
    }

    public ProntuarioEntity buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));
    }
}