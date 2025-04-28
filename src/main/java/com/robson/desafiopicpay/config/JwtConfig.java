package com.robson.desafiopicpay.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.robson.desafiopicpay.entities.UserDetailsImpl;
import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.services.TokenService;
import com.robson.desafiopicpay.services.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtConfig extends OncePerRequestFilter{

    private TokenService tokenService;
    
    private UsuarioService usuarioService;
    
    public JwtConfig(TokenService tokenService, UsuarioService usuarioService) {
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer")) {
                String token = header.substring(7);
                try {
                    String email = tokenService.validateToken(token);
                    Usuario usuario = usuarioService.findByEmail(email);
                    UserDetailsImpl uDetails = new UserDetailsImpl(usuario);
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(uDetails, null, uDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
            filterChain.doFilter(request, response);
    }
    
}
