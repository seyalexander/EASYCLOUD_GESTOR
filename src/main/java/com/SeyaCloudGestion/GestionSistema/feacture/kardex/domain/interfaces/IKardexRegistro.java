package com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestRegistroKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseRegistroKardex;

public interface IKardexRegistro {
    ResponseRegistroKardex RegistroKardex(RequestRegistroKardex request, double saldoCantidad, double saldoCosto);
}