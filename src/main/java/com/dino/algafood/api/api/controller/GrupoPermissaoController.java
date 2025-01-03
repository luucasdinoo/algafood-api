package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.PermissaoAssembler;
import com.dino.algafood.api.api.model.output.PermissaoResponseDTO;
import com.dino.algafood.api.domain.entity.Permissao;
import com.dino.algafood.api.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoAssembler assembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PermissaoResponseDTO> listar(@PathVariable Long grupoId) {
        List<Permissao> permissoes = grupoService.listarPermissoesPorGrupo(grupoId);
        return assembler.toCollectionDTO(permissoes);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
    }
}
