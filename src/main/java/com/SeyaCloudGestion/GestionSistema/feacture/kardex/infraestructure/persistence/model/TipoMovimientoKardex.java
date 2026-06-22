package com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.model.TipoMovimientoModel;

public enum TipoMovimientoKardex {
    INGRESO_COMPRA,
    INGRESO_AJUSTE,
    EGRESO_VENTA,
    EGRESO_AJUSTE;

    public boolean esIngreso() {
        return this.name().startsWith("INGRESO_");
    }

    public boolean esEgreso() {
        return this.name().startsWith("EGRESO_");
    }

    public static TipoMovimientoKardex desdeModelo(TipoMovimientoModel model) {
        if (model == null) {
            throw new IllegalArgumentException("El model no puede ser nulo.");
        }

        return (model.getEsEntrada() == 1) ? INGRESO_AJUSTE : EGRESO_AJUSTE;
    }
}
