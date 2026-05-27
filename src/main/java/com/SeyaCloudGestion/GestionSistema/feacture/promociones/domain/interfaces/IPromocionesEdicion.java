package com.SeyaCloudGestion.GestionSistema.feacture.promociones.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.request.RequestEditarAllPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.request.RequestEditarEstadoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response.ResponseEditarAllPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response.ResponseEditarEstadoPromociones;

public interface IPromocionesEdicion {
    ResponseEditarAllPromociones EditarAllPromociones(RequestEditarAllPromociones request);
    ResponseEditarEstadoPromociones EditarEstadoPromociones(RequestEditarEstadoPromociones request, int estado);
}