package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventarioService implements IInventarioListado, IInventarioRegistro, IInventarioEdicion, IInventarioDetalle {

    private final InventarioListadoRepository inventarioListadoRepository;
    private final InventarioRegistroRepository inventarioRegistroRepository;
    private final InventarioEdicionRepository inventarioEdicionRepository;
    private final InventarioDetalleRepository inventarioDetalleRepository;

    public InventarioService(
            InventarioListadoRepository inventarioListadoRepository,
            InventarioRegistroRepository inventarioRegistroRepository,
            InventarioEdicionRepository inventarioEdicionRepository,
            InventarioDetalleRepository inventarioDetalleRepository
    ) {
        this.inventarioListadoRepository = inventarioListadoRepository;
        this.inventarioRegistroRepository = inventarioRegistroRepository;
        this.inventarioEdicionRepository = inventarioEdicionRepository;
        this.inventarioDetalleRepository = inventarioDetalleRepository;
    }

    @Override
    @Cacheable(value = "inventarios_lista", key = "#request.estado")
    public ResponseListaInventario listaInventario(RequestListaInventario request) {
        return inventarioListadoRepository.listaInventario(request);
    }

    @Override
    @CacheEvict(value = {"inventarios_lista", "inventario_detalle"}, allEntries = true)
    public ResponseRegistroInventario RegistroInventario(RequestRegistroInventario request) {
        return inventarioRegistroRepository.RegistroInventario(request);
    }

    @Override
    @CacheEvict(value = {"inventarios_lista", "inventario_detalle"}, allEntries = true)
    public ResponseConteoFisicoInventario ConteoFisicoInventario(RequestConteoFisicoInventario request) {
        return inventarioEdicionRepository.ConteoFisicoInventario(request);
    }

    @Override
    @CacheEvict(value = {"inventarios_lista", "inventario_detalle"}, allEntries = true)
    public ResponseAjustarInventario AjusteInventario(RequestAjustarInventario request) {
        return inventarioEdicionRepository.AjusteInventario(request);
    }

    @Override
    @Cacheable(value = "inventario_detalle", key = "#request.idInventario")
    public ResponseDetalleInventario DetalleInventario(RequestDetalleInventario request) {
        return inventarioDetalleRepository.DetalleInventario(request);
    }
}