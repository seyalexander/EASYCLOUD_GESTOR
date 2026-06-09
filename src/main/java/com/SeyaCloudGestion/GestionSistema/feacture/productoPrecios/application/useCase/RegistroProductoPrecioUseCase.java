package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestDetalleListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseDetalleListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.services.ListaPreciosService;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestRegistroProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseRegistroProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.services.ProductoPrecioService;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.validations.ValidacionRequest_RegistrarProductoPrecio;
import org.springframework.stereotype.Component;

@Component
public class RegistroProductoPrecioUseCase {
    private final ProductoPrecioService productoPrecioService;
    private final ListaPreciosService listaPreciosService;
    private final ArticulosService articulosService;

    public RegistroProductoPrecioUseCase(ProductoPrecioService productoPrecioService, ListaPreciosService listaPreciosService, ArticulosService articulosService) {
        this.productoPrecioService = productoPrecioService;
        this.listaPreciosService = listaPreciosService;
        this.articulosService = articulosService;
    }

    public ResponseRegistroProductoPrecio RegistroProductoPrecio(RequestRegistroProductoPrecio request) {
        try {
            RequestDetalleArticulo requestArt = new RequestDetalleArticulo();
            requestArt.setIdArticulo(request.getIdArticulo());
            ResponseDetalleArticulo detalleBDArt = articulosService.DetalleArticulos(requestArt);

            RequestDetalleListaPrecios requestLis = new RequestDetalleListaPrecios();
            requestLis.setIdListaPrecios(request.getIdListaPrecio());
            ResponseDetalleListaPrecios detalleBDLis = listaPreciosService.DetalleListaPrecios(requestLis);

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
