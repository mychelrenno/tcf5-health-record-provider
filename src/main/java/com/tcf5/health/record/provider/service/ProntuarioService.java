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

    public List<ProntuarioEntity> listarHistorico(String cpf) {
        return repository.findByCpfOrderByDataRegistroDesc(cpf);
    }

    public List<ProntuarioEntity> filtrarPorTipo(String cpf, String tipo) {
        return repository.findByCpfAndTipoRegistro(cpf, tipo.toUpperCase());
    }

    public List<ProntuarioEntity> filtrarPorEspecialidade(String cpf, String especialidade) {
        return repository.findByCpfAndEspecialidade(cpf, especialidade);
    }

    public ProntuarioEntity buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));
    }

    public ProntuarioEntity atualizarObservacoes(UUID id, String novasObservacoes) {
        ProntuarioEntity entity = buscarPorId(id);
        entity.setObservacoes(novasObservacoes);
        return repository.save(entity);
    }
}