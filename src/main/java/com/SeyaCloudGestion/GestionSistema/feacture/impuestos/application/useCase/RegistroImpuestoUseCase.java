package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestRegistroImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseRegistroImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.services.ImpuestoService;
import org.springframework.stereotype.Component;

@Component
public class RegistroImpuestoUseCase {
    private final ImpuestoService impuestoService;

    public RegistroImpuestoUseCase(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }
    public ResponseRegistroImpuesto RegistroImpuesto(RequestRegistroImpuesto request) {
        try {
            ResponseRegistroImpuesto response = impuestoService.RegistroImpuesto(request);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroImpuesto response = new ResponseRegistroImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar el impuesto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroImpuesto response = new ResponseRegistroImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
