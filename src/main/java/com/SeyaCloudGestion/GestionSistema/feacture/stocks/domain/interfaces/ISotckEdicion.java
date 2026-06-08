package com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestEditarAllSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestEditarEstadoSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseEditarAllSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseEditarEstadoSotck;

public interface ISotckEdicion {
    ResponseEditarAllSotck EditarAllSotck(RequestEditarAllSotck request);
    ResponseEditarEstadoSotck EditarEstadoSotck(RequestEditarEstadoSotck request, int estado);
}