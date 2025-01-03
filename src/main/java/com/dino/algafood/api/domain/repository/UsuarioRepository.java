package com.dino.algafood.api.domain.repository;

import com.dino.algafood.api.domain.entity.Grupo;
import com.dino.algafood.api.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("select u.grupos from Usuario u where u.id = :usuarioId")
    List<Grupo> findGrupoByUsuarioId(@Param("usuarioId") Long id);
}
