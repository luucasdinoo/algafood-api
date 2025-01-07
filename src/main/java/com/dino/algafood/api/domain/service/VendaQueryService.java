package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.filter.VendaDiariaFilter;
import com.dino.algafood.api.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
