package com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.request.RequestRegistroCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.response.ResponseRegistroCierreCaja;

public interface ICierreCajaRegistro {
    ResponseRegistroCierreCaja RegistroCierreCaja(RequestRegistroCierreCaja request);
}