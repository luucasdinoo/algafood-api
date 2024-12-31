package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.entity.Cidade;
import com.dino.algafood.api.domain.entity.Cozinha;
import com.dino.algafood.api.domain.entity.Restaurante;
import com.dino.algafood.api.domain.exception.RestauranteNaoEncontradoException;
import com.dino.algafood.api.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CidadeService cidadeService;

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Restaurante buscarOuFalhar(Long id){
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.inativar();
    }
}
