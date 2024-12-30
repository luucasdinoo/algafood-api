package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.entity.Estado;
import com.dino.algafood.api.domain.exception.EntidadeEmUsoException;
import com.dino.algafood.api.domain.exception.EstadoNaoEncontradoException;
import com.dino.algafood.api.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoService {

    public static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> listar(){
        return estadoRepository.findAll();
    }

    public Estado buscarOuFalhar(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }

    @Transactional
    public Estado salvar(Estado cozinha) {
        return estadoRepository.save(cozinha);
    }

    @Transactional
    public void excluir(Long id) {
        if (!estadoRepository.existsById(id)) {
            throw new EstadoNaoEncontradoException(id);
        }

        try {
            estadoRepository.deleteById(id);

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, id));
        }
    }
}
