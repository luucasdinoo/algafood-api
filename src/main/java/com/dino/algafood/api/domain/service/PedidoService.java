package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.entity.*;
import com.dino.algafood.api.domain.exception.NegocioException;
import com.dino.algafood.api.domain.exception.PedidoNaoEncontradoException;
import com.dino.algafood.api.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private ProdutoService produtoService;

    @Transactional
    public Pedido buscarOuFalhar(String codigoPedido) {
       Pedido pedido = pedidoRepository.findByCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
       pedido.getItens().size();
       pedido.getEnderecoEntrega().getCidade().getEstado();
       return pedido;
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItensPedido(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);
    }

    public void validarPedido(Pedido pedido) {
        Cidade cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
        Restaurante restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (!restaurante.aceitaFormaPagamento(formaPagamento))
            throw new NegocioException(String.format("Forma de pagamento '%s' não aceita por esse restaurante",
                    formaPagamento.getDescricao()));
    }

    public void validarItensPedido(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = produtoService.buscarOuFalhar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setProduto(produto);
            item.setPedido(pedido);
            item.setPrecoUnitario(produto.getPreco());
        });

    }
}
