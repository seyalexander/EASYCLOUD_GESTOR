package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.services.FamiliaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleFamiliaUseCase {

    private final FamiliaService familiaService;

    public DetalleFamiliaUseCase(
            FamiliaService familiaService
    ){
        this.familiaService = familiaService;
    }

    public ResponseDetalleFamilia DetalleFamilia(long idFamilia) {
        try {
            RequestDetalleFamilia request = new RequestDetalleFamilia();
            request.setIdFamilia(idFamilia);
            ResponseDetalleFamilia response = familiaService.DetalleFamilia(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleFamilia response = new ResponseDetalleFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las familias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleFamilia response = new ResponseDetalleFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

}
