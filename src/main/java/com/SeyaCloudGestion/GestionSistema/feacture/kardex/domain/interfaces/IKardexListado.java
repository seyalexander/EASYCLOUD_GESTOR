package com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestListaKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseListaKardex;

public interface IKardexListado {
    ResponseListaKardex listaKardex(RequestListaKardex request);
}