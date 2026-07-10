package com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestEditarAllSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseEditarAllSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.services.SotckService;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.validations.VerificarCambiosSotck; // Asegúrate de crear esta clase de validación
import org.springframework.stereotype.Component;

@Component
public class EdicionSotckUseCase {

    private final SotckService sotckService;
    private final VerificarCambiosSotck verificarCambiosSotck;

    public EdicionSotckUseCase(
            SotckService sotckService,
            VerificarCambiosSotck verificarCambiosSotck
    ) {
        this.sotckService = sotckService;
        this.verificarCambiosSotck = verificarCambiosSotck;
    }

    public ResponseEditarAllSotck EdicionAllSotck(RequestEditarAllSotck request,long idArticulo) {
        try {
            RequestDetalleSotck requestDetalle = new RequestDetalleSotck();
            requestDetalle.setIdProducto(idArticulo);
            requestDetalle.setIdAlmacen(request.getIdAlmacen());

            ResponseDetalleSotck detalleBD = sotckService.DetalleSotck(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getSotck() == null) {
                throw new IllegalArgumentException("El stock no existe.");
            }

            if (!verificarCambiosSotck.verificarCambios(detalleBD.getSotck(), request)) {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllSotck response = sotckService.EditarAllSotck(request);
            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllSotck response = new ResponseEditarAllSotck();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al editar el stock: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllSotck response = new ResponseEditarAllSotck();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}