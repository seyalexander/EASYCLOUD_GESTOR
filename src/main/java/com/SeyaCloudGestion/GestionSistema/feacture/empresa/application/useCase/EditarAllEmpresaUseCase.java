package com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestEditarAllEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestEditarEstadoEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseEditarAllEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseEditarEstadoEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.services.EmpresaService;
import org.springframework.stereotype.Component;

@Component
public class EditarAllEmpresaUseCase {
    private final EmpresaService empresaService;

    public EditarAllEmpresaUseCase(
            EmpresaService empresaService
    ){
        this.empresaService = empresaService;
    }

    public ResponseEditarAllEmpresa EditarEmpresa(RequestEditarAllEmpresa request) {
        try {
            // VALIDACIÓN DE CAMPOS
            if (request == null) {
                String mensajeError = "No se encontró datos para registrar";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getRazonSocial() == null || request.getRazonSocial().isEmpty()) {
                String mensajeError = "La razón social de la empresa no puede estar vacía.";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getRuc() == null || request.getRuc().trim().isEmpty()) {
                String mensajeError = "El RUC de la empresa no puede estar vacía.";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getRuc().trim().length() != 11) {
                String mensajeError = "El RUC debe tener 11 dígitos";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getTelefono().trim().length() != 9) {
                String mensajeError = "El teléfono debe tener 9 dígitos";
                throw new IllegalArgumentException(mensajeError);
            }

            if ( !request.getEmail().trim().isEmpty() && !request.getEmail().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new IllegalArgumentException("El email no tiene un formato válido.");
            }

            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            ResponseEditarAllEmpresa response = empresaService.editarAllEmpresa(request, userId);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllEmpresa response = new ResponseEditarAllEmpresa();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar la empresa: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllEmpresa response = new ResponseEditarAllEmpresa();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
