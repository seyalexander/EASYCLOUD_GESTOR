package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestEditarEstadoArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseEditarEstadoArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseDetalleFamilia;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionEstadoArticuloUseCase {
    private final ArticulosService articulosService;
    private final DetalleArticuloUseCase detalleArticuloUseCase;
    public EdicionEstadoArticuloUseCase(ArticulosService articulosService, DetalleArticuloUseCase detalleArticuloUseCase) {
        this.articulosService = articulosService;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
    }

    public ResponseEditarEstadoArticulo AnularArticulo(long idArticulo) {
        try {
            //articulo
            ResponseDetalleArticulo detalleBDArticulo= detalleArticuloUseCase.DetalleArticulo(idArticulo);

            if (!detalleBDArticulo.isExito() || detalleBDArticulo.getArticulos() == null) {
                throw new IllegalArgumentException("El articulo no existe.");
            }

            if (Objects.equals(detalleBDArticulo.getArticulos().getEstado(), 0)) {
                throw new IllegalArgumentException("Elarticulo ya se encuentra anulado.");
            }

            RequestEditarEstadoArticulo request = new RequestEditarEstadoArticulo();
            request.setIdArticulo(idArticulo);

            ResponseEditarEstadoArticulo response = articulosService.EditarEstadoArticulos(request, 0);

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

    public ResponseEditarEstadoArticulo ActivarArticulo(long idArticulo) {
        try {
            //articulo
            ResponseDetalleArticulo detalleBDArticulo= detalleArticuloUseCase.DetalleArticulo(idArticulo);

            if (!detalleBDArticulo.isExito() || detalleBDArticulo.getArticulos() == null) {
                throw new IllegalArgumentException("El articulo no existe.");
            }

            if (Objects.equals(detalleBDArticulo.getArticulos().getEstado(), 1)) {
                throw new IllegalArgumentException("Elarticulo ya se encuentra activado.");
            }

            RequestEditarEstadoArticulo request = new RequestEditarEstadoArticulo();
            request.setIdArticulo(idArticulo);

            ResponseEditarEstadoArticulo response = articulosService.EditarEstadoArticulos(request, 1);

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

