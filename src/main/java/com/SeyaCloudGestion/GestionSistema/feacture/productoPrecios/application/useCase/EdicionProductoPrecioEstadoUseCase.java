package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestEditarEstadoProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseEditarEstadoProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.services.ProductoPrecioService;
import org.springframework.stereotype.Component;

@Component
public class EdicionProductoPrecioEstadoUseCase {
    private  final ProductoPrecioService productoPrecioService;

    public EdicionProductoPrecioEstadoUseCase(ProductoPrecioService productoPrecioService) {
        this.productoPrecioService = productoPrecioService;
    }
    public ResponseEditarEstadoProductoPrecio AnularProductoPrecio(long idProductoPrecio) {
        try {
            RequestEditarEstadoProductoPrecio request = new RequestEditarEstadoProductoPrecio();
            request.setIdProductoPrecio(idProductoPrecio);

            ResponseEditarEstadoProductoPrecio response = productoPrecioService.EditarEstadoProductoPrecio(request, 0);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoProductoPrecio response = new ResponseEditarEstadoProductoPrecio();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el precio del producto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoProductoPrecio response = new ResponseEditarEstadoProductoPrecio();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoProductoPrecio ActivarProductoPrecio(long idProductoPrecio) {
        try {
            RequestEditarEstadoProductoPrecio request = new RequestEditarEstadoProductoPrecio();
            request.setIdProductoPrecio(idProductoPrecio);

            ResponseEditarEstadoProductoPrecio response = productoPrecioService.EditarEstadoProductoPrecio(request, 1);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoProductoPrecio response = new ResponseEditarEstadoProductoPrecio();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el precio del producto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoProductoPrecio response = new ResponseEditarEstadoProductoPrecio();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
