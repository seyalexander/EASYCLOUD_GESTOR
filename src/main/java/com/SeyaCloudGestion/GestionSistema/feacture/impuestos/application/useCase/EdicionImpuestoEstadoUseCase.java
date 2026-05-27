package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestEditarEstadoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseEditarAllImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseEditarEstadoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.services.ImpuestoService;
import org.springframework.stereotype.Component;

@Component
public class EdicionImpuestoEstadoUseCase {
    private final ImpuestoService impuestoService;

    public EdicionImpuestoEstadoUseCase(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    public ResponseEditarEstadoImpuesto AnularImpuesto(long idImpuesto) {
        try {
            RequestEditarEstadoImpuesto request = new RequestEditarEstadoImpuesto();
            request.setIdImpuesto(idImpuesto);
            ResponseEditarEstadoImpuesto response = impuestoService.EditarEstadoImpuesto(request, 0);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoImpuesto response = new ResponseEditarEstadoImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar el estado del impuesto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoImpuesto response = new ResponseEditarEstadoImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoImpuesto ActivarImpuesto(long idImpuesto) {
        try {
            RequestEditarEstadoImpuesto request = new RequestEditarEstadoImpuesto();
            request.setIdImpuesto(idImpuesto);
            ResponseEditarEstadoImpuesto response = impuestoService.EditarEstadoImpuesto(request, 1);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoImpuesto response = new ResponseEditarEstadoImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar el estado del impuesto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoImpuesto response = new ResponseEditarEstadoImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
