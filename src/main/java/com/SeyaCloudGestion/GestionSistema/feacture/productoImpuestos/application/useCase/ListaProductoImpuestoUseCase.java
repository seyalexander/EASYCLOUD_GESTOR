package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestListaProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseListaProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.services.ProductoImpuestoService;
import org.springframework.stereotype.Component;

@Component
public class ListaProductoImpuestoUseCase {
    private final ProductoImpuestoService productoImpuestoService;

    public ListaProductoImpuestoUseCase(ProductoImpuestoService productoImpuestoService) {
        this.productoImpuestoService = productoImpuestoService;
    }
    public ResponseListaProductoImpuesto ListaProductoImpuesto(RequestListaProductoImpuesto request) {
        try {
            ResponseListaProductoImpuesto response = productoImpuestoService.ListaProductoImpuesto(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaProductoImpuesto response = new ResponseListaProductoImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setProductoImpuestos(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los impuestos del producto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaProductoImpuesto response = new ResponseListaProductoImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setProductoImpuestos(java.util.List.of());
            return response;
        }
    }
}
