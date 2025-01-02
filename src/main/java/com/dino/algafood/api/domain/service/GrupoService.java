package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.entity.Grupo;
import com.dino.algafood.api.domain.entity.Permissao;
import com.dino.algafood.api.domain.exception.EntidadeEmUsoException;
import com.dino.algafood.api.domain.exception.GrupoNaoEncontradoException;
import com.dino.algafood.api.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private PermissaoService permissaoService;

    public static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso";


    public Grupo buscarOuFalhar(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    public List<Grupo> listar(){
        return grupoRepository.findAll();
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long id) {
        if (!grupoRepository.existsById(id)) {
            throw new GrupoNaoEncontradoException(id);
        }
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();

        }catch (DataIntegrityViolationException ex){
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
        }
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo gp = buscarOuFalhar(grupoId);
        Permissao pm = permissaoService.buscarOuFalhar(permissaoId);

        gp.adicionarPermissao(pm);
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo gp = buscarOuFalhar(grupoId);
        Permissao pm = permissaoService.buscarOuFalhar(permissaoId);

        gp.removerPermissao(pm);
    }
}
