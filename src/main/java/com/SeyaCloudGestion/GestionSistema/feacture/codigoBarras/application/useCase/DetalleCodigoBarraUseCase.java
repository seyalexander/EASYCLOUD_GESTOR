package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestDetalleCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseDetalleCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.services.CodigoBarraService;
import org.springframework.stereotype.Component;

@Component
public class DetalleCodigoBarraUseCase {
    private  final CodigoBarraService codigoBarraService;

    public DetalleCodigoBarraUseCase(CodigoBarraService codigoBarraService) {
        this.codigoBarraService = codigoBarraService;
    }
    public ResponseDetalleCodigoBarra DetalleCodigoBarra(long idFamilia) {
        try {
            RequestDetalleCodigoBarra request = new RequestDetalleCodigoBarra();
            request.setIdCodigoBarra(idFamilia);
            ResponseDetalleCodigoBarra response = codigoBarraService.DetalleCodigoBarra(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleCodigoBarra response = new ResponseDetalleCodigoBarra();
            response.setExito(false);
            response.setMessage(e.getMessage() );
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las familias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleCodigoBarra response = new ResponseDetalleCodigoBarra();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
