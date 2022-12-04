package com.platform.naxterbackend.auth.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.platform.naxterbackend.auth.model.JwtToken;
import com.platform.naxterbackend.auth.model.UserDetailsImpl;
import com.platform.naxterbackend.user.model.Role;
import com.platform.naxterbackend.user.model.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;


    public String getContentFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody().getSubject();
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl)authentication.getPrincipal();
        List<String> roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return Jwts.builder()
                   .setSubject(principal.getUsername())
                   .claim("roles", roles)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(new Date().getTime() +  expiration))
                   .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                   .compact();
    }

    public Boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);

            return Boolean.TRUE;
        } catch(MalformedJwtException e) {
            logger.error("Token malformed");
        } catch(UnsupportedJwtException e) {
            logger.error("Token unsupported");
        } catch(ExpiredJwtException e) {
            logger.error("Token expired");
        } catch(IllegalArgumentException e) {
            logger.error("Token with illegal argument");
        } catch(SignatureException e) {
            logger.error("Token signature exception");
        }

        return Boolean.FALSE;
    }

    public String refreshJwtToken(JwtToken jwtToken) throws ParseException {
        JWT jwtParsed = JWTParser.parse(jwtToken.getToken());
        ReadOnlyJWTClaimsSet jwtClaims = jwtParsed.getJWTClaimsSet();
        String name = jwtClaims.getSubjectClaim();
        List<String> roles = (List<String>)jwtClaims.getCustomClaim("roles");

        return Jwts.builder()
                   .setSubject(name)
                   .claim("roles", roles)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(new Date().getTime() + expiration))
                   .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                   .compact();
    }

    public String refreshJwtToken(User user) {
        List<String> userRoles = user.getRoles().stream().map(Role::getType).collect(Collectors.toList());
        List<String> roles = userRoles.stream().map(role -> String.format("ROLE_%s", role)).collect(Collectors.toList());

        return Jwts.builder()
                   .setSubject(user.getName())
                   .claim("roles", roles)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(new Date().getTime() + expiration))
                   .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                   .compact();
    }
}
