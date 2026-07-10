package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestDetalleProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseDetalleProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.services.ProductoImpuestoService;
import org.springframework.stereotype.Component;

@Component
public class DetalleProductoImpuestoUseCase {
    private final ProductoImpuestoService productoImpuestoService;

    public DetalleProductoImpuestoUseCase(ProductoImpuestoService productoImpuestoService) {
        this.productoImpuestoService = productoImpuestoService;
    }
    public ResponseDetalleProductoImpuesto DetalleProductoImpuesto(long idProductoImpuesto) {
        try {
            RequestDetalleProductoImpuesto request = new RequestDetalleProductoImpuesto();
            request.setIdProductoImpuesto(idProductoImpuesto);
            ResponseDetalleProductoImpuesto response = productoImpuestoService.DetalleProductoImpuesto(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleProductoImpuesto response = new ResponseDetalleProductoImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage() );
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar elimpuesto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleProductoImpuesto response = new ResponseDetalleProductoImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
