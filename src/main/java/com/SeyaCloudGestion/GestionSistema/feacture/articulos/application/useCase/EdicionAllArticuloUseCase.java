package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestEditarAllArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseEditarAllArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllArticuloUseCase {
    private final ArticulosService articulosService;

    public EdicionAllArticuloUseCase(ArticulosService articulosService) {
        this.articulosService = articulosService;
    }
    public ResponseEditarAllArticulo EdicionAllArticulo(RequestEditarAllArticulo request) {
        try {
            ResponseEditarAllArticulo response = articulosService.EditarAllArticulos(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllArticulo response = new ResponseEditarAllArticulo();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el artículo: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllArticulo response = new ResponseEditarAllArticulo();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
