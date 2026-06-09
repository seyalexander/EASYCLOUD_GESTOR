package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestDetalleListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseDetalleListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.services.ListaPreciosService;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.validations.VerificarCambiosListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestDetalleProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestEditarAllProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseDetalleProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseEditarAllProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.services.ProductoPrecioService;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.validations.ValidacionRequest_RegistrarProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.validations.VerificarCambiosProductoPrecio;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllProductoPrecioUseCase {
    private  final ProductoPrecioService productoPrecioService;
    private final ListaPreciosService listaPreciosService;
    private final ArticulosService articulosService;
    private final VerificarCambiosProductoPrecio verificarCambiosProductoPrecio;

    public EdicionAllProductoPrecioUseCase(ProductoPrecioService productoPrecioService, ListaPreciosService listaPreciosService, ArticulosService articulosService, VerificarCambiosProductoPrecio verificarCambiosProductoPrecio) {
        this.productoPrecioService = productoPrecioService;
        this.listaPreciosService = listaPreciosService;
        this.articulosService = articulosService;
        this.verificarCambiosProductoPrecio = verificarCambiosProductoPrecio;
    }
    public ResponseEditarAllProductoPrecio EditarAllProductoPrecio(RequestEditarAllProductoPrecio request) {
        try {
            //verificar el id
            RequestDetalleProductoPrecio requestDetalle = new RequestDetalleProductoPrecio();
            requestDetalle.setIdProductoPrecio(request.getIdProductoPrecio());

            ResponseDetalleProductoPrecio detalleBD= productoPrecioService.DetalleProductoPrecio(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getProductoPrecio() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            //verificar cambios
            if (!verificarCambiosProductoPrecio.verificarCambios(detalleBD.getProductoPrecio(), request)) {
                throw new ResourceNotFoundException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllProductoPrecio response = productoPrecioService.EditarAllProductoPrecio(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllProductoPrecio response = new ResponseEditarAllProductoPrecio();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el precio del producto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllProductoPrecio response = new ResponseEditarAllProductoPrecio();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
