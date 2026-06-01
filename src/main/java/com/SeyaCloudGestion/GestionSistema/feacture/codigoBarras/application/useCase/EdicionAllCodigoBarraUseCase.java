package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestEditarAllCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseEditarAllCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.services.CodigoBarraService;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllCodigoBarraUseCase {
    private  final CodigoBarraService codigoBarraService;

    public EdicionAllCodigoBarraUseCase(CodigoBarraService codigoBarraService) {
        this.codigoBarraService = codigoBarraService;
    }
    public ResponseEditarAllCodigoBarra EdicionAllCodigoBarra(RequestEditarAllCodigoBarra request) {
        try {
            ResponseEditarAllCodigoBarra response = codigoBarraService.EditarAllCodigoBarra(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllCodigoBarra response = new ResponseEditarAllCodigoBarra();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el código de barra: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllCodigoBarra response = new ResponseEditarAllCodigoBarra();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
