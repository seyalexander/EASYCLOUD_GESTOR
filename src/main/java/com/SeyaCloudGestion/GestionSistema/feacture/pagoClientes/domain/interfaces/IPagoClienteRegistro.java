package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestRegistroDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroPagoCliente;

public interface IPagoClienteRegistro {
    ResponseRegistroDetallePagoCliente RegistroDetallePagoCliente(long idCuentaPorCobrar , RequestRegistroDetallePagoCliente request);
}