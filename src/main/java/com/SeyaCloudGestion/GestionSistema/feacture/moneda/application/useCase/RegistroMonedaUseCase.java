package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestListaMonedas;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestRegistroMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseListaMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseRegistroMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.services.MonedaService;
import org.springframework.stereotype.Component;

@Component
public class RegistroMonedaUseCase {
    private final MonedaService monedaService;

    public RegistroMonedaUseCase(
            MonedaService monedaService
    ){
        this.monedaService = monedaService;
    }

    public ResponseRegistroMoneda RegistrarMoneda(RequestRegistroMoneda request) {
        try {

            if (request.getDescripcion().isEmpty()) {
                String mensajeError = "La descripción no puede estar vacía";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getEsPrincipal() < 0 || request.getEsPrincipal() > 1) {
                String mensajeError = "El valor ingresado no es el correcto";
                throw new IllegalArgumentException(mensajeError);
            }



            RequestListaMonedas requestLista = new RequestListaMonedas();
            requestLista.setEstado(1);

            ResponseListaMoneda moneda = monedaService.ListaMoneda(requestLista);

            if (!moneda.isExito()) {
                throw new IllegalArgumentException("No se pudo obtener la lista de monedas");
            }

            if (moneda.getMonedas().isEmpty()) {
                request.setEsPrincipal(1);
            }

            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            ResponseRegistroMoneda response = monedaService.RegistrarMoneda(request, userId);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroMoneda response = new ResponseRegistroMoneda();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar la moneda: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroMoneda response = new ResponseRegistroMoneda();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
