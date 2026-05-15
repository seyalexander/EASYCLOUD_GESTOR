package com.SeyaCloudGestion.GestionSistema.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            String authHeader = request.getHeader("Authorization");

            if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
                String userId = jwtTokenProvider.getUserIdFromToken(jwt);
                String nombreUsuario = jwtTokenProvider.getNombreUsuarioFromToken(jwt);
                String idUsuario = jwtTokenProvider.getIdUsuarioFromToken(jwt);
                String idEmpresa = jwtTokenProvider.getIdEmpresaFromToken(jwt);

                request.setAttribute("userId", userId);
                request.setAttribute("nombreUsuario", nombreUsuario);
                request.setAttribute("idUsuario", idUsuario);
                request.setAttribute("idEmpresa", idEmpresa);
                request.setAttribute("isAuthenticated", true);


                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        nombreUsuario,
                        null,
                        new ArrayList<>()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                request.setAttribute("isAuthenticated", false);
                SecurityContextHolder.clearContext();
//                if (jwt == null) {
//                    log.warn(" [JwtAuthenticationFilter] JWT no encontrado en request");
//                } else {
//                    log.warn("[JwtAuthenticationFilter] JWT inválido o expirado");
//                }
            }
        } catch (Exception e) {
            log.error("[JwtAuthenticationFilter] Error: {}", e.getMessage(), e);
            request.setAttribute("isAuthenticated", false);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
