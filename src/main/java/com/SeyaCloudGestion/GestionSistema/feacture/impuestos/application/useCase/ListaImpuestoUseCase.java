package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestListaImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseListaImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.services.ImpuestoService;
import org.springframework.stereotype.Component;

@Component
public class ListaImpuestoUseCase {
    private final ImpuestoService impuestoService;

    public ListaImpuestoUseCase(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    public ResponseListaImpuesto ListaImpuesto (RequestListaImpuesto request) {
        try {
            ResponseListaImpuesto response = impuestoService.listaImpuesto(request);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaImpuesto response = new ResponseListaImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setImpuestos(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al obtener la lista de impuestos: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaImpuesto response = new ResponseListaImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setImpuestos(java.util.List.of());
            return response;
        }
    }
}
