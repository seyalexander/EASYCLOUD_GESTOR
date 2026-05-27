package com.SeyaCloudGestion.GestionSistema.feacture.guias.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.request.RequestDetalleGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response.ResponseDetalleGuiasIngreso;

public interface IGuiasIngresoDetalle {
    ResponseDetalleGuiasIngreso DetalleGuiasIngreso(RequestDetalleGuiasIngreso request);
}