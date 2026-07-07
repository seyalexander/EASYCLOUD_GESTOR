package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.interfaces.ICuentasPorPagarDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.interfaces.ICuentasPorPagarListado;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.interfaces.ICuentasPorPagarRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.infraestructure.persistence.repository.crud.CuentasPorPagarDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.infraestructure.persistence.repository.crud.CuentasPorPagarListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.infraestructure.persistence.repository.crud.CuentasPorPagarRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CuentasPorPagarService implements ICuentasPorPagarListado, ICuentasPorPagarRegistro, ICuentasPorPagarDetalle {

    private final CuentasPorPagarListadoRepository cuentasPorPagarListadoRepository;
    private final CuentasPorPagarRegistroRepository  cuentasPorPagarRegistroRepository;
    private final CuentasPorPagarDetalleRepository cuentasPorPagarDetalleRepository;

    public CuentasPorPagarService(
            CuentasPorPagarListadoRepository cuentasPorPagarListadoRepository,
            CuentasPorPagarRegistroRepository cuentasPorPagarRegistroRepository,
            CuentasPorPagarDetalleRepository cuentasPorPagarDetalleRepository
    ) {
        this.cuentasPorPagarListadoRepository = cuentasPorPagarListadoRepository;
        this.cuentasPorPagarRegistroRepository = cuentasPorPagarRegistroRepository;
        this.cuentasPorPagarDetalleRepository = cuentasPorPagarDetalleRepository;
    }

    @Override
    @Cacheable(value = "cxp_lista", key = "#request.estado")
    public ResponseListaCuentasPorPagar listaCuentasPorPagar(RequestListaCuentasPorPagar request) {
        return cuentasPorPagarListadoRepository.listaCuentasPorPagar(request);
    }

    @Override
    @CacheEvict(value = {"cxp_lista", "cxp_detalle"}, allEntries = true)
    public ResponseRegistroCuentasPorPagar RegistroCuentasPorPagar(RequestRegistroCuentasPorPagar request) {
        return cuentasPorPagarRegistroRepository.RegistroCuentasPorPagar(request);
    }

    @Override
    @Cacheable(value = "cxp_detalle", key = "#request.idCuentaPorPagar")
    public ResponseDetalleCuentasPorPagar DetalleCuentasPorPagar(RequestDetalleCuentasPorPagar request) {
        return cuentasPorPagarDetalleRepository.DetalleCuentasPorPagar(request);
    }
}