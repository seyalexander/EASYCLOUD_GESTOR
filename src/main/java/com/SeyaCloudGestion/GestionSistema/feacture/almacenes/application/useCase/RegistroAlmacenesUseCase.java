package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestRegistroAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseRegistroAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenesService;
import org.springframework.stereotype.Component;

@Component
public class RegistroAlmacenesUseCase {
    private final AlmacenesService almacenesService;

    public RegistroAlmacenesUseCase(AlmacenesService almacenesService) {
        this.almacenesService = almacenesService;
    }
    public ResponseRegistroAlmacenes RegistroAlmacenes(RequestRegistroAlmacenes request) {
        try {
            ResponseRegistroAlmacenes response = almacenesService.RegistroAlmacenes(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroAlmacenes response = new ResponseRegistroAlmacenes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroAlmacenes response = new ResponseRegistroAlmacenes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}