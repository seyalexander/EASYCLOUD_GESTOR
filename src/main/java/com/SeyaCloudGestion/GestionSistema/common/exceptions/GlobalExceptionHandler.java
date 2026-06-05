package com.SeyaCloudGestion.GestionSistema.common.exceptions;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseGeneral> handleSQLException(SQLException ex) {
        String mensaje = "Error en la base de datos: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseGeneral(false, mensaje, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseGeneral> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );
        String primerError = errores.values().stream().findFirst().orElse("Error de validación");
        ResponseGeneral response = new ResponseGeneral(false, primerError, errores);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseGeneral> handleGeneralException(Exception ex) {
        String mensaje = "Error interno del servidor: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseGeneral(false, mensaje, null));
    }
    // my handlers
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseGeneral> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ResponseGeneral(false, ex.getMessage(), null));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseGeneral> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseGeneral(false, ex.getMessage(), null));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseGeneral> handleBindException(BindException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        String primerError = errores.values()
                .stream()
                .findFirst()
                .orElse("Datos inválidos");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseGeneral(false, primerError, errores));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseGeneral> handleConstraintViolation(ConstraintViolationException ex) {
        String mensaje = "Datos inválidos";

        if (!ex.getConstraintViolations().isEmpty()) {
            mensaje = ex.getConstraintViolations()
                    .iterator()
                    .next()
                    .getMessage();
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseGeneral(false, mensaje, null));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseGeneral> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String mensaje = "El tipo de dato enviado no es válido";

        if (ex.getName() != null) {
            mensaje = "El valor enviado para '" + ex.getName() + "' no tiene el tipo correcto";
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseGeneral(false, mensaje, null));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseGeneral> handleMissingParameter(MissingServletRequestParameterException ex) {
        String mensaje = "Falta el parámetro obligatorio: " + ex.getParameterName();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseGeneral(false, mensaje, null));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ResponseGeneral> handleMissingPathVariable(MissingPathVariableException ex) {
        String mensaje = "Falta la variable obligatoria en la ruta: " + ex.getVariableName();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseGeneral(false, mensaje, null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseGeneral> handleJsonError(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseGeneral(false, "El formato del JSON es inválido", null));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseGeneral> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ResponseGeneral(false, "Método HTTP no permitido para esta ruta", null));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseGeneral> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new ResponseGeneral(false, "Tipo de contenido no soportado", null));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ResponseGeneral> handleDuplicateKey(DuplicateKeyException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ResponseGeneral(false, "Ya existe un registro con esos datos", null));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseGeneral> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ResponseGeneral(false, "No se puede realizar la operación por una restricción de base de datos", null));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseGeneral> handleDataAccessException(DataAccessException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseGeneral(false, "Error al ejecutar la operación en la base de datos", null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseGeneral> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseGeneral(false, ex.getMessage(), null));
    }

}
