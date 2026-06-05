package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestEditarAllDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestEditarEstadoDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseEditarAllDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseEditarEstadoDevolucion;

public interface IDevolucionEdicion {
    ResponseEditarAllDevolucion EditarAllDevolucion(RequestEditarAllDevolucion request);
    ResponseEditarEstadoDevolucion EditarEstadoDevolucion(RequestEditarEstadoDevolucion request, int estado);
}