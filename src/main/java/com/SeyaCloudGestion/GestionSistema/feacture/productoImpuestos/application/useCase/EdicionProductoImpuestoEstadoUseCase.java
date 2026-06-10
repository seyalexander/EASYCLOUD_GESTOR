package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestDetalleProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestEditarEstadoProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseDetalleProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseEditarEstadoProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.services.ProductoImpuestoService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionProductoImpuestoEstadoUseCase {
    private final ProductoImpuestoService productoImpuestoService;

    public EdicionProductoImpuestoEstadoUseCase(ProductoImpuestoService productoImpuestoService) {
        this.productoImpuestoService = productoImpuestoService;
    }

    public ResponseEditarEstadoProductoImpuesto AnularProductoImpuesto(long idProductoImpuesto) {
        try {
            RequestDetalleProductoImpuesto requestDetalle = new RequestDetalleProductoImpuesto();
            requestDetalle.setIdProductoImpuesto(idProductoImpuesto);

            ResponseDetalleProductoImpuesto detalleBD= productoImpuestoService.DetalleProductoImpuesto(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getProductoImpuesto() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getProductoImpuesto().getEstado(), 0)) {
                throw new IllegalArgumentException("Elarticulo ya se encuentra anulado.");
            }

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
            RequestDetalleProductoImpuesto requestDetalle = new RequestDetalleProductoImpuesto();
            requestDetalle.setIdProductoImpuesto(idProductoImpuesto);

            ResponseDetalleProductoImpuesto detalleBD= productoImpuestoService.DetalleProductoImpuesto(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getProductoImpuesto() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getProductoImpuesto().getEstado(), 1)) {
                throw new IllegalArgumentException("Elarticulo ya se encuentra activado.");
            }

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
