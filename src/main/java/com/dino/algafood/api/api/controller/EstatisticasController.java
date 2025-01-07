package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.domain.filter.VendaDiariaFilter;
import com.dino.algafood.api.domain.model.dto.VendaDiaria;
import com.dino.algafood.api.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @GetMapping("/vendas-diarias")
    @ResponseStatus(HttpStatus.OK)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                                @RequestParam(required = false, defaultValue = "+00:00")String timeOffset){
        return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
    }
}
