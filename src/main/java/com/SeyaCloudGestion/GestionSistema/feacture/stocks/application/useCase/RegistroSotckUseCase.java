package com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.services.AlmacenService;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import org.springframework.stereotype.Component;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestRegistroSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseRegistroSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.services.SotckService;
import org.springframework.stereotype.Component;
@Component
public class RegistroSotckUseCase {

    private final SotckService sotckService;
    private final ArticulosService articulosService;
    private final AlmacenService almacenesService;

    public RegistroSotckUseCase(SotckService sotckService, ArticulosService articulosService, AlmacenService almacenesService) {
        this.sotckService = sotckService;
        this.articulosService = articulosService;
        this.almacenesService = almacenesService;
    }

    public ResponseRegistroSotck RegistroSotck(RequestRegistroSotck request) {
        try {
            //producto
            RequestDetalleArticulo requestDetallePro = new RequestDetalleArticulo();
            requestDetallePro.setIdArticulo(request.getIdProducto());

            ResponseDetalleArticulo detalleBDPro= articulosService.DetalleArticulos(requestDetallePro);

            if (!detalleBDPro.isExito() || detalleBDPro.getArticulos() == null) {
                throw new IllegalArgumentException("El producto no existe.");
            }
            //almacecn
            RequestDetalleAlmacen requestDetalleAlm = new RequestDetalleAlmacen();
            requestDetalleAlm.setIdAlmacen(request.getIdAlmacen());

            ResponseDetalleAlmacen detalleBD= almacenesService.DetalleAlmacen(requestDetalleAlm);

            if (!detalleBD.isExito() || detalleBD.getAlmacen() == null) {
                throw new ResourceNotFoundException("El almacen no existe.");
            }

            ResponseRegistroSotck response = sotckService.RegistroSotck(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroSotck response = new ResponseRegistroSotck();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el stock: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroSotck response = new ResponseRegistroSotck();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}