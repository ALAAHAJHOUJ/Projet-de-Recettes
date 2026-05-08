package com.example.demo.securite;

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
import java.util.Collections;
import java.util.List;


@Component
public class jwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        // ÉTAPE 1 : Chercher le badge (header Authorization)
        String header = request.getHeader("Authorization");
        System.out.println("=== JWT FILTER === Header: " + header);
        // ÉTAPE 2 : Vérifier que le badge existe et commence par "Bearer "
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Enlever "Bearer "
            System.out.println("=== JWT FILTER === Token valide: " +
                    jwtUtil.isTokenValid(token));
            // ÉTAPE 3 : Vérifier que le badge est valide
            if (jwtUtil.isTokenValid(token)) {
                String username = jwtUtil.extractUsername(token);

                String role = jwtUtil.extractRole(token);

                // ÉTAPE 4 : Créer le badge visiteur et le donner à Spring Security
                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username, null,authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("=== JWT FILTER === Auth ajoutée au contexte");
            }
        }

        chain.doFilter(request, response);
    }
}
