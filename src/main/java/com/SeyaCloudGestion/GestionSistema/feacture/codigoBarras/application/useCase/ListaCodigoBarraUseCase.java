package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseListaCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.services.CodigoBarraService;
import org.springframework.stereotype.Component;

@Component
public class ListaCodigoBarraUseCase {
    private  final CodigoBarraService codigoBarraService;

    public ListaCodigoBarraUseCase(CodigoBarraService codigoBarraService) {
        this.codigoBarraService = codigoBarraService;
    }
    public ResponseListaCodigoBarra ListaCodigoBarra() {
        try {
            ResponseListaCodigoBarra response = codigoBarraService.ListaCodigoBarra();

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaCodigoBarra response = new ResponseListaCodigoBarra();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setCodigoBarras(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los códigos de barra: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaCodigoBarra response = new ResponseListaCodigoBarra();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setCodigoBarras(java.util.List.of());
            return response;
        }
    }
}
