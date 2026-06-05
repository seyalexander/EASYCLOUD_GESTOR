package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestEditarAllCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestEditarEstadoCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseEditarAllCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseEditarEstadoCodigoBarra;

public interface ICodigoBarraEdicion {
    ResponseEditarAllCodigoBarra EditarAllCodigoBarra(RequestEditarAllCodigoBarra request);
    //ResponseEditarEstadoCodigoBarra EditarEstadoCodigoBarra(RequestEditarEstadoCodigoBarra request, int estado);
}