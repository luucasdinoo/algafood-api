package com.dino.algafood.api.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    InputStream recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);
        if (nomeArquivoAntigo != null)
            this.remover(nomeArquivoAntigo);
    }

    default String gerarNomeArquivo(String nomeOrignal){
        return UUID.randomUUID()+ "_" + nomeOrignal;
    }

    @Getter
    @Builder
    class NovaFoto{
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
