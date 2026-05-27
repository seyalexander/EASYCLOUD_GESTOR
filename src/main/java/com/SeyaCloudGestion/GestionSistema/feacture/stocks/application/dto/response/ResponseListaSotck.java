package com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.model.SotckModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaSotck extends ResponseGeneral implements Serializable {

    private List<SotckModel> sotcks;
}