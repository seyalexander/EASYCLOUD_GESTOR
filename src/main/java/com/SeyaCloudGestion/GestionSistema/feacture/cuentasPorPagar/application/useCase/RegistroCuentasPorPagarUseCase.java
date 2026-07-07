package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.useCase.DetalleCompraUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request.RequestRegistroCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.ResponseRegistroCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.services.CuentasPorPagarService;
import org.springframework.stereotype.Component;

@Component
public class RegistroCuentasPorPagarUseCase {

    private final CuentasPorPagarService cuentasPorPagarService;
    private final DetalleCompraUseCase detalleCompraUseCase;

    public RegistroCuentasPorPagarUseCase(
            CuentasPorPagarService cuentasPorPagarService, DetalleCompraUseCase detalleCompraUseCase
    ) {
        this.cuentasPorPagarService = cuentasPorPagarService;
        this.detalleCompraUseCase = detalleCompraUseCase;
    }

    public ResponseRegistroCuentasPorPagar registrarCuentaPorPagar(RequestRegistroCuentasPorPagar request) {
        try {
            //get id compra
            ResponseDetalleCompra responseBDcompra = detalleCompraUseCase.detalleCompra(request.getIdCompra());
            if (!responseBDcompra.isExito()|| responseBDcompra.getCompra() == null) {
                throw new IllegalArgumentException("No se encontró la compra con el id proporcionado: " + request.getIdCompra());
            }
            //si el monto pendiente supera el total de la compra
            if (request.getMontoPendiente() > responseBDcompra.getCompra().getTotal()) {
                throw new IllegalArgumentException("El monto pendiente no puede superar el total de la compra.");
            }

            ResponseRegistroCuentasPorPagar response = cuentasPorPagarService.RegistroCuentasPorPagar(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroCuentasPorPagar response = new ResponseRegistroCuentasPorPagar();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar la cuenta por pagar: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroCuentasPorPagar response = new ResponseRegistroCuentasPorPagar();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}