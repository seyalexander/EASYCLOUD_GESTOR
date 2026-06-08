package com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.KardexModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaKardex extends ResponseGeneral implements Serializable {

    private List<KardexModel> kardexs;
}