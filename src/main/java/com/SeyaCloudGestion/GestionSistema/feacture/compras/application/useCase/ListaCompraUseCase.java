package com.SeyaCloudGestion.GestionSistema.feacture.compras.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestListaCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseListaCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.services.CompraService;
import org.springframework.stereotype.Component;

@Component
public class ListaCompraUseCase {
    private final CompraService compraService;

    public ListaCompraUseCase(
            CompraService compraService
    ) {
        this.compraService = compraService;
    }

    public ResponseListaCompra ListaCompra(RequestListaCompra request) {
        try {
            ResponseListaCompra response = compraService.listaCompra(request);
            if (response.isExito()) {

            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaCompra response = new ResponseListaCompra();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setCompras(java.util.List.of());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar las compras: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaCompra response = new ResponseListaCompra();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setCompras(java.util.List.of());
            return response;
        }
    }
}