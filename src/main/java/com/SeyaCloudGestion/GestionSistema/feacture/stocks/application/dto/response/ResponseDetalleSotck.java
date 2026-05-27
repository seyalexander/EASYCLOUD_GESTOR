package com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.model.SotckModel;
import lombok.Data;

@Data
public class ResponseDetalleSotck extends ResponseGeneral {

    private SotckModel sotck;
}