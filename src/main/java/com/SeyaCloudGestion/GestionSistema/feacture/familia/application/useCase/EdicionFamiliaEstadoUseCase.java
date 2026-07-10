package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestEditarEstadoFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseEditarEstadoFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.services.FamiliaService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionFamiliaEstadoUseCase {

    private final FamiliaService familiaService;
    private final DetalleFamiliaUseCase detalleFamiliaUseCase;

    public EdicionFamiliaEstadoUseCase(
            FamiliaService familiaService, DetalleFamiliaUseCase detalleFamiliaUseCase
    ){
        this.familiaService = familiaService;
        this.detalleFamiliaUseCase = detalleFamiliaUseCase;
    }

    public ResponseEditarEstadoFamilia EdicionAnularFamilia(long idFamilia) {
        try {
            //traemos la familia
            ResponseDetalleFamilia detalleBD = detalleFamiliaUseCase.DetalleFamilia(idFamilia);

            if (!detalleBD.isExito() || detalleBD.getFamilia() == null) {
                throw new IllegalArgumentException("La familia no existe.");
            }

            //veiricamos que no tenga el mismo estado
            if (Objects.equals(detalleBD.getFamilia().getEstado(), 0)) {
                throw new IllegalArgumentException("La familia ya se encuentra anulada.");
            }

            RequestEditarEstadoFamilia request = new RequestEditarEstadoFamilia();
            request.setIdFamilia(idFamilia);

            ResponseEditarEstadoFamilia response = familiaService.EditarEstadoFamilia(request, 0);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoFamilia response = new ResponseEditarEstadoFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las familias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoFamilia response = new ResponseEditarEstadoFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoFamilia EdicionActivarFamilia(long idFamilia) {
        try {
            //traemos la familia
            ResponseDetalleFamilia detalleBD = detalleFamiliaUseCase.DetalleFamilia(idFamilia);

            if (!detalleBD.isExito() || detalleBD.getFamilia() == null) {
                throw new IllegalArgumentException("La familia no existe.");
            }

            //veiricamos que no tenga el mismo estado
            if (Objects.equals(detalleBD.getFamilia().getEstado(), 1)) {
                throw new IllegalArgumentException("La familia ya se encuentra activada.");
            }

            RequestEditarEstadoFamilia request = new  RequestEditarEstadoFamilia();
            request.setIdFamilia(idFamilia);

            ResponseEditarEstadoFamilia response = familiaService.EditarEstadoFamilia(request, 1);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoFamilia response = new ResponseEditarEstadoFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las familias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoFamilia response = new ResponseEditarEstadoFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
