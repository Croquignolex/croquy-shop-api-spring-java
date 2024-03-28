package com.shop.croquy.v1.configurations;

import com.shop.croquy.v1.dto.GenericResponse;
import com.shop.croquy.v1.helpers.GeneralHelper;
import com.shop.croquy.v1.services.UserService;
import com.shop.croquy.v1.services.JwtService;

import io.jsonwebtoken.ExpiredJwtException;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtAccessToken;
        final String username;

        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtAccessToken = authHeader.substring(7);
            username = jwtService.extractUsernameFormToken(jwtAccessToken);

            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

                if (jwtService.isTokenValid(jwtAccessToken, userDetails, false)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }
        } catch (ExpiredJwtException ex) {
            GenericResponse rep = new GenericResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());

            log.error("################################# [" + rep.getStatusCode() + "] ===> " + rep.getMessage());

            response.setStatus(rep.getStatusCode());
            response.getWriter().write(GeneralHelper.convertObjectToJson(rep));
            response.getWriter().flush();

            return;
        }

        filterChain.doFilter(request, response);
    }
}
