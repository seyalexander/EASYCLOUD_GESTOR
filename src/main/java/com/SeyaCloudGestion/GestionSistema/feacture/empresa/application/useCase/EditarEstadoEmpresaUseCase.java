package com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestEditarEstadoEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseEditarEstadoEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.services.EmpresaService;
import org.springframework.stereotype.Component;

@Component
public class EditarEstadoEmpresaUseCase {
    private final EmpresaService empresaService;

    public EditarEstadoEmpresaUseCase(
            EmpresaService empresaService
    ){
        this.empresaService = empresaService;
    }

    public ResponseEditarEstadoEmpresa AnularEmpresa(long idEmpresa) {
        try {

            RequestEditarEstadoEmpresa request = new  RequestEditarEstadoEmpresa();
            request.setIdEmpresa(idEmpresa);

            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            ResponseEditarEstadoEmpresa response = empresaService.editarEstadoEmpresa(request, 0, userId);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoEmpresa response = new ResponseEditarEstadoEmpresa();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar la empresa: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoEmpresa response = new ResponseEditarEstadoEmpresa();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoEmpresa ActivarEmpresa(long idEmpresa) {
        try {

            RequestEditarEstadoEmpresa request = new  RequestEditarEstadoEmpresa();
            request.setIdEmpresa(idEmpresa);

            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            ResponseEditarEstadoEmpresa response = empresaService.editarEstadoEmpresa(request, 1, userId);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoEmpresa response = new ResponseEditarEstadoEmpresa();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar la empresa: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoEmpresa response = new ResponseEditarEstadoEmpresa();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
