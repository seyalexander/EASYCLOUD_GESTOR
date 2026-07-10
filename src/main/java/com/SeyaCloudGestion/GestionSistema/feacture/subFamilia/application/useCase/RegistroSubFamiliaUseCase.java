package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.useCase.DetalleFamiliaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.services.FamiliaService;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestRegistrarSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseRegistroSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.services.SubFamiliaService;
import org.springframework.stereotype.Component;

@Component
public class RegistroSubFamiliaUseCase {
    private final SubFamiliaService subFamiliaService;
    private final DetalleFamiliaUseCase detalleFamiliaUseCase;

    public RegistroSubFamiliaUseCase(
            SubFamiliaService subFamiliaService,
            DetalleFamiliaUseCase detalleFamiliaUseCase
    ){
        this.subFamiliaService = subFamiliaService;
        this.detalleFamiliaUseCase = detalleFamiliaUseCase;
    }

    public ResponseRegistroSubFamilia RegistroSubFamilia(RequestRegistrarSubFamilia request) {
        try {
            //verificamos el id familia
            ResponseDetalleFamilia detalleBD= detalleFamiliaUseCase.DetalleFamilia(request.getIdFamilia());

            if (!detalleBD.isExito() || detalleBD.getFamilia() == null) {
                throw new IllegalArgumentException("El id familia no existe.");
            }

            ResponseRegistroSubFamilia response = subFamiliaService.RegistroSubFamilia(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroSubFamilia response = new ResponseRegistroSubFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las subfamilias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroSubFamilia response = new ResponseRegistroSubFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
