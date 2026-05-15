package com.SeyaCloudGestion.GestionSistema.security;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireJwt requireJwt = handlerMethod.getMethodAnnotation(RequireJwt.class);

        if (requireJwt != null) {
            Boolean isAuthenticated = (Boolean) request.getAttribute("isAuthenticated");

            if (isAuthenticated == null || !isAuthenticated) {
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");

                ResponseGeneral errorResponse = new ResponseGeneral();
                errorResponse.setExito(false);
                errorResponse.setMessage("Token JWT no válido o expirado. Por favor, inicie sesión nuevamente.");

                response.getWriter().write(convertObjectToJson(errorResponse));
                return false;
            }
        }

        return true;
    }

    private String convertObjectToJson(Object object) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            log.error("Error al convertir objeto a JSON", e);
            return "{\"exito\": false, \"mensaje\": \"Error al procesar la solicitud\"}";
        }
    }
}
