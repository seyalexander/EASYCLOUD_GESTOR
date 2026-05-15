package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestEditarPredeterminadoMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseEditarPredeterminadoMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.services.MonedaService;
import org.springframework.stereotype.Component;

@Component
public class EditarPredeterminadoMonedaUseCase {
    private final MonedaService monedaService;

    public EditarPredeterminadoMonedaUseCase(
            MonedaService monedaService
    ){
        this.monedaService = monedaService;
    }

    public ResponseEditarPredeterminadoMoneda EditarPredeterminadoMoneda(long idMoneda) {
        try {
            RequestEditarPredeterminadoMoneda request = new RequestEditarPredeterminadoMoneda();
            request.setIdMoneda(idMoneda);
            //long userId = SecurityUtils.getCurrentUserId();
            //long empresaId = SecurityUtils.getCurrentUserId();
            long userId = 1L;
            long empresaId = 1L;

            ResponseEditarPredeterminadoMoneda response = monedaService.EditarPredetermiandoMoneda(request, userId, empresaId);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarPredeterminadoMoneda response = new ResponseEditarPredeterminadoMoneda();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar la moneda: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarPredeterminadoMoneda response = new ResponseEditarPredeterminadoMoneda();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
