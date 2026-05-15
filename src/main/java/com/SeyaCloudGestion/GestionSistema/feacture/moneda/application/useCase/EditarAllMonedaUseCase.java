package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestEditarAllMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseEditarAllMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.services.MonedaService;
import org.springframework.stereotype.Component;

@Component
public class EditarAllMonedaUseCase {
    private final MonedaService monedaService;

    public EditarAllMonedaUseCase(
            MonedaService monedaService
    ){
        this.monedaService = monedaService;
    }

    public ResponseEditarAllMoneda EditarAllMoneda(RequestEditarAllMoneda request) {
        try {

            if (request.getIdMoneda() == 0) {
                String mensajeError = "El id de la moneda es obligatoria.";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getDescripcion().isEmpty()) {
                String mensajeError = "La descripción no puede estar vacía";
                throw new IllegalArgumentException(mensajeError);
            }

            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            ResponseEditarAllMoneda response = monedaService.EditarAllMoneda(request, userId);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllMoneda response = new ResponseEditarAllMoneda();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar la moneda: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllMoneda response = new ResponseEditarAllMoneda();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
