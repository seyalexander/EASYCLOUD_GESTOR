package com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.interfaces.*;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.repository.crud.*;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

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
    @Cacheable(value = "clientes_detalle", key = "#request.idCliente")
    public ResponseDetalleCliente DetalleCliente(RequestDetalleCliente request) {
        return clienteDetalleRepository.DetalleCliente(request);
    }

    @Override
    @CacheEvict(value = {"clientes_lista", "clientes_detalle"}, allEntries = true)
    public ResponseEditarAllCliente EditarAllCliente(RequestEditarAllCliente request) {
        return clienteEdicionRepository.EditarAllCliente(request);
    }

    @Override
    @CacheEvict(value = {"clientes_lista", "clientes_detalle"}, allEntries = true)
    public ResponseEditarEstadoCliente EditarEstadoCliente(RequestEditarEstadoCliente request, int estado) {
        return clienteEdicionRepository.EditarEstadoCliente(request, estado);
    }

    @Override
    @Cacheable(value = "clientes_lista", key = "#request.estado")
    public ResponseListaCliente ListaCliente(RequestListaCliente request) {
        return clienteListadoRepository.ListaCliente(request);
    }

    @Override
    @CacheEvict(value = {"clientes_lista", "clientes_detalle"}, allEntries = true)
    public ResponseRegistroCliente RegistroCliente(RequestRegistroCliente request) {
        return clienteRegistroRepository.RegistroCliente(request);
    }
}
