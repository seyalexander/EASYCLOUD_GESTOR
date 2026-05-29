package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestEditarEstadoArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseEditarEstadoArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import org.springframework.stereotype.Component;

@Component
public class EdicionEstadoArticuloUseCase {
    private final ArticulosService articulosService;

    public EdicionEstadoArticuloUseCase(ArticulosService articulosService) {
        this.articulosService = articulosService;
    }
    public ResponseEditarEstadoArticulo AnularArticulo(long idArticulos) {
        try {
            RequestEditarEstadoArticulo request = new RequestEditarEstadoArticulo();
            request.setIdArticulo(idArticulos);

            ResponseEditarEstadoArticulo response = articulosService.EditarEstadoArticulo(request, 0);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoArticulo response = new ResponseEditarEstadoArticulo();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el artículo: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoArticulo response = new ResponseEditarEstadoArticulo();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoArticulo ActivarArticulo(long idArticulos) {
        try {
            RequestEditarEstadoArticulo request = new RequestEditarEstadoArticulo();
            request.setIdArticulo(idArticulos);

            ResponseEditarEstadoArticulo response = articulosService.EditarEstadoArticulo(request, 1);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoArticulo response = new ResponseEditarEstadoArticulo();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el artículo: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoArticulo response = new ResponseEditarEstadoArticulo();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}

