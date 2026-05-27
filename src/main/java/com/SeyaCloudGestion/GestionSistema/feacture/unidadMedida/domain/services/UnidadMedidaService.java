package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadMedidaService implements IUnidadMedidaListado, IUnidadMedidaDetalle, IUnidadMedidaEdicion, IUnidadMedidaRegistro {

    private final UnidadMedidaListaRepository unidadMedidaListaRepository;
    private final UnidadMedidaEdicionRepository unidadMedidaEdicionRepository;
    private final UnidadMedidaRegistroRepository unidadMedidaRegistroRepository;
    private final UnidadMedidaDetalleRepository unidadMedidaDetalleRepository;

    public UnidadMedidaService (UnidadMedidaListaRepository unidadMedidaListaRepository, UnidadMedidaEdicionRepository unidadMedidaEdicionRepository, UnidadMedidaRegistroRepository unidadMedidaRegistroRepository, UnidadMedidaDetalleRepository unidadMedidaDetalleRepository) {
        this.unidadMedidaListaRepository = unidadMedidaListaRepository;
        this.unidadMedidaEdicionRepository = unidadMedidaEdicionRepository;
        this.unidadMedidaRegistroRepository = unidadMedidaRegistroRepository;
        this.unidadMedidaDetalleRepository = unidadMedidaDetalleRepository;
    }


    @Override
    @Cacheable(value = "unidadMedida_lista", key = "#request.estado")
    public ResponseListaUnidadMedida listaUnidadMedida(RequestListaUnidadMedida request) {
        return unidadMedidaListaRepository.listaUnidadMedida(request);
    }

    @Override
    @Cacheable(value = "unidadMedida_detalle", key = "#request.idUnidadMedida")
    public ResponseDetalleUnidadMedida DetalleUnidadMedida(RequestDetalleUnidadMedida request) {
        return unidadMedidaDetalleRepository.DetalleUnidadMedida(request);
    }

    @Override
    @CacheEvict(value = {"unidadMedida_lista", "unidadMedida_detalle"}, allEntries = true)
    public ResponseEditarAllUnidadMedida EditarAllUnidadMedida(RequestEditarAllUnidadMedida request) {
        return unidadMedidaEdicionRepository.EditarAllUnidadMedida(request);
    }

    @Override
    @CacheEvict(value = {"unidadMedida_lista", "unidadMedida_detalle"}, allEntries = true)
    public ResponseEditarEstadoUnidadMedida EditarEstadoUnidadMedida(RequestEditarEstadoUnidadMedida request, int estado) {
        return unidadMedidaEdicionRepository.EditarEstadoUnidadMedida(request, estado);
    }

    @Override
    @CacheEvict(value = {"unidadMedida_lista", "unidadMedida_detalle"}, allEntries = true)
    public ResponseRegistroUnidadMedida RegistroUnidadMedida(RequestRegistroUnidadMedida request) {
        return unidadMedidaRegistroRepository.RegistroUnidadMedida(request);
    }
}
