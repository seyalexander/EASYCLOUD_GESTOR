package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.repository.crud.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SucursalesService implements ISucursalesListado, ISucursalesRegistro, ISucursalesEdicion, ISucursalesDetalle{
    private final SucursalesListadoRepository sucursalesListadoRepository;
    private final SucursalesRegistroRepository sucursalesRegistroRepository;
    private final SucursalesEdicionRepository sucursalesEdicionRepository;
    private final SucursalesDetalleRepository sucursalesDetalleRepository;

    public SucursalesService(SucursalesListadoRepository sucursalesListadoRepository, SucursalesRegistroRepository sucursalesRegistroRepository, SucursalesEdicionRepository sucursalesEdicionRepository, SucursalesDetalleRepository sucursalesDetalleRepository) {
        this.sucursalesListadoRepository = sucursalesListadoRepository;
        this.sucursalesRegistroRepository = sucursalesRegistroRepository;
        this.sucursalesEdicionRepository = sucursalesEdicionRepository;
        this.sucursalesDetalleRepository = sucursalesDetalleRepository;
    }

    @Override
    @Cacheable(value = "sucursales", key = "#request.estado")
    public ResponseListaSucursales ListaSucursales(RequestListaSucursales request) {
        return sucursalesListadoRepository.ListaSucursales(request);
    }

    @Override
    @CacheEvict(value = {"sucursales", "sucursales_detalle"}, allEntries = true)
    public ResponseRegistroSucursales RegistroSucursales(RequestRegistroSucursales request) {
        return sucursalesRegistroRepository.RegistroSucursales(request);
    }

    @Override
    @CacheEvict(value = {"sucursales", "sucursales_detalle"}, allEntries = true)
    public ResponseEditarAllSucursales EditarAllSucursales(RequestEditarAllSucursales request) {
        return sucursalesEdicionRepository.EditarAllSucursales(request);
    }

    @Override
    @CacheEvict(value = {"sucursales", "sucursales_detalle"}, allEntries = true)
    public ResponseEditarEstadoSucursales EditarEstadoSucursales(RequestEditarEstadoSucursales request, int estado) {
        return sucursalesEdicionRepository.EditarEstadoSucursales(request, estado);
    }

    @Override
    @Cacheable(value = "sucursales_detalle", key = "#request.idSucursales")
    public ResponseDetalleSucursales DetalleSucursales(RequestDetalleSucursales request) {
        return sucursalesDetalleRepository.DetalleSucursales(request);
    }
}