package edu.ntnu.fullstack.prosjekt.quizzer.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Log
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    public static final String USER = "USER";
    public static final String ROLE_USER = "ROLE_ " + USER;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        final String username = validateTokenAndGetUserId(token);
        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singleton(new SimpleGrantedAuthority(ROLE_USER)));
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    public String validateTokenAndGetUserId(final String token) {
        try {
            final Algorithm hmac512 = Algorithm.HMAC512("");
            final JWTVerifier verifier = JWT.require(hmac512).build();
            return verifier.verify(token).getSubject();
        } catch (final JWTVerificationException verificationException) {
            logger.info("token is invald: {} " + verificationException.getMessage());
            return null;
        }
    }
}
