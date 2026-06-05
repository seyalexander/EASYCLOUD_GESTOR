package com.SeyaCloudGestion.GestionSistema.feacture.guias.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.request.RequestRegistroGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response.ResponseRegistroGuiasIngreso;

public interface IGuiasIngresoRegistro {
    ResponseRegistroGuiasIngreso RegistroGuiasIngreso(RequestRegistroGuiasIngreso request);
}