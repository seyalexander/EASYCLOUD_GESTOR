package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestEditarEstadoProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseEditarEstadoProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.services.ProductoImpuestoService;
import org.springframework.stereotype.Component;

@Component
public class EdicionProductoImpuestoEstadoUseCase {
    private final ProductoImpuestoService productoImpuestoService;

    public EdicionProductoImpuestoEstadoUseCase(ProductoImpuestoService productoImpuestoService) {
        this.productoImpuestoService = productoImpuestoService;
    }

    public ResponseEditarEstadoProductoImpuesto AnularProductoImpuesto(long idProductoImpuesto) {
        try {
            RequestEditarEstadoProductoImpuesto request = new RequestEditarEstadoProductoImpuesto();
            request.setIdProductoImpuesto(idProductoImpuesto);

            ResponseEditarEstadoProductoImpuesto response = productoImpuestoService.EditarEstadoProductoImpuesto(request, 0);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoProductoImpuesto response = new ResponseEditarEstadoProductoImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el impuesto del producto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoProductoImpuesto response = new ResponseEditarEstadoProductoImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoProductoImpuesto ActivarProductoImpuesto(long idProductoImpuesto) {
        try {
            RequestEditarEstadoProductoImpuesto request = new RequestEditarEstadoProductoImpuesto();
            request.setIdProductoImpuesto(idProductoImpuesto);

            ResponseEditarEstadoProductoImpuesto response = productoImpuestoService.EditarEstadoProductoImpuesto(request, 1);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoProductoImpuesto response = new ResponseEditarEstadoProductoImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el impuesto del producto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoProductoImpuesto response = new ResponseEditarEstadoProductoImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
