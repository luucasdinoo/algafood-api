package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.exception.RestauranteNaoEncontradoException;
import com.dino.algafood.api.domain.model.entity.*;
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

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional(readOnly = true)
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Restaurante buscarOuFalhar(Long id){
        Restaurante restaurante = restauranteRepository.findById(id)
                    .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
        restaurante.getEndereco().getCidade().getEstado();
        return restaurante;
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

    @Transactional
    public void ativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::ativar);
    }

    @Transactional
    public void inativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::inativar);
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.fechar();
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    public List<Usuario> listarResponsaveis(Long restauranteId) {
        List<Usuario> responsaveis = restauranteRepository.findResponsaveisByRestauranteId(restauranteId);
        return responsaveis;
    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long responsavelId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario responsavel = usuarioService.buscarOuFalhar(responsavelId);

        restaurante.adicionarResponsavel(responsavel);
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long responsavelId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario responsavel = usuarioService.buscarOuFalhar(responsavelId);

        restaurante.removerResponsavel(responsavel);
    }

    public List<FormaPagamento> listarFormasPagamento(Long restauranteId) {
        return restauranteRepository.findFormaPagamentosByRestauranteId(restauranteId);
    }
}
