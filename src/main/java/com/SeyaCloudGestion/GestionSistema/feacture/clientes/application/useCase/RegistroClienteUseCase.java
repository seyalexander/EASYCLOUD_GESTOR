package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestRegistroCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseRegistroCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.services.ClienteService;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.validations.ClienteValidator;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestDetalleTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseDetalleTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.services.TipoClientesService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.services.TipoDocumentoService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.validations.dependencias.TipoDocumentoValidator;
import org.springframework.stereotype.Component;

import static com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.validations.ClienteValidator.validarDatosRuc;

@Component
public class RegistroClienteUseCase {
    private final ClienteService clienteService;
    private final TipoDocumentoService tipoDocumentoService;
    private final TipoClientesService tipoClientesService;

    public RegistroClienteUseCase(ClienteService clienteService, TipoDocumentoService tipoDocumentoService, TipoClientesService tipoClientesService) {
        this.clienteService = clienteService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.tipoClientesService = tipoClientesService;
    }
    public ResponseRegistroCliente RegistroCliente( RequestRegistroCliente request) {
        try {
            //verificar el id tipo cliente
            RequestDetalleTipoClientes requestDetalleTipoCliente = new RequestDetalleTipoClientes();
            requestDetalleTipoCliente.setIdTipoCliente(request.getIdTipoCliente());

            ResponseDetalleTipoClientes detalleBDTipoCliente= tipoClientesService.DetalleTipoClientes(requestDetalleTipoCliente);

            if (!detalleBDTipoCliente.isExito() || detalleBDTipoCliente.getTipoClientes() == null) {
                throw new ResourceNotFoundException("El Id dedl tipo cliente no existe.");
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

            //seteamos
            ResponseRegistroCliente response = clienteService.RegistroCliente(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroCliente response = new ResponseRegistroCliente();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar el cliente: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroCliente response = new ResponseRegistroCliente();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
