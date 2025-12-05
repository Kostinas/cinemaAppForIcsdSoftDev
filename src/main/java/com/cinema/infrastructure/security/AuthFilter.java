package com.cinema.infrastructure.security;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class AuthFilter implements Filter{

    private final TokenValidator tokenValidator;

    public AuthFilter(TokenValidator tokenValidator){ this.tokenValidator = tokenValidator; }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest http = (HttpServletRequest) request;
        String header = http.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer")) {
            String token = header.substring(7);
            try {
                TokenValidator.TokenData data = tokenValidator.validate(token);

                http.setAttribute("userId", data.userId());
                http.setAttribute("role", data.role());



            }catch (Exception e){
                throw new IllegalArgumentException("invalid token");
            }
        }

        chain.doFilter(request, response);
    }
}
