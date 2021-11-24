package com.demo.hotelbackend.secure;

import com.demo.hotelbackend.Model.userprincipal;
import io.jsonwebtoken.*;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        userprincipal usuarioPrincipal = (userprincipal) authentication.getPrincipal();
        return Jwts
            .builder()
            .setSubject(usuarioPrincipal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + expiration * 1000))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public String getNombreUsuarioFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("token mal formado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token no soportado.");
        } catch (ExpiredJwtException e) {
            logger.error("Token expirado.");
        } catch (IllegalArgumentException e) {
            logger.error("Token vacio.");
        } catch (SignatureException e) {
            logger.error("Falla en la firma.");
        }
        return false;
    }
}
