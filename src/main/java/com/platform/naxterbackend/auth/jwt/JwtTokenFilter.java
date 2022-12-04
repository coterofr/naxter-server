package com.platform.naxterbackend.auth.jwt;

import com.platform.naxterbackend.auth.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = this.getJwtToken(request);
            if(jwtToken != null && this.jwtProvider.validateJwtToken(jwtToken)) {
                String name = this.jwtProvider.getContentFromJwtToken(jwtToken);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(name);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch(Exception e) {
            logger.error("Exception on the JWT token filter per request: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        return header != null && header.startsWith("Bearer") ? header.replace("Bearer ", "") : null;
    }
}
