package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.persistence.repository.crud.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DireccionesClientesService implements IDireccionesClientesDetalle, IDireccionesClientesEdicion, IDireccionesClientesListado, IDireccionesClientesRegistro {
   private final DireccionesClientesDetalleRepository  direccionesClientesDetalleRepository;
   private final DireccionesClientesEdicionRepository direccionesClientesEdicionRepository;
   private final DireccionesClientesListadoRepository direccionesClientesListadoRepository;
   private final DireccionesClientesRegistroRepository direccionesClientesRegistroRepository;

    public DireccionesClientesService(DireccionesClientesDetalleRepository direccionesClientesDetalleRepository, DireccionesClientesEdicionRepository direccionesClientesEdicionRepository, DireccionesClientesListadoRepository direccionesClientesListadoRepository, DireccionesClientesRegistroRepository direccionesClientesRegistroRepository) {
        this.direccionesClientesDetalleRepository = direccionesClientesDetalleRepository;
        this.direccionesClientesEdicionRepository = direccionesClientesEdicionRepository;
        this.direccionesClientesListadoRepository = direccionesClientesListadoRepository;
        this.direccionesClientesRegistroRepository = direccionesClientesRegistroRepository;
    }

    @Override
    @Cacheable(value = "direccionesClientes_detalle", key = "#request.idDireccionCliente")
    public ResponseDetalleDireccionesClientes DetalleDireccionesClientes(RequestDetalleDireccionesClientes request) {
        return direccionesClientesDetalleRepository.DetalleDireccionesClientes(request);
    }

    @Override
    @CacheEvict(value = {"direccionesClientes", "direccionesClientes_detalle"}, allEntries = true)
    public ResponseEditarAllDireccionesClientes EditarAllDireccionesClientes(RequestEditarAllDireccionesClientes request) {
        return direccionesClientesEdicionRepository.EditarAllDireccionesClientes(request);
    }

    @Override
    @CacheEvict(value = {"direccionesClientes", "direccionesClientes_detalle"}, allEntries = true)
    public ResponseEditarEstadoDireccionesClientes EditarEstadoDireccionesClientes(RequestEditarEstadoDireccionesClientes request, int estado) {
        return direccionesClientesEdicionRepository.EditarEstadoDireccionesClientes(request, estado);
    }

    @Override
    @Cacheable(value = "direccionesClientes", key = "#request.idCliente")
    public ResponseListaDireccionesClientes ListaDireccionesClientes(RequestListaDireccionesClientes request) {
        return direccionesClientesListadoRepository.ListaDireccionesClientes(request);
    }

    @Override
    @CacheEvict(value = {"direccionesClientes", "direccionesClientes_detalle"}, allEntries = true)
    public ResponseRegistroDireccionesClientes RegistroDireccionesClientes(RequestRegistroDireccionesClientes request) {
        return direccionesClientesRegistroRepository.RegistroDireccionesClientes(request);
    }
}