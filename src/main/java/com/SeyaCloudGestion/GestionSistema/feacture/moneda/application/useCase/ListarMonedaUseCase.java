package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestListaMonedas;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseListaMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.services.MonedaService;
import org.springframework.stereotype.Component;

@Component
public class ListarMonedaUseCase {
    private final MonedaService monedaService;

    public ListarMonedaUseCase(
            MonedaService monedaService
    ){
        this.monedaService = monedaService;
    }

    public ResponseListaMoneda ListaMoneda(RequestListaMonedas request) {
        try {

            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            ResponseListaMoneda response = monedaService.ListaMoneda(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaMoneda response = new ResponseListaMoneda();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setMonedas(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las monedas: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaMoneda response = new ResponseListaMoneda();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setMonedas(java.util.List.of());
            return response;
        }
    }
}
