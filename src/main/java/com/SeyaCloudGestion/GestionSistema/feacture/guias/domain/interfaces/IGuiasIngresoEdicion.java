package com.SeyaCloudGestion.GestionSistema.feacture.guias.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.request.RequestEditarAllGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.request.RequestEditarEstadoGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response.ResponseEditarAllGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response.ResponseEditarEstadoGuiasIngreso;

public interface IGuiasIngresoEdicion {
    ResponseEditarAllGuiasIngreso EditarAllGuiasIngreso(RequestEditarAllGuiasIngreso request);
    ResponseEditarEstadoGuiasIngreso EditarEstadoGuiasIngreso(RequestEditarEstadoGuiasIngreso request, int estado);
}