package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.ProdutoAssembler;
import com.dino.algafood.api.api.disassembler.ProdutoDisassembler;
import com.dino.algafood.api.api.model.input.ProdutoRequestDTO;
import com.dino.algafood.api.api.model.output.ProdutoResponseDTO;
import com.dino.algafood.api.domain.model.entity.Produto;
import com.dino.algafood.api.domain.model.entity.Restaurante;
import com.dino.algafood.api.domain.repository.ProdutoRepository;
import com.dino.algafood.api.domain.service.ProdutoService;
import com.dino.algafood.api.domain.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoAssembler assembler;

    @Autowired
    private ProdutoDisassembler disassembler;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoResponseDTO> listar(@PathVariable Long restauranteId,
                                           @RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        List<Produto> produtos = null;

        if (incluirInativos)
            produtos = produtoRepository.findTodosByRestaurante(restaurante);

        else
            produtos = produtoRepository.findAtivosByRestaurante(restaurante);


        return assembler.toCollectionDTO(produtos);
    }

    @GetMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        return assembler.toDTO(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO adicionar(@PathVariable Long restauranteId, @Valid @RequestBody ProdutoRequestDTO dto){
        Produto produto = disassembler.toDomain(dto);
        produto = produtoService.salvar(produto, restauranteId);
        return assembler.toDTO(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoResponseDTO atualizar(@PathVariable Long restauranteId,
                          @PathVariable Long produtoId,
                          @Valid @RequestBody ProdutoRequestDTO dto) {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        disassembler.copyToDomain(dto, produto);
        produto = produtoService.salvar(produto, restauranteId);
        return assembler.toDTO(produto);
    }
}
