package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestEditarAllProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseEditarAllProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.services.ProductoImpuestoService;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllProductoImpuestoUseCase {
    private final ProductoImpuestoService productoImpuestoService;

    public EdicionAllProductoImpuestoUseCase(ProductoImpuestoService productoImpuestoService) {
        this.productoImpuestoService = productoImpuestoService;
    }
    public ResponseEditarAllProductoImpuesto EditarAllProductoImpuesto(RequestEditarAllProductoImpuesto request) {
        try {
            ResponseEditarAllProductoImpuesto response = productoImpuestoService.EditarAllProductoImpuesto(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllProductoImpuesto response = new ResponseEditarAllProductoImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el impuesto del producto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllProductoImpuesto response = new ResponseEditarAllProductoImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
