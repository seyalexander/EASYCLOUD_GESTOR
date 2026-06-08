package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestRegistroProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseRegistroProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.services.ProductoPrecioService;
import org.springframework.stereotype.Component;

@Component
public class RegistroProductoPrecioUseCase {
    private  final ProductoPrecioService productoPrecioService;

    public RegistroProductoPrecioUseCase(ProductoPrecioService productoPrecioService) {
        this.productoPrecioService = productoPrecioService;
    }

    public ResponseRegistroProductoPrecio RegistroProductoPrecio(RequestRegistroProductoPrecio request) {
        try {
            ResponseRegistroProductoPrecio response = productoPrecioService.RegistroProductoPrecio(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroProductoPrecio response = new ResponseRegistroProductoPrecio();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el precio del producto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroProductoPrecio response = new ResponseRegistroProductoPrecio();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
