package com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.services.SotckService;
import org.springframework.stereotype.Component;

@Component
public class DetalleSotckUseCase {

    private final SotckService sotckService;

    public DetalleSotckUseCase(SotckService sotckService) {
        this.sotckService = sotckService;
    }

    public ResponseDetalleSotck DetalleSotck(long idProducto, long idAlmacen) {
        try {
            RequestDetalleSotck request = new RequestDetalleSotck();
            request.setIdProducto(idProducto);
            request.setIdAlmacen(idAlmacen);

            ResponseDetalleSotck response = sotckService.DetalleSotck(request);
            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleSotck response = new ResponseDetalleSotck();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle del stock: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleSotck response = new ResponseDetalleSotck();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}