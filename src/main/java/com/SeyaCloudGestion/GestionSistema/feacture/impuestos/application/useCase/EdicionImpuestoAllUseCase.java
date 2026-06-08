package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestEditarAllImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseEditarAllImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.services.ImpuestoService;
import org.springframework.stereotype.Component;

@Component
public class EdicionImpuestoAllUseCase {
    private final ImpuestoService impuestoService;

    public EdicionImpuestoAllUseCase(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    public ResponseEditarAllImpuesto EditarAllImpuesto(RequestEditarAllImpuesto request) {
        try {
            ResponseEditarAllImpuesto response = impuestoService.EditarAllImpuesto(request);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllImpuesto response = new ResponseEditarAllImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar el impuesto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllImpuesto response = new ResponseEditarAllImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
