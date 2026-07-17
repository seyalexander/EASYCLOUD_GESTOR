package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestRegistrarAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestRegistrarFullAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseRegistroAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseRegistroFullAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.services.AjustesService;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.DetalleAlmacenUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.DetalleArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestEditarDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseEditarDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.model.DetalleInventarioModel;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.useCase.DetalleInventarioUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.EstadoInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseProcesarFullStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase.ProcesarFullMovimientoStockUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase.DetalleSotckUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase.DetallePorCodigoTipoMovimientoUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegistroFullAjusteUseCase {

    private final DetalleInventarioUseCase detalleInventarioUseCase;
    private final RegistroAjusteUseCase registroAjusteUseCase;
    private final ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase;
    private final DetallePorCodigoTipoMovimientoUseCase detallePorCodigoTipoMovimientoUseCase;
    public RegistroFullAjusteUseCase(DetalleInventarioUseCase detalleInventarioUseCase, RegistroAjusteUseCase registroAjusteUseCase, ProcesarFullMovimientoStockUseCase procesarFullMovimientoStockUseCase, DetallePorCodigoTipoMovimientoUseCase detallePorCodigoTipoMovimientoUseCase) {
        this.detalleInventarioUseCase = detalleInventarioUseCase;
        this.registroAjusteUseCase = registroAjusteUseCase;
        this.procesarFullMovimientoStockUseCase = procesarFullMovimientoStockUseCase;
        this.detallePorCodigoTipoMovimientoUseCase = detallePorCodigoTipoMovimientoUseCase;
    }

    @Transactional("sqlServerTransactionManager")
    public ResponseRegistroFullAjuste registroAjustes(long idInventarioCabecera, List<DetalleInventarioModel> detalles) {
        try {

            //get detalle cabezera
            ResponseDetalleInventario responseBDCabezera = detalleInventarioUseCase.DetalleInventario(idInventarioCabecera);
            if (!responseBDCabezera.isExito() || responseBDCabezera.getInventario() == null) {
                throw new IllegalArgumentException("El inventario no existe.");
            }
            EstadoInventario estado = responseBDCabezera.getInventario().getEstado();
            //verificar que ya etea contado
            if (estado.equals(EstadoInventario.ABIERTO)) {
                throw new IllegalArgumentException("Aun no se a realizado el conteo fisico no se puede ajustar");
            }
            //verificar que no estea cerrado
            if (estado.equals(EstadoInventario.AJUSTADO)) {
                throw new IllegalArgumentException("El inventario ya esta ajustado no se pueden editar los detalles");
            }

            long idAlmacen = responseBDCabezera.getInventario().getIdAlmacen();
            long idCabecera = responseBDCabezera.getInventario().getIdInventarioCabecera();

            List<RequestRegistrarAjuste> listaAjustesValidados = new ArrayList<>();
            List<RequestProcesarFullStock> listaMovimientosValidados = new ArrayList<>();

            for (DetalleInventarioModel item : detalles) {
                double diferencia = item.getDiferencia();

                if (diferencia == 0) {
                    continue;
                }

                //tabla Ajustes
                RequestRegistrarAjuste requestAjusteItem = new RequestRegistrarAjuste();
                requestAjusteItem.setIdArticulo(item.getIdArticulo());
                requestAjusteItem.setIdAlmacen(idAlmacen);
                requestAjusteItem.setCantidad(diferencia);
                requestAjusteItem.setMotivo("Ajuste de inventario N " + idCabecera);
                listaAjustesValidados.add(requestAjusteItem);

                // full movimiento sotck
                RequestProcesarFullStock requestStockCore = new RequestProcesarFullStock();
                requestStockCore.setIdArticulo(item.getIdArticulo());
                requestStockCore.setIdAlmacen(idAlmacen);

                if (diferencia > 0) {
                    requestStockCore.setTipoPrimitivo(TipoMovimientoKardex.INGRESO_AJUSTE);
                    ResponseDetalleTipoMovimiento reqCodigo =detallePorCodigoTipoMovimientoUseCase.DetalleTipoMovimiento(TipoMovimientoKardex.INGRESO_AJUSTE);
                    requestStockCore.setIdTipoMovimiento(reqCodigo.getTipoMovimiento().getIdTipoMovimiento());

                } else {
                    requestStockCore.setTipoPrimitivo(TipoMovimientoKardex.EGRESO_AJUSTE);
                    ResponseDetalleTipoMovimiento reqCodigo =detallePorCodigoTipoMovimientoUseCase.DetalleTipoMovimiento(TipoMovimientoKardex.EGRESO_AJUSTE);
                    requestStockCore.setIdTipoMovimiento(reqCodigo.getTipoMovimiento().getIdTipoMovimiento());
                }

                requestStockCore.setCantidad(Math.abs(diferencia));
                requestStockCore.setObservacion("Ajuste automático por cierre de Inventario N " + idCabecera);
                //se envia en 0 por que el procesar full stok lo jala dado que estos
                //solo son ajustes de inventario osea cosas que ya tenemos
                //los ajustes por inventario incial se hacen en otro modulo
                //asi que como son ajustes de inventario el costo se calcula con el kardex que ya tenemos
                requestStockCore.setCostoUnitario(0);

                listaMovimientosValidados.add(requestStockCore);
            }


            for (int i = 0; i < listaAjustesValidados.size(); i++) {
                RequestRegistrarAjuste reqAjuste = listaAjustesValidados.get(i);
                RequestProcesarFullStock reqStock = listaMovimientosValidados.get(i);

                //tabla Ajustes
                ResponseRegistroAjuste responseRegistoItem = registroAjusteUseCase.registroAjustes(reqAjuste);
                if (!responseRegistoItem.isExito()) {
                    throw new IllegalArgumentException("Error al registrar el documento de Ajuste para el artículo ID " + reqAjuste.getIdArticulo() + ": " + responseRegistoItem.getMessage());
                }

                // procesar full stock
                ResponseProcesarFullStock responseMovReal = procesarFullMovimientoStockUseCase.procesar(reqStock);
                if (!responseMovReal.isExito()) {
                    throw new IllegalArgumentException("Error al actualizar Stock/Kardex para el artículo ID " + reqStock.getIdArticulo() + ": " + responseMovReal.getMessage());
                }
            }

            ResponseRegistroFullAjuste response = new ResponseRegistroFullAjuste();
            response.setExito(true);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroFullAjuste response = new ResponseRegistroFullAjuste();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el ajuste de inventario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroFullAjuste response = new ResponseRegistroFullAjuste();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}