package com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestEditarAllSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseEditarAllSotck;

public interface ISotckEdicion {
    ResponseEditarAllSotck EditarAllSotck(RequestEditarAllSotck request);
}