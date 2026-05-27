package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestListaMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseListaMovimientoCaja;

public interface IMovimientoCajaListado {
    ResponseListaMovimientoCaja listaMovimientoCaja(RequestListaMovimientoCaja request);
}