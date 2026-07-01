package com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestRegistroCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseRegistroCompra;

public interface ICompraRegistro {
    ResponseRegistroCompra RegistroCompra(RequestRegistroCompra request,double subTotal, double igv, double total);
}