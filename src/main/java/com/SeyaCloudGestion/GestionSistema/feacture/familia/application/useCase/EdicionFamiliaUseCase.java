package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestEditarAllFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseEditarAllFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.services.FamiliaService;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.validations.VerificarCambiosFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.model.FamiliaModel;
import org.springframework.stereotype.Component;

@Component
public class EdicionFamiliaUseCase {

    private final FamiliaService familiaService;
    private final VerificarCambiosFamilia verificarCambiosFamilia;

    public EdicionFamiliaUseCase(
            FamiliaService familiaService,
            VerificarCambiosFamilia verificarCambiosFamilia
    ){
        this.familiaService = familiaService;
        this.verificarCambiosFamilia = verificarCambiosFamilia;
    }

    public ResponseEditarAllFamilia EdicionAllFamilia(RequestEditarAllFamilia request) {
        try {

            RequestDetalleFamilia requestDetalle = new RequestDetalleFamilia();
            requestDetalle.setIdFamilia(request.getIdFamilia());

            ResponseDetalleFamilia detalleBD= familiaService.DetalleFamilia(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getFamilia() == null) {
                throw new IllegalArgumentException("La familia no existe.");
            }

            if (!verificarCambiosFamilia.verificarCambios(detalleBD.getFamilia(), request)) {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllFamilia response = familiaService.EditarAllFamilia(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllFamilia response = new ResponseEditarAllFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las familias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllFamilia response = new ResponseEditarAllFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
