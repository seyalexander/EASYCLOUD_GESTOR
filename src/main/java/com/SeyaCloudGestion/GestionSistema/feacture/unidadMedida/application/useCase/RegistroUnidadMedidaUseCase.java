package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestRegistroUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseRegistroUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.services.UnidadMedidaService;
import org.springframework.stereotype.Component;

@Component
public class RegistroUnidadMedidaUseCase {
    private final UnidadMedidaService unidadMedidaService;

    public RegistroUnidadMedidaUseCase(UnidadMedidaService unidadMedidaService) {
        this.unidadMedidaService = unidadMedidaService;
    }

    public ResponseRegistroUnidadMedida RegistroUnidadMedida(RequestRegistroUnidadMedida request) {
        try {

            ResponseRegistroUnidadMedida response = unidadMedidaService.RegistroUnidadMedida(request);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroUnidadMedida response = new ResponseRegistroUnidadMedida();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar la unidad de medida: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroUnidadMedida response = new ResponseRegistroUnidadMedida();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}