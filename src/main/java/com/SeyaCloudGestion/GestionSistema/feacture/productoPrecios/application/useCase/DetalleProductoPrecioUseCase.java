package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestDetalleProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseDetalleProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.services.ProductoPrecioService;
import org.springframework.stereotype.Component;

@Component
public class DetalleProductoPrecioUseCase {
    private final ProductoPrecioService productoPrecioService;

    public DetalleProductoPrecioUseCase(ProductoPrecioService productoPrecioService) {
        this.productoPrecioService = productoPrecioService;
    }

    public ResponseDetalleProductoPrecio DetalleProductoPrecio(long idProductoPrecio) {
        try {
            RequestDetalleProductoPrecio request = new RequestDetalleProductoPrecio();
            request.setIdProductoPrecio(idProductoPrecio);

            ResponseDetalleProductoPrecio response = productoPrecioService.DetalleProductoPrecio(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleProductoPrecio response = new ResponseDetalleProductoPrecio();
            response.setExito(false);
            response.setMessage(e.getMessage() );
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar la lista de precios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleProductoPrecio response = new ResponseDetalleProductoPrecio();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

}
