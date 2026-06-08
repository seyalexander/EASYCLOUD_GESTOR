package com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.repository.crud.*;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements IClienteDetalle, IClienteEdicion, IClienteListado, IClienteRegistro {
    private final ClienteDetalleRepository clienteDetalleRepository;
    private final ClienteEdicionRepository clienteEdicionRepository;
    private final ClienteListadoRepository clienteListadoRepository;
    private final ClienteRegistroRepository clienteRegistroRepository;

    public ClienteService(ClienteDetalleRepository clienteDetalleRepository, ClienteEdicionRepository clienteEdicionRepository, ClienteListadoRepository clienteListadoRepository, ClienteRegistroRepository clienteRegistroRepository) {
        this.clienteDetalleRepository = clienteDetalleRepository;
        this.clienteEdicionRepository = clienteEdicionRepository;
        this.clienteListadoRepository = clienteListadoRepository;
        this.clienteRegistroRepository = clienteRegistroRepository;
    }

    @Override
    public ResponseDetalleCliente DetalleCliente(RequestDetalleCliente request) {
        return clienteDetalleRepository.DetalleCliente(request);
    }

    @Override
    public ResponseEditarAllCliente EditarAllCliente(RequestEditarAllCliente request) {
        return clienteEdicionRepository.EditarAllCliente(request);
    }

    @Override
    public ResponseEditarEstadoCliente EditarEstadoCliente(RequestEditarEstadoCliente request, int estado) {
        return clienteEdicionRepository.EditarEstadoCliente(request, estado);
    }

    @Override
    public ResponseListaCliente ListaCliente(RequestListaCliente request) {
        return clienteListadoRepository.ListaCliente(request);
    }

    @Override
    public ResponseRegistroCliente RegistroCliente(RequestRegistroCliente request) {
        return clienteRegistroRepository.RegistroCliente(request);
    }
}
