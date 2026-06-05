package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestDetalleAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenesService;
import org.springframework.stereotype.Component;

@Component
public class DetalleAlmacenesUseCase {
    private final AlmacenesService almacenesService;

    public DetalleAlmacenesUseCase(AlmacenesService almacenesService) {
        this.almacenesService = almacenesService;
    }
    public ResponseDetalleAlmacenes DetalleAlmacenes(long idAlmacenes) {
        try {
            RequestDetalleAlmacenes request = new RequestDetalleAlmacenes();
            request.setIdAlmacenes(idAlmacenes);

            ResponseDetalleAlmacenes response = almacenesService.DetalleAlmacenes(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleAlmacenes response = new ResponseDetalleAlmacenes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al ver el detalle del almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleAlmacenes response = new ResponseDetalleAlmacenes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}