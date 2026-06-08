package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestRegistroCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseRegistroCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.services.CodigoBarraService;
import org.springframework.stereotype.Component;

@Component
public class RegistroCodigoBarraUseCase {
    private  final CodigoBarraService codigoBarraService;

    public RegistroCodigoBarraUseCase(CodigoBarraService codigoBarraService) {
        this.codigoBarraService = codigoBarraService;
    }
    public ResponseRegistroCodigoBarra RegistroCodigoBarra(RequestRegistroCodigoBarra request) {
        try {
            ResponseRegistroCodigoBarra response = codigoBarraService.RegistroCodigoBarra(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroCodigoBarra response = new ResponseRegistroCodigoBarra();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el código de barra: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroCodigoBarra response = new ResponseRegistroCodigoBarra();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
