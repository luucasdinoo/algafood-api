package com.dino.algafood.api.domain.service;

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

        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }
}
