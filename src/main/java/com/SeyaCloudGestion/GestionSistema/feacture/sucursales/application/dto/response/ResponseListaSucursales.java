package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.model.SucursalesModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaSucursales extends ResponseGeneral implements Serializable {

    private List<SucursalesModel> sucursales;
}