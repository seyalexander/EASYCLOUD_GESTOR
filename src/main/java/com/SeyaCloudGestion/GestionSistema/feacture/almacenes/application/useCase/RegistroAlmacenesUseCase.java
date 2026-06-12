package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestRegistroAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseRegistroAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenesService;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.validations.ValidacionRequest_RegistrarAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.services.SucursalesService;
import org.springframework.stereotype.Component;

@Component
public class RegistroAlmacenesUseCase {
    private final AlmacenesService almacenesService;
    private final SucursalesService sucursalesService;
    public RegistroAlmacenesUseCase(AlmacenesService almacenesService, SucursalesService sucursalesService) {
        this.almacenesService = almacenesService;
        this.sucursalesService = sucursalesService;
    }
    public ResponseRegistroAlmacenes RegistroAlmacenes(RequestRegistroAlmacenes request) {
        try {
            //get suscursal
            RequestDetalleSucursales requestSuc = new RequestDetalleSucursales();
            requestSuc.setIdSucursales(request.getIdSucursales());
            ResponseDetalleSucursales detalleBDsuc = sucursalesService.DetalleSucursales(requestSuc);

            ValidacionRequest_RegistrarAlmacen.validarRegistro(detalleBDsuc);

            ResponseRegistroAlmacenes response = almacenesService.RegistroAlmacenes(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroAlmacenes response = new ResponseRegistroAlmacenes();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroAlmacenes response = new ResponseRegistroAlmacenes();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}