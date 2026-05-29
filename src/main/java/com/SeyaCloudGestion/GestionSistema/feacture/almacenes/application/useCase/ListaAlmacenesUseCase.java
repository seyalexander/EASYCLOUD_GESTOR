package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestListaAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseListaAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenesService;
import org.springframework.stereotype.Component;

@Component
public class ListaAlmacenesUseCase {
    private final AlmacenesService almacenesService;

    public ListaAlmacenesUseCase(AlmacenesService almacenesService) {
        this.almacenesService = almacenesService;
    }

    public ResponseListaAlmacenes ListaAlmacenes(RequestListaAlmacenes request) {
        try {
            ResponseListaAlmacenes response = almacenesService.ListaAlmacenes(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaAlmacenes response = new ResponseListaAlmacenes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setAlmacenes(java.util.List.of());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los almacenes: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaAlmacenes response = new ResponseListaAlmacenes();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setAlmacenes(java.util.List.of());
            return response;
        }
    }
}