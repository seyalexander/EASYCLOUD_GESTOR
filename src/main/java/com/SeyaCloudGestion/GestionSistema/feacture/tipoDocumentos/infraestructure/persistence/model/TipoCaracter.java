package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.model;

public enum TipoCaracter {
    NUMERICO(1, "El documento debe contener solo números.") {
        @Override
        public boolean validar(String valor) {
            return valor.matches("\\d+");
        }
    },

    ALFABETICO(2, "El documento debe contener solo letras.") {
        @Override
        public boolean validar(String valor) {
            return valor.matches("^[\\p{L} ]+$");
        }
    },

    ALFANUMERICO(3, "El documento puede contener solo números y letras.") {
        @Override
        public boolean validar(String valor) {
            return valor.matches("^[a-zA-Z0-9]+$");
        }
    };

    private final int codigo;
    private final String mensajeError;

    TipoCaracter(int codigo, String mensajeError) {
        this.codigo = codigo;
        this.mensajeError = mensajeError;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public static TipoCaracter fromCodigo(int codigo) {
        for (TipoCaracter tipo : values()) {
            if (tipo.codigo == codigo) {
                return tipo;
            }
        }

        throw new IllegalArgumentException(
                "TipoCaracter inválido: " + codigo
        );
    }

    public abstract boolean validar(String valor);
}
