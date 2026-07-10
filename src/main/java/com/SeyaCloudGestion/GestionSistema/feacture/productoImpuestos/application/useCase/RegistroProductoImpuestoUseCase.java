package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestRegistroProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseRegistroProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.services.ProductoImpuestoService;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.validations.ValidacionRequest_RegistrarProductoImpuesto;
import org.springframework.stereotype.Component;

@Component
public class RegistroProductoImpuestoUseCase {
    private final ProductoImpuestoService productoImpuestoService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;

    public RegistroProductoImpuestoUseCase(ProductoImpuestoService productoImpuestoService, DetalleArticuloUseCase detalleArticuloUseCase) {
        this.productoImpuestoService = productoImpuestoService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
    }

    public ResponseRegistroProductoImpuesto RegistroProductoImpuesto(RequestRegistroProductoImpuesto request) {
        try {
            ResponseDetalleArticulo detalleBDArt = detalleArticuloUseCase.DetalleArticulo(request.getIdArticulo());

            ValidacionRequest_RegistrarProductoImpuesto.validar(detalleBDArt);

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
