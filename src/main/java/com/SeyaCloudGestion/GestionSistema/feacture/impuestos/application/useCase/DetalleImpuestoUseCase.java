package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestDetalleImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestEditarAllImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseDetalleImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.services.ImpuestoService;
import org.springframework.stereotype.Component;

@Component
public class DetalleImpuestoUseCase {
    private final ImpuestoService impuestoService;

    public DetalleImpuestoUseCase(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    public ResponseDetalleImpuesto DetalleImpuesto(long idImpuesto) {
        try {
            RequestDetalleImpuesto request = new RequestDetalleImpuesto();
            request.setIdImpuesto(idImpuesto);
            ResponseDetalleImpuesto response = impuestoService.DetalleImpuesto(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleImpuesto response = new ResponseDetalleImpuesto();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al obtener el detalle del impuesto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleImpuesto response = new ResponseDetalleImpuesto();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
