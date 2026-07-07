package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestEditarEstadoSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseDetalleSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseEditarEstadoSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.services.SubFamiliaService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionSubFamiliaEstadoUseCase {

    private final SubFamiliaService subFamiliaService;
    private final DetalleSubFamiliaUseCase detalleSubFamiliaUseCase;

    public EdicionSubFamiliaEstadoUseCase(
            SubFamiliaService subFamiliaService, DetalleSubFamiliaUseCase detalleSubFamiliaUseCase
    ){
        this.subFamiliaService = subFamiliaService;
        this.detalleSubFamiliaUseCase = detalleSubFamiliaUseCase;
    }

    public ResponseEditarEstadoSubFamilia AnularSubFamilia(long idSubFamilia) {
        try {
            //sub familia
            ResponseDetalleSubFamilia detalleBD= detalleSubFamiliaUseCase.DetalleSubFamilia(idSubFamilia);

            if (!detalleBD.isExito() || detalleBD.getSubFamilia() == null) {
                throw new IllegalArgumentException("La subfamilia no existe.");
            }

            if (Objects.equals(detalleBD.getSubFamilia().getEstado(), 0)) {
                throw new IllegalArgumentException("La sub familia ya se encuentra anulada.");
            }

            RequestEditarEstadoSubFamilia request = new RequestEditarEstadoSubFamilia();
            request.setIdSubFamilia(idSubFamilia);

            ResponseEditarEstadoSubFamilia response = subFamiliaService.EditarEstadoSubFamilia(request, 0);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoSubFamilia response = new ResponseEditarEstadoSubFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar la sub familia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoSubFamilia response = new ResponseEditarEstadoSubFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoSubFamilia ActivarSubFamilia(long idSubFamilia) {
        try {
            //sub familia
            ResponseDetalleSubFamilia detalleBD= detalleSubFamiliaUseCase.DetalleSubFamilia(idSubFamilia);

            if (!detalleBD.isExito() || detalleBD.getSubFamilia() == null) {
                throw new IllegalArgumentException("La subfamilia no existe.");
            }

            if (Objects.equals(detalleBD.getSubFamilia().getEstado(), 1)) {
                throw new IllegalArgumentException("La sub familia ya se encuentra activada.");
            }

            RequestEditarEstadoSubFamilia request = new RequestEditarEstadoSubFamilia();
            request.setIdSubFamilia(idSubFamilia);
            ResponseEditarEstadoSubFamilia response = subFamiliaService.EditarEstadoSubFamilia(request, 1);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoSubFamilia response = new ResponseEditarEstadoSubFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar la sub familia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoSubFamilia response = new ResponseEditarEstadoSubFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
