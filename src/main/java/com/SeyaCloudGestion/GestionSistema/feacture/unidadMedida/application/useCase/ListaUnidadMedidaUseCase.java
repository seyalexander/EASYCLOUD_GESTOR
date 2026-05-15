package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestListaUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseListaUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.services.UnidadMedidaService;
import org.springframework.stereotype.Component;

@Component
public class ListaUnidadMedidaUseCase {

    private final UnidadMedidaService unidadMedidaService;

    public ListaUnidadMedidaUseCase(
            UnidadMedidaService unidadMedidaService
    ){
        this.unidadMedidaService = unidadMedidaService;
    }

    public ResponseListaUnidadMedida listaUnidadMedida(RequestListaUnidadMedida request) {
        try {
            ResponseListaUnidadMedida response = unidadMedidaService.listaUnidadMedida(request);
            if(response.isExito()){

            }

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaUnidadMedida response = new ResponseListaUnidadMedida();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setUnidadesMedida(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las unidades de medida: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaUnidadMedida response = new ResponseListaUnidadMedida();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setUnidadesMedida(java.util.List.of());
            return response;
        }
    }
}
