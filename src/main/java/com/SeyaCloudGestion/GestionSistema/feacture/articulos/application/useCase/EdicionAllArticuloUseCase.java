package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestEditarAllArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseEditarAllArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.validations.VerificacionesArticulo;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllArticuloUseCase {
    private final ArticulosService articulosService;
    private final VerificacionesArticulo verificarCambiosArticulo;
    private final DetalleArticuloUseCase detalleArticuloUseCase;

    public EdicionAllArticuloUseCase(ArticulosService articulosService, VerificacionesArticulo verificarCambiosArticulo, DetalleArticuloUseCase detalleArticuloUseCase) {
        this.articulosService = articulosService;
        this.verificarCambiosArticulo = verificarCambiosArticulo;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
    }
    public ResponseEditarAllArticulo EdicionAllArticulo(RequestEditarAllArticulo request) {
        try {
            //articulo
            ResponseDetalleArticulo detalleBDArticulo= detalleArticuloUseCase.DetalleArticulo(request.getIdArticulos());

            if (!detalleBDArticulo.isExito() || detalleBDArticulo.getArticulos() == null) {
                throw new IllegalArgumentException("El articulo no existe.");
            }

            if (!verificarCambiosArticulo.verificarCambios(detalleBDArticulo.getArticulos(), request)) {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }

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
