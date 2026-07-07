package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestEditarEstadoProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseDetalleProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseEditarEstadoProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.services.ProductoPrecioService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionProductoPrecioEstadoUseCase {
    private final ProductoPrecioService productoPrecioService;
    private final DetalleProductoPrecioUseCase detalleProductoPrecioUseCase;
    public EdicionProductoPrecioEstadoUseCase(ProductoPrecioService productoPrecioService, DetalleProductoPrecioUseCase detalleProductoPrecioUseCase) {
        this.productoPrecioService = productoPrecioService;
        this.detalleProductoPrecioUseCase = detalleProductoPrecioUseCase;
    }
    public ResponseEditarEstadoProductoPrecio AnularProductoPrecio(long idProductoPrecio) {
        try {
            //productoPrecio
            ResponseDetalleProductoPrecio detalleBDProductoPrecio= detalleProductoPrecioUseCase.DetalleProductoPrecio(idProductoPrecio);

            if (!detalleBDProductoPrecio.isExito() || detalleBDProductoPrecio.getProductoPrecio() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBDProductoPrecio.getProductoPrecio().getEstado(), 0)) {
                throw new IllegalArgumentException("Elarticulo ya se encuentra anulado.");
            }

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
            //productoPrecio
            ResponseDetalleProductoPrecio detalleBDProductoPrecio= detalleProductoPrecioUseCase.DetalleProductoPrecio(idProductoPrecio);

            if (!detalleBDProductoPrecio.isExito() || detalleBDProductoPrecio.getProductoPrecio() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBDProductoPrecio.getProductoPrecio().getEstado(), 1)) {
                throw new IllegalArgumentException("Elarticulo ya se encuentra activado.");
            }

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
