package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestRegistroProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseRegistroProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.services.ProductoImpuestoService;
import org.springframework.stereotype.Component;

@Component
public class RegistroProductoImpuestoUseCase {
    private final ProductoImpuestoService productoImpuestoService;

    public RegistroProductoImpuestoUseCase(ProductoImpuestoService productoImpuestoService) {
        this.productoImpuestoService = productoImpuestoService;
    }

    public ResponseRegistroProductoImpuesto RegistroProductoImpuesto(RequestRegistroProductoImpuesto request) {
        try {
            ResponseRegistroProductoImpuesto response = productoImpuestoService.RegistroProductoImpuesto(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroProductoImpuesto response = new ResponseRegistroProductoImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el impuesto del producto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroProductoImpuesto response = new ResponseRegistroProductoImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
