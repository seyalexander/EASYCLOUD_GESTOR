package com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestEditarAllMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestEditarEstadoMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestEditarPredeterminadoMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseEditarAllMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseEditarEstadoMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseEditarPredeterminadoMoneda;

public interface IMonedaEdicion {
    ResponseEditarAllMoneda EditarAllMoneda(RequestEditarAllMoneda request, long userAutenticado);
    ResponseEditarEstadoMoneda EditarEstadoMoneda(RequestEditarEstadoMoneda request, int estado, long userAutenticado);
    ResponseEditarPredeterminadoMoneda  EditarPredetermiandoMoneda(RequestEditarPredeterminadoMoneda request, long userAutenticado, long empresaAutenticado);
}
