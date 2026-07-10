package com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestRegistroCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestRegistroSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseRegistroCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseRegistroSerieCaja;

public interface ISerieCajaRegistro {
    ResponseRegistroSerieCaja RegistroSerieCaja(RequestRegistroSerieCaja request);
}