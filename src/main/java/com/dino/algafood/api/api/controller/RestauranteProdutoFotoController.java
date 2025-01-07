package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.FotoProdutoAssembler;
import com.dino.algafood.api.api.model.input.FotoProdutoRequestDTO;
import com.dino.algafood.api.api.model.output.FotoProdutoResponseDTO;
import com.dino.algafood.api.domain.model.entity.FotoProduto;
import com.dino.algafood.api.domain.model.entity.Produto;
import com.dino.algafood.api.domain.service.FotoProdutoService;
import com.dino.algafood.api.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private FotoProdutoService fotoProdutoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FotoProdutoAssembler assembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoResponseDTO atualizarFoto(@PathVariable Long restauranteId,
                                                @PathVariable Long produtoId, @Valid FotoProdutoRequestDTO request) {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        MultipartFile arquivo = request.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(request.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = fotoProdutoService.salvar(fotoProduto);
        return assembler.toDTO(fotoSalva);
    }
}

