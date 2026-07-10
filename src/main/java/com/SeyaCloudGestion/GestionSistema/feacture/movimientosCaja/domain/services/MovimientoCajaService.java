package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestDetalleMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseDetalleMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.interfaces.IMovimientoCajaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.interfaces.IMovimientoCajaRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.repository.crud.MovimientoCajaDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.repository.crud.MovimientoCajaRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovimientoCajaService implements IMovimientoCajaRegistro, IMovimientoCajaDetalle {

    private final MovimientoCajaRegistroRepository movimientoCajaRegistroRepository;
    private final MovimientoCajaDetalleRepository movimientoCajaDetalleRepository;

    public MovimientoCajaService(
            MovimientoCajaRegistroRepository movimientoCajaRegistroRepository,
            MovimientoCajaDetalleRepository movimientoCajaDetalleRepository
    ) {
        this.movimientoCajaRegistroRepository = movimientoCajaRegistroRepository;
        this.movimientoCajaDetalleRepository = movimientoCajaDetalleRepository;
    }

    @Override
    @CacheEvict(value = {"movimientos_lista", "caja_resumen"}, allEntries = true)
    public ResponseRegistroMovimientoCaja RegistroMovimientoCaja(RequestRegistroMovimientoCaja request) {
        return movimientoCajaRegistroRepository.RegistroMovimientoCaja(request);
    }

    @Override
    @Cacheable(value = "movimiento_detalle", key = "#request.idMovimientoCaja")
    public ResponseDetalleMovimientoCaja DetalleMovimientoCaja(RequestDetalleMovimientoCaja request) {
        return movimientoCajaDetalleRepository.DetalleMovimientoCaja(request);
    }
}