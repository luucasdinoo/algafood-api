package com.dino.algafood.api.domain.repository;

import com.dino.algafood.api.domain.entity.Grupo;
import com.dino.algafood.api.domain.entity.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    @Query("select g.permissaos from Grupo g where g.id = :grupoId")
    List<Permissao> findPermissoesByGrupoId(@Param("grupoId") Long id);

}
