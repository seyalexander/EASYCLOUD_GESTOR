package com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestRegistroCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseRegistroCaja;

public interface ICajaRegistro {
    ResponseRegistroCaja RegistroCaja(RequestRegistroCaja request);
}