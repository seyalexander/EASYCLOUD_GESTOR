package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestRegistroAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseRegistroAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenService;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.validations.ValidacionRequest_RegistrarAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.services.SucursalesService;
import org.springframework.stereotype.Component;

@Component
public class RegistroAlmacenUseCase {
    private final AlmacenService almacenesService;
    private final SucursalesService sucursalesService;
    public RegistroAlmacenUseCase(AlmacenService almacenesService, SucursalesService sucursalesService) {
        this.almacenesService = almacenesService;
        this.sucursalesService = sucursalesService;
    }
    public ResponseRegistroAlmacen RegistroAlmacenes(RequestRegistroAlmacen request) {
        try {
            //get suscursal
            RequestDetalleSucursales requestSuc = new RequestDetalleSucursales();
            requestSuc.setIdSucursales(request.getIdSucursal());
            ResponseDetalleSucursales detalleBDsuc = sucursalesService.DetalleSucursales(requestSuc);

            ValidacionRequest_RegistrarAlmacen.validarRegistro(detalleBDsuc);

            ResponseRegistroAlmacen response = almacenesService.RegistroAlmacen(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroAlmacen response = new ResponseRegistroAlmacen();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el almacén: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroAlmacen response = new ResponseRegistroAlmacen();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}