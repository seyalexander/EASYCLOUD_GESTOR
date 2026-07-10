package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.useCase.DetalleFamiliaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestDetalleSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseDetalleSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.services.SubFamiliaService;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.validations.VerificarCambiosSubFamilia;
import org.springframework.stereotype.Component;

@Component
public class EdicionSubFamiliaAllUseCase {
    private final SubFamiliaService subFamiliaService;
    private final VerificarCambiosSubFamilia verificarCambiosSubFamilia;
    private final DetalleFamiliaUseCase detalleFamiliaUseCase;
    private final DetalleSubFamiliaUseCase detalleSubFamiliaUseCase;

    public EdicionSubFamiliaAllUseCase(
            SubFamiliaService subFamiliaService, VerificarCambiosSubFamilia verificarCambiosSubFamilia, DetalleFamiliaUseCase detalleFamiliaUseCase, DetalleSubFamiliaUseCase detalleSubFamiliaUseCase
    ){
        this.subFamiliaService = subFamiliaService;
        this.verificarCambiosSubFamilia = verificarCambiosSubFamilia;
        this.detalleFamiliaUseCase = detalleFamiliaUseCase;
        this.detalleSubFamiliaUseCase = detalleSubFamiliaUseCase;
    }

    public ResponseEditarAllSubFamilia EdicionAllFamilia(RequestEditarAllSubFamilia request) {
        try {
            //sub familia
            ResponseDetalleSubFamilia detalleBD= detalleSubFamiliaUseCase.DetalleSubFamilia(request.getIdSubFamilia());

            if (!detalleBD.isExito() || detalleBD.getSubFamilia() == null) {
                throw new IllegalArgumentException("La subfamilia no existe.");
            }
            //verificamos el id familia
            ResponseDetalleFamilia detalleBDFamilia= detalleFamiliaUseCase.DetalleFamilia(request.getIdFamilia());

            if (!detalleBDFamilia.isExito() || detalleBDFamilia.getFamilia() == null) {
                throw new IllegalArgumentException("El id familia no existe.");
            }

            //verificar cambios
            if (!verificarCambiosSubFamilia.verificarCambios(detalleBD.getSubFamilia(), request))  {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllSubFamilia response = subFamiliaService.EdicionAllSubFamilia(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllSubFamilia response = new ResponseEditarAllSubFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar la sub familia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllSubFamilia response = new ResponseEditarAllSubFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
