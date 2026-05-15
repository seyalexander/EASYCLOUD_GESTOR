package com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestDetalleEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseDetalleEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.services.EmpleadoService;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.validations.ValdiacionRequest_DetalleEmpleados;
import org.springframework.stereotype.Component;

@Component
public class DetalleEmpleadoUseCase {
    private final EmpleadoService empleadoService;

    public DetalleEmpleadoUseCase(
            EmpleadoService empleadoService
    ){
        this.empleadoService = empleadoService;
    }

    public ResponseDetalleEmpleado DetalleEmpleado(long idEmpleado) {
        try {
            RequestDetalleEmpleado request = new RequestDetalleEmpleado();
            request.setIdEmpleado(idEmpleado);
            ValdiacionRequest_DetalleEmpleados.validarDetalleEmpleado(request);
            ResponseDetalleEmpleado response = empleadoService.DetalleEmpleado(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleEmpleado response = new ResponseDetalleEmpleado();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setEmpleado(null);
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al obtener el detalle del empleado: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleEmpleado response = new ResponseDetalleEmpleado();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setEmpleado(null);
            return response;
        }
    }
}
