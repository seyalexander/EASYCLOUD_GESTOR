package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseRegistroDetalleInventario;

public interface IDetalleInventarioRegistro {
    ResponseRegistroDetalleInventario registrarDetalleIventario(long idInventarioCabezera, RequestRegistroDetalleInventario request, double stockSistema );
}
