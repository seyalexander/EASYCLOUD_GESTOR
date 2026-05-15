package com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestEditarAllEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestEditarEstadoEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseEditarAllEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseEditarEstadoEmpresa;

public interface IEmpresaEdicion {
    ResponseEditarEstadoEmpresa editarEstadoEmpresa(RequestEditarEstadoEmpresa request, int estado, long userAutenticado);
    ResponseEditarAllEmpresa  editarAllEmpresa(RequestEditarAllEmpresa request, long userAutenticado);
}
