package com.demo.hotelbackend.secure;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(
        HttpServletRequest req,
        HttpServletResponse res,
        AuthenticationException e
    ) throws IOException, ServletException {
        logger.error(e.getMessage());
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "no autorizado");
    }
}
