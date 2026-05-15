package com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestListaMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseListaMarca;

public interface IMarcaListado {
    ResponseListaMarca ListaMarca(RequestListaMarca request);
}
