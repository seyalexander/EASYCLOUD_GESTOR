package com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model;

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
}
