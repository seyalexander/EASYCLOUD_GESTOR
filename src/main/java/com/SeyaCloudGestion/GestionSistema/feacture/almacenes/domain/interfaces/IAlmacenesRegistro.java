package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestRegistroAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseRegistroAlmacenes;

public interface IAlmacenesRegistro {
    ResponseRegistroAlmacenes RegistroAlmacenes(RequestRegistroAlmacenes request);
}