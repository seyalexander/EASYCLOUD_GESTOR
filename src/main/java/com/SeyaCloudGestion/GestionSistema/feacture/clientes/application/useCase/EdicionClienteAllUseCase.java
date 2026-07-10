package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestDetalleCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseDetalleCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.services.ClienteService;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.validations.ClienteValidator;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.validations.VerificarCambiosCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestDetalleTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseDetalleTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.services.TipoClientesService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.services.TipoDocumentoService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.validations.dependencias.TipoDocumentoValidator;
import org.springframework.stereotype.Component;

@Component
public class EdicionClienteAllUseCase {
    private final ClienteService clienteService;
    private final TipoDocumentoService tipoDocumentoService;
    private final TipoClientesService tipoClientesService;
    private final VerificarCambiosCliente verificarCambiosCliente;
    public EdicionClienteAllUseCase(ClienteService clienteService, TipoDocumentoService tipoDocumentoService, TipoClientesService tipoClientesService, VerificarCambiosCliente verificarCambiosCliente) {
        this.clienteService = clienteService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.tipoClientesService = tipoClientesService;
        this.verificarCambiosCliente = verificarCambiosCliente;
    }

    public ResponseEditarAllCliente EdicionAllCliente(RequestEditarAllCliente request) {
        try {
            //verificar la existencia del cliente
            RequestDetalleCliente requestDetalleCliente = new RequestDetalleCliente();
            requestDetalleCliente.setIdCliente(request.getIdCliente());

            ResponseDetalleCliente detalleBDCliente= clienteService.DetalleCliente(requestDetalleCliente);

            if (!detalleBDCliente.isExito() || detalleBDCliente.getCliente() == null) {
                throw new ResourceNotFoundException("El Id del cliente no existe.");
            }
            //verificar el id tipo cliente
            RequestDetalleTipoClientes requestDetalleTipoCliente = new RequestDetalleTipoClientes();
            requestDetalleTipoCliente.setIdTipoCliente(request.getIdTipoCliente());

            ResponseDetalleTipoClientes detalleBDTipoCliente= tipoClientesService.DetalleTipoClientes(requestDetalleTipoCliente);

            if (!detalleBDTipoCliente.isExito() || detalleBDTipoCliente.getTipoClientes() == null) {
                throw new ResourceNotFoundException("El Id del tipo cliente no existe.");
            }

            //obetener el id del tipoDocumento
            RequestDetalleTipoDocumento requestDetalleDoc = new RequestDetalleTipoDocumento();
            requestDetalleDoc.setIdTipoDocumentos(request.getIdTipoDocumento());

            ResponseDetalleTipoDocumento detalleTipoDocumentoBD= tipoDocumentoService.DetalleTipoDocumento(requestDetalleDoc);

            if (!detalleTipoDocumentoBD.isExito() || detalleTipoDocumentoBD.getTipoDocumento() == null) {
                throw new ResourceNotFoundException("El Id del tipo documento no existe.");
            }
            //verificaciones del tipo documento longitud - contenido
            TipoDocumentoValidator.validarDocumento(request.getNumeroDocumento(), detalleTipoDocumentoBD);

            //validamos los nombres respecto al tipo documento
            switch (detalleTipoDocumentoBD.getTipoDocumento().getCodigoSunat()) {

                case "01" -> ClienteValidator.validarDatosDni(request);

                case "06" -> ClienteValidator.validarDatosRuc(request);

            }
            //verificar cambios
            if (!verificarCambiosCliente.verificarCambios(detalleBDCliente.getCliente(), request)) {
                throw new ResourceNotFoundException("No se detectaron cambios para actualizar.");
            }

            //setear
            ResponseEditarAllCliente response = clienteService.EditarAllCliente(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllCliente response = new ResponseEditarAllCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar el estado del cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllCliente response = new ResponseEditarAllCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
