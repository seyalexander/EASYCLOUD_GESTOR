package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestRegistroDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseRegistroDevolucion;

public interface IDevolucionRegistro {
    ResponseRegistroDevolucion RegistroDevolucion(RequestRegistroDevolucion request);
}