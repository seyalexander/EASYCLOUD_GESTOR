package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestRegistroAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseRegistroAlmacen;

public interface IAlmacenRegistro {
    ResponseRegistroAlmacen RegistroAlmacen(RequestRegistroAlmacen request);
}