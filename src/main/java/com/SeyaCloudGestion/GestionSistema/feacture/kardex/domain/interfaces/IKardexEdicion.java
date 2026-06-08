package com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestEditarAllKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestEditarEstadoKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseEditarAllKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseEditarEstadoKardex;

public interface IKardexEdicion {
    ResponseEditarAllKardex EditarAllKardex(RequestEditarAllKardex request);
    ResponseEditarEstadoKardex EditarEstadoKardex(RequestEditarEstadoKardex request, int estado);
}