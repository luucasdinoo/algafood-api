package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.model.entity.FormaPagamento;
import com.dino.algafood.api.domain.exception.EntidadeEmUsoException;
import com.dino.algafood.api.domain.exception.FormaPagamentoNaoEncontradoException;
import com.dino.algafood.api.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormaPagamentoService {

    public static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento com código %d não pode ser removida, pois está em uso";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento buscarOuFalhar(Long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradoException(id));
    }

    public List<FormaPagamento> listar(){
        return formaPagamentoRepository.findAll();
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long id) {
        if (!formaPagamentoRepository.existsById(id)) {
            throw new FormaPagamentoNaoEncontradoException(id);

        }
        try {
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
        }
    }
}
