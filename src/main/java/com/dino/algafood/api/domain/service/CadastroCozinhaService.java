package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.entity.Cozinha;
import com.dino.algafood.api.domain.exception.CozinhaNaoEncontradaException;
import com.dino.algafood.api.domain.exception.EntidadeEmUsoException;
import com.dino.algafood.api.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCozinhaService {

    public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    public Cozinha buscarOuFalhar(Long id) {
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long id) {
        if (!cozinhaRepository.existsById(id)) {
            throw new CozinhaNaoEncontradaException(id);

        }
        try {
            cozinhaRepository.deleteById(id);

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, id));
        }
    }
}
