package com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestRegistroEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseRegistroEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.services.EmpleadoService;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.validations.EmpleadoValidator;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.validations.ValidacionRequest_RegistrarEmpleados;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.services.TipoDocumentoService;
import com.SeyaCloudGestion.GestionSistema.utils.SecurityUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class RegistroEmpleadoUseCase {
    private final EmpleadoService empleadoService;
    private final TipoDocumentoService tipoDocumentoService;

    public RegistroEmpleadoUseCase(
            EmpleadoService empleadoService,
            TipoDocumentoService tipoDocumentoService
    ){
        this.empleadoService = empleadoService;
        this.tipoDocumentoService= tipoDocumentoService;
    }

    public ResponseRegistroEmpleado RegistrorEmpleado(RequestRegistroEmpleado request) {
        try {
            // Obtener idUsuario del token
            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;
            long idEmpresa = 1L;

            RequestDetalleTipoDocumento requestTipoDocumento = new RequestDetalleTipoDocumento();
            requestTipoDocumento.setIdTipoDocumentos(request.getIdTipoDocumento());

            ResponseDetalleTipoDocumento responseTipoDocumento =
                    tipoDocumentoService.DetalleTipoDocumento(requestTipoDocumento);
            ValidacionRequest_RegistrarEmpleados.validarRegistroEmpleado(request, responseTipoDocumento);

            ResponseRegistroEmpleado response = empleadoService.RegistroEmpleado(request,userId, idEmpresa);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroEmpleado response = new ResponseRegistroEmpleado();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar un empleado: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroEmpleado response = new ResponseRegistroEmpleado();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
