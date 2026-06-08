package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestRegistroPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroPagoCliente;

public interface IPagoClienteRegistro {
    ResponseRegistroPagoCliente RegistroPagoCliente(RequestRegistroPagoCliente request);
}