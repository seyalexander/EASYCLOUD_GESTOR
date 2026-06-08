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
public class TurnoCajaService implements ITurnoCajaListado, ITurnoCajaRegistro, ITurnoCajaEdicion, ITurnoCajaDetalle {

    private final TurnoCajaListadoRepository turnoCajaListadoRepository;
    private final TurnoCajaRegistroRepository turnoCajaRegistroRepository;
    private final TurnoCajaEdicionRepository turnoCajaEdicionRepository;
    private final TurnoCajaDetalleRepository turnoCajaDetalleRepository;

    public TurnoCajaService(
            TurnoCajaListadoRepository turnoCajaListadoRepository,
            TurnoCajaRegistroRepository turnoCajaRegistroRepository,
            TurnoCajaEdicionRepository turnoCajaEdicionRepository,
            TurnoCajaDetalleRepository turnoCajaDetalleRepository
    ) {
        this.turnoCajaListadoRepository = turnoCajaListadoRepository;
        this.turnoCajaRegistroRepository = turnoCajaRegistroRepository;
        this.turnoCajaEdicionRepository = turnoCajaEdicionRepository;
        this.turnoCajaDetalleRepository = turnoCajaDetalleRepository;
    }

    @Override
    @Cacheable(value = "turnosCaja", key = "#request")
    public ResponseListaTurnoCaja ListaTurnoCaja(RequestListaTurnoCaja request) {
        return turnoCajaListadoRepository.ListaTurnoCaja(request);
    }

    @Override
    @CacheEvict(value = {"turnosCaja", "turnoCaja_detalle"}, allEntries = true)
    public ResponseRegistroTurnoCaja RegistroTurnoCaja(RequestRegistroTurnoCaja request) {
        return turnoCajaRegistroRepository.RegistroTurnoCaja(request);
    }

    @Override
    @CacheEvict(value = {"turnosCaja", "turnoCaja_detalle"}, allEntries = true)
    public ResponseEditarAllTurnoCaja EditarAllTurnoCaja(RequestEditarAllTurnoCaja request) {
        return turnoCajaEdicionRepository.EditarAllTurnoCaja(request);
    }

    @Override
    @CacheEvict(value = {"turnosCaja", "turnoCaja_detalle"}, allEntries = true)
    public ResponseEditarEstadoTurnoCaja EditarEstadoTurnoCaja(RequestEditarEstadoTurnoCaja request, int estado) {
        return turnoCajaEdicionRepository.EditarEstadoTurnoCaja(request, estado);
    }

    @Override
    @Cacheable(value = "turnoCaja_detalle", key = "#request.idTurnoCaja")
    public ResponseDetalleTurnoCaja DetalleTurnoCaja(RequestDetalleTurnoCaja request) {
        return turnoCajaDetalleRepository.DetalleTurnoCaja(request);
    }
}