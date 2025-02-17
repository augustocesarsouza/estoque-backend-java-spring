package com.estoque.backend.data.authentication;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.estoque.backend.application.services.ResultService;
import com.estoque.backend.data.utilToken.ForbiddenError;
import com.estoque.backend.domain.authentication.ITokenGenerator;
import com.estoque.backend.domain.repositories.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class FilterToken extends OncePerRequestFilter {
    private final ITokenGenerator tokenGenerator;
    private final IUserRepository userRepository;

    @Autowired
    public FilterToken(ITokenGenerator tokenGenerator, IUserRepository userRepository) {
        this.tokenGenerator = tokenGenerator;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestMatcher permitAllMatcher = new AntPathRequestMatcher("/v1/public/**");
        if(permitAllMatcher.matches(request)){
            filterChain.doFilter(request, response);
            return;
        }

        var authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null){
            String token = authorizationHeader.replace("Bearer ", "");

            try {
                Claim guidId = tokenGenerator.getClaimUserId(token);
                UUID claimId = UUID.fromString(guidId.asString());
                UUID headerValueUID = UUID.fromString(request.getHeader("uid"));

                if(claimId.equals(headerValueUID)){
                    var user = userRepository.GetUserByIdInfoEmailPasswordHash(claimId);
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else {
                    throwError(response);
                    return;
                }

            }catch (NullPointerException ex){//Info Header nao informado e acessar claim são mesmo erro
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                var user = new ForbiddenError("acesso_negado", "error some information needs to be checked or Token is invalid or Header is missing information");
                var objectMapper = new ObjectMapper();
                var jsonString = objectMapper.writeValueAsString(ResultService.Fail(user));

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonString);
                return;
            }catch (TokenExpiredException e){
                throwError(response);
                return;
            }catch (SignatureVerificationException exSig){
                String message = exSig.getMessage();
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                var user = new ForbiddenError("acesso_negado", message);
                var objectMapper = new ObjectMapper();
                var jsonString = objectMapper.writeValueAsString(ResultService.Fail(user));

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonString);
                return;
            }catch (Exception ex){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                var user = new ForbiddenError("acesso_negado", "Usuario não contem as devidas informações necessarias para acesso");
                var objectMapper = new ObjectMapper();
                var jsonString = objectMapper.writeValueAsString(ResultService.Fail(user));

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonString);
                return;
            }
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            var user = new ForbiddenError("acesso_negado", "User must provide a token in the request");
            var objectMapper = new ObjectMapper();
            var jsonString = objectMapper.writeValueAsString(ResultService.Fail(user));

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonString);
            return;
        }

        filterChain.doFilter(request, response);
    }

    protected void throwError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        var user = new ForbiddenError("acesso_negado", "Usuario não contem as devidas informações necessarias para acesso");
        var objectMapper = new ObjectMapper();
        var jsonString = objectMapper.writeValueAsString(ResultService.Fail(user));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
    }
}
