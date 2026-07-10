package com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CajaService implements ICajaListado, ICajaRegistro, ICajaEdicion, ICajaDetalle, ISerieCajaRegistro {

    private final CajaListadoRepository cajaListadoRepository;
    private final CajaRegistroRepository cajaRegistroRepository;
    private final CajaEdicionRepository cajaEdicionRepository;
    private final CajaDetalleRepository cajaDetalleRepository;
    private final SerieCajaRegistroRepository serieCajaRegistroRepository;
    public CajaService(
            CajaListadoRepository cajaListadoRepository,
            CajaRegistroRepository cajaRegistroRepository,
            CajaEdicionRepository cajaEdicionRepository,
            CajaDetalleRepository cajaDetalleRepository, SerieCajaRegistroRepository serieCajaRegistroRepository
    ) {
        this.cajaListadoRepository = cajaListadoRepository;
        this.cajaRegistroRepository = cajaRegistroRepository;
        this.cajaEdicionRepository = cajaEdicionRepository;
        this.cajaDetalleRepository = cajaDetalleRepository;
        this.serieCajaRegistroRepository = serieCajaRegistroRepository;
    }

    @Override
    @Cacheable(value = "cajas_lista")
    public ResponseListaCaja listaCaja() {
        return cajaListadoRepository.listaCaja();
    }

    @Override
    @CacheEvict(value = {"cajas_lista", "caja_detalle"}, allEntries = true)
    public ResponseRegistroCaja RegistroCaja(RequestRegistroCaja request) {
        return cajaRegistroRepository.RegistroCaja(request);
    }

    @Override
    @CacheEvict(value = {"cajas_lista", "caja_detalle"}, allEntries = true)
    public ResponseRegistroSerieCaja RegistroSerieCaja(RequestRegistroSerieCaja request) {
        return serieCajaRegistroRepository.RegistroSerieCaja(request);
    }

    @Override
    @CacheEvict(value = {"cajas_lista", "caja_detalle"}, allEntries = true)
    public ResponseEditarAllCaja EditarAllCaja(RequestEditarAllCaja request) {
        return cajaEdicionRepository.EditarAllCaja(request);
    }

    @Override
    @Cacheable(value = "caja_detalle", key = "#request.idCaja")
    public ResponseDetalleCaja DetalleCaja(RequestDetalleCaja request) {
        return cajaDetalleRepository.DetalleCaja(request);
    }
}