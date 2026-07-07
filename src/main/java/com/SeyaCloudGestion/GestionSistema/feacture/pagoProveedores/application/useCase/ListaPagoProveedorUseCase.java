package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestListaPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseListaPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.services.PagoProveedorService;
import org.springframework.stereotype.Component;

@Component
public class ListaPagoProveedorUseCase {

    private final PagoProveedorService pagoProveedorService;

    public ListaPagoProveedorUseCase(
            PagoProveedorService pagoProveedorService) {
        this.pagoProveedorService = pagoProveedorService;
    }

    public ResponseListaPagoProveedor listaPagoProveedor(RequestListaPagoProveedor request) {
        try {
            ResponseListaPagoProveedor response = pagoProveedorService.listaPagoProveedor(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaPagoProveedor response = new ResponseListaPagoProveedor();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setPagoProveedor(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los pagos de proveedores: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaPagoProveedor response = new ResponseListaPagoProveedor();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setPagoProveedor(java.util.List.of());
            return response;
        }
    }
}