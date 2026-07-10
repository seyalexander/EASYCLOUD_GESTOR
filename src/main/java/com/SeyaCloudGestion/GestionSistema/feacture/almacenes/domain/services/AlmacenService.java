package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.repository.crud.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AlmacenService implements IAlmacenListado, IAlmacenRegistro, IAlmacenEdicion, IAlmacenDetalle {

    private final AlmacenListadoRepository almacenListadoRepository;
    private final AlmacenRegistroRepository almacenRegistroRepository;
    private final AlmacenEdicionRepository almacenEdicionRepository;
    private final AlmacenDetalleRepository almacenDetalleRepository;

    public AlmacenService(
            AlmacenListadoRepository almacenListadoRepository,
            AlmacenRegistroRepository almacenRegistroRepository,
            AlmacenEdicionRepository almacenEdicionRepository,
            AlmacenDetalleRepository almacenDetalleRepository
    ) {
        this.almacenListadoRepository = almacenListadoRepository;
        this.almacenRegistroRepository = almacenRegistroRepository;
        this.almacenEdicionRepository = almacenEdicionRepository;
        this.almacenDetalleRepository = almacenDetalleRepository;
    }

    @Override
    @Cacheable(value = "almacen", key = "#request.estado")
    public ResponseListaAlmacen ListaAlmacen(RequestListaAlmacen request) {
        return almacenListadoRepository.ListaAlmacen(request);
    }

    @Override
    @CacheEvict(value = {"almacen", "almacenes_detalle"}, allEntries = true)
    public ResponseRegistroAlmacen RegistroAlmacen(RequestRegistroAlmacen request) {
        return almacenRegistroRepository.RegistroAlmacen(request);
    }

    @Override
    @CacheEvict(value = {"almacen", "almacenes_detalle"}, allEntries = true)
    public ResponseEditarAllAlmacen EditarAllAlmacen(RequestEditarAllAlmacen request) {
        return almacenEdicionRepository.EditarAllAlmacen(request);
    }

    @Override
    @CacheEvict(value = {"almacen", "almacenes_detalle"}, allEntries = true)
    public ResponseEditarEstadoAlmacen EditarEstadoAlmacen(RequestEditarEstadoAlmacen request, int estado) {
        return almacenEdicionRepository.EditarEstadoAlmacen(request, estado);
    }

    @Override
    @Cacheable(value = "almacenes_detalle", key = "#request.idAlmacen")
    public ResponseDetalleAlmacen DetalleAlmacen(RequestDetalleAlmacen request) {
        return almacenDetalleRepository.DetalleAlmacen(request);
    }
}