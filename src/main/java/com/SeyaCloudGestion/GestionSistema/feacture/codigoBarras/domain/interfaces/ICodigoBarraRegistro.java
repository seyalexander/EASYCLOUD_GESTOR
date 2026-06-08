package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestRegistroCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseRegistroCodigoBarra;

public interface ICodigoBarraRegistro {
    ResponseRegistroCodigoBarra RegistroCodigoBarra(RequestRegistroCodigoBarra request);
}