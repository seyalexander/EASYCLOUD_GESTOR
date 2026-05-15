package com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestDetalleEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseDetalleEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.services.EmpresaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleEmpresaUseCase {
    private final EmpresaService empresaService;

    public DetalleEmpresaUseCase(
            EmpresaService empresaService
    ){
        this.empresaService = empresaService;
    }

    public ResponseDetalleEmpresa DetalleEmpresa(long idEmpresa) {
        try {
            RequestDetalleEmpresa request = new RequestDetalleEmpresa();
            request.setIdEmpresa(idEmpresa);
            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            ResponseDetalleEmpresa response = empresaService.DetalleEmpresa(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleEmpresa response = new ResponseDetalleEmpresa();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setEmpresa(null);
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar la empresa: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleEmpresa response = new ResponseDetalleEmpresa();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setEmpresa(null);
            return response;
        }
    }
}
