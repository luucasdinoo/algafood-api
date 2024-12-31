package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.entity.Usuario;
import com.dino.algafood.api.domain.exception.EntidadeEmUsoException;
import com.dino.algafood.api.domain.exception.NegocioException;
import com.dino.algafood.api.domain.exception.UsuarioNaoEncontradoExcepetion;
import com.dino.algafood.api.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    public static final String MSG_USUARIO_EM_USO = "Usuário com código %d não pode ser removido, pois está em uso";

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario buscarOuFalhar(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoExcepetion(id));
    }

    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id))
            throw new UsuarioNaoEncontradoExcepetion(id);

        try{
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, id));
        }
    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha){
        Usuario usuario = buscarOuFalhar(id);

        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }
}
