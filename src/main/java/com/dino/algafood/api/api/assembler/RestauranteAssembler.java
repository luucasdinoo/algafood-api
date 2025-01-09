package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.controller.RestauranteController;
import com.dino.algafood.api.api.links.AlgaLinks;
import com.dino.algafood.api.api.model.output.RestauranteResponseDTO;
import com.dino.algafood.api.domain.model.entity.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RestauranteAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteResponseDTO> {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteAssembler(){
        super(RestauranteController.class, RestauranteResponseDTO.class);
    }

    @Override
    public RestauranteResponseDTO toModel(Restaurante entity) {
        RestauranteResponseDTO dto = mapper.map(entity, RestauranteResponseDTO.class);

        if (entity.ativacaoPermitida())
            dto.add(linkTo(methodOn(RestauranteController.class).ativar(dto.getId())).withRel("ativar"));

        if (entity.inativacaoPermitida())
            dto.add(linkTo(methodOn(RestauranteController.class).inativar(dto.getId())).withRel("inativar"));

        if (entity.aberturaPermitida())
            dto.add(linkTo(methodOn(RestauranteController.class).abrir(dto.getId())).withRel("abrir"));

        if (entity.fechamentoPermitido())
            dto.add(linkTo(methodOn(RestauranteController.class).fechar(dto.getId())).withRel("fechar"));

        dto.add(algaLinks.linkToRestaurante(dto.getId()));
        dto.add(algaLinks.linkToRestaurantes("restaurantes"));
        dto.getCozinha().add(
                algaLinks.linkToCozinha(dto.getCozinha().getId()));
        dto.getEndereco().getCidade().add(
                algaLinks.linkToCidade(dto.getEndereco().getCidade().getId()));
        dto.add(algaLinks.linkToRestauranteFormasPagamento(dto.getId(), "formas_pagamentos"));
        dto.add(algaLinks.linkToRestauranteResponsaveis(dto.getId(), "restaurantes"));

        return dto;
    }

    @Override
    public CollectionModel<RestauranteResponseDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }
}
