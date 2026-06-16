package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestListaAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseListaAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenService;
import org.springframework.stereotype.Component;

@Component
public class ListaAlmacenUseCase {
    private final AlmacenService almacenesService;

    public ListaAlmacenUseCase(AlmacenService almacenesService) {
        this.almacenesService = almacenesService;
    }

    public ResponseListaAlmacen ListaAlmacenes(RequestListaAlmacen request) {
        try {
            ResponseListaAlmacen response = almacenesService.ListaAlmacen(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaAlmacen response = new ResponseListaAlmacen();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setAlmacenes(java.util.List.of());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los almacenes: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaAlmacen response = new ResponseListaAlmacen();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setAlmacenes(java.util.List.of());
            return response;
        }
    }
}