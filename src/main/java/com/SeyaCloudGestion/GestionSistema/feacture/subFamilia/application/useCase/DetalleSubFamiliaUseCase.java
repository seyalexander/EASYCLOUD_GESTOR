package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestDetalleSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseDetalleSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.services.SubFamiliaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleSubFamiliaUseCase {
    private final SubFamiliaService subFamiliaService;

    public DetalleSubFamiliaUseCase(
            SubFamiliaService subFamiliaService
    ){
        this.subFamiliaService = subFamiliaService;
    }

    public ResponseDetalleSubFamilia DetalleSubFamilia(long idSubFamilia) {
        try {
            RequestDetalleSubFamilia request = new RequestDetalleSubFamilia();
            request.setIdSubFamilia(idSubFamilia);
            ResponseDetalleSubFamilia response = subFamiliaService.DetalleSubFamilia(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleSubFamilia response = new ResponseDetalleSubFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al ver el detalle de la sub familia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleSubFamilia response = new ResponseDetalleSubFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
