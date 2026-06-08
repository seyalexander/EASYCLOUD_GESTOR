package com.SeyaCloudGestion.GestionSistema.common.sqlParametersDate;

import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

public class SqlParameterDate {

    private SqlParameterDate() {
    }

    //envia null ala base
    public static  void setLocalDateTimeOrNull(
            CallableStatement pstmt,
            int index,
            LocalDateTime value
    ) throws SQLException {
        if (value != null) {
            pstmt.setTimestamp(index, Timestamp.valueOf(value));
        } else {
            pstmt.setNull(index, Types.TIMESTAMP);
        }
    }

    //jamas envia null
    public static void setLocalDateTime(
            CallableStatement pstmt,
            int index,
            LocalDateTime value
    ) throws SQLException {
        pstmt.setTimestamp(index, Timestamp.valueOf(value));
    }
}
