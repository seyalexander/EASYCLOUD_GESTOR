package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarAllAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseEditarAllAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenesService;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllAlmacenesUseCase {
    private final AlmacenesService almacenesService;

    public EdicionAllAlmacenesUseCase(AlmacenesService almacenesService) {
        this.almacenesService = almacenesService;
    }
    public ResponseEditarAllAlmacenes EdicionAllAlmacenes(RequestEditarAllAlmacenes request) {
        try {
            ResponseEditarAllAlmacenes response = almacenesService.EditarAllAlmacenes(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllAlmacenes response = new ResponseEditarAllAlmacenes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllAlmacenes response = new ResponseEditarAllAlmacenes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}