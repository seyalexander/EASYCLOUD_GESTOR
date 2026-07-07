package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestDetalleUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestEditarEstadoUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseDetalleUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseEditarEstadoUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.services.UnidadMedidaService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionUnidadMedidaEstadoUseCase {
    private final UnidadMedidaService unidadMedidaService;
    private final DetalleUnidadMedidaUseCase detalleUnidadMedidaUseCase;

    public EdicionUnidadMedidaEstadoUseCase(UnidadMedidaService unidadMedidaService, DetalleUnidadMedidaUseCase detalleUnidadMedidaUseCase) {
        this.unidadMedidaService = unidadMedidaService;
        this.detalleUnidadMedidaUseCase = detalleUnidadMedidaUseCase;
    }

    public ResponseEditarEstadoUnidadMedida AnularUnidadMedida(Long idUnidadMedida) {
        try {
            //unidad medida
            ResponseDetalleUnidadMedida detalleBD= detalleUnidadMedidaUseCase.DetalleUnidadMedida(idUnidadMedida);
            if (!detalleBD.isExito() || detalleBD.getUnidadMedida() == null) {
                throw new IllegalArgumentException("La unidad de medida no existe.");
            }

            if (Objects.equals(detalleBD.getUnidadMedida().getEstado(), 0)) {
                throw new IllegalArgumentException("La unidad medida ya se encuentra anulada.");
            }

            RequestEditarEstadoUnidadMedida request = new RequestEditarEstadoUnidadMedida();
            request.setIdUnidadMedida(idUnidadMedida);
            ResponseEditarEstadoUnidadMedida response = unidadMedidaService.EditarEstadoUnidadMedida(request,0);;
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoUnidadMedida response = new ResponseEditarEstadoUnidadMedida();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al anular la unidad de medida: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoUnidadMedida response = new ResponseEditarEstadoUnidadMedida();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoUnidadMedida ActivarUnidadMedida(Long idUnidadMedida) {
        try {
            //unidad medida
            ResponseDetalleUnidadMedida detalleBD= detalleUnidadMedidaUseCase.DetalleUnidadMedida(idUnidadMedida);
            if (!detalleBD.isExito() || detalleBD.getUnidadMedida() == null) {
                throw new IllegalArgumentException("La unidad de medida no existe.");
            }

            if (Objects.equals(detalleBD.getUnidadMedida().getEstado(), 1)) {
                throw new IllegalArgumentException("La unidad medida ya se encuentra activada.");
            }

            RequestEditarEstadoUnidadMedida request = new RequestEditarEstadoUnidadMedida();
            request.setIdUnidadMedida(idUnidadMedida);

            ResponseEditarEstadoUnidadMedida response = unidadMedidaService.EditarEstadoUnidadMedida(request,1);;
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoUnidadMedida response = new ResponseEditarEstadoUnidadMedida();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al anular la unidad de medida: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoUnidadMedida response = new ResponseEditarEstadoUnidadMedida();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}