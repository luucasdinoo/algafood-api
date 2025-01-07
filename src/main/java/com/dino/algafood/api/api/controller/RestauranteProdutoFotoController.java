package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.FotoProdutoAssembler;
import com.dino.algafood.api.api.model.input.FotoProdutoRequestDTO;
import com.dino.algafood.api.api.model.output.FotoProdutoResponseDTO;
import com.dino.algafood.api.domain.model.entity.FotoProduto;
import com.dino.algafood.api.domain.model.entity.Produto;
import com.dino.algafood.api.domain.service.FotoProdutoService;
import com.dino.algafood.api.domain.service.FotoStorageService;
import com.dino.algafood.api.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private FotoProdutoService fotoProdutoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Autowired
    private FotoProdutoAssembler assembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoResponseDTO atualizarFoto(@PathVariable Long restauranteId,
                                                @PathVariable Long produtoId, @Valid FotoProdutoRequestDTO request) throws IOException {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        MultipartFile arquivo = request.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(request.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = fotoProdutoService.salvar(fotoProduto, arquivo.getInputStream());
        return assembler.toDTO(fotoSalva);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public FotoProdutoResponseDTO buscar(@PathVariable Long restauranteId,
                                         @PathVariable Long produtoId){
        FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return assembler.toDTO(fotoProduto);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId,
                                                          @PathVariable Long produtoId,
                                                          @RequestHeader("accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

        MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
        List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

        verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
        InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
        return ResponseEntity.ok()
                .contentType(mediaTypeFoto)
                .body(new InputStreamResource(inputStream));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        fotoProdutoService.excluir(restauranteId, produtoId);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas)
            throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel)
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
    }
}

