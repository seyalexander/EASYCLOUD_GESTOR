package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestListaCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestListaCuentasPorCobrarIDCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseListaCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.services.CuentasPorCobrarService;
import org.springframework.stereotype.Component;

@Component
public class ListaCuentasPorCobrarUseCase {
    private final CuentasPorCobrarService cuentasPorCobrarService;

    public ListaCuentasPorCobrarUseCase(CuentasPorCobrarService cuentasPorCobrarService) {
        this.cuentasPorCobrarService = cuentasPorCobrarService;
    }
    public ResponseListaCuentasPorCobrar ListaCuentasPorCobrar(RequestListaCuentasPorCobrar request) {
        try {
            ResponseListaCuentasPorCobrar response = cuentasPorCobrarService.ListaCuentasPorCobrar(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaCuentasPorCobrar response = new ResponseListaCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setCuentasPorCobrares(java.util.List.of());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar las cuentas por cobrar: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaCuentasPorCobrar response = new ResponseListaCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setCuentasPorCobrares(java.util.List.of());
            return response;
        }
    }
    public ResponseListaCuentasPorCobrar ListaCuentasPorCobrarIDCliente(RequestListaCuentasPorCobrarIDCliente request) {
        try {
            ResponseListaCuentasPorCobrar response = cuentasPorCobrarService.ListaCuentasPorCobrarIDCliente(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaCuentasPorCobrar response = new ResponseListaCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setCuentasPorCobrares(java.util.List.of());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar las cuentas por cobrar: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaCuentasPorCobrar response = new ResponseListaCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setCuentasPorCobrares(java.util.List.of());
            return response;
        }
    }
}