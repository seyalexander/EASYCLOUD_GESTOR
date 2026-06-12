package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.repository.crud.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TurnoCajaService implements ITurnoCajaListado, ITurnoCajaAbrir, ITurnoCajaCerrar, ITurnoCajaDetalle {

    private final TurnoCajaListadoRepository turnoCajaListadoRepository;
    private final TurnoCajaAbrirRepository abrirTurnoCaja;
    private final TurnoCajaCerrarRepository cerrarTurnoCaja;
    private final TurnoCajaDetalleRepository turnoCajaDetalleRepository;

    public TurnoCajaService(TurnoCajaListadoRepository turnoCajaListadoRepository, TurnoCajaAbrirRepository abrirTurnoCaja, TurnoCajaCerrarRepository cerrarTurnoCaja, TurnoCajaDetalleRepository turnoCajaDetalleRepository) {
        this.turnoCajaListadoRepository = turnoCajaListadoRepository;
        this.abrirTurnoCaja = abrirTurnoCaja;
        this.cerrarTurnoCaja = cerrarTurnoCaja;
        this.turnoCajaDetalleRepository = turnoCajaDetalleRepository;
    }


    @Override
    @Cacheable(value = "turnosCaja", key = "#request.estado")
    public ResponseListaTurnoCaja ListaTurnoCaja(RequestListaTurnoCaja request) {
        return turnoCajaListadoRepository.ListaTurnoCaja(request);
    }

    @Override
    @CacheEvict(value = {"turnosCaja", "turnoCaja_detalle"}, allEntries = true)
    public ResponseAbrirTurnoCaja AbrirTurnoCaja(RequestAbrirTurnoCaja request) {
        return abrirTurnoCaja.AbrirTurnoCaja(request);
    }

    @Override
    @CacheEvict(value = {"turnosCaja", "turnoCaja_detalle"}, allEntries = true)
    public ResponseCerrarTurnoCaja CerrarTurnoCaja(RequestCerrarTurnoCaja request,double montoSistema,double diferencia) {
        return cerrarTurnoCaja.CerrarTurnoCaja(request,montoSistema,diferencia);
    }

    @Override
    @Cacheable(value = "turnoCaja_detalle", key = "#request.idTurnoCaja")
    public ResponseDetalleTurnoCaja DetalleTurnoCaja(RequestDetalleTurnoCaja request) {
        return turnoCajaDetalleRepository.DetalleTurnoCaja(request);
    }
}