package com.SeyaCloudGestion.GestionSistema.feacture.marca.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestListaMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseListaMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.services.MarcaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleMarcaUseCase {
    private final MarcaService marcaService;

    public DetalleMarcaUseCase(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    public ResponseDetalleMarca detalleMarcas(long idMarca) {
        try {
            RequestDetalleMarca request = new RequestDetalleMarca();
            request.setIdMarca(idMarca);
            ResponseDetalleMarca response = marcaService.DetalleMarca(request);
            if(response.isExito()){

            }

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleMarca response = new ResponseDetalleMarca();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setMarca(null);
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al obtener detalle las marca: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleMarca response = new ResponseDetalleMarca();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setMarca(null);
            return response;
        }
    }
}
