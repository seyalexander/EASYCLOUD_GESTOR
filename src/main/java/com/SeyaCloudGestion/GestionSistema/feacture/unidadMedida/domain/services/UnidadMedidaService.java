package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestListaUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseListaUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.interfaces.IUnidadMedidaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.repository.crud.UnidadMedidaListaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadMedidaService implements IUnidadMedidaListado {

    private final UnidadMedidaListaRepository unidadMedidaListaRepository;

    public UnidadMedidaService (UnidadMedidaListaRepository unidadMedidaListaRepository) {
        this.unidadMedidaListaRepository = unidadMedidaListaRepository;
    }


    @Override
    @Cacheable(value = "unidadMedida_lista", key = "#request.estado")
    public ResponseListaUnidadMedida listaUnidadMedida(RequestListaUnidadMedida request) {
        return unidadMedidaListaRepository.listaUnidadMedida(request);
    }
}
