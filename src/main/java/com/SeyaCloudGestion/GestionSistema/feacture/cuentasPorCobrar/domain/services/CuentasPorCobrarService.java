package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class CuentasPorCobrarService implements ICuentasPorCobrarListado, ICuentasPorCobrarRegistro, ICuentasPorCobrarEdicion, ICuentasPorCobrarDetalle  {
    private final CuentasPorCobrarListadoRepository cuentasPorCobrarListadoRepository;
    private final CuentasPorCobrarRegistroRepository cuentasPorCobrarRegistroRepository;
    private final CuentasPorCobrarEdicionRepository cuentasPorCobrarEdicionRepository;
    private final CuentasPorCobrarDetalleRepository cuentasPorCobrarDetalleRepository;

    public CuentasPorCobrarService(CuentasPorCobrarListadoRepository cuentasPorCobrarListadoRepository, CuentasPorCobrarRegistroRepository cuentasPorCobrarRegistroRepository, CuentasPorCobrarEdicionRepository cuentasPorCobrarEdicionRepository, CuentasPorCobrarDetalleRepository cuentasPorCobrarDetalleRepository) {
        this.cuentasPorCobrarListadoRepository = cuentasPorCobrarListadoRepository;
        this.cuentasPorCobrarRegistroRepository = cuentasPorCobrarRegistroRepository;
        this.cuentasPorCobrarEdicionRepository = cuentasPorCobrarEdicionRepository;
        this.cuentasPorCobrarDetalleRepository = cuentasPorCobrarDetalleRepository;
    }

    @Override
    @Cacheable(value = "cuentasPorCobrar", key = "#request.estado")
    public ResponseListaCuentasPorCobrar ListaCuentasPorCobrar(RequestListaCuentasPorCobrar request) {
        return cuentasPorCobrarListadoRepository.ListaCuentasPorCobrar(request);
    }

    @Override
    @Cacheable(value = "cuentasPorCobrar", key = "#request.idCliente")
    public ResponseListaCuentasPorCobrar ListaCuentasPorCobrarIDCliente(RequestListaCuentasPorCobrarIDCliente request) {
        return cuentasPorCobrarListadoRepository.ListaCuentasPorCobrarIDCliente(request);
    }

    @Override
    @CacheEvict(value = {"cuentasPorCobrar", "cuentasPorCobrar_detalle"}, allEntries = true)
    public ResponseRegistroCuentasPorCobrar RegistroCuentasPorCobrar(RequestRegistroCuentasPorCobrar request) {
        return cuentasPorCobrarRegistroRepository.RegistroCuentasPorCobrar(request);
    }

    @Override
    @CacheEvict(value = {"cuentasPorCobrar", "cuentasPorCobrar_detalle"}, allEntries = true)
    public ResponseEditarAllCuentasPorCobrar EditarAllCuentasPorCobrar(RequestEditarAllCuentasPorCobrar request) {
        return cuentasPorCobrarEdicionRepository.EditarAllCuentasPorCobrar(request);
    }

    @Override
    @CacheEvict(value = {"cuentasPorCobrar", "cuentasPorCobrar_detalle"}, allEntries = true)
    public ResponseEditarEstadoCuentasPorCobrar EditarEstadoCuentasPorCobrar(RequestEditarEstadoCuentasPorCobrar request,String estado) {
        return cuentasPorCobrarEdicionRepository.EditarEstadoCuentasPorCobrar(request,estado);
    }

    @Override
    @Cacheable(value = "cuentasPorCobrar_detalle", key = "#request.idCuentaPorCobrar")
    public ResponseDetalleCuentasPorCobrar DetalleCuentasPorCobrar(RequestDetalleCuentasPorCobrar request) {
        return cuentasPorCobrarDetalleRepository.DetalleCuentasPorCobrar(request);
    }
}