package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.services;

import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.useCase.ListaUsuarioUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.interfaces.IUsuarioDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.interfaces.IUsuarioEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.interfaces.IUsuarioListado;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.interfaces.IUsuarioRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.repository.crud.UsuarioDetalleRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.repository.crud.UsuarioEdicionRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.repository.crud.UsuarioListadoRepository;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.repository.crud.UsuarioRegistroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsuarioService implements IUsuarioRegistro, IUsuarioEdicion, IUsuarioListado, IUsuarioDetalle {

    private final UsuarioEdicionRepository usuarioEdicionRepository;
    private final UsuarioListadoRepository usuarioListadoRepository;
    private final UsuarioRegistroRepository usuarioRegistroRepository;
    private final UsuarioDetalleRepository usuarioDetalleRepository;

    public UsuarioService(
            UsuarioEdicionRepository usuarioEdicionRepository,
            UsuarioListadoRepository usuarioListadoRepository,
            UsuarioRegistroRepository usuarioRegistroRepository,
            UsuarioDetalleRepository usuarioDetalleRepository
    ){
        this.usuarioEdicionRepository = usuarioEdicionRepository;
        this.usuarioListadoRepository = usuarioListadoRepository;
        this.usuarioRegistroRepository = usuarioRegistroRepository;
        this.usuarioDetalleRepository = usuarioDetalleRepository;
    }


    @Override
    @Cacheable(value = "usuarios_detalle", key = "#request.idUsuario")
    public ResponseDetalleUsuario DetalleUsuario(RequestDetalleUsuario request) {
        return usuarioDetalleRepository.DetalleUsuario(request);
    }

    @Override
    @CacheEvict(value = {"usuarios_lista", "usuarios_detalle"}, allEntries = true)
    public ResponseEditarAllUsuario EditarUsuario(RequestEditarAllUsuario request) {
        return usuarioEdicionRepository.EditarUsuario(request);
    }

    @Override
    @CacheEvict(value = {"usuarios_lista", "usuarios_detalle"}, allEntries = true)
    public ResponseEditarEstadoUsuario EditarEstadoUsuario(RequestEditarEstadoUsuario request, int estado) {
        return usuarioEdicionRepository.EditarEstadoUsuario(request, estado);
    }

    @Override
    @Cacheable(value = "usuarios_lista", key = "#request.estado")
    public ResponseListaUsuario ListaUsuarios(RequestListaUsuario request) {
        return usuarioListadoRepository.ListaUsuarios(request);
    }

    @Override
    @CacheEvict(value = {"usuarios_lista", "usuarios_detalle"}, allEntries = true)
    public ResponseRegistroUsuario registrarUsuario(RequestRegistroUsuario request) {
        return usuarioRegistroRepository.registrarUsuario(request);
    }
}
