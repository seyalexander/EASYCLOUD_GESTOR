package com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestEditarEstadoEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseEditarEstadoEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.services.EmpleadoService;
import com.SeyaCloudGestion.GestionSistema.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EdicionEmpleadoEstadoUseCase {
    private final EmpleadoService empleadoService;

    public EdicionEmpleadoEstadoUseCase(
            EmpleadoService empleadoService
    ) {
        this.empleadoService = empleadoService;
    }

    public ResponseEditarEstadoEmpleado AnularEmpleado(long idEmpleado) {
        try {

            // Obtener idUsuario del token
            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            // VALIDACIONES
            if (idEmpleado == 0) {
                throw new IllegalArgumentException("El código del empleado es obligatorio.");
            }

            if (idEmpleado < 0) {
                throw new IllegalArgumentException("Código de empleado no válido.");
            }

            RequestEditarEstadoEmpleado request = new RequestEditarEstadoEmpleado();
            request.setIdEmpleado(idEmpleado);

            ResponseEditarEstadoEmpleado response =
                    empleadoService.EditarEstadoEmpleado(request, 0, userId);

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseEditarEstadoEmpleado response = new ResponseEditarEstadoEmpleado();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al anular al empleado: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoEmpleado response = new ResponseEditarEstadoEmpleado();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoEmpleado ActivarEmpleado(long idEmpleado) {
        try {
            // Obtener idUsuario del token
            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            // VALIDACIONES
            if (idEmpleado == 0) {
                throw new IllegalArgumentException("El código del empleado es obligatorio.");
            }

            if (idEmpleado < 0) {
                throw new IllegalArgumentException("Código de empleado no válido.");
            }

            RequestEditarEstadoEmpleado request = new RequestEditarEstadoEmpleado();
            request.setIdEmpleado(idEmpleado);
            ResponseEditarEstadoEmpleado response = empleadoService.EditarEstadoEmpleado(request, 1, userId);
            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoEmpleado response = new ResponseEditarEstadoEmpleado();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al activar al empleado: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoEmpleado response = new ResponseEditarEstadoEmpleado();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;

        }
    }
}
