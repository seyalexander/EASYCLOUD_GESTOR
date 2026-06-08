package com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestDetalleParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseDetalleParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.services.ParametrosService;
import org.springframework.stereotype.Component;

@Component
public class DetalleParametrosUseCase {
    private final ParametrosService parametrosService;


    public DetalleParametrosUseCase(ParametrosService parametrosService) {
        this.parametrosService = parametrosService;
    }
    public ResponseDetalleParametros DetalleParametro(long idParametroSistema) {
        try {
            RequestDetalleParametros request = new RequestDetalleParametros();
            request.setIdParametros(idParametroSistema);

            ResponseDetalleParametros response = parametrosService.DetalleParametros(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseDetalleParametros response = new ResponseDetalleParametros();
            response.setExito(false);
            response.setMessage(e.getMessage());

            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al ver el detalle del parámetro: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleParametros response = new ResponseDetalleParametros();
            response.setExito(false);
            response.setMessage(mensajeError);

            return response;
        }
    }
}