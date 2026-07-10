package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseDetalleListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.useCase.DetalleListaPreciosUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestRegistroProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseRegistroProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.services.ProductoPrecioService;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.validations.ValidacionRequest_RegistrarProductoPrecio;
import org.springframework.stereotype.Component;

@Component
public class RegistroProductoPrecioUseCase {
    private final ProductoPrecioService productoPrecioService;
    private final DetalleListaPreciosUseCase detalleListaPreciosUseCase;
    private final DetalleArticuloUseCase detalleArticuloUseCase;

    public RegistroProductoPrecioUseCase(ProductoPrecioService productoPrecioService, DetalleListaPreciosUseCase detalleListaPreciosUseCase, DetalleArticuloUseCase detalleArticuloUseCase) {
        this.productoPrecioService = productoPrecioService;

        this.detalleListaPreciosUseCase = detalleListaPreciosUseCase;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
    }

    public ResponseRegistroProductoPrecio RegistroProductoPrecio(RequestRegistroProductoPrecio request) {
        try {
            ResponseDetalleArticulo detalleBDArt = detalleArticuloUseCase.DetalleArticulo(request.getIdArticulo());

            ResponseDetalleListaPrecios detalleBDLis = detalleListaPreciosUseCase.DetalleListaPrecios(request.getIdListaPrecio());

            ValidacionRequest_RegistrarProductoPrecio.validarRegistroArticulo(detalleBDArt,detalleBDLis);

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
