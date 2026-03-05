package com.devnotfound.talenthub.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.devnotfound.talenthub.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // rotas públicas
        if (path.equals("/auth/gerarToken")
        		 || (path.equals("/api/clientes") && "POST".equalsIgnoreCase(method))
                 || (path.equals("/api/clientes/resetpass") && "PATCH".equalsIgnoreCase(method))
                 || path.startsWith("/swagger-ui")
                 || path.startsWith("/v2/api-docs")
                 || path.startsWith("/v3/api-docs")
                 || path.startsWith("/swagger-resources")
                 || path.startsWith("/webjars")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            String token = authorizationHeader.substring(7);

            try {
                DecodedJWT jwt = tokenService.verificarToken(token); //verificação do token, se existe no banco, verifica assinatura, verifica se expirou

                String email = jwt.getSubject(); //guarda o JSON chave = sub = email e valor = cliente@email.com
                String role = jwt.getClaim("role").asString(); //pega o claim do role, se é role = cliente ou role = usuario
                
                if (role == null || role.isBlank()) { //verifica se o role existe e se o token não tiver role, é considerado inválido
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token sem role");
                    return;
                }

                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role)); //o springSecurity exige prefixo nas permissões

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(email, null, authorities); //o spring cria um objeto representando usuário logado no sistema

                SecurityContextHolder.getContext().setAuthentication(auth); //o springSecurity passa a saber que o usuário ou cliente está autenticado

            } catch (Exception e) { //tratamento de erro
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //401 status
                response.getWriter().write("Token inválido!"); 
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}