package com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestRegistroSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseRegistroSotck;

public interface ISotckRegistro {
    ResponseRegistroSotck RegistroSotck(RequestRegistroSotck request);
}