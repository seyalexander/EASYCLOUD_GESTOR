package com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.interfaces.IArticulosRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseRegistroFamilia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ArticuloListadoRepository {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


}
