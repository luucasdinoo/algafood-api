package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.GrupoAssembler;
import com.dino.algafood.api.api.model.output.GrupoResponseDTO;
import com.dino.algafood.api.domain.model.entity.Grupo;
import com.dino.algafood.api.domain.service.GrupoService;
import com.dino.algafood.api.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoAssembler assembler;

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GrupoResponseDTO> listar(@PathVariable Long usuarioId) {
        List<Grupo> gps = grupoService.listarPorUsuario(usuarioId);
        return assembler.toCollectionDTO(gps);
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);
    }
}
