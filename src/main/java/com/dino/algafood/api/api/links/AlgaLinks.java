package com.dino.algafood.api.api.links;

import com.dino.algafood.api.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos(){
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );
        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
        return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), "pedidos");
    }

    public Link linkToPedido(String codigo, String rel){
        return linkTo(methodOn(PedidoController.class)
                .buscar(codigo)).withRel(rel);
    }

    public Link linkToPedido(String codigo){
        return linkToPedido(codigo, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantes(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteId)).withRel(rel);
    }

    public Link linkToRestaurantes(Long restauranteId) {
        return linkToRestaurantes(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCliente(Long clienteId, String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .buscar(clienteId)).withRel(rel);
    }

    public Link linkToCliente(Long clienteId) {
        return linkToCliente(clienteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoId)).withRel(rel);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidade(Long cidadeId, String rel){
        return linkTo(methodOn(CidadeController.class)
                .buscar(cidadeId)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(rel);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }
}
