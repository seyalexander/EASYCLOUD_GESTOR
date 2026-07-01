package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces.IProveedoresDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces.IProveedoresEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces.IProveedoresListado;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces.IProveedoresRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.repository.crud.ProveedoresDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.repository.crud.ProveedoresEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.repository.crud.ProveedoresListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.repository.crud.ProveedoresRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProveedoresService implements IProveedoresListado, IProveedoresRegistro, IProveedoresEdicion, IProveedoresDetalle {

    private final ProveedoresListadoRepository proveedoresListadoRepository;
    private final ProveedoresRegistroRepository proveedoresRegistroRepository;
    private final ProveedoresEdicionRepository proveedoresEdicionRepository;
    private final ProveedoresDetalleRepository proveedoresDetalleRepository;

    public ProveedoresService(
            ProveedoresListadoRepository proveedoresListadoRepository,
            ProveedoresRegistroRepository proveedoresRegistroRepository,
            ProveedoresEdicionRepository proveedoresEdicionRepository,
            ProveedoresDetalleRepository proveedoresDetalleRepository
    ) {
        this.proveedoresListadoRepository = proveedoresListadoRepository;
        this.proveedoresRegistroRepository = proveedoresRegistroRepository;
        this.proveedoresEdicionRepository = proveedoresEdicionRepository;
        this.proveedoresDetalleRepository = proveedoresDetalleRepository;
    }

    @Override
    @Cacheable(value = "proveedores_lista", key = "#request.estado")
    public ResponseListaProveedor listaProveedores(RequestListaProveedor request) {
        return proveedoresListadoRepository.listaProveedores(request);
    }

    @Override
    @CacheEvict(value = {"proveedores_lista", "proveedor_detalle"}, allEntries = true)
    public ResponseRegistroProveedor RegistroProveedores(RequestRegistroProveedor request) {
        return proveedoresRegistroRepository.RegistroProveedores(request);
    }

    @Override
    @CacheEvict(value = {"proveedores_lista", "proveedor_detalle"}, allEntries = true)
    public ResponseEditarAllProveedor EditarAllProveedores(RequestEditarAllProveedor request) {
        return proveedoresEdicionRepository.EditarAllProveedores(request);
    }

    @Override
    @CacheEvict(value = {"proveedores_lista", "proveedor_detalle"}, allEntries = true)
    public ResponseEditarEstadoProveedor EditarEstadoProveedores(RequestEditarEstadoProveedor request, int estado) {
        return proveedoresEdicionRepository.EditarEstadoProveedores(request, estado);
    }

    @Override
    @Cacheable(value = "proveedor_detalle", key = "#request.idProveedor")
    public ResponseDetalleProveedor DetalleProveedores(RequestDetalleProveedor request) {
        return proveedoresDetalleRepository.DetalleProveedores(request);
    }
}