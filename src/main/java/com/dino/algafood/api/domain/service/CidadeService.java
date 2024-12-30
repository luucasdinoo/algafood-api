package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.entity.Cidade;
import com.dino.algafood.api.domain.entity.Estado;
import com.dino.algafood.api.domain.exception.CidadeNaoEncontradaException;
import com.dino.algafood.api.domain.exception.EntidadeEmUsoException;
import com.dino.algafood.api.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CidadeService {

    private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoService estadoService;

    public Cidade buscarOuFalhar(Long id){
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }

    @Transactional
    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoService.buscarOuFalhar(estadoId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long id){
        if (!cidadeRepository.existsById(id)) {
            throw new CidadeNaoEncontradaException(id);

        }
        try {
            cidadeRepository.deleteById(id);

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
        }
    }
}
