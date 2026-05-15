package com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestListaEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseListaEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.services.EmpleadoService;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.validations.ValidacionRequest_ListarEmpleados;
import org.springframework.stereotype.Component;

@Component
public class ListaEmpleadoUseCase {
    private final EmpleadoService empleadoService;

    public ListaEmpleadoUseCase(
            EmpleadoService empleadoService
    ){
        this.empleadoService = empleadoService;
    }

    public ResponseListaEmpleado ListarEmpleado(RequestListaEmpleado request) {
        try {
            ValidacionRequest_ListarEmpleados.validarListarEmpleado(request);
            ResponseListaEmpleado response = empleadoService.ListaEmpleado(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaEmpleado response = new ResponseListaEmpleado();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setEmpleados(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar los empleados: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaEmpleado response = new ResponseListaEmpleado();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setEmpleados(java.util.List.of());
            return response;
        }
    }
}
