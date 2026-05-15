package com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces.IMonedaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces.IMonedaEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces.IMonedaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces.IMonedaRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.repository.crud.MonedaDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.repository.crud.MonedaEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.repository.crud.MonedaListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.repository.crud.MonedaRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MonedaService implements IMonedaListado, IMonedaRegistro, IMonedaEdicion, IMonedaDetalle {

    private final MonedaListadoRepository monedaListadoRepository;
    private final MonedaRegistroRepository monedaRegistroRepository;
    private final MonedaEdicionRepository monedaEdicionRepository;
    private final MonedaDetalleRepository monedaDetalleRepository;

    public MonedaService(
            MonedaListadoRepository monedaListadoRepository,
            MonedaRegistroRepository monedaRegistroRepository,
            MonedaEdicionRepository monedaEdicionRepository,
            MonedaDetalleRepository monedaDetalleRepository
    ) {
        this.monedaListadoRepository = monedaListadoRepository;
        this.monedaRegistroRepository = monedaRegistroRepository;
        this.monedaEdicionRepository = monedaEdicionRepository;
        this.monedaDetalleRepository = monedaDetalleRepository;
    }

    @Override
    @Cacheable(value = "moneda_lista", key = "#request.estado")
    public ResponseListaMoneda ListaMoneda(RequestListaMonedas request) {
        return monedaListadoRepository.ListaMoneda(request);
    }

    @Override
    @CacheEvict(value = {"moneda_lista", "moneda_detalle"}, allEntries = true)
    public ResponseRegistroMoneda RegistrarMoneda(RequestRegistroMoneda request, long userAutenticado) {
        return monedaRegistroRepository.RegistrarMoneda(request, userAutenticado);
    }

    @Override
    @CacheEvict(value = {"moneda_lista", "moneda_detalle"}, allEntries = true)
    public ResponseEditarAllMoneda EditarAllMoneda(RequestEditarAllMoneda request, long userAutenticado) {
        return monedaEdicionRepository.EditarAllMoneda(request,  userAutenticado);
    }

    @Override
    @CacheEvict(value = {"moneda_lista", "moneda_detalle"}, allEntries = true)
    public ResponseEditarEstadoMoneda EditarEstadoMoneda(RequestEditarEstadoMoneda request,int estado, long userAutenticado) {
        return monedaEdicionRepository.EditarEstadoMoneda(request, estado,  userAutenticado);
    }

    @Override
    @CacheEvict(value = {"moneda_lista", "moneda_detalle"}, allEntries = true)
    public ResponseEditarPredeterminadoMoneda EditarPredetermiandoMoneda(RequestEditarPredeterminadoMoneda request, long userAutenticado, long empresaAutenticado) {
        return monedaEdicionRepository.EditarPredetermiandoMoneda(request,  userAutenticado, empresaAutenticado);
    }

    @Override
    @Cacheable(value = "moneda_detalle", key = "#request.idMoneda")
    public ResponseDetalleMoneda DetalleMoneda(RequestDetalleMoneda request) {
        return monedaDetalleRepository.DetalleMoneda(request);
    }
}
