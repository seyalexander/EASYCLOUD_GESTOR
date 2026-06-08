package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestDetalleUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseDetalleUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.services.UnidadMedidaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleUnidadMedidaUseCase {
    private final UnidadMedidaService unidadMedidaService;

    public DetalleUnidadMedidaUseCase(UnidadMedidaService unidadMedidaService) {
        this.unidadMedidaService = unidadMedidaService;
    }

    public ResponseDetalleUnidadMedida DetalleUnidadMedida(long idUnidadMedida) {
        try {
            RequestDetalleUnidadMedida request = new RequestDetalleUnidadMedida();
            request.setIdUnidadMedida(idUnidadMedida);
            ResponseDetalleUnidadMedida response = unidadMedidaService.DetalleUnidadMedida(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleUnidadMedida response = new ResponseDetalleUnidadMedida();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al ver el detalle de la unidad de medida: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleUnidadMedida response = new ResponseDetalleUnidadMedida();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}