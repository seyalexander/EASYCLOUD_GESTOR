package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestDetalleProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestEditarAllProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseDetalleProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseEditarAllProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.services.ProductoImpuestoService;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.validations.VerificacionesProductoImpuesto;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllProductoImpuestoUseCase {
    private final ProductoImpuestoService productoImpuestoService;
    private final VerificacionesProductoImpuesto verificacionesProductoImpuesto;

    public EdicionAllProductoImpuestoUseCase(ProductoImpuestoService productoImpuestoService, VerificacionesProductoImpuesto verificacionesProductoImpuesto) {
        this.productoImpuestoService = productoImpuestoService;
        this.verificacionesProductoImpuesto = verificacionesProductoImpuesto;
    }
    public ResponseEditarAllProductoImpuesto EditarAllProductoImpuesto(RequestEditarAllProductoImpuesto request) {
        try {

            RequestDetalleProductoImpuesto requestDetalle = new RequestDetalleProductoImpuesto();
            requestDetalle.setIdProductoImpuesto(request.getIdProductoImpuesto());

            ResponseDetalleProductoImpuesto detalleBD= productoImpuestoService.DetalleProductoImpuesto(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getProductoImpuesto() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }
            if (!verificacionesProductoImpuesto.verificarCambios(detalleBD.getProductoImpuesto(), request)) {
                throw new ResourceNotFoundException("No se detectaron cambios para actualizar.");
            }

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
