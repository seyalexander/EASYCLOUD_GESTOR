package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestDetalleUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestEditarAllUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseDetalleUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseEditarAllUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.services.UnidadMedidaService;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.validations.VerificarCambiosUnidadMedida;
import org.springframework.stereotype.Component;

@Component
public class EdicionUnidadMedidaUseCase {
    private final UnidadMedidaService unidadMedidaService;
    private final VerificarCambiosUnidadMedida verificarCambiosUnidadMedida;
    private final DetalleUnidadMedidaUseCase detalleUnidadMedidaUseCase;

    public EdicionUnidadMedidaUseCase(UnidadMedidaService unidadMedidaService, VerificarCambiosUnidadMedida verificarCambiosUnidadMedida, DetalleUnidadMedidaUseCase detalleUnidadMedidaUseCase) {
        this.unidadMedidaService = unidadMedidaService;
        this.verificarCambiosUnidadMedida = verificarCambiosUnidadMedida;
        this.detalleUnidadMedidaUseCase = detalleUnidadMedidaUseCase;
    }

    public ResponseEditarAllUnidadMedida EdicionAllUnidadMedida(RequestEditarAllUnidadMedida request) {
        try {
            //unidad medida
            ResponseDetalleUnidadMedida detalleBD= detalleUnidadMedidaUseCase.DetalleUnidadMedida(request.getIdUnidadMedida());
            if (!detalleBD.isExito() || detalleBD.getUnidadMedida() == null) {
                throw new IllegalArgumentException("La unidad de medida no existe.");
            }

            if (!verificarCambiosUnidadMedida.verificarCambios(detalleBD.getUnidadMedida(), request)) {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllUnidadMedida response = unidadMedidaService.EditarAllUnidadMedida(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllUnidadMedida response = new ResponseEditarAllUnidadMedida();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar la unidad de medida: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllUnidadMedida response = new ResponseEditarAllUnidadMedida();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}