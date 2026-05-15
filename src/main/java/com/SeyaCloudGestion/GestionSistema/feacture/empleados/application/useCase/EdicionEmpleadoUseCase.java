package com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestEditarAllEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseEditarAllEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.services.EmpleadoService;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.validations.ValidacionRequest_EditarEmpleados;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.services.TipoDocumentoService;
import com.SeyaCloudGestion.GestionSistema.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EdicionEmpleadoUseCase {

    private final EmpleadoService empleadoService;
    private final TipoDocumentoService tipoDocumentoService;

    public EdicionEmpleadoUseCase(
            EmpleadoService empleadoService,
            TipoDocumentoService tipoDocumentoService
    ){
        this.empleadoService = empleadoService;
        this.tipoDocumentoService = tipoDocumentoService;
    }

    public ResponseEditarAllEmpleado EditarEmpleado(RequestEditarAllEmpleado request) {
        try {
            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;

            RequestDetalleTipoDocumento requestTipoDocumento = new RequestDetalleTipoDocumento();
            requestTipoDocumento.setIdTipoDocumentos(request.getIdTipoDocumento());

            ResponseDetalleTipoDocumento responseTipoDocumento =
                    tipoDocumentoService.DetalleTipoDocumento(requestTipoDocumento);

            ValidacionRequest_EditarEmpleados.validarEdicionEmpleado(request, responseTipoDocumento);

            return empleadoService.EditarAllEmpleado(request, userId);

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseEditarAllEmpleado response = new ResponseEditarAllEmpleado();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al editar el empleado: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllEmpleado response = new ResponseEditarAllEmpleado();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}