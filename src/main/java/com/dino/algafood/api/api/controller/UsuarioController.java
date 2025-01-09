package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.UsuarioAssembler;
import com.dino.algafood.api.api.disassembler.UsuarioDisassembler;
import com.dino.algafood.api.api.model.input.SenhaRequestDTO;
import com.dino.algafood.api.api.model.input.UsuarioComSenhaRequestDTO;
import com.dino.algafood.api.api.model.input.UsuarioRequestDTO;
import com.dino.algafood.api.api.model.output.UsuarioResponseDTO;
import com.dino.algafood.api.domain.model.entity.Usuario;
import com.dino.algafood.api.domain.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioAssembler assembler;

    @Autowired
    private UsuarioDisassembler disassembler;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseDTO buscar(@PathVariable Long id) {
        Usuario response = usuarioService.buscarOuFalhar(id);
        return assembler.toModel(response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UsuarioResponseDTO> listar() {
        List<Usuario> responseList = usuarioService.listar();
        return assembler.toCollectionModel(responseList);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO salvar(@Valid @RequestBody UsuarioComSenhaRequestDTO request){
        Usuario usuario = disassembler.toDomain(request);
        usuario = usuarioService.salvar(usuario);
        return assembler.toModel(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizar (@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO request){
        Usuario usuario = usuarioService.buscarOuFalhar(id);
        disassembler.copyToDomain(request, usuario);
        usuario = usuarioService.salvar(usuario);
        return assembler.toModel(usuario);
    }


    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @Valid @RequestBody SenhaRequestDTO request){
        usuarioService.alterarSenha(id, request.getSenhaAtual(), request.getNovaSenha());
    }
}
