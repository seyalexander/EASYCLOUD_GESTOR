package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseRegistroMovimientoCaja;

public interface IMovimientoCajaRegistro {
    ResponseRegistroMovimientoCaja RegistroMovimientoCaja(RequestRegistroMovimientoCaja request);
}