package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.repository.crud.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AlmacenesService implements IAlmacenesListado, IAlmacenesRegistro, IAlmacenesEdicion, IAlmacenesDetalle{

    private final AlmacenesListadoRepository almacenesListadoRepository;
    private final AlmacenesRegistroRepository almacenesRegistroRepository;
    private final AlmacenesEdicionRepository almacenesEdicionRepository;
    private final AlmacenesDetalleRepository almacenesDetalleRepository;

    public AlmacenesService(
            AlmacenesListadoRepository almacenesListadoRepository,
            AlmacenesRegistroRepository almacenesRegistroRepository,
            AlmacenesEdicionRepository almacenesEdicionRepository,
            AlmacenesDetalleRepository almacenesDetalleRepository
    ) {
        this.almacenesListadoRepository = almacenesListadoRepository;
        this.almacenesRegistroRepository = almacenesRegistroRepository;
        this.almacenesEdicionRepository = almacenesEdicionRepository;
        this.almacenesDetalleRepository = almacenesDetalleRepository;
    }

    @Override
    @Cacheable(value = "almacenes", key = "#request.estado")
    public ResponseListaAlmacenes ListaAlmacenes(RequestListaAlmacenes request) {
        return almacenesListadoRepository.ListaAlmacenes(request);
    }

    @Override
    @CacheEvict(value = {"almacenes", "almacenes_detalle"}, allEntries = true)
    public ResponseRegistroAlmacenes RegistroAlmacenes(RequestRegistroAlmacenes request) {
        return almacenesRegistroRepository.RegistroAlmacenes(request);
    }

    @Override
    @CacheEvict(value = {"almacenes", "almacenes_detalle"}, allEntries = true)
    public ResponseEditarAllAlmacenes EditarAllAlmacenes(RequestEditarAllAlmacenes request) {
        return almacenesEdicionRepository.EditarAllAlmacenes(request);
    }

    @Override
    @CacheEvict(value = {"almacenes", "almacenes_detalle"}, allEntries = true)
    public ResponseEditarEstadoAlmacenes EditarEstadoAlmacenes(RequestEditarEstadoAlmacenes request, int estado) {
        return almacenesEdicionRepository.EditarEstadoAlmacenes(request, estado);
    }

    @Override
    @Cacheable(value = "almacenes_detalle", key = "#request.idAlmacenes")
    public ResponseDetalleAlmacenes DetalleAlmacenes(RequestDetalleAlmacenes request) {
        return almacenesDetalleRepository.DetalleAlmacenes(request);
    }
}