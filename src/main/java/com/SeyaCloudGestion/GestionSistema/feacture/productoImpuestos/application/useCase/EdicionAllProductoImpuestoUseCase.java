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
    private final DetalleProductoImpuestoUseCase detalleProductoImpuestoUseCase;

    public EdicionAllProductoImpuestoUseCase(ProductoImpuestoService productoImpuestoService, VerificacionesProductoImpuesto verificacionesProductoImpuesto, DetalleProductoImpuestoUseCase detalleProductoImpuestoUseCase) {
        this.productoImpuestoService = productoImpuestoService;
        this.verificacionesProductoImpuesto = verificacionesProductoImpuesto;
        this.detalleProductoImpuestoUseCase = detalleProductoImpuestoUseCase;
    }
    public ResponseEditarAllProductoImpuesto EditarAllProductoImpuesto(RequestEditarAllProductoImpuesto request) {
        try {

            //productoImpuesto
            ResponseDetalleProductoImpuesto detalleBDProductoImpuesto= detalleProductoImpuestoUseCase.DetalleProductoImpuesto(request.getIdProductoImpuesto());

            if (!detalleBDProductoImpuesto.isExito() || detalleBDProductoImpuesto.getProductoImpuesto() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (!verificacionesProductoImpuesto.verificarCambios(detalleBDProductoImpuesto.getProductoImpuesto(), request)) {
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
