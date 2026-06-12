package com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestDetalleEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestEditarEstadoEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseDetalleEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseEditarEstadoEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.services.EmpresaService;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
            RequestDetalleEmpresa requestDetalle = new RequestDetalleEmpresa();
            requestDetalle.setIdEmpresa(idEmpresa);
            ResponseDetalleEmpresa detalleBD= empresaService.DetalleEmpresa(requestDetalle);
            if (!detalleBD.isExito() || detalleBD.getEmpresa() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getEmpresa().getEstado(), 0)) {
                throw new IllegalArgumentException("La Empresa ya se encuentra anulado.");
            }

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
            RequestDetalleEmpresa requestDetalle = new RequestDetalleEmpresa();
            requestDetalle.setIdEmpresa(idEmpresa);

            ResponseDetalleEmpresa detalleBD= empresaService.DetalleEmpresa(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getEmpresa() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getEmpresa().getEstado(), 1)) {
                throw new IllegalArgumentException("La Empresa ya se encuentra activada.");
            }

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
