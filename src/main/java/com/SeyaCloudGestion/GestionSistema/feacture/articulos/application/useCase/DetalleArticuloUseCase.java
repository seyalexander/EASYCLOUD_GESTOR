package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import org.springframework.stereotype.Component;

@Component
public class DetalleArticuloUseCase {
    private final ArticulosService articulosService;

    public DetalleArticuloUseCase(ArticulosService articulosService) {
        this.articulosService = articulosService;
    }

    public ResponseDetalleArticulo DetalleArticulo(long idArticulos) {
        try {
            RequestDetalleArticulo request = new RequestDetalleArticulo();
            request.setIdArticulo(idArticulos);

            ResponseDetalleArticulo response = articulosService.DetalleArticulos(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleArticulo response = new ResponseDetalleArticulo();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al ver el detalle del artículo: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleArticulo response = new ResponseDetalleArticulo();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
