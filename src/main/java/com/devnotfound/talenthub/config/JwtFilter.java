package com.devnotfound.talenthub.config;

import com.devnotfound.talenthub.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (path.equals("/auth/gerarToken")
        || path.startsWith("/swagger-ui")
        || path.startsWith("/v2/api-docs")
        || path.startsWith("/v3/api-docs")
        || path.equals("/swagger-resources")
        || path.equals("webjars")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // pegar o que vier depois da palavra Bearer
            // substring = recortar a string, no caso do 7 em diante
            String token = authorizationHeader.substring(7);

            try {
                var jwtValidador = tokenService.verificarToken(token);

                String email = jwtValidador.getSubject();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token invalido");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
