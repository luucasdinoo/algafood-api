package com.dino.algafood.api.core.mapper;

import com.dino.algafood.api.api.model.input.ItemPedidoRequestDTO;
import com.dino.algafood.api.api.model.output.EnderecoResponseDTO;
import com.dino.algafood.api.domain.entity.Endereco;
import com.dino.algafood.api.domain.entity.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemPedidoRequestDTO.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        var enderecoToEnderecoDtoTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoResponseDTO.class);

        enderecoToEnderecoDtoTypeMap.<String>addMapping(
                src -> src.getCidade().getEstado().getNome(),
                (dest, value) -> dest.getCidade().setEstado(value));

        return modelMapper;
    }
}
