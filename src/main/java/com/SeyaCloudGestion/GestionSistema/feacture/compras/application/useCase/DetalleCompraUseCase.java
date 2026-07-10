package com.SeyaCloudGestion.GestionSistema.feacture.compras.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.services.CompraService;
import org.springframework.stereotype.Component;

@Component
public class DetalleCompraUseCase {

    private final CompraService compraService;

    public DetalleCompraUseCase(
            CompraService compraService
    ) {
        this.compraService = compraService;
    }

    public ResponseDetalleCompra detalleCompra(long idCompra) {
        try {
            RequestDetalleCompra request = new RequestDetalleCompra();
            request.setIdCompra(idCompra);

            ResponseDetalleCompra response = compraService.DetalleCompra(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleCompra response = new ResponseDetalleCompra();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle de la compra: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleCompra response = new ResponseDetalleCompra();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}