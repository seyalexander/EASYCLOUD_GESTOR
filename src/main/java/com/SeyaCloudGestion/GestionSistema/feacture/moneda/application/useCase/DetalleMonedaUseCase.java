package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestDetalleMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseDetalleMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.services.MonedaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleMonedaUseCase {
    private final MonedaService monedaService;

    public DetalleMonedaUseCase(
            MonedaService monedaService
    ){
        this.monedaService = monedaService;
    }

    public ResponseDetalleMoneda DetalleMoneda(long idMoneda) {
        try {
            RequestDetalleMoneda request = new RequestDetalleMoneda();
            request.setIdMoneda(idMoneda);
            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            ResponseDetalleMoneda response = monedaService.DetalleMoneda(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleMoneda response = new ResponseDetalleMoneda();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar la moneda: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleMoneda response = new ResponseDetalleMoneda();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
