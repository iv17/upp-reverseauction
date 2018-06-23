package com.upp.reverseauction.security;

import com.upp.reverseauction.model.Authorization;
import com.upp.reverseauction.model.PrivateUser;
import com.upp.reverseauction.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AuthenticationFilter extends GenericFilterBean {

    private final AuthorizationService authorizationService;

    public static final String AUTHORIZATION = "X-AUTH-TOKEN";

    @Autowired
    public AuthenticationFilter(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final String token = httpServletRequest.getHeader(AUTHORIZATION);
        if (token != null && !token.isEmpty()) {
            authenticate(token);
        }

        chain.doFilter(request, response);
    }

    private void authenticate(String token) {
        Authorization authorization = authorizationService.findByToken(token);
        final PrivateUser user = authorization.getUser();
//            final List<SimpleGrantedAuthority> authorities = user.getPermissions().stream()
//                    .map(permission -> new SimpleGrantedAuthority(permission.name()))
//                    .collect(Collectors.toList());
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, "");
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}