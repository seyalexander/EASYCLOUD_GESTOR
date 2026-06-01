package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestListaProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseListaProductoImpuesto;

public interface IProductoImpuestoListado {
    ResponseListaProductoImpuesto ListaProductoImpuesto(RequestListaProductoImpuesto request);
}