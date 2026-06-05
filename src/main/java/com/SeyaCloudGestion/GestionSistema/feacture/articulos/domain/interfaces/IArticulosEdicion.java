package com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestEditarAllArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestEditarEstadoArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseEditarAllArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseEditarEstadoArticulo;

public interface IArticulosEdicion {
    ResponseEditarAllArticulo EditarAllArticulos(RequestEditarAllArticulo request);
    ResponseEditarEstadoArticulo EditarEstadoArticulos(RequestEditarEstadoArticulo request, int estado);
}